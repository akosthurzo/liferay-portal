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
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {
		ExportImportValidator.class, AvailableLocalesExportImportValidator.class
	}
)
public class AvailableLocalesExportImportValidator
	implements ExportImportValidator
		<AvailableLocalesExportImportValidatorParameters> {

	@Override
	public void validate(
			AvailableLocalesExportImportValidatorParameters
				exportImportValidatorParameters)
		throws PortalException {

		List<Locale> sourceAvailableLocales =
			exportImportValidatorParameters.getSourceAvailableLocales();
		long groupId = exportImportValidatorParameters.getGroupId();

		for (Locale availableLocale : sourceAvailableLocales) {
			if (!LanguageUtil.isAvailableLocale(groupId, availableLocale)) {
				LocaleException le = new LocaleException(
					LocaleException.TYPE_EXPORT_IMPORT);

				le.setSourceAvailableLocales(sourceAvailableLocales);
				le.setTargetAvailableLocales(
					LanguageUtil.getAvailableLocales(groupId));

				throw le;
			}
		}
	}

}