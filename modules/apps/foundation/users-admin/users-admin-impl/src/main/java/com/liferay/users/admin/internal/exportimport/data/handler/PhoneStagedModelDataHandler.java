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

package com.liferay.users.admin.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PhoneStagedModelDataHandler
	extends BaseStagedModelDataHandler<Phone> {

	public static final String[] CLASS_NAMES = {Phone.class.getName()};

	@Override
	public void deleteStagedModel(Phone phone) throws PortalException {
		_stagedModelRepository.deleteStagedModel(phone);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<Phone> fetchStagedModelsByUuidAndCompanyId(
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
			PortletDataContext portletDataContext, Phone phone)
		throws Exception {

		Element phoneElement = portletDataContext.getExportDataElement(phone);

		portletDataContext.addClassedModel(
			phoneElement, ExportImportPathUtil.getModelPath(phone), phone);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Phone phone)
		throws Exception {

		List<Phone> existingPhones =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				phone.getUuid(), portletDataContext.getCompanyId());

		Phone existingPhone = existingPhones.get(0);

		Phone importedPhone = (Phone)phone.clone();

		if (existingPhone == null) {
			importedPhone = _stagedModelRepository.addStagedModel(
				portletDataContext, importedPhone);
		}
		else {
			importedPhone.setPhoneId(existingPhone.getPhoneId());

			importedPhone = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedPhone);
		}

		portletDataContext.importClassedModel(phone, importedPhone);
	}

	@Override
	protected StagedModelRepository<Phone> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Phone)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<Phone> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<Phone> _stagedModelRepository;

}