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
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
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
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroup"},
	service = StagedModelRepository.class
)
public class MDRRuleGroupStagedModelRepository
	implements StagedModelRepository<MDRRuleGroup> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;

	@Override
	public MDRRuleGroup addStagedModel(
			PortletDataContext portletDataContext, MDRRuleGroup ruleGroup)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MDRRuleGroup ruleGroup = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ruleGroup != null) {
			deleteStagedModel(ruleGroup);
		}
	}

	@Override
	public void deleteStagedModel(MDRRuleGroup ruleGroup)
		throws PortalException {

		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_mdrRuleGroupLocalService.deleteRuleGroups(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public MDRRuleGroup fetchMissingReference(String uuid, long groupId) {
		return (MDRRuleGroup)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public MDRRuleGroup fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mdrRuleGroupLocalService.fetchMDRRuleGroupByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MDRRuleGroup> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {
		return _mdrRuleGroupLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, MDRRuleGroup ruleGroup)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MDRRuleGroup saveStagedModel(MDRRuleGroup ruleGroup)
		throws PortalException {

		return _mdrRuleGroupLocalService.updateMDRRuleGroup(ruleGroup);
	}

	@Override
	public MDRRuleGroup updateStagedModel(
			PortletDataContext portletDataContext, MDRRuleGroup ruleGroup)
		throws PortalException {

		return null;
	}
}
