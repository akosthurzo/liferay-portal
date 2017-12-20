package com.liferay.users.admin.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class AddressStagedModelRepositoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testDeleteStagedModels() throws Exception {
		Company company1 = CompanyTestUtil.addCompany();

		User user1 = UserTestUtil.addCompanyAdminUser(company1);

		Organization organization1 =
			OrganizationLocalServiceUtil.addOrganization(
				user1.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), false);

		Address address1 = OrganizationTestUtil.addAddress(organization1);

		Assert.assertEquals(
			address1,
			AddressLocalServiceUtil.fetchAddress(address1.getAddressId()));

		Company company2 = CompanyTestUtil.addCompany();

		User user2 = UserTestUtil.addCompanyAdminUser(company2);

		Organization organization2 =
			OrganizationLocalServiceUtil.addOrganization(
				user2.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), false);

		Address address2 = OrganizationTestUtil.addAddress(organization2);

		Assert.assertEquals(
			address2,
			AddressLocalServiceUtil.fetchAddress(address2.getAddressId()));

		StagedModelRepository<?> stagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				Address.class.getName());

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				company1.getCompanyId(), organization1.getGroupId(),
				new HashMap<>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		stagedModelRepository.deleteStagedModels(portletDataContext);

		Assert.assertEquals(
			null,
			AddressLocalServiceUtil.fetchAddress(address1.getAddressId()));

		Assert.assertEquals(
			address2,
			AddressLocalServiceUtil.fetchAddress(address2.getAddressId()));
	}
}
