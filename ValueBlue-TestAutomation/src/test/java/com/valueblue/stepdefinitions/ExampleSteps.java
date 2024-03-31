package com.valueblue.stepdefinitions;

import com.valueblue.base.BaseClass;
import com.valueblue.pages.ExamplePage;
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


    // This hook ensures WebDriver is set up before any steps are executed
    @Before
    public void setup(Scenario scenario) {
        LogClass.startTestCase(scenario.getName());
        BaseClass.setupWebDriver();
        WebDriver driver = BaseClass.getDriver();
        this.webDriverActions = new WebDriverActions(driver);
        this.examplePage = new ExamplePage(driver);
    }


    @When("I start the browser")
    public void iStartTheBrowser() {
        LogClass.info("Starting the browser.");
    }


    @When("I navigate to the configured URL")
    public void iNavigateToTheConfiguredUrl() {
        String currentUrl = getDriver().getCurrentUrl();
        String expectedUrl = prop.getProperty("baseUrl");

        // Normalize URLs by removing trailing slashes
        currentUrl = currentUrl.endsWith("/") ? currentUrl.substring(0, currentUrl.length() - 1) : currentUrl;
        expectedUrl = expectedUrl.endsWith("/") ? expectedUrl.substring(0, expectedUrl.length() - 1) : expectedUrl;

        Assert.assertEquals(currentUrl, expectedUrl, "Did not navigate to the expected URL");
        LogClass.info("Successfully navigated to the configured URL.");
    }


    @And("I click on the 'More information...' link")
    public void iClickOnTheMoreInformationLink() {
        examplePage.clickMoreInformation();
        LogClass.info("Clicked on the 'More information...' link.");
    }

    @Then("a link with text 'RFC 2606' must be present")
    public void aLinkWithTextRfc2606MustBePresent() {
        Assert.assertTrue(examplePage.isRfc2606LinkPresent(), "RFC 2606 link is not present");
    }

    @And("a link with text 'RFC 6761' must be present")
    public void aLinkWithTextRfc6761MustBePresent() {
        Assert.assertTrue(examplePage.isRfc6761LinkPresent(), "RFC 6761 link is not present");
    }

    @And("I click on the 'Domains' element")
    public void iClickOnDomainsLink() {
        examplePage.clickDomainBox();
        LogClass.info("Clicked on the 'Domains' element.");
    }

    @And("the 'Domain Names' box must contain 'Root Zone Management' at index {int}")
    public void assertDomainNameInTheNavBox(int index) {
        String expectedText = prop.getProperty("expectedText");
        String actualText = examplePage.getDomainNamesBoxTextAtIndex(index);
        Assert.assertEquals(actualText, expectedText, "The text at the specified index does not match the expected text");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Taking a screenshot on failure
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            webDriverActions.takeScreenshot(screenshotName);
            LogClass.info(scenario.getName() + " - Test FAILED. Screenshot taken: " + screenshotName);
        } else {
            LogClass.info(scenario.getName() + " - Test PASSED");
        }
        BaseClass.tearDownWebDriver();
        LogClass.endTestCase(scenario.getName());
    }

}
