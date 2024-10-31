package com.framework.stepdefinition;

import io.cucumber.java.en.Given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultStepDefinition {

    private static String testDataId;
    private static String scenarioName;
    private static String excel;
    private static String sheetName;
    private static List<Map<String, String>> excelData =  new ArrayList<>();

    @Given("Read workbook {string} and sheet named {string}")
    public void read_workbook_and_sheet_named(String excelFile, String sheet) {

        if (scenarioName == null || !sheetName.equals(sheet) || !excelFile.equals(excel) || !testDataId.equals(scenarioName)) {
            //excelData.addAll(ReadExcel.)
        }

    }


}
