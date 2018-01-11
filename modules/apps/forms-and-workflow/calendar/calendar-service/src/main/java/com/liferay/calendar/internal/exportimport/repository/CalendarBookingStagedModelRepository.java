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
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.calendar.model.CalendarBooking"},
	service = {StagedModelRepository.class}
)
public class CalendarBookingStagedModelRepository
	implements StagedModelRepository<CalendarBooking> {

	@Override
	public CalendarBooking addStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			calendarBooking.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			calendarBooking);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(calendarBooking.getUuid());
		}

		long[] childCalendarIds = new long[0];

		if (calendarBooking.isMasterBooking()) {
			Map<Long, Long> calendarIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Calendar.class);

			childCalendarIds = _getChildCalendarIds(
				portletDataContext, calendarIds, calendarBooking);
		}

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(calendarBooking.getUuid());
		}

		return _calendarBookingLocalService.addCalendarBooking(
			userId, calendarBooking.getCalendarId(), childCalendarIds,
			calendarBooking.getParentCalendarBookingId(),
			calendarBooking.getRecurringCalendarBookingId(),
			calendarBooking.getTitleMap(), calendarBooking.getDescriptionMap(),
			calendarBooking.getLocation(), calendarBooking.getStartTime(),
			calendarBooking.getEndTime(), calendarBooking.isAllDay(),
			calendarBooking.getRecurrence(), calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CalendarBooking calendarBooking)
		throws PortalException {

		_calendarBookingLocalService.deleteCalendarBooking(calendarBooking);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CalendarBooking calendarBooking = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (calendarBooking != null) {
			deleteStagedModel(calendarBooking);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_calendarBookingLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.
				PerformActionMethod<CalendarBooking>)calendarBooking ->
					_calendarBookingLocalService.deleteCalendarBooking(
						calendarBooking));

		actionableDynamicQuery.performActions();
	}

	@Override
	public CalendarBooking fetchMissingReference(String uuid, long groupId) {
		return (CalendarBooking)_stagedModelRepositoryHelper.
			fetchMissingReference(uuid, groupId, this);
	}

	@Override
	public CalendarBooking fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _calendarBookingLocalService.
			fetchCalendarBookingByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CalendarBooking> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _calendarBookingLocalService.
			getCalendarBookingsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CalendarBooking>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _calendarBookingLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking saveStagedModel(CalendarBooking calendarBooking)
		throws PortalException {

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	@Override
	public CalendarBooking updateStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			calendarBooking.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			calendarBooking);

		serviceContext.setUserId(userId);

		long[] childCalendarIds = new long[0];

		if (calendarBooking.isMasterBooking()) {
			Map<Long, Long> calendarIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Calendar.class);

			childCalendarIds = _getChildCalendarIds(
				portletDataContext, calendarIds, calendarBooking);
		}

		return _calendarBookingLocalService.updateCalendarBooking(
			userId, calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId(), childCalendarIds,
			calendarBooking.getTitleMap(), calendarBooking.getDescriptionMap(),
			calendarBooking.getLocation(), calendarBooking.getStartTime(),
			calendarBooking.getEndTime(), calendarBooking.isAllDay(),
			calendarBooking.getRecurrence(), calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(), serviceContext);
	}

	@Reference(unbind = "-")
	protected void setCalendarLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	private long[] _getChildCalendarIds(
		PortletDataContext portletDataContext, Map<Long, Long> calendarIds,
		CalendarBooking calendarBooking) {

		long[] childCalendarIds = new long[0];

		Element calendarBookingElement =
			portletDataContext.getImportDataElement(calendarBooking);

		List<Element> childElements = calendarBookingElement.elements("child");

		for (Element childElement : childElements) {
			long calendarId = Long.valueOf(
				childElement.attributeValue("calendarId"));

			calendarId = MapUtil.getLong(calendarIds, calendarId, calendarId);

			childCalendarIds = ArrayUtil.append(childCalendarIds, calendarId);
		}

		return childCalendarIds;
	}

	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}