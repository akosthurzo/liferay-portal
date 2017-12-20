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
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
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
	property = {"model.class.name=com.liferay.knowledge.base.model.KBArticle"},
	service = StagedModelRepository.class
)
public class KBArticleStagedModelRepository
	implements StagedModelRepository<KBArticle> {

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Override
	public KBArticle addStagedModel(
			PortletDataContext portletDataContext, KBArticle kbArticle)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBArticle kbArticle = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (kbArticle != null) {
			deleteStagedModel(kbArticle);
		}
	}

	@Override
	public void deleteStagedModel(KBArticle kbArticle) throws PortalException {
		_kbArticleLocalService.deleteKBArticle(kbArticle);
	}

	@Override
	public void deleteStagedModels(
		PortletDataContext portletDataContext) throws PortalException {

		_kbArticleLocalService.deleteGroupKBArticles(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public KBArticle fetchMissingReference(String uuid, long groupId) {
		return (KBArticle)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public KBArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbArticleLocalService.fetchKBArticleByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbArticleLocalService.getKBArticlesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _kbArticleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBArticle kbArticle)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBArticle saveStagedModel(KBArticle kbArticle)
		throws PortalException {

		return _kbArticleLocalService.updateKBArticle(kbArticle);
	}

	@Override
	public KBArticle updateStagedModel(
			PortletDataContext portletDataContext, KBArticle kbArticle)
		throws PortalException {

		return null;
	}
}
