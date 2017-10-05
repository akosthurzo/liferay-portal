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

package com.liferay.changeset;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
public class ChangeSetBuilder {

	public static ChangeSetBuilder changeSet() {
		return new ChangeSetBuilder();
	}

	public ChangeSet build() {
		return _changeSet;
	}

	public ExportImportConfiguration buildExportConfiguration(
		ThemeDisplay themeDisplay) {

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
	}

	public <T extends ClassedModel> ChangeSetBuilder with(
		ChangeSetModelSupplier<T> modelSupplier) {

		_changeSet.addModel(modelSupplier);

		return this;
	}

	private ChangeSetBuilder() {
		_changeSet = new ChangeSetImpl(PortalUUIDUtil.generate());
	}

	private final ChangeSet _changeSet;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	private class ChangeSetImpl implements ChangeSet, Serializable {

		public ChangeSetImpl() {
			_modelSuppliers = new ArrayList<>();
		}

		public ChangeSetImpl(String uuid) {
			_modelSuppliers = new ArrayList<>();
			_uuid = uuid;
		}

		@Override
		public <T extends ClassedModel> void addModel(
			ChangeSetModelSupplier<T> modelSupplier) {

			Objects.requireNonNull(modelSupplier);

			_modelSuppliers.add(modelSupplier);
		}

		@Override
		public String getUuid() {
			return _uuid;
		}

		public void setUuid(String uuid) {
			_uuid = uuid;
		}

		@Override
		public Stream<? extends ClassedModel> stream() {
			return _modelSuppliers.stream().map(s -> s.supplyModel());
		}

		private final List<ChangeSetModelSupplier> _modelSuppliers;
		private String _uuid;

	}

}