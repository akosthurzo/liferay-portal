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

import com.liferay.exportimport.api.validator.ExportImportValidator;
import com.liferay.exportimport.api.validator.ExportImportValidatorParameters;
import com.liferay.exportimport.api.validator.ExportImportValidatorRegistryUtil;
import com.liferay.exportimport.service.base.ExportImportValidatorLocalServiceBaseImpl;
import com.liferay.exportimport.validator.AvailableLocalesExportImportValidator;
import com.liferay.exportimport.validator.AvailableLocalesExportImportValidatorParameters;
import com.liferay.exportimport.validator.BuildNumberExportImportValidatorParameters;
import com.liferay.exportimport.validator.LarTypeExportImportValidatorParameters;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.Group;

/**
 * The implementation of the export import validator local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.exportimport.service.ExportImportValidatorLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorLocalServiceBaseImpl
 * @see com.liferay.exportimport.service.ExportImportValidatorLocalServiceUtil
 */
public class ExportImportValidatorLocalServiceImpl
	extends ExportImportValidatorLocalServiceBaseImpl {

	@Override
	public ExportImportValidatorParameters
		getBuildNumberExportImportValidatorParameters() {

		return new BuildNumberExportImportValidatorParameters(
			ReleaseInfo.getBuildNumber());
	}

	@Override
	public ExportImportValidatorParameters
			getRemoteLayoutLarTypeExportImportValidatorParameters(long groupId)
		throws PortalException {

		LarTypeExportImportValidatorParameters
			larTypeExportImportValidatorParameters =
				new LarTypeExportImportValidatorParameters("layout");

		Group group = groupLocalService.getGroup(groupId);

		larTypeExportImportValidatorParameters.setRemoteParameters(
			group.isCompany(), group.hasStagingGroup(),
			group.isLayoutPrototype(), group.isLayoutSetPrototype(),
			group.isStaged());

		return larTypeExportImportValidatorParameters;
	}

	@Override
	public void validate(
			String className,
			ExportImportValidatorParameters exportImportValidatorParameters)
		throws PortalException {

		ExportImportValidator exportImportValidator =
			ExportImportValidatorRegistryUtil.getExportImportValidator(
				className);

		exportImportValidator.validate(exportImportValidatorParameters);
	}

}