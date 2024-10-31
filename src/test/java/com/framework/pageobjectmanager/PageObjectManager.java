package com.framework.pageobjectmanager;

import com.framework.pages.BingPage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    private BingPage bingPage;

    public BingPage getBingPage() {
        return (bingPage == null) ? bingPage = new BingPage(driver) : bingPage;
    }
}
