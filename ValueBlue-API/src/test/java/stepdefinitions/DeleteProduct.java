package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.ConfigLoader;
import utils.LogClass;
import io.cucumber.java.Scenario;
import java.util.HashMap;
import java.util.Map;

public class DeleteProduct {
    private Response response;
    private String endpoint;
    private Map<String, String> headers = new HashMap<>();
    private ConfigLoader configLoader = new ConfigLoader();

    @Before
    public void setup(Scenario scenario) {
        LogClass.startTestCase(scenario.getName());

    }

    @Given("I set DELETE product API endpoint")
    public void iSetDeleteEndpoint() {
        endpoint = configLoader.getProperty("deleteEndPoint");
        LogClass.info("DELETE product API endpoint set to: " + endpoint);
    }

    @When("I set the delete request HEADER")
    public void iSetTheRequestHeader() {
        headers.put("Content-Type", "application/json");
        LogClass.info("Setting request headers for DELETE request.");
    }

    @And("I send DELETE HTTP request")
    public void iSendTheDeleteHttpRequest() {
        response = RestAssured.given().headers(headers).when().delete(endpoint);
        LogClass.info("DELETE request sent successfully.");
    }

    //While I expected a 204 (No Content) response, indicating successful deletion without returning any content,
    // the actual response was 200 (OK), indicating success with content returned
    @Then("I receive the valid HTTP response code 200")
    public void iReceiveTheValidHttpResponseCode200() {
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP status code 200 for successful delete operation with content.");
        boolean isDeleted = response.jsonPath().getBoolean("isDeleted");
        String deletedOn = response.jsonPath().getString("deletedOn");
        Assert.assertTrue(isDeleted, "Product marked as deleted.");
        LogClass.info("Product deleted successfully on: " + deletedOn);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {

            LogClass.info(scenario.getName() + " - Test FAILED");
        } else {
            LogClass.info(scenario.getName() + " - Test PASSED");
        }

        LogClass.endTestCase(scenario.getName());
    }
}
