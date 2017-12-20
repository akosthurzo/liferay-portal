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

package com.liferay.mobile.device.rules.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRAction"},
	service = StagedModelRepository.class
)
public class MDRActionStagedModelRepository
	implements StagedModelRepository<MDRAction> {


	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private MDRActionLocalService _mdrActionLocalService;

	@Override
	public MDRAction addStagedModel(
			PortletDataContext portletDataContext, MDRAction action)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MDRAction action = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (action != null) {
			deleteStagedModel(action);
		}
	}

	@Override
	public void deleteStagedModel(MDRAction action) throws PortalException {
		_mdrActionLocalService.deleteAction(action);
	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_mdrActionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<MDRAction>) action ->
				_mdrActionLocalService.deleteMDRAction(action));

		actionableDynamicQuery.performActions();
	}

	@Override
	public MDRAction fetchMissingReference(String uuid, long groupId) {
		return (MDRAction)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public MDRAction fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mdrActionLocalService.fetchMDRActionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MDRAction> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrActionLocalService.getMDRActionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MDRAction>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mdrActionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, MDRAction stagedModel)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MDRAction saveStagedModel(MDRAction action) throws PortalException {
		return _mdrActionLocalService.updateMDRAction(action);
	}

	@Override
	public MDRAction updateStagedModel(
			PortletDataContext portletDataContext, MDRAction action)
		throws PortalException {

		return null;
	}
}
