package org.openstack4j.api.identity;

import static org.testng.Assert.assertEquals;

import org.openstack4j.api.AbstractTest;
import org.openstack4j.model.identity.Policy;

public class KeystonePolicyServiceTest extends AbstractTest {

    private static final String JSON_POLICIES_GET_BYID = "/identity/policies_get_byId.json";
    private static final String JSON_POLICIES_UPDATE = "/identity/policies_update_response.json";

    private static final String POLICY_ID = "13c92821e4c4476a878d3aae7444f52f";
    private static final String POLICY_BLOB_UPDATE = "{'admin': 'role:admin-user'}";
    private static final String POLICY_TYPE = "application/json";
    private static final String POLICY_PROJECT_ID = "123ac695d4db400a9001b91bb3b8aa46";
    private static final String POLICY_USER_ID = "aa9f25defa6d4cafb48466df83106065";

    @Override
    protected Service service() {
        return Service.IDENTITY;
    }

    // ------------ Policy Tests ------------

    // The following tests are to verify the update() method of the
    // PolicyService using HTTP PATCH, which is not supported by betamax.
    // Find more tests in KeystonePolicyServiceSpec in core-integration-test
    // module.

    public void policy_update_Test() throws Exception {

        respondWith(JSON_POLICIES_GET_BYID);

        Policy policy_setToUpdate = os().identity().policies().get(POLICY_ID);

        respondWith(JSON_POLICIES_UPDATE);

        Policy updatedPolicy = os().identity().policies()
                .update(policy_setToUpdate.toBuilder().blob(POLICY_BLOB_UPDATE).build());

        assertEquals(updatedPolicy.getId(), POLICY_ID);
        assertEquals(updatedPolicy.getBlob(), POLICY_BLOB_UPDATE);
        assertEquals(updatedPolicy.getProjectId(), POLICY_PROJECT_ID);
        assertEquals(updatedPolicy.getUserId(), POLICY_USER_ID);
        assertEquals(updatedPolicy.getType(), POLICY_TYPE);

    }

}
