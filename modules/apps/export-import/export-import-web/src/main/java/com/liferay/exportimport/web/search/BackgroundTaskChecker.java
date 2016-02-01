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

package com.liferay.exportimport.web.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Akos Thurzo
 */
public class BackgroundTaskChecker extends EmptyOnClickRowChecker {

	public BackgroundTaskChecker(PortletResponse portletResponse) {
		super(portletResponse);

		_portletResponse = portletResponse;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		String checkBoxRowIds = getEntryRowIds();
		String checkBoxAllRowIds = "'#" + getAllRowIds() + "'";

		return getRowCheckBox(
			request, checked, disabled,
			_portletResponse.getNamespace() + "backgroundTaskId", primaryKey,
			checkBoxRowIds, checkBoxAllRowIds, StringPool.BLANK);
	}

	protected String getEntryRowIds() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(_portletResponse.getNamespace() + "backgroundTaskId");

		return jsonArray.toString();
	}

	private final PortletResponse _portletResponse;

}