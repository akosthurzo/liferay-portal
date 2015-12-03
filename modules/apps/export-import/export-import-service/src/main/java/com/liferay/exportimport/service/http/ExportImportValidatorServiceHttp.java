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

package com.liferay.exportimport.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.service.ExportImportValidatorServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link ExportImportValidatorServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportValidatorServiceSoap
 * @see HttpPrincipal
 * @see ExportImportValidatorServiceUtil
 * @generated
 */
@ProviderType
public class ExportImportValidatorServiceHttp {
	public static com.liferay.exportimport.api.validator.ExportImportValidatorParameters getRemoteLayoutLarTypeExportImportValidatorParameters(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportValidatorServiceUtil.class,
					"getRemoteLayoutLarTypeExportImportValidatorParameters",
					_getRemoteLayoutLarTypeExportImportValidatorParametersParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.exportimport.api.validator.ExportImportValidatorParameters)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.exportimport.api.validator.ExportImportValidatorParameters getBuildNumberExportImportValidatorParameters(
		HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(ExportImportValidatorServiceUtil.class,
					"getBuildNumberExportImportValidatorParameters",
					_getBuildNumberExportImportValidatorParametersParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.exportimport.api.validator.ExportImportValidatorParameters)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void validate(HttpPrincipal httpPrincipal,
		java.lang.String className,
		com.liferay.exportimport.api.validator.ExportImportValidatorParameters exportImportValidatorParameters)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportValidatorServiceUtil.class,
					"validate", _validateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, exportImportValidatorParameters);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportValidatorServiceHttp.class);
	private static final Class<?>[] _getRemoteLayoutLarTypeExportImportValidatorParametersParameterTypes0 =
		new Class[] { long.class };
	private static final Class<?>[] _getBuildNumberExportImportValidatorParametersParameterTypes1 =
		new Class[] {  };
	private static final Class<?>[] _validateParameterTypes2 = new Class[] {
			java.lang.String.class,
			com.liferay.exportimport.api.validator.ExportImportValidatorParameters.class
		};
}