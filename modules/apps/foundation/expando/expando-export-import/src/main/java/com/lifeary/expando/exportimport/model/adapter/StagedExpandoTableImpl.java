package com.lifeary.expando.exportimport.model.adapter;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoTableImpl implements StagedExpandoTable {

	public StagedExpandoTableImpl(ExpandoTable expandoTable) {
		/////////////////////////////////////////////////////////////////////////
	}

	@Override
	public boolean isDefaultTable() {
		return false;
	}

	@Override
	public long getPrimaryKey() {
		return 0;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {

	}

	@Override
	public long getTableId() {
		return 0;
	}

	@Override
	public void setTableId(long tableId) {

	}

	@Override
	public long getCompanyId() {
		return 0;
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return null;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return null;
	}

	@Override
	public String getUuid() {
		return getClassName() + StringPool.POUND + getName();
	}

	@Override
	public void setCompanyId(long companyId) {

	}

	@Override
	public void setCreateDate(Date date) {

	}

	@Override
	public void setModifiedDate(Date date) {

	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClassName() {
		return null;////////////////////////////////////////////////////////////////// .getClassname
	}

	@Override
	public void setClassName(String className) {

	}

	@Override
	public long getClassNameId() {
		return 0;
	}

	@Override
	public void setClassNameId(long classNameId) {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void resetOriginalValues() {

	}

	@Override
	public void setNew(boolean n) {

	}

	@Override
	public boolean isCachedModel() {
		return false;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return false;
	}

	@Override
	public void setCachedModel(boolean cachedModel) {

	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return false;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {

	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

	@Override
	public String getModelClassName() {
		return null;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return null;
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {

	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {

	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {

	}

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {

	}

	@Override
	public int compareTo(ExpandoTable expandoTable) {
		return 0;
	}

	@Override
	public CacheModel<ExpandoTable> toCacheModel() {
		return null;
	}

	@Override
	public ExpandoTable toEscapedModel() {
		return null;
	}

	@Override
	public ExpandoTable toUnescapedModel() {
		return null;
	}

	@Override
	public String toXmlString() {
		return null;
	}

	@Override
	public void persist() {

	}

	private ExpandoTable _expandoTable;
}
