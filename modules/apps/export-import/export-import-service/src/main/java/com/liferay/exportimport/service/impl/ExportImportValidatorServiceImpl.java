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

package com.liferay.exportimport.service.impl;

import com.liferay.exportimport.api.validator.ExportImportValidatorParameters;
import com.liferay.exportimport.service.base.ExportImportValidatorServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * The implementation of the export import validator remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.exportimport.service.ExportImportValidatorService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorServiceBaseImpl
 * @see com.liferay.exportimport.service.ExportImportValidatorServiceUtil
 */
public class ExportImportValidatorServiceImpl
	extends ExportImportValidatorServiceBaseImpl {

	@Override
	public ExportImportValidatorParameters
			getRemoteLayoutLarTypeExportImportValidatorParameters(long groupId)
		throws PortalException {

		return exportImportValidatorLocalService.
			getRemoteLayoutLarTypeExportImportValidatorParameters(groupId);
	}

	public ExportImportValidatorParameters
			getBuildNumberExportImportValidatorParameters() {

		return exportImportValidatorLocalService.
			getBuildNumberExportImportValidatorParameters();
	}

	public void validate(
			String className,
			ExportImportValidatorParameters exportImportValidatorParameters)
		throws PortalException {

		exportImportValidatorLocalService.validate(
			className, exportImportValidatorParameters);
	}

}