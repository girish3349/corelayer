package com.framework.pages;

import org.framework.utilities.BasePageObject;
import org.framework.utilities.Screenshots;
import org.openqa.selenium.WebDriver;

public class BingPage extends BasePageObject {

    public BingPage(WebDriver driver) {
        super(driver);
    }

    private String searchBox = "//textarea[@id='sb_form_q']";

    public void enterSearchBox(String searchText) {
        setInputValue(searchBox, searchText);
        getElement(searchBox).submit();
    }
}
