package com.framework.stepdefinition;

import com.framework.pageobjectmanager.PageObjectManager;
import org.framework.configprovider.ConfigProvider;
import org.framework.drivers.DriverManagerFactory;
import org.framework.drivers.DriverManager;
import org.openqa.selenium.WebDriver;

public class AbstractSteps {

    private static DriverManager driverManager;
    private static WebDriver driver;
    private static PageObjectManager pageObjectManager;


    public void startDriver() {
        if (driverManager == null)
            driverManager = DriverManagerFactory.getManager(ConfigProvider.getAsString("browser"));
        driver = driverManager.getDriver();
        pageObjectManager = new PageObjectManager(driver);

    }

    public void stopDriver(){
        if(driverManager != null){
            driverManager.stopService();
        }
        driverManager = null;
        driver = null;
        pageObjectManager = null;
    }

    public WebDriver getDriver(){
        return driver;
    }

    public PageObjectManager getPageObjectManager(){
        return pageObjectManager;
    }
}
