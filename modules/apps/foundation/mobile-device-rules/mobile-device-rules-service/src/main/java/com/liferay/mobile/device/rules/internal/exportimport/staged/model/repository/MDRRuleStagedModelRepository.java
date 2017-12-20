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
import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.service.MDRRuleLocalService;
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
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRule"},
	service = StagedModelRepository.class
)
public class MDRRuleStagedModelRepository
	implements StagedModelRepository<MDRRule> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private MDRRuleLocalService _mdrRuleLocalService;

	@Override
	public MDRRule addStagedModel(
			PortletDataContext portletDataContext, MDRRule stagedModel)
		throws PortalException {
		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MDRRule rule = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (rule != null) {
			deleteStagedModel(rule);
		}
	}

	@Override
	public void deleteStagedModel(MDRRule rule) throws PortalException {
		_mdrRuleLocalService.deleteRule(rule);
	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_mdrRuleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<MDRRule>)rule ->
				_mdrRuleLocalService.deleteRule(rule));

		actionableDynamicQuery.performActions();
	}

	@Override
	public MDRRule fetchMissingReference(String uuid, long groupId) {
		return (MDRRule)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public MDRRule fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return _mdrRuleLocalService.fetchMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<MDRRule> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleLocalService.getMDRRulesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mdrRuleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MDRRule saveStagedModel(MDRRule rule) throws PortalException {
		return _mdrRuleLocalService.updateMDRRule(rule);
	}

	@Override
	public MDRRule updateStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws PortalException {

		return null;
	}
}
