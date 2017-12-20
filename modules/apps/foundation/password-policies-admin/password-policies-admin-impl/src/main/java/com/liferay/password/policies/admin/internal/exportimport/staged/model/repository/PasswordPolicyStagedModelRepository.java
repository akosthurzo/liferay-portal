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

package com.liferay.password.policies.admin.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.PasswordPolicy"},
	service = StagedModelRepository.class
)
public class PasswordPolicyStagedModelRepository
	implements StagedModelRepository<PasswordPolicy> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private PasswordPolicyLocalService _passwordPolicyLocalService;
	@Reference
	private GroupLocalService _groupLocalService;


	@Override
	public PasswordPolicy addStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			passwordPolicy.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			passwordPolicy);

		serviceContext.setUuid(passwordPolicy.getUuid());

		return _passwordPolicyLocalService.addPasswordPolicy(
			userId, passwordPolicy.isDefaultPolicy(),
			passwordPolicy.getName(), passwordPolicy.getDescription(),
			passwordPolicy.getChangeable(),
			passwordPolicy.isChangeRequired(),
			passwordPolicy.getMinAge(), passwordPolicy.getCheckSyntax(),
			passwordPolicy.isAllowDictionaryWords(),
			passwordPolicy.getMinAlphanumeric(),
			passwordPolicy.getMinLength(),
			passwordPolicy.getMinLowerCase(),
			passwordPolicy.getMinNumbers(),
			passwordPolicy.getMinSymbols(),
			passwordPolicy.getMinUpperCase(), passwordPolicy.getRegex(),
			passwordPolicy.isHistory(),
			passwordPolicy.getHistoryCount(),
			passwordPolicy.isExpireable(), passwordPolicy.getMaxAge(),
			passwordPolicy.getWarningTime(),
			passwordPolicy.getGraceLimit(), passwordPolicy.isLockout(),
			passwordPolicy.getMaxFailure(),
			passwordPolicy.getLockoutDuration(),
			passwordPolicy.getResetFailureCount(),
			passwordPolicy.getResetTicketMaxAge(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (passwordPolicy != null) {
			deleteStagedModel(passwordPolicy);
		}
	}

	@Override
	public void deleteStagedModel(PasswordPolicy passwordPolicy)
		throws PortalException {

		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_passwordPolicyLocalService.deleteNondefaultPasswordPolicies(
			portletDataContext.getCompanyId());
	}

	@Override
	public PasswordPolicy fetchMissingReference(String uuid, long groupId) {
		return
			(PasswordPolicy)_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public PasswordPolicy fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<PasswordPolicy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<PasswordPolicy> passwordPolicies = new ArrayList<>();

		passwordPolicies.add(
			_passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(
				uuid, companyId));

		return passwordPolicies;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _passwordPolicyLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PasswordPolicy saveStagedModel(PasswordPolicy passwordPolicy)
		throws PortalException {

		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy);
	}

	@Override
	public PasswordPolicy updateStagedModel(
		PortletDataContext portletDataContext, PasswordPolicy passwordPolicy)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			passwordPolicy);

		return _passwordPolicyLocalService.updatePasswordPolicy(
			passwordPolicy.getPasswordPolicyId(),
			passwordPolicy.getName(), passwordPolicy.getDescription(),
			passwordPolicy.isChangeable(),
			passwordPolicy.isChangeRequired(),
			passwordPolicy.getMinAge(), passwordPolicy.isCheckSyntax(),
			passwordPolicy.isAllowDictionaryWords(),
			passwordPolicy.getMinAlphanumeric(),
			passwordPolicy.getMinLength(),
			passwordPolicy.getMinLowerCase(),
			passwordPolicy.getMinNumbers(),
			passwordPolicy.getMinSymbols(),
			passwordPolicy.getMinUpperCase(), passwordPolicy.getRegex(),
			passwordPolicy.isHistory(),
			passwordPolicy.getHistoryCount(),
			passwordPolicy.isExpireable(), passwordPolicy.getMaxAge(),
			passwordPolicy.getWarningTime(),
			passwordPolicy.getGraceLimit(), passwordPolicy.isLockout(),
			passwordPolicy.getMaxFailure(),
			passwordPolicy.getLockoutDuration(),
			passwordPolicy.getResetFailureCount(),
			passwordPolicy.getResetTicketMaxAge(), serviceContext);
	}
}
