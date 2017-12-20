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

package com.liferay.layout.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.LayoutFriendlyURL"},
	service = StagedModelRepository.class
)
public class LayoutFriendlyURLStagedModelRepository
	implements StagedModelRepository<LayoutFriendlyURL> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Override
	public LayoutFriendlyURL addStagedModel(
		PortletDataContext portletDataContext, LayoutFriendlyURL stagedModel)
		throws PortalException {
		return null;
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData)
		throws PortalException {

	}

	@Override
	public void deleteStagedModel(
		LayoutFriendlyURL stagedModel) throws PortalException {

	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

	}

	@Override
	public LayoutFriendlyURL fetchMissingReference(String uuid, long groupId) {
		return null;
	}

	@Override
	public LayoutFriendlyURL fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {
		return null;
	}

	@Override
	public List<LayoutFriendlyURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {
		return null;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {
		return null;
	}

	@Override
	public void restoreStagedModel(
		PortletDataContext portletDataContext, LayoutFriendlyURL stagedModel)
		throws PortletDataException {

	}

	@Override
	public LayoutFriendlyURL saveStagedModel(
		LayoutFriendlyURL stagedModel) throws PortalException {
		return null;
	}

	@Override
	public LayoutFriendlyURL updateStagedModel(
		PortletDataContext portletDataContext, LayoutFriendlyURL stagedModel)
		throws PortalException {
		return null;
	}
}
