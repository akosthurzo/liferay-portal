package com.liferay.users.admin.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.model.Phone"
	},
	service = StagedModelRepository.class
)
public class PhoneStagedModelRepository
	implements StagedModelRepository<Phone> {

	@Override
	public Phone addStagedModel(
		PortletDataContext portletDataContext, Phone phone)
		throws PortalException {

		long userId = portletDataContext.getUserId(phone.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			phone);

		serviceContext.setUuid(phone.getUuid());

		return _phoneLocalService.addPhone(
			userId, phone.getClassName(), phone.getClassPK(),
			phone.getNumber(), phone.getExtension(), phone.getTypeId(),
			phone.isPrimary(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Phone phone = _phoneLocalService.fetchPhoneByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (phone != null) {
			deleteStagedModel(phone);
		}
	}

	@Override
	public void deleteStagedModel(Phone phone) throws PortalException {
		_phoneLocalService.deletePhone(phone);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_phoneLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Phone>) phone ->
				_phoneLocalService.deletePhone(phone));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Phone fetchMissingReference(String uuid, long groupId) {
		return (Phone)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public Phone fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Phone> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Phone> phones = new ArrayList<>();

		phones.add(
			_phoneLocalService.fetchPhoneByUuidAndCompanyId(uuid, companyId));

		return phones;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _phoneLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Phone stagedModel)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Phone saveStagedModel(Phone phone) throws PortalException {
		return _phoneLocalService.updatePhone(phone);
	}

	@Override
	public Phone updateStagedModel(
			PortletDataContext portletDataContext, Phone phone)
		throws PortalException {

		return _phoneLocalService.updatePhone(
			phone.getPhoneId(), phone.getNumber(), phone.getExtension(),
			phone.getTypeId(), phone.isPrimary());
	}

	@Reference
	private PhoneLocalService _phoneLocalService;
	@Reference
	private GroupLocalService _groupLocalService;
	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;
}
