package com.framework.testrunners;

import io.cucumber.testng.CucumberOptions;
import org.framework.cucumber.AbstractTestNGCucumberTest;

@CucumberOptions(features = "classpath:features/",
        glue = "com.framework.stepdefinition",
        plugin = {"pretty", "summary", "json:AutomationReports/Cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        tags = "@TestGooglePage")
public class TestRunner extends AbstractTestNGCucumberTest {

}
