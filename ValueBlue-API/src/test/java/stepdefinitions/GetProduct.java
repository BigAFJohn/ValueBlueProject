package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.ConfigLoader;
import utils.LogClass;

public class GetProduct {
    private String endpoint;
    private Response response;
    private ConfigLoader configLoader;

    public GetProduct() {
        configLoader = new ConfigLoader();
    }

    @Given("I set GET product API endpoint")
    public void isetGetProductApiEndpoint() {
        try {
            endpoint = configLoader.getProperty("getEndPoint");
            LogClass.info("GET product API endpoint set to: " + endpoint);
        } catch (Exception e) {
            LogClass.error("Failed to set GET product API endpoint: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve endpoint from config", e);
        }
    }

    @When("I set the GET request HEADER")
    public void iSetTheRequestHeader() {
        try {
            LogClass.info("Setting request headers.");
            response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get(endpoint);
            LogClass.info("Headers set and GET request sent to: " + endpoint);
        } catch (Exception e) {
            LogClass.error("Failed to set request headers or send GET request: " + e.getMessage());
            throw e;
        }
    }

    @Then("I receive a valid HTTP response code 200")
    public void iAssertHttpResponseCode() {
        try {
            int statusCode = response.getStatusCode();
            LogClass.info("Received HTTP response code: " + statusCode);
            Assert.assertEquals(statusCode, 200, "Expected HTTP status code 200, but received: " + statusCode);
        } catch (AssertionError e) {
            LogClass.error("HTTP response code validation failed: " + e.getMessage());
            throw e;
        }
    }

    @And("Response body contains the product brand")
    public void iAssertResponseBodyContainsProduct() {
        try {
            String expectedProductName = configLoader.getProperty("expectedProductName");
            String actualProductName = response.jsonPath().getString("title");
            Assert.assertEquals(actualProductName, expectedProductName, "The actual product name does not match the expected.");
            LogClass.info("Response body validation passed: Product name matches the expected value.");
        } catch (AssertionError e) {
            LogClass.error("Validation for product name in response body failed: " + e.getMessage());
            throw e;
        }
    }
}
