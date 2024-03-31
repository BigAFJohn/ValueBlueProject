package com.valueblue.base;

import com.valueblue.utils.ExtentReportManager;
import com.valueblue.utils.LogClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.aventstack.extentreports.ExtentReports;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseClass {

    protected static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected static Properties prop = new Properties();

    static {

        try (InputStream inputStream = BaseClass.class.getClassLoader().getResourceAsStream("Configuration/config.properties")) {
            if (inputStream != null) {
                LogClass.info("Loading configuration from config.properties");
                prop.load(inputStream);
            } else {
                LogClass.error("Configuration file not found");
                throw new RuntimeException("Configuration file not found in classpath");
            }
        } catch (IOException e) {
            LogClass.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load config.properties", e);
        }

    }

    public static void setupWebDriver() {
        String browserType = prop.getProperty("browser", "chrome").toLowerCase();
        switch (browserType) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driverThreadLocal.set(new FirefoxDriver());
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driverThreadLocal.set(new ChromeDriver());
                break;
        }

        WebDriver driver = getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

            String baseUrl = prop.getProperty("baseUrl");
            if (baseUrl != null && !baseUrl.isEmpty()) {
                driver.navigate().to(baseUrl);
                LogClass.info("Navigated to baseURL: " + baseUrl);
            } else {
                LogClass.error("Base URL is not specified in config.properties");
                throw new IllegalStateException("Base URL is not specified in config.properties.");
            }
        } else {
            LogClass.error("WebDriver is not initialized.");
            throw new IllegalStateException("WebDriver is not initialized.");
        }
    }

    public static void tearDownWebDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            LogClass.info("WebDriver has been shutdown.");
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
