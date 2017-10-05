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

package com.liferay.changeset.internal.exportimport.data.handler;

import com.liferay.changeset.ChangeSet;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true, property = {"javax.portlet.name=ChangeSetPortlet"},
	service = PortletDataHandler.class
)
public class ChangeSetPortletDataHandler implements PortletDataHandler {

	@Override
	public PortletPreferences addDefaultData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		Element rootElement = null;

		try {
			rootElement = portletDataContext.getExportDataRootElement();

			portletDataContext.addDeletionSystemEventStagedModelTypes(
				getDeletionSystemEventStagedModelTypes());

			for (PortletDataHandlerControl portletDataHandlerControl :
					getExportControls()) {

				portletDataContext.addUncheckedModelAdditionCount(
					portletDataHandlerControl);
			}

			return doExportData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
			//throw _handleException(
			//	e, PortletDataException.EXPORT_PORTLET_DATA, portletId);
		}
		finally {
			portletDataContext.setExportDataRootElement(rootElement);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Exported portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public DataLevel getDataLevel() {
		return null;
	}

	@Override
	public String[] getDataPortletPreferences() {
		return new String[0];
	}

	@Override
	public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		return new StagedModelType[0];
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean privateLayout)
		throws Exception {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid,
			boolean privateLayout)
		throws Exception {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public long getExportModelCount(ManifestSummary manifestSummary) {
		return 0;
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary) {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions) {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[0];
	}

	@Override
	public String getPortletId() {
		return null;
	}

	@Override
	public int getRank() {
		return 0;
	}

	@Override
	public String getSchemaVersion() {
		return null;
	}

	@Override
	public String getServiceName() {
		return null;
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		return null;
	}

	@Override
	public boolean isDataAlwaysStaged() {
		return false;
	}

	@Override
	public boolean isDataLocalized() {
		return false;
	}

	@Override
	public boolean isDataPortalLevel() {
		return false;
	}

	@Override
	public boolean isDataPortletInstanceLevel() {
		return false;
	}

	@Override
	public boolean isDataSiteLevel() {
		return false;
	}

	@Override
	public boolean isDisplayPortlet() {
		return false;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return false;
	}

	@Override
	public boolean isRollbackOnException() {
		return false;
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException {
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Override
	public void setPortletId(String portletId) {
	}

	@Override
	public void setRank(int rank) {
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		return false;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = portletDataContext.addExportDataRootElement();

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ChangeSet changeSet = null;

		changeSet.stream(
		).map(
			cm -> (StagedModel)cm
		).forEach(
			sm -> {
				try {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, sm);
				}
				catch (PortletDataException pde) {
					pde.printStackTrace();
				}
			}
		);

		return portletDataContext.getExportDataRootElementString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangeSetPortletDataHandler.class);

}