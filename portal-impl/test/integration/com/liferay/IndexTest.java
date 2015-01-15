package com.liferay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.CompanyTestUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import java.io.Serializable;
import java.util.HashMap;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

@Sync(cleanTransaction = true)
public class IndexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testService() throws Exception {
		long companyId = _group.getCompanyId();
		long groupId = _group.getGroupId();
		User user = UserTestUtil.getAdminUser(companyId);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), groupId, "testName", "testDescription",
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					new HashMap<String, Serializable>(),
					ServiceContextTestUtil.getServiceContext());

		BaseModelSearchResult<ExportImportConfiguration> baseModelSearchResult = null;
		long startTime = System.currentTimeMillis();
		long endTime = 0;

		do {
			ExportImportConfigurationLocalServiceUtil.
				searchExportImportConfigurations(
					companyId, groupId,
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					StringPool.BLANK, null, 0, 10, null);

			endTime = System.currentTimeMillis();
		}
		while (((endTime - startTime) >= 10000) ||
			   (baseModelSearchResult.getBaseModels().size() > 0));

		System.out.println(
			baseModelSearchResult.getBaseModels().size() + " | " +
				(endTime - startTime));
	}

	@DeleteAfterTestRun
	private Group _group;
}
