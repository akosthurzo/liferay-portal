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

/**
 * @author Akos Thurzo
 */
public class PortletCompatibilityExportImportValidatorParameters
	implements ExportImportValidatorParameters {

	public PortletCompatibilityExportImportValidatorParameters() {
	}

	public PortletCompatibilityExportImportValidatorParameters(
		String portletId, String rootPortletId) {

		setPortletId(portletId);
		setRootPortletId(rootPortletId);
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getRootPortletId() {
		return _rootPortletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setRootPortletId(String rootPortletId) {
		_rootPortletId = rootPortletId;
	}

	private String _portletId;
	private String _rootPortletId;

}