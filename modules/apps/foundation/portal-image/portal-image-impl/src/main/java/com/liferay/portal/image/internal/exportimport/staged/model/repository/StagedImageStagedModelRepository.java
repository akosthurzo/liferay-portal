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

package com.liferay.portal.image.internal.exportimport.staged.model.repository;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.image.model.adapter.StagedImage;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.ImageLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.image.model.adapter.StagedImage"
	},
	service = {
		StagedImageStagedModelRepository.class, StagedModelRepository.class
	}
)
public class StagedImageStagedModelRepository
	extends BaseStagedModelRepository<StagedImage> {

	@Override
	public StagedImage addStagedModel(
			PortletDataContext portletDataContext, StagedImage stagedImage)
		throws PortalException {

		Image image = _imageLocalService.updateImage(
			_counterLocalService.increment(), stagedImage.getTextObj(),
			stagedImage.getType(), stagedImage.getHeight(),
			stagedImage.getWidth(), stagedImage.getSize());

		return ModelAdapterUtil.adapt(image, Image.class, StagedImage.class);
	}

	@Override
	public void deleteStagedModel(StagedImage stagedImage)
		throws PortalException {
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public List<StagedModel> fetchChildrenStagedModels(
		PortletDataContext portletDataContext, StagedImage stagedImage) {

		return Collections.emptyList();
	}

	@Override
	public List<StagedModel> fetchDependencyStagedModels(
		PortletDataContext portletDataContext, StagedImage stagedImage) {

		return Collections.emptyList();
	}

	@Override
	public List<StagedImage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	@Override
	public StagedImage saveStagedModel(StagedImage stagedImage)
		throws PortalException {

		Image image = _imageLocalService.updateImage(stagedImage);

		return ModelAdapterUtil.adapt(image, Image.class, StagedImage.class);
	}

	@Override
	public StagedImage updateStagedModel(
			PortletDataContext portletDataContext, StagedImage stagedImage)
		throws PortalException {

		return saveStagedModel(stagedImage);
	}

	@Reference(unbind = "-")
	protected void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	private CounterLocalService _counterLocalService;
	private ImageLocalService _imageLocalService;

}