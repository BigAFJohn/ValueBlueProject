package com.valueblue.utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports  getReport() {
        if (extent == null) {
            // Create an ExtentHtmlReporter
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReport/extentReports.html");
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle("Automation Test Report");
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setReportName("Automation Test Report");

            // Create an instance of ExtentReports and attach the HtmlReporter
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Tester", "John Ige");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("OS", "MacOS");
        }
        return extent;
    }
}
