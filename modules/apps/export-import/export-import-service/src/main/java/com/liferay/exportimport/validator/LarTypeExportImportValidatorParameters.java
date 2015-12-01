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

package com.liferay.exportimport.validator;

import com.liferay.exportimport.api.validator.ExportImportValidatorParameters;

/**
 * @author Akos Thurzo
 */
public class LarTypeExportImportValidatorParameters
	implements ExportImportValidatorParameters {

	public LarTypeExportImportValidatorParameters(String exportImportType) {
		_exportImportType = exportImportType;
	}

	public LarTypeExportImportValidatorParameters(
		String exportImportType, boolean groupCompany,
		boolean groupHasStagingGroup, boolean groupLayoutPrototype,
		boolean groupLayoutSetPrototype, boolean groupStaged, String larType,
		String layoutsImportMode, boolean sourceGroupCompany,
		long sourceCompanyGroupId, long sourceGroupId) {

		this(exportImportType, larType);

		setGroupCompany(groupCompany);
		setGroupHasStagingGroup(groupHasStagingGroup);
		setGroupLayoutPrototype(groupLayoutPrototype);
		setGroupLayoutSetPrototype(groupLayoutSetPrototype);
		setGroupStaged(groupStaged);
		setLayoutsImportMode(layoutsImportMode);
		setSourceCompanyGroupId(sourceCompanyGroupId);
		setSourceGroupCompany(sourceGroupCompany);
		setSourceGroupId(sourceGroupId);
	}

	public LarTypeExportImportValidatorParameters(
		String exportImportType, String larType) {

		this(exportImportType);

		setLarType(larType);
	}

	public String getExportImportType() {
		return _exportImportType;
	}

	public String getLarType() {
		return _larType;
	}

	public String getLayoutsImportMode() {
		return _layoutsImportMode;
	}

	public long getSourceCompanyGroupId() {
		return _sourceCompanyGroupId;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public boolean isGroupCompany() {
		return _groupCompany;
	}

	public boolean isGroupHasStagingGroup() {
		return _groupHasStagingGroup;
	}

	public boolean isGroupLayoutPrototype() {
		return _groupLayoutPrototype;
	}

	public boolean isGroupLayoutSetPrototype() {
		return _groupLayoutSetPrototype;
	}

	public boolean isGroupStaged() {
		return _groupStaged;
	}

	public boolean isSourceGroupCompany() {
		return _sourceGroupCompany;
	}

	public void setExportImportType(String exportImportType) {
		_exportImportType = exportImportType;
	}

	public void setGroupCompany(boolean groupCompany) {
		_groupCompany = groupCompany;
	}

	public void setGroupHasStagingGroup(boolean groupHasStagingGroup) {
		_groupHasStagingGroup = groupHasStagingGroup;
	}

	public void setGroupLayoutPrototype(boolean groupLayoutPrototype) {
		_groupLayoutPrototype = groupLayoutPrototype;
	}

	public void setGroupLayoutSetPrototype(boolean groupLayoutSetPrototype) {
		_groupLayoutSetPrototype = groupLayoutSetPrototype;
	}

	public void setGroupStaged(boolean groupStaged) {
		_groupStaged = groupStaged;
	}

	public void setLarType(String larType) {
		_larType = larType;
	}

	public void setLayoutsImportMode(String layoutsImportMode) {
		_layoutsImportMode = layoutsImportMode;
	}

	public void setLocalParameters(
		String exportImportType, String larType, String layoutsImportMode,
		boolean sourceGroupCompany, long sourceCompanyGroupId,
		long sourceGroupId) {

		setExportImportType(exportImportType);
		setLarType(larType);
		setLayoutsImportMode(layoutsImportMode);
		setSourceGroupCompany(sourceGroupCompany);
		setSourceCompanyGroupId(sourceCompanyGroupId);
		setSourceGroupId(sourceGroupId);
	}

	public void setRemoteParameters(
		boolean groupCompany, boolean groupHasStagingGroup,
		boolean groupLayoutPrototype, boolean groupLayoutSetPrototype,
		boolean groupStaged) {

		setGroupCompany(groupCompany);
		setGroupHasStagingGroup(groupHasStagingGroup);
		setGroupLayoutPrototype(groupLayoutPrototype);
		setGroupLayoutSetPrototype(groupLayoutSetPrototype);
		setGroupStaged(groupStaged);
	}

	public void setSourceCompanyGroupId(long sourceCompanyGroupId) {
		_sourceCompanyGroupId = sourceCompanyGroupId;
	}

	public void setSourceGroupCompany(boolean sourceGroupCompany) {
		_sourceGroupCompany = sourceGroupCompany;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	private String _exportImportType;
	private boolean _groupCompany;
	private boolean _groupHasStagingGroup;
	private boolean _groupLayoutPrototype;
	private boolean _groupLayoutSetPrototype;
	private boolean _groupStaged;
	private String _larType;
	private String _layoutsImportMode;
	private long _sourceCompanyGroupId;
	private boolean _sourceGroupCompany;
	private long _sourceGroupId;

}