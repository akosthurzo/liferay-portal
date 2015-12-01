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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExportImportValidator. This utility wraps
 * {@link com.liferay.exportimport.service.impl.ExportImportValidatorLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorLocalService
 * @see com.liferay.exportimport.service.base.ExportImportValidatorLocalServiceBaseImpl
 * @see com.liferay.exportimport.service.impl.ExportImportValidatorLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExportImportValidatorLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.exportimport.service.impl.ExportImportValidatorLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static com.liferay.exportimport.api.validator.ExportImportValidatorParameters getBuildNumberExportImportValidatorParameters() {
		return getService().getBuildNumberExportImportValidatorParameters();
	}

	public static com.liferay.exportimport.api.validator.ExportImportValidatorParameters getRemoteLayoutLarTypeExportImportValidatorParameters(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getRemoteLayoutLarTypeExportImportValidatorParameters(groupId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void validate(java.lang.String className,
		com.liferay.exportimport.api.validator.ExportImportValidatorParameters exportImportValidatorParameters)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(className, exportImportValidatorParameters);
	}

	public static ExportImportValidatorLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ExportImportValidatorLocalService service) {
	}

	private static ServiceTracker<ExportImportValidatorLocalService, ExportImportValidatorLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExportImportValidatorLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<ExportImportValidatorLocalService, ExportImportValidatorLocalService>(bundle.getBundleContext(),
				ExportImportValidatorLocalService.class, null);

		_serviceTracker.open();
	}
}