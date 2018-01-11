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
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalService;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.polls.service.PollsVoteLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.polls.model.PollsChoice"},
	service = {StagedModelRepository.class}
)
public class PollsChoiceStagedModelRepository
	implements StagedModelRepository<PollsChoice> {

	@Override
	public PollsChoice addStagedModel(
			PortletDataContext portletDataContext, PollsChoice pollsChoice)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsChoice.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsChoice);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(pollsChoice.getUuid());
		}

		return _pollsChoiceLocalService.addChoice(
			userId, pollsChoice.getQuestionId(), pollsChoice.getName(),
			pollsChoice.getDescription(), serviceContext);
	}

	@Override
	public void deleteStagedModel(PollsChoice pollsChoice)
		throws PortalException {

		_pollsChoiceLocalService.deletePollsChoice(pollsChoice);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		PollsChoice pollsChoice = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (pollsChoice != null) {
			deleteStagedModel(pollsChoice);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		List<PollsQuestion> pollsQuestions =
			_pollsQuestionLocalService.getQuestions(
				portletDataContext.getScopeGroupId());

		for (PollsQuestion pollsQuestion : pollsQuestions) {
			for (PollsChoice pollsChoice :
					_pollsChoiceLocalService.getChoices(
						pollsQuestion.getQuestionId())) {

				_pollsChoiceLocalService.deletePollsChoice(pollsChoice);

				for (PollsVote pollsVote :
						_pollsVoteLocalService.getChoiceVotes(
							pollsChoice.getChoiceId(), QueryUtil.ALL_POS,
							QueryUtil.ALL_POS)) {

					_pollsVoteLocalService.deletePollsVote(pollsVote);
				}
			}
		}
	}

	@Override
	public PollsChoice fetchMissingReference(String uuid, long groupId) {
		return (PollsChoice)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public PollsChoice fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsChoiceLocalService.fetchPollsChoiceByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsChoice> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _pollsChoiceLocalService.getPollsChoicesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsChoice>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _pollsChoiceLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, PollsChoice pollsChoice)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsChoice saveStagedModel(PollsChoice pollsChoice)
		throws PortalException {

		return _pollsChoiceLocalService.updatePollsChoice(pollsChoice);
	}

	@Override
	public PollsChoice updateStagedModel(
			PortletDataContext portletDataContext, PollsChoice pollsChoice)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsChoice.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsChoice);

		return _pollsChoiceLocalService.updateChoice(
			pollsChoice.getChoiceId(), pollsChoice.getQuestionId(),
			pollsChoice.getName(), pollsChoice.getDescription(),
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setPollsChoiceLocalService(
		PollsChoiceLocalService pollsChoiceLocalService) {

		_pollsChoiceLocalService = pollsChoiceLocalService;
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {

		_pollsQuestionLocalService = pollsQuestionLocalService;
	}

	@Reference(unbind = "-")
	protected void setPollsVoteLocalService(
		PollsVoteLocalService pollsVoteLocalService) {

		_pollsVoteLocalService = pollsVoteLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsChoiceStagedModelRepository.class);

	private PollsChoiceLocalService _pollsChoiceLocalService;
	private PollsQuestionLocalService _pollsQuestionLocalService;
	private PollsVoteLocalService _pollsVoteLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}