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

package com.liferay.bookmarks.web.internal.portlet.action;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.changeset.ChangeSet;
import com.liferay.changeset.ChangeSetBuilder;
import com.liferay.exportimport.kernel.exception.LARFileNameException;
import com.liferay.exportimport.kernel.service.ExportImportService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS,
		"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS_ADMIN,
		"mvc.command.name=/bookmarks/export_entry"
	},
	service = MVCActionCommand.class
)
public class ExportEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String uuid = ParamUtil.getString(actionRequest, "uuid");

			long plid = ParamUtil.getLong(actionRequest, "plid");
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String fileName = "Bookmarks" + uuid + ".lar";

			ChangeSet changeSet = ChangeSetBuilder.changeSet(
			).with(
				() ->
					_bookmarksEntryLocalService.
						fetchBookmarksEntryByUuidAndGroupId(uuid, groupId)
			).build();

			_changeSetLocalService.exportChangeSet(changeSet);
		}
		catch (Exception e) {
			if (e instanceof LARFileNameException) {
				throw e;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportEntryMVCActionCommand.class);

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private ChangeSetLocalService _changeSetLocalService;

	@Reference
	private ExportImportService _exportImportService;

}