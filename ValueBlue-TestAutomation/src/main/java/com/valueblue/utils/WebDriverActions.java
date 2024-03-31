package com.valueblue.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class WebDriverActions {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Passing WebDriver instance to the constructor
    public WebDriverActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Initialize WebDriverWait with a 20-second timeout
        LogClass.info("WebDriverActions initialized with a 20-second wait");
    }

    public void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            LogClass.info("Clicking on element: " + locator.toString());
            element.click();
        } catch (Exception e) {
            LogClass.error("Failed to click on element: " + locator.toString(), e);
            throw e;
        }
    }

    public WebElement waitForVisibilityOfElement(By locator) {
        try {
            LogClass.info("Waiting for visibility of element: " + locator.toString());
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            LogClass.error("Element not visible: " + locator.toString(), e);
            throw e;
        }
    }

    public void takeScreenshot(String scenarioName) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = scenarioName.replaceAll(" ", "_") + "_" + timestamp + ".png";
        try {
            Files.copy(scrFile.toPath(), new File("./screenshots/" + fileName).toPath());
            LogClass.info("Screenshot taken: " + fileName);
        } catch (IOException e) {
            LogClass.error("Failed to take screenshot: " + scenarioName, e);
        }
    }


}
