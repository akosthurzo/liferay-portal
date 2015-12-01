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
import com.liferay.portlet.exportimport.LARTypeException;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {ExportImportValidator.class, LarTypeExportImportValidator.class}
)
public class LarTypeExportImportValidator
	implements ExportImportValidator<LarTypeExportImportValidatorParameters> {

	@Override
	public void validate(
			LarTypeExportImportValidatorParameters
				exportImportValidatorParameters)
		throws PortalException {

		String exportImportType =
			exportImportValidatorParameters.getExportImportType();
		String larType = exportImportValidatorParameters.getLarType();

		if (exportImportType.equals("portlet")) {
			if (!larType.equals("portlet")) {
				throw new LARTypeException(larType);
			}
			else {
				return;
			}
		}

		if (!exportImportType.equals("layout")) {
			throw new PortalException(
				"Invalid export/import type: " + exportImportType);
		}

		if (!larType.equals("layout-prototype") &&
			!larType.equals("layout-set") &&
			!larType.equals("layout-set-prototype")) {

			throw new LARTypeException(larType);
		}

		String layoutsImportMode =
			exportImportValidatorParameters.getLayoutsImportMode();

		if (larType.equals("layout-prototype") &&
			!exportImportValidatorParameters.isGroupLayoutPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(
				"A page template can only be imported to a page template");
		}

		if (larType.equals("layout-set")) {
			if (exportImportValidatorParameters.isGroupLayoutPrototype() ||
				exportImportValidatorParameters.isGroupLayoutSetPrototype()) {

				throw new LARTypeException(
					"A site can only be imported to a site");
			}

			boolean companySourceGroup = false;

			if (exportImportValidatorParameters.getSourceCompanyGroupId() ==
					exportImportValidatorParameters.getSourceGroupId()) {

				companySourceGroup = true;
			}
			else if (exportImportValidatorParameters.isGroupStaged() ||
					 exportImportValidatorParameters.isGroupHasStagingGroup()) {

				companySourceGroup =
					exportImportValidatorParameters.isSourceGroupCompany();
			}

			if (exportImportValidatorParameters.isGroupCompany() ^
				companySourceGroup) {

				throw new LARTypeException(
					"A company site can only be imported to a company site");
			}
		}

		if (larType.equals("layout-set-prototype") &&
			!exportImportValidatorParameters.isGroupLayoutSetPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(
				"A site template can only be imported to a site template");
		}
	}

}