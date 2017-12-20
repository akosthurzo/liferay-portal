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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.model.User"
	},
	service = StagedModelRepository.class
)
public class UserStagedModelRepository implements StagedModelRepository<User> {

	@Override
	public User addStagedModel(
			PortletDataContext portletDataContext, User stagedModel)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userLocalService.fetchUserByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (user != null) {
			deleteStagedModel(user);
		}
	}

	@Override
	public void deleteStagedModel(User user) throws PortalException {
		_userLocalService.deleteUser(user);
	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_userLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<User>) user ->
				_userLocalService.deleteUser(user));

		actionableDynamicQuery.performActions();
	}

	@Override
	public User fetchMissingReference(String uuid, long groupId) {
		return (User)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public User fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<User> users = new ArrayList<>();

		users.add(
			_userLocalService.fetchUserByUuidAndCompanyId(uuid, companyId));

		return users;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _userLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
		PortletDataContext portletDataContext, User stagedModel)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public User saveStagedModel(User user) throws PortalException {
		return _userLocalService.updateUser(user);
	}

	@Override
	public User updateStagedModel(
			PortletDataContext portletDataContext, User stagedModel)
		throws PortalException {

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;
	@Reference
	private UserLocalService _userLocalService;
	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
}
