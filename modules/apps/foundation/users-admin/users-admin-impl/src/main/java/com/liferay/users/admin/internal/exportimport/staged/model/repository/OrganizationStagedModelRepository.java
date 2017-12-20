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

package com.liferay.users.admin.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.model.Organization"
	},
	service = StagedModelRepository.class
)
public class OrganizationStagedModelRepository
	implements StagedModelRepository<Organization> {

	@Override
	public Organization addStagedModel(
			PortletDataContext portletDataContext, Organization stagedModel)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(Organization organization)
		throws PortalException {

		_organizationLocalService.deleteOrganization(organization);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Organization organization =
			_organizationLocalService.fetchOrganizationByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (organization != null) {
			deleteStagedModel(organization);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_organizationLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Organization>)
				organization -> _organizationLocalService.deleteOrganization(
					organization));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Organization fetchMissingReference(String uuid, long groupId) {
		return (Organization)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public Organization fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<Organization> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Organization> organizations = new ArrayList<>();

		organizations.add(
			_organizationLocalService.fetchOrganizationByUuidAndCompanyId(
				uuid, companyId));

		return organizations;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _organizationLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Organization stagedModel)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Organization saveStagedModel(Organization organization)
		throws PortalException {

		return _organizationLocalService.updateOrganization(organization);
	}

	@Override
	public Organization updateStagedModel(
			PortletDataContext portletDataContext, Organization stagedModel)
		throws PortalException {

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
}