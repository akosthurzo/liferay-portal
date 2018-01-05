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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
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
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.asset.kernel.model.AssetCategory"
	},
	service = {StagedModelRepository.class}
)
public class AssetCategoryStagedModelRepository
	implements StagedModelRepository<AssetCategory> {

	@Override
	public AssetCategory addStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetCategory);

		serviceContext.setUuid(assetCategory.getUuid());

		Element categoryElement = portletDataContext.getImportDataElement(
			assetCategory);

		List<Element> propertyElements = categoryElement.elements("property");

		String[] properties = getPropertiesFromElements(propertyElements);

		return _assetCategoryLocalService.addCategory(
			userId, assetCategory.getGroupId(),
			assetCategory.getParentCategoryId(), assetCategory.getTitleMap(),
			assetCategory.getDescriptionMap(), assetCategory.getVocabularyId(),
			properties, serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetCategory assetCategory)
		throws PortalException {

		_assetCategoryLocalService.deleteCategory(assetCategory);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetCategory category = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (category != null) {
			deleteStagedModel(category);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		List<AssetVocabulary> vocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				portletDataContext.getScopeGroupId());

		for (AssetVocabulary vocabulary : vocabularies) {
			_assetCategoryLocalService.deleteVocabularyCategories(
				vocabulary.getVocabularyId());
		}
	}

	@Override
	public AssetCategory fetchMissingReference(String uuid, long groupId) {
		return (AssetCategory)_stagedModelRepositoryHelper.
			fetchMissingReference(uuid, groupId, this);
	}

	@Override
	public AssetCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetCategoryLocalService.fetchAssetCategoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<AssetCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetCategoryLocalService.getAssetCategoriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<AssetCategory>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetCategoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());

		AssetCategory existingAssetCategory = fetchStagedModelByUuidAndGroupId(
			assetCategory.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingAssetCategory == null) ||
			!_stagedModelRepositoryHelper.isStagedModelInTrash(
				existingAssetCategory)) {

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			AssetCategory.class.getName());

		try {
			if (trashHandler.isRestorable(
					existingAssetCategory.getCategoryId())) {

				trashHandler.restoreTrashEntry(
					userId, existingAssetCategory.getCategoryId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public AssetCategory saveStagedModel(AssetCategory assetCategory)
		throws PortalException {

		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	@Override
	public AssetCategory updateStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetCategory);

		Element categoryElement = portletDataContext.getImportDataElement(
			assetCategory);

		List<Element> propertyElements = categoryElement.elements("property");

		String[] properties = getPropertiesFromElements(propertyElements);

		return _assetCategoryLocalService.updateCategory(
			userId, assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(), assetCategory.getTitleMap(),
			assetCategory.getDescriptionMap(), assetCategory.getVocabularyId(),
			properties, serviceContext);
	}

	protected String[] getPropertiesFromElements(
		List<Element> propertyElements) {

		String[] properties = new String[propertyElements.size()];

		for (int i = 0; i < propertyElements.size(); i++) {
			Element propertyElement = propertyElements.get(i);

			String key = propertyElement.attributeValue("key");
			String value = propertyElement.attributeValue("value");

			properties[i] = key.concat(
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR).concat(
					value);
		}

		return properties;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryStagedModelRepository.class);

	private AssetCategoryLocalService _assetCategoryLocalService;
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}