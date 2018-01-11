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

package com.liferay.calendar.internal.exportimport.repository;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.calendar.model.Calendar"},
	service = {StagedModelRepository.class}
)
public class CalendarStagedModelRepository
	implements StagedModelRepository<Calendar> {

	@Override
	public Calendar addStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortalException {

		long userId = portletDataContext.getUserId(calendar.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			calendar);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(calendar.getUuid());
		}

		return _calendarLocalService.addCalendar(
			userId, portletDataContext.getScopeGroupId(),
			calendar.getCalendarResourceId(), calendar.getNameMap(),
			calendar.getDescriptionMap(), calendar.getTimeZoneId(),
			calendar.getColor(), calendar.isDefaultCalendar(),
			calendar.isEnableComments(), calendar.isEnableRatings(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(Calendar calendar) throws PortalException {
		_calendarLocalService.deleteCalendar(calendar);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Calendar calendar = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (calendar != null) {
			deleteStagedModel(calendar);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_calendarLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Calendar>)calendar ->
				_calendarLocalService.deleteCalendar(calendar));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Calendar fetchMissingReference(String uuid, long groupId) {
		return (Calendar)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public Calendar fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _calendarLocalService.fetchCalendarByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<Calendar> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _calendarLocalService.getCalendarsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Calendar>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _calendarLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Calendar saveStagedModel(Calendar calendar) throws PortalException {
		return _calendarLocalService.updateCalendar(calendar);
	}

	@Override
	public Calendar updateStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortalException {

		long userId = portletDataContext.getUserId(calendar.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			calendar);

		serviceContext.setUserId(userId);

		return _calendarLocalService.updateCalendar(
			calendar.getCalendarId(), calendar.getNameMap(),
			calendar.getDescriptionMap(), calendar.getTimeZoneId(),
			calendar.getColor(), calendar.isDefaultCalendar(),
			calendar.isEnableComments(), calendar.isEnableRatings(),
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setCalendarLocalService(
		CalendarLocalService calendarLocalService) {

		_calendarLocalService = calendarLocalService;
	}

	private CalendarLocalService _calendarLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}