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
import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portlet.exportimport.LayoutImportException;

import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {
		ExportImportValidator.class, LayoutPrototypeExportImportValidator.class
	}
)
public class LayoutPrototypeExportImportValidator
	implements ExportImportValidator
		<LayoutPrototypeExportImportValidatorParameters> {

	@Override
	public void validate(
		LayoutPrototypeExportImportValidatorParameters
			exportImportValidatorParameters)
		throws PortalException {

		long companyId = exportImportValidatorParameters.getCompanyId();
		String layoutSetPrototypeUuid =
			exportImportValidatorParameters.getLayoutSetPrototypeUuid();

		List<Tuple> missingLayoutPrototypes = new ArrayList<>();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			try {
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);
			}
			catch (NoSuchLayoutSetPrototypeException nlspe) {
				String layoutSetPrototypeName =
					exportImportValidatorParameters.getLayoutSetPrototypeName();

				missingLayoutPrototypes.add(
					new Tuple(
						LayoutSetPrototype.class.getName(),
						layoutSetPrototypeUuid, layoutSetPrototypeName));
			}
		}
	}

}