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

package com.liferay.expando.exportimport.data.handler;

import com.lifeary.expando.exportimport.model.adapter.StagedExpandoTable;

import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.web.constants.ExpandoPortletKeys;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ExpandoPortletKeys.EXPANDO},
	service = PortletDataHandler.class
)
public class ExpandoPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "expando";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(LayoutSetPrototype.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "expando", true, true, null,
				ExpandoTable.class.getName()));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

//		if (portletDataContext.addPrimaryKey(
//			LayoutSetPrototypePortletDataHandler.class, "deleteData")) {
//
//			return portletPreferences;
//		}
//
//		layoutSetPrototypeLocalService.deleteNondefaultLayoutSetPrototypes(
//			portletDataContext.getCompanyId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortalPermissions(); ///////////////////////////////// ????????????????

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ExportActionableDynamicQuery actionableDynamicQuery =
			_stagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element stagedExpandoTablesElement =
			portletDataContext.getImportDataGroupElement(
				StagedExpandoTable.class);

		List<Element> stagedExpandoTablesElements =
			stagedExpandoTablesElement.elements();

		for (Element stagedExpandoTableElement : stagedExpandoTablesElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedExpandoTableElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

//		ActionableDynamicQuery layoutSetPrototypeExportActionableDynamicQuery =
//			layoutSetPrototypeLocalService.getExportActionableDynamicQuery(
//				portletDataContext);

//
//		layoutSetPrototypeExportActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.lifeary.expando.exportimport.model.adapter.StagedExpandoTable)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<StagedExpandoTable> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	private StagedModelRepository<StagedExpandoTable> _stagedModelRepository;

}