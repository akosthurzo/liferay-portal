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

package com.liferay.expando.exportimport.data.handler;

import com.lifeary.expando.exportimport.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedExpandoColumnStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedExpandoColumn> {

	public static final String[] CLASS_NAMES =
		{StagedExpandoColumn.class.getName()};

	@Override
	public void deleteStagedModel(StagedExpandoColumn stagedExpandoColumn)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(stagedExpandoColumn);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<StagedExpandoColumn> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoColumn stagedExpandoColumn)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			stagedExpandoColumn);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(stagedExpandoColumn),
			stagedExpandoColumn);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoColumn stagedExpandoColumn)
		throws Exception {

		StagedExpandoColumn importedExpandoColumn =
			(StagedExpandoColumn)stagedExpandoColumn.clone();

		List<StagedExpandoColumn> stagedExpandoColumns =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				stagedExpandoColumn.getUuid(),
				portletDataContext.getCompanyId());

		if (ListUtil.isEmpty(stagedExpandoColumns)) {
			ExpandoTable expandoTable = _expandoTableLocalService.getTable(
				portletDataContext.getCompanyId(),
				stagedExpandoColumn.getExpandoTableClassName(),
				stagedExpandoColumn.getExpandoTableName());

			importedExpandoColumn.setTableId(expandoTable.getTableId());

			_stagedModelRepository.addStagedModel(
				portletDataContext, importedExpandoColumn);
		}
		else {
			StagedExpandoColumn existingExpandoColumn =
				stagedExpandoColumns.get(0);

			importedExpandoColumn.setColumnId(
				existingExpandoColumn.getColumnId());

			_stagedModelRepository.updateStagedModel(
				portletDataContext, importedExpandoColumn);
		}

	}

	@Reference(
		target = "(model.class.name=com.lifeary.expando.exportimport.model.adapter.StagedExpandoColumn)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<StagedExpandoColumn> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<StagedExpandoColumn> _stagedModelRepository;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}