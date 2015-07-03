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

package com.liferay.site.teams.web.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.site.teams.web.constants.SiteTeamsPortletKeys;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + SiteTeamsPortletKeys.SITE_TEAMS_ADMIN},
	service = StagedModelDataHandler.class
)
public class TeamStagedModelDataHandler
	extends BaseStagedModelDataHandler<Team> {

	public static final String[] CLASS_NAMES = {Team.class.getName()};

	@Override
	public void deleteStagedModel(Team team) throws PortalException {
		TeamLocalServiceUtil.deleteTeam(team);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Team team = TeamLocalServiceUtil.fetchTeamByUuidAndGroupId(
			uuid, groupId);

		if (team != null) {
			deleteStagedModel(team);
		}
	}

	@Override
	public void doExportStagedModel(
			PortletDataContext portletDataContext, Team team)
		throws Exception {

		Element teamElement = portletDataContext.getExportDataElement(team);

		portletDataContext.addClassedModel(
			teamElement, ExportImportPathUtil.getModelPath(team), team);
	}

	@Override
	public List<Team> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return TeamLocalServiceUtil.getTeamsByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Team team)
		throws Exception {

		long userId = portletDataContext.getUserId(team.getUserUuid());

		Team existingTeam = TeamLocalServiceUtil.fetchTeamByUuidAndGroupId(
			team.getUuid(), portletDataContext.getScopeGroupId());

		Team importedTeam = null;

		if (existingTeam == null) {
			importedTeam = TeamLocalServiceUtil.addTeam(
				userId, portletDataContext.getScopeGroupId(), team.getName(),
				team.getDescription());
		}
		else {
			importedTeam = TeamLocalServiceUtil.updateTeam(
				existingTeam.getTeamId(), team.getName(),
				team.getDescription());
		}

		portletDataContext.importClassedModel(team, importedTeam);
	}

}