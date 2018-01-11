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
	property = {"model.class.name=com.liferay.polls.model.PollsVote"},
	service = {StagedModelRepository.class}
)
public class PollsVoteStagedModelRepository
	implements StagedModelRepository<PollsVote> {

	@Override
	public PollsVote addStagedModel(
			PortletDataContext portletDataContext, PollsVote pollsVote)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsVote.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsVote);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(pollsVote.getUuid());
		}

		return _pollsVoteLocalService.addVote(
			userId, pollsVote.getQuestionId(), pollsVote.getChoiceId(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(PollsVote pollsVote) throws PortalException {
		_pollsVoteLocalService.deletePollsVote(pollsVote);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		PollsVote pollsVote = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (pollsVote != null) {
			deleteStagedModel(pollsVote);
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
	public PollsVote fetchMissingReference(String uuid, long groupId) {
		return (PollsVote)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public PollsVote fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsVoteLocalService.fetchPollsVoteByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsVote> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _pollsVoteLocalService.getPollsVotesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsVote>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _pollsVoteLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, PollsVote pollsVote)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsVote saveStagedModel(PollsVote pollsVote)
		throws PortalException {

		return _pollsVoteLocalService.updatePollsVote(pollsVote);
	}

	@Override
	public PollsVote updateStagedModel(
			PortletDataContext portletDataContext, PollsVote pollsVote)
		throws PortalException {

		long userId = portletDataContext.getUserId(pollsVote.getUserUuid());

		pollsVote.setUserId(userId);

		return _pollsVoteLocalService.updatePollsVote(pollsVote);
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
		PollsVoteStagedModelRepository.class);

	private PollsChoiceLocalService _pollsChoiceLocalService;
	private PollsQuestionLocalService _pollsQuestionLocalService;
	private PollsVoteLocalService _pollsVoteLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}