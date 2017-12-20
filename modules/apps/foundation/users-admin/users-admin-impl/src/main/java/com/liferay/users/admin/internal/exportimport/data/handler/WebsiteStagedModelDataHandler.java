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
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class WebsiteStagedModelDataHandler
	extends BaseStagedModelDataHandler<Website> {

	public static final String[] CLASS_NAMES = {Website.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(Website website) throws PortalException {
		_stagedModelRepository.deleteStagedModel(website);
	}

	@Override
	public List<Website> fetchStagedModelsByUuidAndCompanyId(
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
			PortletDataContext portletDataContext, Website website)
		throws Exception {

		Element websiteElement = portletDataContext.getExportDataElement(
			website);

		portletDataContext.addClassedModel(
			websiteElement, ExportImportPathUtil.getModelPath(website),
			website);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws Exception {

		List<Website> existingWebsites =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				website.getUuid(), portletDataContext.getCompanyGroupId());

		Website existingWebsite = existingWebsites.get(0);

		Website importedWebsite = (Website)website.clone();

		if (existingWebsite == null) {
			importedWebsite = _stagedModelRepository.addStagedModel(
				portletDataContext, importedWebsite);
		}
		else {
			importedWebsite.setWebsiteId(existingWebsite.getWebsiteId());

			importedWebsite = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedWebsite);
		}

		portletDataContext.importClassedModel(website, importedWebsite);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Website)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<Website> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Override
	protected StagedModelRepository<Website> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	private StagedModelRepository<Website> _stagedModelRepository;

}