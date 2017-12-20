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

package com.liferay.knowledge.base.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class KBFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<KBFolder> {

	public static final String[] CLASS_NAMES = {KBFolder.class.getName()};

	@Override
	public void deleteStagedModel(KBFolder kbFolder) throws PortalException {
		_stagedModelRepository.deleteStagedModel(kbFolder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<KBFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, KBFolder kbFolder)
		throws Exception {

		KBFolder parentKBFolder = kbFolder.getParentKBFolder();

		if (parentKBFolder != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, kbFolder, parentKBFolder,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element kbFolderElement = portletDataContext.getExportDataElement(
			kbFolder);

		portletDataContext.addClassedModel(
			kbFolderElement, ExportImportPathUtil.getModelPath(kbFolder),
			kbFolder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, KBFolder kbFolder)
		throws Exception {

		long userId = portletDataContext.getUserId(kbFolder.getUserUuid());

		Map<Long, Long> kbFolderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				KBFolder.class);

		long parentFolderId = MapUtil.getLong(
			kbFolderIds, kbFolder.getParentKBFolderId(),
			kbFolder.getParentKBFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			kbFolder);

		KBFolder importedKBFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			KBFolder existingKBFolder = _kbFolderLocalService.fetchKBFolder(
				kbFolder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingKBFolder == null) {
				serviceContext.setUuid(kbFolder.getUuid());

				importedKBFolder = _kbFolderLocalService.addKBFolder(
					userId, portletDataContext.getScopeGroupId(),
					kbFolder.getClassNameId(), parentFolderId,
					kbFolder.getName(), kbFolder.getDescription(),
					serviceContext);
			}
			else {
				importedKBFolder = _kbFolderLocalService.updateKBFolder(
					kbFolder.getClassNameId(), parentFolderId,
					existingKBFolder.getKbFolderId(), kbFolder.getName(),
					kbFolder.getDescription(), serviceContext);
			}
		}
		else {
			importedKBFolder = _kbFolderLocalService.addKBFolder(
				userId, portletDataContext.getScopeGroupId(),
				kbFolder.getClassNameId(), parentFolderId, kbFolder.getName(),
				kbFolder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(kbFolder, importedKBFolder);
	}

	@Reference(unbind = "-")
	protected void setKBFolderLocalService(
		KBFolderLocalService kbFolderLocalService) {

		_kbFolderLocalService = kbFolderLocalService;
	}

	private KBFolderLocalService _kbFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBFolder)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<KBFolder> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Override
	protected StagedModelRepository<KBFolder> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	private StagedModelRepository<KBFolder> _stagedModelRepository;

}