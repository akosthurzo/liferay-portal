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

package com.liferay.exportimport.validator;

import com.liferay.exportimport.api.validator.ExportImportValidatorParameters;

import java.util.List;
import java.util.Locale;

/**
 * @author Akos Thurzo
 */
public class AvailableLocalesExportImportValidatorParameters
	implements ExportImportValidatorParameters {

	public AvailableLocalesExportImportValidatorParameters() {
	}

	public AvailableLocalesExportImportValidatorParameters(
		List<Locale> sourceAvailableLocales, long groupId) {

		setSourceAvailableLocales(sourceAvailableLocales);
		setGroupId(groupId);
	}

	public List<Locale> getSourceAvailableLocales() {
		return _sourceAvailableLocales;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setSourceAvailableLocales(List<Locale> sourceAvailableLocales) {
		_sourceAvailableLocales = sourceAvailableLocales;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private List<Locale> _sourceAvailableLocales;
	private long _groupId;

}