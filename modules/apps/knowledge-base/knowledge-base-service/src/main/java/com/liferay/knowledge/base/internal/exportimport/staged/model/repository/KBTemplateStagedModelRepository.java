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
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.knowledge.base.model.KBTemplate"},
	service = StagedModelRepository.class
)
public class KBTemplateStagedModelRepository
	implements StagedModelRepository<KBTemplate>{

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
	@Reference
	private KBTemplateLocalService _kbTemplateLocalService;


	@Override
	public KBTemplate addStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortalException {

		long userId = portletDataContext.getUserId(kbTemplate.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			kbTemplate);

		serviceContext.setUuid(kbTemplate.getUuid());

		return _kbTemplateLocalService.addKBTemplate(
			userId, kbTemplate.getTitle(), kbTemplate.getContent(),
			serviceContext);;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBTemplate kbTemplate = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (kbTemplate != null) {
			deleteStagedModel(kbTemplate);
		}
	}

	@Override
	public void deleteStagedModel(KBTemplate kbTemplate)
		throws PortalException {

		_kbTemplateLocalService.deleteKBTemplate(kbTemplate);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_kbTemplateLocalService.deleteGroupKBTemplates(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public KBTemplate fetchMissingReference(String uuid, long groupId) {
		return (KBTemplate)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public KBTemplate fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbTemplateLocalService.fetchKBTemplateByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBTemplate> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbTemplateLocalService.getKBTemplatesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _kbTemplateLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBTemplate saveStagedModel(KBTemplate kbTemplate)
		throws PortalException {

		return _kbTemplateLocalService.updateKBTemplate(kbTemplate);
	}

	@Override
	public KBTemplate updateStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			kbTemplate);

		return _kbTemplateLocalService.updateKBTemplate(
			kbTemplate.getKbTemplateId(), kbTemplate.getTitle(),
			kbTemplate.getContent(), serviceContext);;
	}
}
