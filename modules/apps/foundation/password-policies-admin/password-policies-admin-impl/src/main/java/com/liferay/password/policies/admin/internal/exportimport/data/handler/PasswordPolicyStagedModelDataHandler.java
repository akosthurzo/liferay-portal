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

package com.liferay.password.policies.admin.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PasswordPolicyStagedModelDataHandler
	extends BaseStagedModelDataHandler<PasswordPolicy> {

	public static final String[] CLASS_NAMES = {PasswordPolicy.class.getName()};

	@Override
	public void deleteStagedModel(PasswordPolicy passwordPolicy)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(passwordPolicy);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<PasswordPolicy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws Exception {

		Element passwordPolicyElement = portletDataContext.getExportDataElement(
			passwordPolicy);

		portletDataContext.addClassedModel(
			passwordPolicyElement,
			ExportImportPathUtil.getModelPath(passwordPolicy), passwordPolicy);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			PasswordPolicy passwordPolicy)
		throws Exception {

		List<PasswordPolicy> existingPasswordPolicies =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				passwordPolicy.getUuid(), portletDataContext.getCompanyId());

		PasswordPolicy existingPasswordPolicy = existingPasswordPolicies.get(0);

		if (existingPasswordPolicy == null) {
			existingPasswordPolicy =
				_passwordPolicyLocalService.fetchPasswordPolicy(
					portletDataContext.getCompanyId(),
					passwordPolicy.getName());
		}

		PasswordPolicy importedPasswordPolicy =
			(PasswordPolicy)passwordPolicy.clone();

		if (existingPasswordPolicy == null) {
			importedPasswordPolicy = _stagedModelRepository.addStagedModel(
				portletDataContext, importedPasswordPolicy);
		}
		else {
			importedPasswordPolicy.setPasswordPolicyId(
				existingPasswordPolicy.getPasswordPolicyId());

			importedPasswordPolicy = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedPasswordPolicy);

		}

		portletDataContext.importClassedModel(
			passwordPolicy, importedPasswordPolicy);
	}

	@Reference(unbind = "-")
	protected void setPasswordPolicyLocalService(
		PasswordPolicyLocalService passwordPolicyLocalService) {

		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.PasswordPolicy)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<PasswordPolicy> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Override
	protected StagedModelRepository<PasswordPolicy> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	private StagedModelRepository<PasswordPolicy> _stagedModelRepository;

}