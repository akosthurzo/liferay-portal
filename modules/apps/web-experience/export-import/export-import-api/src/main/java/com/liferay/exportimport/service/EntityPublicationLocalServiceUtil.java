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

package com.liferay.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for EntityPublication. This utility wraps
 * {@link com.liferay.exportimport.service.impl.EntityPublicationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see EntityPublicationLocalService
 * @see com.liferay.exportimport.service.base.EntityPublicationLocalServiceBaseImpl
 * @see com.liferay.exportimport.service.impl.EntityPublicationLocalServiceImpl
 * @generated
 */
@ProviderType
public class EntityPublicationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.exportimport.service.impl.EntityPublicationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the entity publication to the database. Also notifies the appropriate model listeners.
	*
	* @param entityPublication the entity publication
	* @return the entity publication that was added
	*/
	public static com.liferay.exportimport.model.EntityPublication addEntityPublication(
		com.liferay.exportimport.model.EntityPublication entityPublication) {
		return getService().addEntityPublication(entityPublication);
	}

	public static com.liferay.exportimport.model.EntityPublication addEntityPublication(
		long userId, long classNameId, long classPK,
		java.util.Date publicationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntityPublication(userId, classNameId, classPK,
			publicationDate);
	}

	public static com.liferay.exportimport.model.EntityPublication addEntityPublication(
		long userId, java.lang.String className, long classPK,
		java.util.Date publicationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntityPublication(userId, className, classPK,
			publicationDate);
	}

	/**
	* Creates a new entity publication with the primary key. Does not add the entity publication to the database.
	*
	* @param id the primary key for the new entity publication
	* @return the new entity publication
	*/
	public static com.liferay.exportimport.model.EntityPublication createEntityPublication(
		long id) {
		return getService().createEntityPublication(id);
	}

	/**
	* Deletes the entity publication from the database. Also notifies the appropriate model listeners.
	*
	* @param entityPublication the entity publication
	* @return the entity publication that was removed
	*/
	public static com.liferay.exportimport.model.EntityPublication deleteEntityPublication(
		com.liferay.exportimport.model.EntityPublication entityPublication) {
		return getService().deleteEntityPublication(entityPublication);
	}

	/**
	* Deletes the entity publication with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the entity publication
	* @return the entity publication that was removed
	* @throws PortalException if a entity publication with the primary key could not be found
	*/
	public static com.liferay.exportimport.model.EntityPublication deleteEntityPublication(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntityPublication(id);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.exportimport.model.impl.EntityPublicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.exportimport.model.impl.EntityPublicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.exportimport.model.EntityPublication fetchEntityPublication(
		long id) {
		return getService().fetchEntityPublication(id);
	}

	public static com.liferay.exportimport.model.EntityPublication fetchEntityPublication(
		long classNameId, long classPK) {
		return getService().fetchEntityPublication(classNameId, classPK);
	}

	public static com.liferay.exportimport.model.EntityPublication fetchEntityPublication(
		java.lang.String className, long classPK) {
		return getService().fetchEntityPublication(className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the entity publication with the primary key.
	*
	* @param id the primary key of the entity publication
	* @return the entity publication
	* @throws PortalException if a entity publication with the primary key could not be found
	*/
	public static com.liferay.exportimport.model.EntityPublication getEntityPublication(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntityPublication(id);
	}

	public static com.liferay.exportimport.model.EntityPublication getEntityPublication(
		long classNameId, long classPK)
		throws com.liferay.exportimport.exception.NoSuchEntityPublicationException {
		return getService().getEntityPublication(classNameId, classPK);
	}

	public static com.liferay.exportimport.model.EntityPublication getEntityPublication(
		java.lang.String className, long classPK)
		throws com.liferay.exportimport.exception.NoSuchEntityPublicationException {
		return getService().getEntityPublication(className, classPK);
	}

	public static java.util.List<com.liferay.exportimport.model.EntityPublication> getEntityPublications() {
		return getService().getEntityPublications();
	}

	/**
	* Returns a range of all the entity publications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.exportimport.model.impl.EntityPublicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of entity publications
	* @param end the upper bound of the range of entity publications (not inclusive)
	* @return the range of entity publications
	*/
	public static java.util.List<com.liferay.exportimport.model.EntityPublication> getEntityPublications(
		int start, int end) {
		return getService().getEntityPublications(start, end);
	}

	/**
	* Returns the number of entity publications.
	*
	* @return the number of entity publications
	*/
	public static int getEntityPublicationsCount() {
		return getService().getEntityPublicationsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the entity publication in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param entityPublication the entity publication
	* @return the entity publication that was updated
	*/
	public static com.liferay.exportimport.model.EntityPublication updateEntityPublication(
		com.liferay.exportimport.model.EntityPublication entityPublication) {
		return getService().updateEntityPublication(entityPublication);
	}

	public static com.liferay.exportimport.model.EntityPublication updateEntityPublication(
		long userId, long classNameId, long classPK,
		java.util.Date publicationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntityPublication(userId, classNameId, classPK,
			publicationDate);
	}

	public static com.liferay.exportimport.model.EntityPublication updateEntityPublication(
		long userId, java.lang.String className, long classPK,
		java.util.Date publicationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntityPublication(userId, className, classPK,
			publicationDate);
	}

	public static EntityPublicationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<EntityPublicationLocalService, EntityPublicationLocalService> _serviceTracker =
		ServiceTrackerFactory.open(EntityPublicationLocalService.class);
}