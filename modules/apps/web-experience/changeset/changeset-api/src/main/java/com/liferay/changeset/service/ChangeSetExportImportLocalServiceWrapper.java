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
 * Provides a wrapper for {@link ChangeSetExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeSetExportImportLocalService
 * @generated
 */
@ProviderType
public class ChangeSetExportImportLocalServiceWrapper
	implements ChangeSetExportImportLocalService,
		ServiceWrapper<ChangeSetExportImportLocalService> {
	public ChangeSetExportImportLocalServiceWrapper(
		ChangeSetExportImportLocalService changeSetExportImportLocalService) {
		_changeSetExportImportLocalService = changeSetExportImportLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _changeSetExportImportLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ChangeSetExportImportLocalService getWrappedService() {
		return _changeSetExportImportLocalService;
	}

	@Override
	public void setWrappedService(
		ChangeSetExportImportLocalService changeSetExportImportLocalService) {
		_changeSetExportImportLocalService = changeSetExportImportLocalService;
	}

	private ChangeSetExportImportLocalService _changeSetExportImportLocalService;
}