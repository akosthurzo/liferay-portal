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

package com.liferay.asset.categories.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.asset.kernel.model.AssetVocabulary"
	},
	service = {StagedModelRepository.class}
)
public class AssetVocabularyStagedModelRepository
	implements StagedModelRepository<AssetVocabulary> {

	@Override
	public AssetVocabulary addStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetVocabulary);

		serviceContext.setUuid(assetVocabulary.getUuid());

		return _assetVocabularyLocalService.addVocabulary(
			userId, portletDataContext.getScopeGroupId(), StringPool.BLANK,
			assetVocabulary.getTitleMap(), assetVocabulary.getDescriptionMap(),
			assetVocabulary.getSettings(), serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetVocabulary vocabulary = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (vocabulary != null) {
			deleteStagedModel(vocabulary);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_assetVocabularyLocalService.deleteVocabularies(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public AssetVocabulary fetchMissingReference(String uuid, long groupId) {
		return (AssetVocabulary)_stagedModelRepositoryHelper.
			fetchMissingReference(uuid, groupId, this);
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetVocabularyLocalService.
			fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetVocabulary> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetVocabularyLocalService.
			getAssetVocabulariesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<AssetVocabulary>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetVocabularyLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());

		AssetVocabulary existingAssetVocabulary =
			fetchStagedModelByUuidAndGroupId(
				assetVocabulary.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingAssetVocabulary == null) ||
			!_stagedModelRepositoryHelper.isStagedModelInTrash(
				existingAssetVocabulary)) {

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			AssetVocabulary.class.getName());

		try {
			if (trashHandler.isRestorable(
					existingAssetVocabulary.getVocabularyId())) {

				trashHandler.restoreTrashEntry(
					userId, existingAssetVocabulary.getVocabularyId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public AssetVocabulary saveStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		return _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);
	}

	@Override
	public AssetVocabulary updateStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetVocabulary);

		serviceContext.setUserId(userId);

		return _assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getVocabularyId(), assetVocabulary.getTitle(),
			assetVocabulary.getTitleMap(), assetVocabulary.getDescriptionMap(),
			assetVocabulary.getSettings(), serviceContext);
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyStagedModelRepository.class);

	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}