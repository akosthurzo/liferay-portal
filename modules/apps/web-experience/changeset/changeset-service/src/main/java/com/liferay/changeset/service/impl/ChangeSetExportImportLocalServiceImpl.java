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

package com.liferay.changeset.service.impl;

import com.liferay.changeset.ChangeSet;
import com.liferay.changeset.service.base.ChangeSetExportImportLocalServiceBaseImpl;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The implementation of the change set export import local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.service.ChangeSetExportImportLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeSetExportImportLocalServiceBaseImpl
 * @see com.liferay.changeset.service.ChangeSetExportImportLocalServiceUtil
 */
public class ChangeSetExportImportLocalServiceImpl
	extends ChangeSetExportImportLocalServiceBaseImpl {

	public File exportChangeSet(ChangeSet changeSet) {
		Objects.requireNonNull(changeSet);

		_draftChangeSets.put(changeSet.getUuid(), changeSet);

		Map<String, Serializable> exportPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildExportPortletSettingsMap(
					themeDisplay.getUserId(), themeDisplay.getPlid(),
					themeDisplay.getScopeGroupId(), "ChangeSetPortlet",
					ExportImportConfigurationParameterMapFactory.
						buildParameterMap(),
					themeDisplay.getLocale(), themeDisplay.getTimeZone(),
					"ChangeSet" + _changeSet.getUuid());

		exportPortletSettingsMap.put(ChangeSet.class.getName(), _changeSet);

		try {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
						exportPortletSettingsMap);

			return exportImportConfiguration;
		}
		catch (PortalException pe) {
			pe.printStackTrace();

			return null;
		}

		return null;
	}

	public ChangeSet fetchChangeSet(String uuid) {
		return _draftChangeSets.get(uuid);
	}

	private static final Map<String, ChangeSet> _draftChangeSets =
		new HashMap<String, ChangeSet>();

}