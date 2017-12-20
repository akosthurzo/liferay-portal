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

package com.liferay.knowledge.base.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.knowledge.base.model.KBFolder"},
	service = StagedModelRepository.class
)
public class KBFolderStagedModelRepository
	implements StagedModelRepository<KBFolder> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Override
	public KBFolder addStagedModel(
		PortletDataContext portletDataContext, KBFolder stagedModel)
		throws PortalException {
		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBFolder kbFolder = _kbFolderLocalService.fetchKBFolderByUuidAndGroupId(
			uuid, groupId);

		if (kbFolder != null) {
			deleteStagedModel(kbFolder);
		}
	}

	@Override
	public void deleteStagedModel(KBFolder kbFolder) throws PortalException {

		_kbFolderLocalService.deleteKBFolder(kbFolder);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_kbFolderLocalService.deleteKBFolders(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public KBFolder fetchMissingReference(String uuid, long groupId) {
		return (KBFolder)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public KBFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbFolderLocalService.fetchKBFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbFolderLocalService.getKBFoldersByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _kbFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBFolder kbFolder)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBFolder saveStagedModel(KBFolder kbFolder) throws PortalException {
		return _kbFolderLocalService.updateKBFolder(kbFolder);
	}

	@Override
	public KBFolder updateStagedModel(
			PortletDataContext portletDataContext, KBFolder kbFolder)
		throws PortalException {

		return null;
	}
}
