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

package com.liferay.exportimport.kernel.staging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Raymond Augé
 */
@ProviderType
public class StagingConstants {

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		PropsUtil.get("lock.expiration.time." + Staging.class.getName()));

	public static final String RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME =
		"RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME";

	public static final String STAGED_PORTLET = "staged-portlet_";

	public static final String STAGED_PREFIX = "staged--";

	public static final int TYPE_LOCAL_STAGING = 1;

	public static final int TYPE_NOT_STAGED = 0;

	public static final int TYPE_REMOTE_STAGING = 2;

}