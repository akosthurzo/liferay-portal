package com.lifeary.expando.exportimport.model.adapter;

import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoTableModelAdapterBuilder
	implements ModelAdapterBuilder<ExpandoTable, StagedExpandoTable> {

	@Override
	public StagedExpandoTable build(ExpandoTable expandoTable) {
		return new StagedExpandoTableImpl(expandoTable);
	}
}
