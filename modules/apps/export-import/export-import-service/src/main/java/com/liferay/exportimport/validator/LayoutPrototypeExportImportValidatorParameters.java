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
public class LayoutPrototypeExportImportValidatorParameters
	implements ExportImportValidatorParameters {

	public LayoutPrototypeExportImportValidatorParameters() {
	}

	public LayoutPrototypeExportImportValidatorParameters(
		long companyId, String layoutSetPrototypeName,
		String layoutSetPrototypeUuid) {

		setCompanyId(companyId);
		setLayoutSetPrototypeName(layoutSetPrototypeName);
		setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getLayoutSetPrototypeUuid() {
		return _layoutSetPrototypeUuid;
	}

	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_layoutSetPrototypeUuid = layoutSetPrototypeUuid;
	}

	public String getLayoutSetPrototypeName() {
		return _layoutSetPrototypeName;
	}

	public void setLayoutSetPrototypeName(String layoutSetPrototypeName) {
		_layoutSetPrototypeName = layoutSetPrototypeName;
	}

	private long _companyId;
	private String _layoutSetPrototypeName;
	private String _layoutSetPrototypeUuid;

}