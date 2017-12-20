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

package com.liferay.users.admin.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.Address"},
	service = StagedModelRepository.class
)
public class AddressStagedModelRepository
	implements StagedModelRepository<Address> {

	@Override
	public Address addStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws PortalException {

		long userId = portletDataContext.getUserId(address.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			address);

		serviceContext.setUuid(address.getUuid());

		return _addressLocalService.addAddress(
			userId, address.getClassName(), address.getClassPK(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), address.getTypeId(), address.getMailing(),
			address.isPrimary(), serviceContext);
	}

	@Override
	public void deleteStagedModel(Address address) throws PortalException {
		_addressLocalService.deleteAddress(address);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Address address = _addressLocalService.fetchAddressByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (address != null) {
			deleteStagedModel(address);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_addressLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Address>)address ->
				_addressLocalService.deleteAddress(address));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Address fetchMissingReference(String uuid, long groupId) {
		return (Address)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public Address fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Address> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Address> addresses = new ArrayList<>();

		addresses.add(
			_addressLocalService.fetchAddressByUuidAndCompanyId(
				uuid, companyId));

		return addresses;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _addressLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Address stagedModel)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Address saveStagedModel(Address address) throws PortalException {
		return _addressLocalService.updateAddress(address);
	}

	@Override
	public Address updateStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws PortalException {

		return _addressLocalService.updateAddress(
			address.getAddressId(), address.getStreet1(), address.getStreet2(),
			address.getStreet3(), address.getCity(), address.getZip(),
			address.getRegionId(), address.getCountryId(), address.getTypeId(),
			address.getMailing(), address.isPrimary());
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}