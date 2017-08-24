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

package com.liferay.expando.exportimport.staged.model.repository;

import com.lifeary.expando.exportimport.model.adapter.StagedExpandoTable;

import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.lifeary.expando.exportimport.model.adapter.StagedExpandoTable"},
	service = StagedModelRepository.class
)
public class StagedExpandoTableStagedModelRepository
	implements StagedModelRepository<StagedExpandoTable> {

	@Override
	public StagedExpandoTable addStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedExpandoTable)
		throws PortalException {

		ExpandoTable expandoTable = _expandoTableLocalService.addDefaultTable(
			portletDataContext.getCompanyId(),
			stagedExpandoTable.getClassName());

		return ModelAdapterUtil.adapt(
			expandoTable, ExpandoTable.class, StagedExpandoTable.class);
	}

	@Override
	public void deleteStagedModel(StagedExpandoTable stagedExpandoTable)
		throws PortalException {

		_expandoTableLocalService.deleteTable(stagedExpandoTable);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		//_expandoTableLocalService.deleteTable(
		//	, ,ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		List<ExpandoTable> expandoTables =
			_expandoTableLocalService.getExpandoTables(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ExpandoTable expandoTable : expandoTables) {
			_expandoTableLocalService.deleteTable(expandoTable);
		}
	}

	@Override
	public StagedExpandoTable fetchMissingReference(String uuid, long groupId) {
		return null;
	}

	@Override
	public StagedExpandoTable fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public List<StagedExpandoTable> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		DynamicQuery dynamicQuery = _expandoTableLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		String className = _parseClassName(uuid);

		dynamicQuery.add(
			classNameIdProperty.eq(_portal.getClassNameId(className)));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		String name = _parseName(uuid);

		dynamicQuery.add(nameProperty.eq(name));

		List<ExpandoTable> expandoTables =
			_expandoTableLocalService.dynamicQuery(dynamicQuery);

		if (ListUtil.isNotEmpty(expandoTables)) {
			return ModelAdapterUtil.adapt(
				expandoTables, ExpandoTable.class, StagedExpandoTable.class);
		}

		return Collections.emptyList();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedModel)
		throws PortletDataException {
	}

	@Override
	public StagedExpandoTable saveStagedModel(StagedExpandoTable stagedModel)
		throws PortalException {

		return null;
	}

	@Override
	public StagedExpandoTable updateStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedModel)
		throws PortalException {

		return null;
	}

	private String _parseClassName(String uuid) {
		return uuid.substring(0, uuid.indexOf(StringPool.POUND));
	}

	private String _parseName(String uuid) {
		return uuid.substring(uuid.indexOf(StringPool.POUND) + 1);
	}

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private Portal _portal;

}