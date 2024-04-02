package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.ConfigLoader;
import utils.JsonUtil;
import utils.LogClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpdateProduct {
    private Response response;
    private String endpoint;
    private Map<String, String> headers = new HashMap<>();
    private String requestBody;
    private ConfigLoader configLoader = new ConfigLoader();
    private JsonUtil jsonUtil = new JsonUtil();
    private RequestSpecification request;

    public UpdateProduct() {
        String updateEndpoint = configLoader.getProperty("updateRequestEndPoint");
        String jsonFilePath = "src/test/resources/json/updateRequest.json";
        requestBody = jsonUtil.readJsonFromFile(jsonFilePath).orElseThrow(() -> new IllegalStateException("JSON file not found"));
    }

    // step definitions

    @Given("I set PUT product API endpoint")
    public void iSetPutProductApiEndpoint() {
        endpoint = configLoader.getProperty("updateRequestEndPoint");
        String jsonFilePath = "src/test/resources/json/updateRequest.json";
        String jsonBody = jsonUtil.readJsonFromFile(jsonFilePath).orElseThrow(() -> new IllegalStateException("JSON file not found"));
        // Initialize request
        request = RestAssured.given()
                .baseUri(endpoint)
                .header("Content-Type", "application/json")
                .body(jsonBody);
        LogClass.info("PUT product API endpoint set: " + endpoint);
    }

    @When("I set request HEADER and send PUT HTTP request")
    public void iSetRequestHeaderAndSendPutHttpRequest() {
        // Headers are already set during request initialization, so directly send the request
        response = request.when().put();
        LogClass.info("Sending PUT HTTP request.");
    }


    @And("I set request body with updated product data")
    public void iSetRequestBodyWithUpdatedProductData() {
        String jsonFilePath = "src/test/resources/json/updateRequest.json";
        Optional<String> jsonBodyOptional = jsonUtil.readJsonFromFile(jsonFilePath);
        if(jsonBodyOptional.isPresent()) {
            requestBody = jsonBodyOptional.get();
            LogClass.info("Request body for update set with data: " + requestBody);
        } else {
            throw new RuntimeException("Failed to read JSON from file: " + jsonFilePath);
        }
    }

    @When("I send PUT HTTP request")
    public void iSendPutHttpRequest() {
        response = RestAssured.given().headers(headers).body(requestBody).when().put(endpoint);
        LogClass.info("Sending PUT HTTP request to: " + endpoint);
    }

    @Then("I receive valid HTTP response code 200")
    public void iReceiveValidHttpResponseCode200() {
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP status code 200 for update operation.");
        LogClass.info("Validating HTTP response code 200 for update request.");
    }

    @And("Response body contains updated product")
    public void responseBodyContainsUpdatedProduct() {
        String updatedTitle = response.jsonPath().getString("title");
        Assert.assertEquals(updatedTitle, configLoader.getProperty("updatedProduct"), "Product title was not updated correctly.");
        LogClass.info("Validating that response body contains updated product.");
    }
}
