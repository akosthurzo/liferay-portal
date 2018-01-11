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

package com.liferay.polls.internal.exportimport.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.polls.model.PollsQuestion"},
	service = {StagedModelRepository.class}
)
public class PollsQuestionStagedModelRepository
	implements StagedModelRepository<PollsQuestion> {

	@Override
	public PollsQuestion addStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsQuestion.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsQuestion);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(pollsQuestion.getUuid());
		}

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		Date expirationDate = pollsQuestion.getExpirationDate();

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);

			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		return _pollsQuestionLocalService.addQuestion(
			userId, pollsQuestion.getTitleMap(),
			pollsQuestion.getDescriptionMap(), expirationMonth, expirationDay,
			expirationYear, expirationHour, expirationMinute, neverExpire, null,
			serviceContext);
	}

	@Override
	public void deleteStagedModel(PollsQuestion pollsQuestion)
		throws PortalException {

		_pollsQuestionLocalService.deleteQuestion(pollsQuestion);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		PollsQuestion question = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (question != null) {
			deleteStagedModel(question);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_pollsQuestionLocalService.deleteQuestions(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public PollsQuestion fetchMissingReference(String uuid, long groupId) {
		return (PollsQuestion)_stagedModelRepositoryHelper.
			fetchMissingReference(uuid, groupId, this);
	}

	@Override
	public PollsQuestion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsQuestionLocalService.fetchPollsQuestionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsQuestion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _pollsQuestionLocalService.getPollsQuestionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsQuestion>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _pollsQuestionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion saveStagedModel(PollsQuestion pollsQuestion)
		throws PortalException {

		return _pollsQuestionLocalService.updatePollsQuestion(pollsQuestion);
	}

	@Override
	public PollsQuestion updateStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsQuestion.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsQuestion);

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		Date expirationDate = pollsQuestion.getExpirationDate();

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);

			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		return _pollsQuestionLocalService.updateQuestion(
			userId, pollsQuestion.getQuestionId(), pollsQuestion.getTitleMap(),
			pollsQuestion.getDescriptionMap(), expirationMonth, expirationDay,
			expirationYear, expirationHour, expirationMinute, neverExpire, null,
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {

		_pollsQuestionLocalService = pollsQuestionLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsQuestionStagedModelRepository.class);

	private PollsQuestionLocalService _pollsQuestionLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}