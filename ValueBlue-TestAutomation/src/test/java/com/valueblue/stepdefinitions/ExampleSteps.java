package com.valueblue.stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.valueblue.base.BaseClass;
import com.valueblue.pages.ExamplePage;
import com.valueblue.utils.ExtentReportManager;
import com.valueblue.utils.LogClass;
import com.valueblue.utils.WebDriverActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ExampleSteps extends BaseClass {

    private ExamplePage examplePage;
    private WebDriverActions webDriverActions;
    private static ExtentReports extent;
    private ExtentTest test;


    // This hook ensures WebDriver and Extent report are set up before any steps are executed
    @Before
    public void setUp(Scenario scenario) {
        LogClass.startTestCase(scenario.getName());
        extent = ExtentReportManager.getReport();
        test = extent.createTest(scenario.getName());
        BaseClass.setupWebDriver();
        WebDriver driver = BaseClass.getDriver();
        this.webDriverActions = new WebDriverActions(driver);
        this.examplePage = new ExamplePage(driver);
    }


    @When("I start the browser")
    public void iStartTheBrowser() {
        test.log(Status.INFO, "Starting the browser");
    }


    @When("I navigate to the configured URL")
    public void iNavigateToTheConfiguredUrl() {
        String currentUrl = getDriver().getCurrentUrl();
        String expectedUrl = prop.getProperty("baseUrl");

        // Normalize URLs by removing trailing slashes
        currentUrl = currentUrl.endsWith("/") ? currentUrl.substring(0, currentUrl.length() - 1) : currentUrl;
        expectedUrl = expectedUrl.endsWith("/") ? expectedUrl.substring(0, expectedUrl.length() - 1) : expectedUrl;

        try {
            Assert.assertEquals(currentUrl, expectedUrl);
            LogClass.info("Successfully navigated to the configured URL.");
            test.log(Status.PASS, "Navigated to the configured URL: " + expectedUrl);
        } catch (AssertionError e) {
            LogClass.error("Navigation to the URL failed. Expected: " + expectedUrl + ", but found: " + currentUrl);
            test.log(Status.FAIL, "Failed to navigate to the expected URL. Expected: " + expectedUrl + ", but found: " + currentUrl);
            throw e;
        }
    }

    @And("I click on the 'More information...' link")
    public void iClickOnTheMoreInformationLink() {

        try {
            examplePage.clickMoreInformation();
            LogClass.info("Clicked on the 'More information...' link.");
            test.log(Status.PASS, "Clicked on the 'More information...' link.");
        } catch (AssertionError e) {
            LogClass.error("Clicking on More information.. failed" );
            test.log(Status.FAIL, "Failed to click on More information..");
            throw e;
        }
    }

    @Then("a link with text 'RFC 2606' must be present")
    public void aLinkWithTextRfc2606MustBePresent() {
        try {
            Assert.assertTrue(examplePage.isRfc2606LinkPresent(), "RFC 2606 link is not present");
            test.log(Status.PASS, "RFC 2606 link is present");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "RFC 2606 link is not present");
            throw e;
        }
    }

    @And("a link with text 'RFC 6761' must be present")
    public void aLinkWithTextRfc6761MustBePresent() {
        try {
            Assert.assertTrue(examplePage.isRfc6761LinkPresent(), "RFC 6761 link is not present");
            test.log(Status.PASS, "RFC 6761 link is present");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "RFC 6761 link is not present");
            throw e;
        }
    }

    @And("I click on the 'Domains' element")
    public void iClickOnDomainsLink() {

        try {
            examplePage.clickDomainBox();
            LogClass.info("Clicked on the Domains element");
            test.log(Status.PASS, "Clicked on the Domains element");
        } catch (AssertionError e) {
            LogClass.error("Clicking onon the Domains element failed" );
            test.log(Status.FAIL, "Failed to click on on the Domains element");
            throw e;
        }
    }

    @And("the 'Domain Names' box must contain 'Root Zone Management' at index {int}")
    public void assertDomainNameInTheNavBox(int index) {
        String expectedText = prop.getProperty("expectedText");
        String actualText = examplePage.getDomainNamesBoxTextAtIndex(index);

        try {
            Assert.assertEquals(actualText, expectedText, "The text at the specified index does not match the expected text");
            test.log(Status.PASS, "The text at the specified index: " + index + " matches the expected text");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "The text at the specified index does not match the expected text");
            throw e;
        }

    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Taking a screenshot on failure
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            webDriverActions.takeScreenshot(screenshotName);
            LogClass.info(scenario.getName() + " - Test FAILED. Screenshot taken: " + screenshotName);
            test.log(Status.FAIL, "Test Failed");
        } else {
            LogClass.info(scenario.getName() + " - Test PASSED");
            test.log(Status.PASS, "Test Passed");
        }
        extent.flush();
        BaseClass.tearDownWebDriver();
        LogClass.endTestCase(scenario.getName());
    }

}
