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

package com.liferay.knowledge.base.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBCommentLocalService;
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
	property = {"model.class.name=com.liferay.knowledge.base.model.KBComment"},
	service = StagedModelRepository.class
)
public class KBCommentStagedModelRepository
	implements StagedModelRepository<KBComment> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private KBCommentLocalService _kbCommentLocalService;

	@Override
	public KBComment addStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBComment kbComment = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (kbComment != null) {
			deleteStagedModel(kbComment);
		}
	}

	@Override
	public void deleteStagedModel(KBComment kbComment) throws PortalException {
		_kbCommentLocalService.deleteKBComment(kbComment);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_kbCommentLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<KBComment>) kbComment ->
				_kbCommentLocalService.deleteKBComment(kbComment));

		actionableDynamicQuery.performActions();
	}

	@Override
	public KBComment fetchMissingReference(String uuid, long groupId) {
		return (KBComment)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public KBComment fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbCommentLocalService.fetchKBCommentByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBComment> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbCommentLocalService.getKBCommentsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _kbCommentLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBComment saveStagedModel(KBComment kbComment)
		throws PortalException {

		return _kbCommentLocalService.updateKBComment(kbComment);
	}

	@Override
	public KBComment updateStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortalException {

		return null;
	}
}
