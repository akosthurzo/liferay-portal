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

package com.liferay.journal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalFeed"},
	service = StagedModelRepository.class
)
public class JournalFeedStagedModelRepository
	implements StagedModelRepository<JournalFeed> {


	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

	@Override
	public JournalFeed addStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws PortalException {
		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFeed feed = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (feed != null) {
			deleteStagedModel(feed);
		}
	}

	@Override
	public void deleteStagedModel(JournalFeed feed) throws PortalException {

		_journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_journalFeedLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<JournalFeed>)
				feed -> _journalFeedLocalService.deleteJournalFeed(feed));

		actionableDynamicQuery.performActions();
	}

	@Override
	public JournalFeed fetchMissingReference(String uuid, long groupId) {
		return
			(JournalFeed)_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
			String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalFeedLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFeed saveStagedModel(JournalFeed feed)
		throws PortalException {

		return _journalFeedLocalService.updateJournalFeed(feed);
	}

	@Override
	public JournalFeed updateStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws PortalException {

		return null;
	}
}
