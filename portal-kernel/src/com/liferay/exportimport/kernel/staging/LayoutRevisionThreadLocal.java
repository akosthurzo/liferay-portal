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

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
public class LayoutRevisionThreadLocal {

	public static void changeAdditionInProgress(
		long layoutRevisionId, boolean inProgress) {

		Map<Long, Boolean> layoutRevisionAdditionInProgressMap =
			_layoutRevisionAdditionInProgressMap.get();

		layoutRevisionAdditionInProgressMap.put(layoutRevisionId, inProgress);
	}

	public static boolean isAdditionInProgress(long layoutRevisionId) {
		Map<Long, Boolean> layoutRevisionAdditionInProgressMap =
			_layoutRevisionAdditionInProgressMap.get();

		return MapUtil.getBoolean(
			layoutRevisionAdditionInProgressMap, layoutRevisionId, false);
	}

	private static final ThreadLocal<Map<Long, Boolean>>
		_layoutRevisionAdditionInProgressMap =
			new AutoResetThreadLocal<Map<Long, Boolean>>(
				LayoutRevisionThreadLocal.class +
					"._layoutRevisionAdditionInProgressMap",
				new HashMap<Long, Boolean>());

}