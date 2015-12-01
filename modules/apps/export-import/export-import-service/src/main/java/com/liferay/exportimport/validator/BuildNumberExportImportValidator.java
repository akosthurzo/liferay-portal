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

import com.liferay.exportimport.api.validator.ExportImportValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portlet.exportimport.LayoutImportException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {
		ExportImportValidator.class, BuildNumberExportImportValidator.class
	}
)
public class BuildNumberExportImportValidator
	implements ExportImportValidator
		<BuildNumberExportImportValidatorParameters> {

	@Override
	public void validate(
			BuildNumberExportImportValidatorParameters
				exportImportValidatorParameters)
		throws PortalException {

		int buildNumber = ReleaseInfo.getBuildNumber();

		if (exportImportValidatorParameters.getBuildNumber() != buildNumber) {
			throw new LayoutImportException(
				"LAR build number " + buildNumber + " does not match " +
					"portal build number " +
						exportImportValidatorParameters.getBuildNumber());
		}
	}

}