/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventModel;
import com.liferay.portlet.calendar.model.CalEventSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the CalEvent service. Represents a row in the &quot;CalEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.calendar.model.CalEventModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CalEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventImpl
 * @see com.liferay.portlet.calendar.model.CalEvent
 * @see com.liferay.portlet.calendar.model.CalEventModel
 * @generated
 */
@JSON(strict = true)
public class CalEventModelImpl extends BaseModelImpl<CalEvent>
	implements CalEventModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cal event model instance should use the {@link com.liferay.portlet.calendar.model.CalEvent} interface instead.
	 */
	public static final String TABLE_NAME = "CalEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "eventId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "title", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "location", Types.VARCHAR },
			{ "startDate", Types.TIMESTAMP },
			{ "endDate", Types.TIMESTAMP },
			{ "durationHour", Types.INTEGER },
			{ "durationMinute", Types.INTEGER },
			{ "allDay", Types.BOOLEAN },
			{ "timeZoneSensitive", Types.BOOLEAN },
			{ "type_", Types.VARCHAR },
			{ "repeating", Types.BOOLEAN },
			{ "recurrence", Types.CLOB },
			{ "remindBy", Types.INTEGER },
			{ "firstReminder", Types.INTEGER },
			{ "secondReminder", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table CalEvent (uuid_ VARCHAR(75) null,eventId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,description STRING null,location STRING null,startDate DATE null,endDate DATE null,durationHour INTEGER,durationMinute INTEGER,allDay BOOLEAN,timeZoneSensitive BOOLEAN,type_ VARCHAR(75) null,repeating BOOLEAN,recurrence TEXT null,remindBy INTEGER,firstReminder INTEGER,secondReminder INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table CalEvent";
	public static final String ORDER_BY_JPQL = " ORDER BY calEvent.startDate ASC, calEvent.title ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CalEvent.startDate ASC, CalEvent.title ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CalEvent toModel(CalEventSoap soapModel) {
		CalEvent model = new CalEventImpl();

		model.setUuid(soapModel.getUuid());
		model.setEventId(soapModel.getEventId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setLocation(soapModel.getLocation());
		model.setStartDate(soapModel.getStartDate());
		model.setEndDate(soapModel.getEndDate());
		model.setDurationHour(soapModel.getDurationHour());
		model.setDurationMinute(soapModel.getDurationMinute());
		model.setAllDay(soapModel.getAllDay());
		model.setTimeZoneSensitive(soapModel.getTimeZoneSensitive());
		model.setType(soapModel.getType());
		model.setRepeating(soapModel.getRepeating());
		model.setRecurrence(soapModel.getRecurrence());
		model.setRemindBy(soapModel.getRemindBy());
		model.setFirstReminder(soapModel.getFirstReminder());
		model.setSecondReminder(soapModel.getSecondReminder());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CalEvent> toModels(CalEventSoap[] soapModels) {
		List<CalEvent> models = new ArrayList<CalEvent>(soapModels.length);

		for (CalEventSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return CalEvent.class;
	}

	public String getModelClassName() {
		return CalEvent.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.calendar.model.CalEvent"));

	public CalEventModelImpl() {
	}

	public long getPrimaryKey() {
		return _eventId;
	}

	public void setPrimaryKey(long primaryKey) {
		setEventId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_eventId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getEventId() {
		return _eventId;
	}

	public void setEventId(long eventId) {
		_eventId = eventId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	public void setTitle(String title) {
		_title = title;
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	public String getLocation() {
		if (_location == null) {
			return StringPool.BLANK;
		}
		else {
			return _location;
		}
	}

	public void setLocation(String location) {
		_location = location;
	}

	@JSON
	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@JSON
	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@JSON
	public int getDurationHour() {
		return _durationHour;
	}

	public void setDurationHour(int durationHour) {
		_durationHour = durationHour;
	}

	@JSON
	public int getDurationMinute() {
		return _durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		_durationMinute = durationMinute;
	}

	@JSON
	public boolean getAllDay() {
		return _allDay;
	}

	public boolean isAllDay() {
		return _allDay;
	}

	public void setAllDay(boolean allDay) {
		_allDay = allDay;
	}

	@JSON
	public boolean getTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public boolean isTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		_timeZoneSensitive = timeZoneSensitive;
	}

	@JSON
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	@JSON
	public boolean getRepeating() {
		return _repeating;
	}

	public boolean isRepeating() {
		return _repeating;
	}

	public void setRepeating(boolean repeating) {
		_repeating = repeating;
	}

	@JSON
	public String getRecurrence() {
		if (_recurrence == null) {
			return StringPool.BLANK;
		}
		else {
			return _recurrence;
		}
	}

	public void setRecurrence(String recurrence) {
		_recurrence = recurrence;
	}

	@JSON
	public int getRemindBy() {
		return _remindBy;
	}

	public void setRemindBy(int remindBy) {
		_remindBy = remindBy;
	}

	@JSON
	public int getFirstReminder() {
		return _firstReminder;
	}

	public void setFirstReminder(int firstReminder) {
		_firstReminder = firstReminder;
	}

	@JSON
	public int getSecondReminder() {
		return _secondReminder;
	}

	public void setSecondReminder(int secondReminder) {
		_secondReminder = secondReminder;
	}

	@Override
	public CalEvent toEscapedModel() {
		if (isEscapedModel()) {
			return (CalEvent)this;
		}
		else {
			return (CalEvent)Proxy.newProxyInstance(_classLoader,
				_escapedModelProxyInterfaces, new AutoEscapeBeanHandler(this));
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					CalEvent.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		CalEventImpl calEventImpl = new CalEventImpl();

		calEventImpl.setUuid(getUuid());
		calEventImpl.setEventId(getEventId());
		calEventImpl.setGroupId(getGroupId());
		calEventImpl.setCompanyId(getCompanyId());
		calEventImpl.setUserId(getUserId());
		calEventImpl.setUserName(getUserName());
		calEventImpl.setCreateDate(getCreateDate());
		calEventImpl.setModifiedDate(getModifiedDate());
		calEventImpl.setTitle(getTitle());
		calEventImpl.setDescription(getDescription());
		calEventImpl.setLocation(getLocation());
		calEventImpl.setStartDate(getStartDate());
		calEventImpl.setEndDate(getEndDate());
		calEventImpl.setDurationHour(getDurationHour());
		calEventImpl.setDurationMinute(getDurationMinute());
		calEventImpl.setAllDay(getAllDay());
		calEventImpl.setTimeZoneSensitive(getTimeZoneSensitive());
		calEventImpl.setType(getType());
		calEventImpl.setRepeating(getRepeating());
		calEventImpl.setRecurrence(getRecurrence());
		calEventImpl.setRemindBy(getRemindBy());
		calEventImpl.setFirstReminder(getFirstReminder());
		calEventImpl.setSecondReminder(getSecondReminder());

		calEventImpl.resetOriginalValues();

		return calEventImpl;
	}

	public int compareTo(CalEvent calEvent) {
		int value = 0;

		value = DateUtil.compareTo(getStartDate(), calEvent.getStartDate());

		if (value != 0) {
			return value;
		}

		value = getTitle().toLowerCase()
					.compareTo(calEvent.getTitle().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CalEvent calEvent = null;

		try {
			calEvent = (CalEvent)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = calEvent.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		CalEventModelImpl calEventModelImpl = this;

		calEventModelImpl._originalUuid = calEventModelImpl._uuid;

		calEventModelImpl._originalGroupId = calEventModelImpl._groupId;

		calEventModelImpl._setOriginalGroupId = false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", eventId=");
		sb.append(getEventId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", location=");
		sb.append(getLocation());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", durationHour=");
		sb.append(getDurationHour());
		sb.append(", durationMinute=");
		sb.append(getDurationMinute());
		sb.append(", allDay=");
		sb.append(getAllDay());
		sb.append(", timeZoneSensitive=");
		sb.append(getTimeZoneSensitive());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", repeating=");
		sb.append(getRepeating());
		sb.append(", recurrence=");
		sb.append(getRecurrence());
		sb.append(", remindBy=");
		sb.append(getRemindBy());
		sb.append(", firstReminder=");
		sb.append(getFirstReminder());
		sb.append(", secondReminder=");
		sb.append(getSecondReminder());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(73);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.calendar.model.CalEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>eventId</column-name><column-value><![CDATA[");
		sb.append(getEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>location</column-name><column-value><![CDATA[");
		sb.append(getLocation());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationHour</column-name><column-value><![CDATA[");
		sb.append(getDurationHour());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationMinute</column-name><column-value><![CDATA[");
		sb.append(getDurationMinute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allDay</column-name><column-value><![CDATA[");
		sb.append(getAllDay());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timeZoneSensitive</column-name><column-value><![CDATA[");
		sb.append(getTimeZoneSensitive());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>repeating</column-name><column-value><![CDATA[");
		sb.append(getRepeating());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recurrence</column-name><column-value><![CDATA[");
		sb.append(getRecurrence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>remindBy</column-name><column-value><![CDATA[");
		sb.append(getRemindBy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>firstReminder</column-name><column-value><![CDATA[");
		sb.append(getFirstReminder());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>secondReminder</column-name><column-value><![CDATA[");
		sb.append(getSecondReminder());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = CalEvent.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			CalEvent.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _eventId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private String _location;
	private Date _startDate;
	private Date _endDate;
	private int _durationHour;
	private int _durationMinute;
	private boolean _allDay;
	private boolean _timeZoneSensitive;
	private String _type;
	private boolean _repeating;
	private String _recurrence;
	private int _remindBy;
	private int _firstReminder;
	private int _secondReminder;
	private transient ExpandoBridge _expandoBridge;
}