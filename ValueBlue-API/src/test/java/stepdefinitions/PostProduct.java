package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import utils.ConfigLoader;
import utils.LogClass;
import utils.JsonUtil;

public class PostProduct {

    private RequestSpecification request;
    private Response response;
    private ConfigLoader configLoader;

    private JsonUtil jsonUtil;

    public PostProduct() {
        try {
            configLoader = new ConfigLoader();
            jsonUtil = new JsonUtil();
            String postEndpoint = configLoader.getProperty("postRequestEndPoint");
            String jsonFilePath = "src/test/resources/json/myRequest.json";
            String jsonBody = jsonUtil.readJsonFromFile(jsonFilePath).orElseThrow(() -> new IllegalStateException("JSON file not found"));
            LogClass.info("The parsed Json body is: " + jsonBody );

            // Initialize RequestSpecification with baseUri and body
            request = RestAssured.given()
                    .baseUri(postEndpoint)
                    .header("Content-Type", "application/json")
                    .body(jsonBody);

            LogClass.info("POST API endpoint and request body set for new product addition");
        } catch (Exception e) {
            LogClass.error("Error setting up POST product test: " + e.getMessage());
            throw e;
        }
    }

    @Given("I set POST product API endpoint")
    public void iSetPostRequestEndpoint() {
        // baseUri and request body are set in the constructor
        LogClass.info("POST API endpoint for new product already set in constructor.");
    }

    @When("I set the POST request HEADER")
    public void iSetPostRequestHeader() {
        try {
            request.header("Content-Type", "application/json");
            LogClass.info("Request headers set.");
        } catch (Exception e) {
            LogClass.error("Failed to set request headers: " + e.getMessage());
            throw e;
        }
    }

    @And("I set request body with new product data")
    public void iSetRequestBodyWithNewData() {
        // request body is set in the constructor
        LogClass.info("Request body with new product data already set in constructor.");
    }

    @Then("I receive valid HTTP response code for the POST call {int}")
    public void i_receive_valid_http_response_code_for_the_post_call(int statusCode) {
        try {
            response = request.when().post();
            response.then().statusCode(statusCode);
            LogClass.info("Received valid HTTP response code " + statusCode);
        } catch (Exception e) {
            LogClass.error("Error during POST request: " + e.getMessage());
            throw e;
        }
    }

    @And("Response body contains new product")
    public void responseBodyContainsNewProduct() {
        try {
            response.then().body("title", equalTo(configLoader.getProperty("expectedProductTitle")));
            LogClass.info("Response body validated for new product.");
        } catch (Exception e) {
            LogClass.error("Error validating response body: " + e.getMessage());
            throw e;
        }
    }
}
