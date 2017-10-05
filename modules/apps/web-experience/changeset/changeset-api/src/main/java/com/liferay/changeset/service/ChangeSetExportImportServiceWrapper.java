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

package com.liferay.changeset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangeSetExportImportService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeSetExportImportService
 * @generated
 */
@ProviderType
public class ChangeSetExportImportServiceWrapper
	implements ChangeSetExportImportService,
		ServiceWrapper<ChangeSetExportImportService> {
	public ChangeSetExportImportServiceWrapper(
		ChangeSetExportImportService changeSetExportImportService) {
		_changeSetExportImportService = changeSetExportImportService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _changeSetExportImportService.getOSGiServiceIdentifier();
	}

	@Override
	public ChangeSetExportImportService getWrappedService() {
		return _changeSetExportImportService;
	}

	@Override
	public void setWrappedService(
		ChangeSetExportImportService changeSetExportImportService) {
		_changeSetExportImportService = changeSetExportImportService;
	}

	private ChangeSetExportImportService _changeSetExportImportService;
}