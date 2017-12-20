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
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
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
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance"},
	service = StagedModelRepository.class
)
public class MDRRuleGroupInstanceStagedModelRepository
	implements StagedModelRepository<MDRRuleGroupInstance> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;

	@Override
	public MDRRuleGroupInstance addStagedModel(
		PortletDataContext portletDataContext, MDRRuleGroupInstance stagedModel)
		throws PortalException {
		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MDRRuleGroupInstance ruleGroupInstance =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ruleGroupInstance != null) {
			deleteStagedModel(ruleGroupInstance);
		}
	}

	@Override
	public void deleteStagedModel(MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException {

		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_mdrRuleGroupInstanceLocalService.deleteGroupRuleGroupInstances(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public MDRRuleGroupInstance fetchMissingReference(
		String uuid, long groupId) {

		return
			(MDRRuleGroupInstance)_stagedModelRepositoryHelper.
				fetchMissingReference(uuid, groupId, this);
	}

	@Override
	public MDRRuleGroupInstance fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mdrRuleGroupInstanceLocalService.
			fetchMDRRuleGroupInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<MDRRuleGroupInstance> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleGroupInstanceLocalService.
			getMDRRuleGroupInstancesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return
			_mdrRuleGroupInstanceLocalService.getExportActionableDynamicQuery(
				portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance ruleGroupInstance)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MDRRuleGroupInstance saveStagedModel(
			MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException {

		return _mdrRuleGroupInstanceLocalService.updateMDRRuleGroupInstance(
			ruleGroupInstance);
	}

	@Override
	public MDRRuleGroupInstance updateStagedModel(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException {

		return null;
	}
}
