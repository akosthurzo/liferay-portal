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

package com.liferay.exportimport.api.validator;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Akos Thurzo
 */
@ProviderType
public class ExportImportValidatorRegistryUtil {

	public static ExportImportValidator getExportImportValidator(
		String className) {

		return _instance._getExportImportValidator(className);
	}

	public static List<ExportImportValidator> getExportImportValidators() {
		return _instance._getExportImportValidators();
	}

	public static void register(ExportImportValidator exportImportValidator) {
		_instance._register(exportImportValidator);
	}

	public static void unregister(ExportImportValidator exportImportValidator) {
		_instance._unregister(exportImportValidator);
	}

	private ExportImportValidatorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ExportImportValidator.class,
			new ExportImportValidatorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private ExportImportValidator _getExportImportValidator(String className) {
		return _exportImportValidators.get(className);
	}

	private List<ExportImportValidator> _getExportImportValidators() {
		return ListUtil.fromMapValues(_exportImportValidators);
	}

	private void _register(ExportImportValidator exportImportValidator) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ExportImportValidator> serviceRegistration =
			registry.registerService(
				ExportImportValidator.class, exportImportValidator);

		_serviceRegistrations.put(exportImportValidator, serviceRegistration);
	}

	private void _unregister(ExportImportValidator exportImportValidator) {
		ServiceRegistration<ExportImportValidator> serviceRegistration =
			_serviceRegistrations.remove(exportImportValidator);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ExportImportValidatorRegistryUtil _instance =
		new ExportImportValidatorRegistryUtil();

	private final Map<String, ExportImportValidator> _exportImportValidators =
		new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<ExportImportValidator>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker<ExportImportValidator, ExportImportValidator>
		_serviceTracker;

	private class ExportImportValidatorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportValidator, ExportImportValidator> {

		@Override
		public ExportImportValidator addingService(
			ServiceReference<ExportImportValidator> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ExportImportValidator exportImportValidator = registry.getService(
				serviceReference);

			_exportImportValidators.put(
				exportImportValidator.getClass().getName(),
				exportImportValidator);

			return exportImportValidator;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportValidator> serviceReference,
			ExportImportValidator stagedModelDataHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportValidator> serviceReference,
			ExportImportValidator exportImportValidator) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_exportImportValidators.remove(exportImportValidator);
		}

	}

}