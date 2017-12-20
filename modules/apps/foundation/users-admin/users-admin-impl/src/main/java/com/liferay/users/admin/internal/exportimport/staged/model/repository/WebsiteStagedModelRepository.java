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
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.Website"},
	service = StagedModelRepository.class
)
public class WebsiteStagedModelRepository
	implements StagedModelRepository<Website> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private GroupLocalService _groupLocalService;
	@Reference
	private WebsiteLocalService _websiteLocalService;

	@Override
	public Website addStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws PortalException {

		long userId = portletDataContext.getUserId(website.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			website);

		serviceContext.setUuid(website.getUuid());

		return _websiteLocalService.addWebsite(
			userId, website.getClassName(), website.getClassPK(),
			website.getUrl(), website.getTypeId(), website.isPrimary(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Website website = _websiteLocalService.fetchWebsiteByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (website != null) {
			deleteStagedModel(website);
		}
	}

	@Override
	public void deleteStagedModel(Website website) throws PortalException {
		_websiteLocalService.deleteWebsite(website);
	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_websiteLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Website>) website ->
				_websiteLocalService.deleteWebsite(website));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Website fetchMissingReference(String uuid, long groupId) {
		return (Website)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public Website fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Website> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Website> websites = new ArrayList<>();

		websites.add(
			_websiteLocalService.fetchWebsiteByUuidAndCompanyId(
				uuid, companyId));

		return websites;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _websiteLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Website saveStagedModel(Website website) throws PortalException {
		return _websiteLocalService.updateWebsite(website);
	}

	@Override
	public Website updateStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws PortalException {

		return _websiteLocalService.updateWebsite(
			website.getWebsiteId(), website.getUrl(), website.getTypeId(),
			website.isPrimary());
	}
}
