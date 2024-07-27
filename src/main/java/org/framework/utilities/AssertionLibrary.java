package org.framework.utilities;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AssertionLibrary {

    private static WebDriver driver;
    private static final String ACTUAL = "<br> Actual: ";
    private static final String EXPECTED = "<br> Expected";

    public AssertionLibrary(WebDriver driver) {
        AssertionLibrary.driver = driver;
    }

    public enum Screenshot {
        REQUIRED, NOT_REQUIRED
    }

    public static void assertEqual(String actual, String expected, String message, Screenshot screenshot) {
        AssertionLibrary.assertEqual(actual, expected, message, screenshot);
    }

    public static void assertEquals(Double actual, Double expected, Double delta, String message, Screenshot screenshot){
        String reportMessage = message + ACTUAL + actual.toString() + EXPECTED + expected.toString();
        Assert.assertEquals(actual, expected, delta, message);
        attachScreenshotIfRequired(screenshot, reportMessage);
    }

    public static void assertEquals(Double actual, Double expected, Double delta, String message){
        assertEquals(actual, expected, delta, message, Screenshot.REQUIRED);
    }

    public static void assertEquals(Object actual, Object expected, String message, Screenshot screenshot){
        String reportMessage = message + ACTUAL + actual.toString() + EXPECTED + expected.toString();
        Assert.assertEquals(actual, expected, message);
        attachScreenshotIfRequired(screenshot, reportMessage);
    }

    public static void assertEquals(Object actual, Object expected, String message){
        assertEquals(actual, expected, message, Screenshot.REQUIRED);
    }

    public static void assertTrue(boolean condition, String message, Screenshot screenshot){
        String reportMessage = message +"<br> condition :"+ condition;
        Assert.assertTrue(condition, message);
        attachScreenshotIfRequired(screenshot, reportMessage);
    }

    public static void assertNotEquals(String actual, String expected, String message, Screenshot screenshot){
        assertNotEquals(actual, expected, message, screenshot);
    }

    public static void assertNotEquals(String actual, String expected, String message){
        assertNotEquals(actual,expected, message, Screenshot.REQUIRED);

    }

    public static void assertNotEquals(Double actual, Double expected, Double delta, String message, Screenshot screenshot){
        String reportMessage = message +ACTUAL+ actual.toString()+EXPECTED+ expected.toString();
        Assert.assertNotEquals(actual, expected, delta, message);
        attachScreenshotIfRequired(screenshot, reportMessage);
    }

    public static void assertNotEquals(Double actual, Double expected, Double delta, String message){
        assertNotEquals(actual,expected,delta, message, Screenshot.REQUIRED);
    }


    private static void attachScreenshotIfRequired(Screenshot screenshot, String message){
        if (screenshot.equals(Screenshot.REQUIRED)){
            Screenshots.addStepWithScreenshotInReport(driver, message);

        } else {
            Screenshots.addStepInReport(message);
        }
    }
}
