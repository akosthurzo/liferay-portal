/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.internal.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.wiki.internal.exportimport.content.processor.WikiPageExportImportContentProcessor;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;

import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiPage"},
	service = {StagedModelRepository.class}
)
public class WikiPageStagedModelRepository
	implements StagedModelRepository<WikiPage> {

	@Override
	public WikiPage addStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
		throws PortalException {

		long userId = portletDataContext.getUserId(wikiPage.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			wikiPage);

		Element pageElement =
			portletDataContext.getImportDataStagedModelElement(wikiPage);

		WikiPage importedPage = _wikiPageLocalService.addPage(
			userId, wikiPage.getNodeId(), wikiPage.getTitle(),
			wikiPage.getVersion(), wikiPage.getContent(), wikiPage.getSummary(),
			wikiPage.isMinorEdit(), wikiPage.getFormat(), wikiPage.getHead(),
			wikiPage.getParentTitle(), wikiPage.getRedirectTitle(),
			serviceContext);

		WikiPageResource importedPageResource =
			_wikiPageResourceLocalService.getPageResource(
				importedPage.getResourcePrimKey());

		String pageResourceUuid = GetterUtil.getString(
			pageElement.attributeValue("page-resource-uuid"));

		if (Validator.isNotNull(pageResourceUuid)) {
			importedPageResource.setUuid(
				pageElement.attributeValue("page-resource-uuid"));
		}

		return importedPage;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiPage wikiPage = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiPage != null) {
			deleteStagedModel(wikiPage);

			return;
		}

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.fetchWikiPageResourceByUuidAndGroupId(
				uuid, groupId);

		if (pageResource == null) {
			return;
		}

		WikiPage latestPage = _wikiPageLocalService.getLatestPage(
			pageResource.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
			true);

		deleteStagedModel(latestPage);
	}

	@Override
	public void deleteStagedModel(WikiPage wikiPage) throws PortalException {
		_wikiPageLocalService.deletePage(wikiPage);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		List<WikiNode> nodes = _wikiNodeLocalService.getNodes(
			portletDataContext.getScopeGroupId());

		for (WikiNode node : nodes) {
			_wikiPageLocalService.deletePages(node.getNodeId());
		}
	}

	@Override
	public WikiPage fetchMissingReference(String uuid, long groupId) {
		return (WikiPage)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _wikiPageLocalService.fetchWikiPageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<WikiPage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _wikiPageLocalService.getWikiPagesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<WikiPage>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _wikiPageLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(wikiPage.getUserUuid());

		WikiPage existingPage = fetchStagedModelByUuidAndGroupId(
			wikiPage.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingPage == null) || !existingPage.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		try {
			if (trashHandler.isRestorable(existingPage.getResourcePrimKey())) {
				trashHandler.restoreTrashEntry(
					userId, existingPage.getResourcePrimKey());
			}
		}
		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
		}
	}

	@Override
	public WikiPage saveStagedModel(WikiPage wikiPage) throws PortalException {
		return _wikiPageLocalService.updateWikiPage(wikiPage);
	}

	@Override
	public WikiPage updateStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
		throws PortalException {

		long userId = portletDataContext.getUserId(wikiPage.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			wikiPage);

		return _wikiPageLocalService.updatePage(
			userId, wikiPage.getNodeId(), wikiPage.getTitle(), 0.0,
			wikiPage.getContent(), wikiPage.getSummary(),
			wikiPage.isMinorEdit(), wikiPage.getFormat(),
			wikiPage.getParentTitle(), wikiPage.getRedirectTitle(),
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageExportImportContentProcessor(
		WikiPageExportImportContentProcessor
			wikiPageExportImportContentProcessor) {

		_wikiPageExportImportContentProcessor =
			wikiPageExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	private InputStream _getPageAttachmentInputStream(
			String binPath, PortletDataContext portletDataContext,
			FileEntry fileEntry)
		throws Exception {

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				return FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException nsfe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsfe, nsfe);
				}

				return null;
			}
		}

		return portletDataContext.getZipEntryAsInputStream(binPath);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageStagedModelRepository.class);

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiPageExportImportContentProcessor
		_wikiPageExportImportContentProcessor;
	private WikiPageLocalService _wikiPageLocalService;
	private WikiPageResourceLocalService _wikiPageResourceLocalService;

}