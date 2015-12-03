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

package com.liferay.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExportImportValidatorLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorLocalService
 * @generated
 */
@ProviderType
public class ExportImportValidatorLocalServiceWrapper
	implements ExportImportValidatorLocalService,
		ServiceWrapper<ExportImportValidatorLocalService> {
	public ExportImportValidatorLocalServiceWrapper(
		ExportImportValidatorLocalService exportImportValidatorLocalService) {
		_exportImportValidatorLocalService = exportImportValidatorLocalService;
	}

	@Override
	public com.liferay.exportimport.api.validator.ExportImportValidatorParameters getBuildNumberExportImportValidatorParameters() {
		return _exportImportValidatorLocalService.getBuildNumberExportImportValidatorParameters();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _exportImportValidatorLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.exportimport.api.validator.ExportImportValidatorParameters getRemoteLayoutLarTypeExportImportValidatorParameters(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportValidatorLocalService.getRemoteLayoutLarTypeExportImportValidatorParameters(groupId);
	}

	@Override
	public void validate(java.lang.String className,
		com.liferay.exportimport.api.validator.ExportImportValidatorParameters exportImportValidatorParameters)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportValidatorLocalService.validate(className,
			exportImportValidatorParameters);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportValidatorLocalService getWrappedExportImportValidatorLocalService() {
		return _exportImportValidatorLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportValidatorLocalService(
		ExportImportValidatorLocalService exportImportValidatorLocalService) {
		_exportImportValidatorLocalService = exportImportValidatorLocalService;
	}

	@Override
	public ExportImportValidatorLocalService getWrappedService() {
		return _exportImportValidatorLocalService;
	}

	@Override
	public void setWrappedService(
		ExportImportValidatorLocalService exportImportValidatorLocalService) {
		_exportImportValidatorLocalService = exportImportValidatorLocalService;
	}

	private ExportImportValidatorLocalService _exportImportValidatorLocalService;
}