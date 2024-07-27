package org.framework.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.framework.ConfigProvider.ConfigProvider;
import org.framework.extentreports.ExtentTestManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Screenshots {

    private static final Logger logger = Logger.getLogger(Screenshots.class.getName());
    private static final String SCREENSHOTS_FOLDER = "\\screenshots\\";

    private static final String ACTION_1 = "action1";

    private static String screenshotsFolderPath;

    private static String screenShotsLevel;

    static {
        createDirectory();
        setScreenShotLevel();
    }

    private Screenshots() {

    }

    public static String getScreenshotsFolderPath() {
        return screenshotsFolderPath;
    }

    private static void createDirectory() {
        String drive = getDriverWithFreeSpace();
        if (drive != null && drive.contains("c:")) {
            //screenshotsFolderPath = FileUtils.getTempDirecotryPath() + SCREENSHOTS_FOLDER;
        } else {
            screenshotsFolderPath = drive + SCREENSHOTS_FOLDER;
        }
        screenshotsFolderPath = "AutomationReports/screenshots/";
        File file = new File(screenshotsFolderPath);
        if (!file.exists() && !file.mkdir()) {
            logger.warning("Failed to create directory");
        }
    }

    private static void setScreenShotLevel() {
        screenShotsLevel = ConfigProvider.getAsString("ScreenShotsLevel");
        if (screenShotsLevel == null) {
            screenShotsLevel = "Detailed";
        }
    }

    public static void addStepWithScreenshotInReport(WebDriver driver, String messsage) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        if (extentTest != null) {
            if (driver != null) {
                String path = Screenshots.captureScreenshot(driver, ACTION_1);
                try {
                    if (!screenShotsLevel.equalsIgnoreCase("FailureOnly")) {
                        extentTest.pass(messsage, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                    } else {
                        extentTest.pass(messsage);
                    }
                } catch (Exception e) {
                    logger.warning(e.getMessage());
                }
            } else {
                extentTest.pass(messsage);
            }
        }
    }

    public static void addStepInReport(String message){
        ExtentTest extentTest = ExtentTestManager.getTest();
        if (extentTest !=null) extentTest.pass(message);
    }

    public static void addStepInReport(boolean condition, String message) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        if (extentTest != null) {
            if (condition) {
                extentTest.pass(message);
            } else {
                extentTest.fail(message);
            }
        }
    }

    private static String getDriverWithFreeSpace() {
        String driveWithFreeSpace = null;
        File[] availableDrives = File.listRoots();
        if (availableDrives.length > 1) {
            for (File file : availableDrives) {
                if (file.getFreeSpace() > 10000000) {
                    driveWithFreeSpace = file.toString();
                    break;
                }
            }
        }
        return driveWithFreeSpace;
    }

    protected static String captureDesktop(String screenshotName) throws AWTException {
        String randomNumber = RandomStringUtils.randomNumeric(5);
        String destinationPath = screenshotsFolderPath + screenshotName + randomNumber + ".png";
        Robot r = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = r.createScreenCapture(capture);
        try {
            ImageIO.write(image, "png", new File(destinationPath));
        } catch (IOException ioe) {
            logger.warning("Not able to capture desktop");
        }
        return destinationPath;
    }

    public static void addStepWithDesktopScreenInReport(String message) {
        String path = null;
        try {
            path = Screenshots.captureDesktop("screenshot");
        } catch (AWTException e) {
            logger.warning(e.getMessage());
        }
        if (!screenShotsLevel.equalsIgnoreCase("FailureOnly")) {
            ExtentTestManager.getTest().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } else {
            ExtentTestManager.getTest().pass(message);
        }
    }

    public static void addFailureStepWithScreenshotInReport(WebDriver driver, String message) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        if (extentTest != null) {
            if (driver != null) {
                String path = Screenshots.captureScreenshot(driver, "screenshot");
                try {
                    extentTest.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                } catch (Exception e) {
                    logger.warning(e.getMessage());
                }
            } else {
                extentTest.fail(message);
            }
        }
    }

    public static void addJSONOutputToReport(String jsonString, Status status) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        Markup markup = MarkupHelper.createCodeBlock(jsonString, CodeLanguage.JSON);
        if (status.equals(Status.PASS)) {
            extentTest.pass(markup);
        } else {
            extentTest.fail(markup);
        }
    }


    /**
     * Add formatted xml data to report
     *
     * @param xmlString is added to the Report
     * @param status    is either pass or fail
     */
    public static void addXMLOutputToReport(String xmlString, Status status) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        Markup markup = MarkupHelper.createCodeBlock(xmlString, CodeLanguage.XML);
        if (status.equals(Status.PASS)) {
            extentTest.pass(markup);
        } else {
            extentTest.fail(markup);
        }
    }

    public static void addTableOutputToReport(String[][] tableData, Status status) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        Markup markup = MarkupHelper.createTable(tableData);
        if (status.equals(Status.PASS)) {
            extentTest.pass(markup);
        } else {
            extentTest.fail(markup);
        }
    }

    protected static String captureScreenshot(WebDriver driver, String screenshotName) {
        String randomNumber = RandomStringUtils.randomNumeric(5);
        String destinationPath = screenshotsFolderPath + screenshotName + randomNumber + ".png";

        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        try {
            updateTimeStamp(srcFile, destinationPath);
        } catch (IOException e) {
            logger.warning("unable to capture screenshot");
        }
        return destinationPath.substring(destinationPath.indexOf("/") + 1);
    }

    private static void updateTimeStamp(File sourceFile, String targetFileName) throws IOException {

        final BufferedImage image = ImageIO.read(sourceFile);

        SimpleDateFormat formatter = new SimpleDateFormat();
        Graphics g = image.getGraphics();
       // g.setFont(g.getFont(), deriveFont(18f));
        g.setColor(new Color(255, 20, 20));
        Date date = new Date(System.currentTimeMillis());
        g.drawString(formatter.format(date), image.getWidth() - 250, image.getHeight() - 20);
        g.dispose();

        ImageIO.write(image, "png", new File(targetFileName));
    }

    public enum Status {
        PASS, FAIL
    }


}
