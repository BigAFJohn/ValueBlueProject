package com.valueblue.pages;

import com.valueblue.base.BaseClass;
import com.valueblue.utils.ConfigLoader;
import com.valueblue.utils.WebDriverActions;
import com.valueblue.utils.LogClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ExamplePage extends BaseClass {

    private final WebDriverActions actions;
    private ConfigLoader configLoader = new ConfigLoader();

    // Utilize ConfigLoader to fetch locator texts
    private final By moreInformationLink = By.xpath("//a[@href='https://www.iana.org/domains/example' and text()='" + configLoader.getProperty("linkTextMoreInformation") + "']");
    private final By rfc2606Link = By.xpath("//a[@href='/go/rfc2606' and text()='" + configLoader.getProperty("linkTextRFC2606") + "']");
    private final By rfc6761Link = By.xpath("//a[@href='/go/rfc6761' and text()='" + configLoader.getProperty("linkTextRFC6761") + "']");
    private final By domainBoxLink = By.xpath("//a[@href='/domains' and text()='" + configLoader.getProperty("linkTextDomains") + "']");
    private final By domainNamesBox = By.className(configLoader.getProperty("classNameDomainNamesBox"));
    public ExamplePage(WebDriver driver) {
        this.actions = new WebDriverActions(driver);
    }

    public void clickMoreInformation() {
        try {
            LogClass.info("Clicking on 'More information...' link.");
            actions.click(moreInformationLink);
        } catch (Exception e) {
            LogClass.error("Failed to click on 'More information...' link.", e);
            throw e;
        }
    }

    public boolean isRfc2606LinkPresent() {
        try {
            LogClass.info("Checking presence of RFC 2606 link.");
            return actions.waitForVisibilityOfElement(rfc2606Link) != null;
        } catch (Exception e) {
            LogClass.error("RFC 2606 link is not present.", e);
            return false;
        }
    }

    public boolean isRfc6761LinkPresent() {
        try {
            LogClass.info("Checking presence of RFC 6761 link.");
            return actions.waitForVisibilityOfElement(rfc6761Link) != null;
        } catch (Exception e) {
            LogClass.error("RFC 6761 link is not present.", e);
            return false;
        }
    }

    public void clickDomainBox() {
        try {
            LogClass.info("Clicking on 'Domain box link' ");
            actions.click(domainBoxLink);
        } catch (Exception e) {
            LogClass.error("Failed to click on 'More information...' link.", e);
            throw e;
        }
    }

    public String getDomainNamesBoxTextAtIndex(int index) {
        try {
            WebElement navigationBox = actions.waitForVisibilityOfElement(domainNamesBox);
            List<WebElement> listItems = navigationBox.findElements(By.tagName("li"));
            if (index >= 1 && index <= listItems.size()) {
                WebElement item = listItems.get(index - 1);
                String text = item.findElement(By.tagName("a")).getText();
                LogClass.info("Found text in the navigation box: " + text);
                return text;
            } else {
                LogClass.error("Index out of bounds: " + index);
                return "";
            }
        } catch (Exception e) {
            LogClass.error("Failed to get text from domain names box at index: " + index, e);
            throw e;
        }
    }


}
