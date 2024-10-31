package com.framework.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class SampleStepDefinition extends AbstractSteps {

    @Given("Navigate to Bing Page")
    public void navigateToBingPage() {
        startDriver();
        getDriver().get("http://www.bing.com");

    }

    @When("Enter a search {string} term and click search button")
    public void enterASearchTermAndClickSearchButton(String term) {
        getPageObjectManager().getBingPage().enterSearchBox(term);
        stopDriver();
    }
}
