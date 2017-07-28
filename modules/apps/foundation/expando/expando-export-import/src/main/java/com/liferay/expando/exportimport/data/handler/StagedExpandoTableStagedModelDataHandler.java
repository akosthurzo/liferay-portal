package com.liferay.expando.exportimport.data.handler;

import com.lifeary.expando.exportimport.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

public class StagedExpandoTableStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedExpandoTable> {

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

	}

	@Override
	public void deleteStagedModel(StagedExpandoTable stagedModel)
		throws PortalException {

	}

	@Override
	public List<StagedExpandoTable> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public String[] getClassNames() {
		return new String[0];
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedExpandoTable)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			stagedExpandoTable);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(stagedExpandoTable),
			stagedExpandoTable);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedModel)
		throws Exception {


	}
}