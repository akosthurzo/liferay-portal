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
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

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
		"model.class.name=com.liferay.portal.kernel.model.EmailAddress"
	},
	service = StagedModelRepository.class
)
public class EmailAddressStagedModelRepository
	implements StagedModelRepository<EmailAddress> {

	@Override
	public EmailAddress addStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws PortalException {

		long userId = portletDataContext.getUserId(emailAddress.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			emailAddress);

		serviceContext.setUuid(emailAddress.getUuid());

		return _emailAddressLocalService.addEmailAddress(
			userId, emailAddress.getClassName(), emailAddress.getClassPK(),
			emailAddress.getAddress(), emailAddress.getTypeId(),
			emailAddress.isPrimary(), serviceContext);
	}

	@Override
	public void deleteStagedModel(EmailAddress emailAddress)
		throws PortalException {

		_emailAddressLocalService.deleteEmailAddress(emailAddress);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		EmailAddress emailAddress =
			_emailAddressLocalService.fetchEmailAddressByUuidAndCompanyId(
				uuid, group.getCompanyId());

		deleteStagedModel(emailAddress);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_emailAddressLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<EmailAddress>)
				emailAddress -> _emailAddressLocalService.deleteEmailAddress(
					emailAddress));

		actionableDynamicQuery.performActions();
	}

	@Override
	public EmailAddress fetchMissingReference(String uuid, long groupId) {
		return (EmailAddress)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public EmailAddress fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<EmailAddress> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<EmailAddress> emailAddresses = new ArrayList<>();

		emailAddresses.add(
			_emailAddressLocalService.fetchEmailAddressByUuidAndCompanyId(
				uuid, companyId));

		return emailAddresses;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _emailAddressLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public EmailAddress saveStagedModel(EmailAddress emailAddress)
		throws PortalException {

		return _emailAddressLocalService.updateEmailAddress(emailAddress);
	}

	@Override
	public EmailAddress updateStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws PortalException {

		return _emailAddressLocalService.updateEmailAddress(
			emailAddress.getEmailAddressId(), emailAddress.getAddress(),
			emailAddress.getTypeId(), emailAddress.isPrimary());
	}

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}