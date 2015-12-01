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
 * Provides a wrapper for {@link ExportImportValidatorService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorService
 * @generated
 */
@ProviderType
public class ExportImportValidatorServiceWrapper
	implements ExportImportValidatorService,
		ServiceWrapper<ExportImportValidatorService> {
	public ExportImportValidatorServiceWrapper(
		ExportImportValidatorService exportImportValidatorService) {
		_exportImportValidatorService = exportImportValidatorService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _exportImportValidatorService.getBeanIdentifier();
	}

	@Override
	public com.liferay.exportimport.api.validator.ExportImportValidatorParameters getBuildNumberExportImportValidatorParameters() {
		return _exportImportValidatorService.getBuildNumberExportImportValidatorParameters();
	}

	@Override
	public com.liferay.exportimport.api.validator.ExportImportValidatorParameters getRemoteLayoutLarTypeExportImportValidatorParameters(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportValidatorService.getRemoteLayoutLarTypeExportImportValidatorParameters(groupId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_exportImportValidatorService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void validate(java.lang.String className,
		com.liferay.exportimport.api.validator.ExportImportValidatorParameters exportImportValidatorParameters)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportValidatorService.validate(className,
			exportImportValidatorParameters);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportValidatorService getWrappedExportImportValidatorService() {
		return _exportImportValidatorService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportValidatorService(
		ExportImportValidatorService exportImportValidatorService) {
		_exportImportValidatorService = exportImportValidatorService;
	}

	@Override
	public ExportImportValidatorService getWrappedService() {
		return _exportImportValidatorService;
	}

	@Override
	public void setWrappedService(
		ExportImportValidatorService exportImportValidatorService) {
		_exportImportValidatorService = exportImportValidatorService;
	}

	private ExportImportValidatorService _exportImportValidatorService;
}