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

package com.liferay.staging.taglib.action;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.taglib.exception.ExportImportEntityException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=exportImportEntity"
	},
	service = MVCActionCommand.class
)
public class ExportImportEntityMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_log.error("ExportImportEntityMVCActionCommand called!!");

		String cmd = ParamUtil.getString(actionRequest, "cmd");

//		if (cmd.equals(Constants.EXPORT) || cmd.equals(Constants.PUBLISH)) {
//			processExportAndPublishAction(actionRequest, actionResponse);
//		}
//		else if (cmd.equals(Constants.IMPORT)) {
//			processImportAction(actionRequest, actionResponse);
//		}

//		SessionErrors.add(actionRequest, Exception.class, "hiba");

		SessionErrors.add(actionRequest, ExportImportEntityException.class, new ExportImportEntityException(ExportImportEntityException.TYPE_INVALID_COMMAND));

		LiferayPortletURL renderURL =
			PortletURLFactoryUtil.create(
				actionRequest,
				"com_liferay_blogs_web_portlet_BlogsAdminPortlet",
				PortletRequest.RENDER_PHASE);

		renderURL.setParameter("mvcPath", "/blogs_admin/view.jsp");

		actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());
		sendRedirect(actionRequest, actionResponse);
//		String redirect = HttpUtil.setParameter(actionRequest.getParameter("backURL"),
//			"error", "invalid-command");

//		actionRequest.setAttribute(WebKeys.REDIRECT, actionRequest.getParameter("backURL"));
//		sendRedirect(actionRequest, actionResponse);
//		HttpServletRequest httpServletRequest =
//			PortalUtil.getHttpServletRequest(actionRequest);
//		HttpSession session = httpServletRequest.getSession();
//		session.setAttribute("error", "hiba");
//		actionResponse.setRenderParameter("mvcPath", actionRequest.getParameter("backURL"));
//		actionRequest.setAttribute(WebKeys.FORWARD_URL, actionRequest.getParameter("backURL"));

//		PortletSession portletSession = actionRequest.getPortletSession();
//
//		PortletContext portletContext = portletSession.getPortletContext();
//
//		PortletRequestDispatcher requestDispatcher =
//			portletContext.getRequestDispatcher(actionRequest.getParameter("backURL"));
//
//		requestDispatcher.forward(actionRequest, actionResponse);
	}

	private void processImportAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

	}

	private void processExportAndPublishAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		String[] classNameClassPKArray;

		if (Validator.isNotNull(
			actionRequest.getParameter("classNameClassPK"))) {

			classNameClassPKArray = ParamUtil.getStringValues(
				actionRequest, "classNameClassPK");
		}
		else if (Validator.isNotNull(actionRequest.getParameter("className")) &&
				 Validator.isNotNull(actionRequest.getParameter("classPK"))) {

			String className = ParamUtil.getString(actionRequest, "className");
			long classPK = ParamUtil.getLong(actionRequest, "classPK");

			classNameClassPKArray = new String[] {className + StringPool.POUND + classPK};
		}
		else {
			//TODO ERROR!!!
			return;
		}

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put("classNameClassPK", classNameClassPKArray);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, "cmd");

		if (cmd.equals("export")) {
			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.buildExportPortletSettingsMap(
					themeDisplay.getUser(), themeDisplay.getPlid(),
					themeDisplay.getScopeGroupId(),
					ExportImportPortletKeys.ENTITY_SET, parameterMap,
					"fajlnev.lar"); // TODO auto name

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.addDraftExportImportConfiguration(
					themeDisplay.getUserId(), "com_liferay_blogs_web_portlet_BlogsAdminPortlet", // TODO CONSTANT
					ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
					settingsMap);

			setRedirect(
				actionRequest, actionResponse,
				_exportImportLocalService.exportPortletInfoAsFileInBackground(
				themeDisplay.getUserId(), exportImportConfiguration));
		}
		else if (cmd.equals("publish")) {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (!scopeGroup.isStagingGroup() && !scopeGroup.isStagedRemotely()) {
				return;
				// TODO display error message
			}

			long liveGroupId = 0;

			if (scopeGroup.isStagingGroup()) {
				liveGroupId = scopeGroup.getLiveGroupId();
			}
			else if (scopeGroup.isStagedRemotely()) {
				liveGroupId = scopeGroup.getRemoteLiveGroupId();
			}

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.buildPublishPortletSettingsMap(
					themeDisplay.getUser(), themeDisplay.getScopeGroupId(),
					themeDisplay.getPlid(), liveGroupId, themeDisplay.getPlid(),
					ExportImportPortletKeys.ENTITY_SET, parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.addDraftExportImportConfiguration(
					themeDisplay.getUserId(), "com_liferay_blogs_web_portlet_BlogsAdminPortlet", // TODO CONSTANT
					ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET,
					settingsMap);

			setRedirect(
				actionRequest, actionResponse,
				_staging.publishPortlet(
					themeDisplay.getUserId(), exportImportConfiguration));
		}
	}

	protected void setRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long backgroundTaskId) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL renderURL = liferayPortletResponse.createRenderURL();

		renderURL.setParameter("mvcPath", "/view_export_import.jsp");
		renderURL.setParameter(
			"backgroundTaskId", String.valueOf(backgroundTaskId));
		renderURL.setParameter(
			"backURL", actionRequest.getParameter("backURL"));

		actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());

		hideDefaultSuccessMessage(actionRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportEntityMVCActionCommand.class);

	private class SimpleClassedModel implements ClassedModel {

		public SimpleClassedModel(Class<?> modelClass, Serializable primaryKeyObj) {
			_modelClass = modelClass;
			_modelClassName = modelClass.getName();
			_primaryKeyObj = primaryKeyObj;
		}

		public SimpleClassedModel(String modelClassName, Serializable primaryKeyObj) {
			_modelClassName = modelClassName;

			ClassName className =
				_classNameLocalService.getClassName(modelClassName);

			_modelClass = className.getModelClass();

			_primaryKeyObj = primaryKeyObj;
		}

		@Override
		public ExpandoBridge getExpandoBridge() {
			return null;
		}

		@Override
		public Class<?> getModelClass() {
			return _modelClass;
		}

		@Override
		public String getModelClassName() {
			return _modelClassName;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _primaryKeyObj;
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			_primaryKeyObj = primaryKeyObj;
		}

		private Class<?> _modelClass;
		private String _modelClassName;
		private Serializable _primaryKeyObj;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Staging _staging;
}