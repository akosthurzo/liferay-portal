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

import com.liferay.expando.web.internal.constants.ExpandoPortletKeys;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExpandoPortletKeys.EXPANDO
	},
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
				NAMESPACE, "expando", true, true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "page-templates", true, false)
				},
				LayoutSetPrototype.class.getName()));
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

		portletDataContext.addPortalPermissions();

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

//		ActionableDynamicQuery actionableDynamicQuery =
//			layoutSetPrototypeLocalService.getExportActionableDynamicQuery(
//				portletDataContext);
//
//		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element layoutSetPrototypesElement =
			portletDataContext.getImportDataGroupElement(
				LayoutSetPrototype.class);

		List<Element> layoutSetPrototypeElements =
			layoutSetPrototypesElement.elements();

		for (Element layoutSetPrototypeElement : layoutSetPrototypeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutSetPrototypeElement);
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
}
