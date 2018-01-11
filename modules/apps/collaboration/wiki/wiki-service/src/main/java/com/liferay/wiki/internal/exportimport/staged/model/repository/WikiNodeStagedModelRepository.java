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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiNode"},
	service = {StagedModelRepository.class}
)
public class WikiNodeStagedModelRepository
	implements StagedModelRepository<WikiNode> {

	@Override
	public WikiNode addStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
		throws PortalException {

		long userId = portletDataContext.getUserId(wikiNode.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			wikiNode);

		serviceContext.setUuid(wikiNode.getUuid());

		return _wikiNodeLocalService.addNode(
			userId, wikiNode.getName(), wikiNode.getDescription(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiNode wikiNode = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiNode != null) {
			deleteStagedModel(wikiNode);
		}
	}

	@Override
	public void deleteStagedModel(WikiNode wikiNode) throws PortalException {
		_wikiNodeLocalService.deleteNode(wikiNode);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_wikiNodeLocalService.deleteNodes(portletDataContext.getScopeGroupId());
	}

	@Override
	public WikiNode fetchMissingReference(String uuid, long groupId) {
		return (WikiNode)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public WikiNode fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _wikiNodeLocalService.fetchWikiNodeByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<WikiNode> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _wikiNodeLocalService.getWikiNodesByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _wikiNodeLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(wikiNode.getUserUuid());

		WikiNode existingNode = fetchStagedModelByUuidAndGroupId(
			wikiNode.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingNode == null) || !existingNode.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiNode.class.getName());

		try {
			if (trashHandler.isRestorable(existingNode.getNodeId())) {
				trashHandler.restoreTrashEntry(
					userId, existingNode.getNodeId());
			}
		}
		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
		}
	}

	@Override
	public WikiNode saveStagedModel(WikiNode wikiNode) throws PortalException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	@Override
	public WikiNode updateStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
		throws PortalException {

		long userId = portletDataContext.getUserId(wikiNode.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			wikiNode);

		return _wikiNodeLocalService.updateNode(
			wikiNode.getNodeId(), wikiNode.getName(), wikiNode.getDescription(),
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiNodeStagedModelRepository.class);

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	private WikiNodeLocalService _wikiNodeLocalService;

}