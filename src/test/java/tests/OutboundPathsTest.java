package tests;

import setup.Setup;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class OutboundPathsTest extends Setup {
    @Test(groups = {"api"})
    @SuppressWarnings("unchecked")
    public void validateOutboundPaths() {
        verifiedEndpoint = "/api/v2/outbound/messagingcampaigns";

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/docs/swagger")
                .then()
                .statusCode(200)
                .extract().response();

        //asserting at least 5 outbound paths exist
        Map<String,Object> allPaths = response.jsonPath().getMap("paths");
        assertNotNull(allPaths);
        long outboundPathCount = allPaths.keySet().stream()
            .filter(k -> k.contains("/outbound"))
            .count();
        assertTrue(outboundPathCount >= 5, "Expected at least 5 outbound paths, found " + outboundPathCount);

        // asserting messagingCampaign has GET and POST operations
        Map<String,Object> messagingCampaignPath = (Map<String, Object>) allPaths.get(verifiedEndpoint);
        assertNotNull(messagingCampaignPath, "Required path not found");
        assertNotNull(messagingCampaignPath.get("post"), "POST operation should exist");
        assertNotNull(messagingCampaignPath.get("get"), "GET operation should exist");

        // asserting GET operation has a 200 response and it's schema
        Map<String, Object> getSuccessResponse = response.jsonPath().getMap("paths.'" + verifiedEndpoint + "'.get.responses.200");
        assertNotNull(getSuccessResponse, "Success response not found");
        assertNotNull(getSuccessResponse.get("schema"), "GET 200 should reference a schema");

        // asserting POST operation has a request body schema
        List<Map<String, Object>> parameters = response.jsonPath().getList("paths.'" + verifiedEndpoint + "'.post.parameters");
        assertNotNull(parameters, "Missing parameters");
        assertFalse(parameters.isEmpty(), "Expected at least one parameter");
        Map<String, Object> bodyParam = parameters.get(0);
        assertEquals(bodyParam.get("in"), "body");
        assertNotNull(bodyParam.get("required"), "Required field should be present");
        assertTrue((boolean) bodyParam.get("required"));
        assertNotNull(bodyParam.get("schema"));

        // asserting MessagingCampaign schema has required properties
        Map<String, Object> props = response.jsonPath().getMap("definitions.MessagingCampaign.properties");
        assertTrue(props.containsKey("name"), "Missing property 'name'");
        assertTrue(props.containsKey("contactList"), "Missing property 'contactList'");
        assertTrue(props.containsKey("messagesPerMinute"), "Missing property 'messagesPerMinute'");
    }
}
