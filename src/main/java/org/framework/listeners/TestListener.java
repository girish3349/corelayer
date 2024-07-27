package org.framework.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.framework.extentreports.ExtentConfiguration;
import org.framework.extentreports.ExtentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestListener implements ITestListener {

    private static Logger logger = Logger.getLogger(TestListener.class.getName());
    private Map<String, String> allParameter = new HashMap<>();
    private Map<String, String> suiteParameter = new HashMap<>();
    private Map<String, String> localParameter = new HashMap<>();
    private List<String> fileList = new ArrayList<>();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    protected static String testScreenShot() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        String imageName = "C:\\temp\\" + timeStamp + ".png";

        BufferedImage image = null;

        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (HeadlessException | AWTException e) {
            logger.warning(e.getMessage());
        }

        try {
            assert image != null;
            ImageIO.write(image, "png", new File(imageName));
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
        return imageName;
    }

    @Override
    public void onStart(ITestContext iTextContext) {
        allParameter = iTextContext.getSuite().getXmlSuite().getAllParameters();
        suiteParameter = iTextContext.getSuite().getXmlSuite().getParameters();
        localParameter = iTextContext.getCurrentXmlTest().getLocalParameters();
    }

    public Map<String, String> getAllParameters() {
        return allParameter;
    }

    public Map<String, String> getSuiteParameters() {
        return suiteParameter;
    }

    public Map<String, String> getLocalParameters() {
        return localParameter;
    }

    @Override
    public void onFinish(ITestContext iTextContext) {
        ExtentConfiguration.getInstance().flush();
        ExtentTestManager.endTest();
        compressDirectory("AutomationReports", "AutomationReports.zip");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentTestManager.startTest(iTestResult.getParameters()[0].toString().replace("\"", ""), iTestResult.getParameters()[1].toString().replace("\"", ""));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info(iTestResult.getName() + "passed successfully ");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.warning("failed" + getTestMethodName(iTestResult));
        if (ExtentTestManager.getTest() != null) {
            if (iTestResult.getThrowable().toString().contains("java.lang.AssertionError")) {
                String errMsg = iTestResult.getThrowable().getMessage();
                try {
                    ExtentTestManager.getTest().log(Status.FAIL,
                            "Test step failed due to following error:" + errMsg.substring(0, errMsg.indexOf("expected") - 1).trim(),
                            MediaEntityBuilder.createScreenCaptureFromPath(testScreenShot()).build());

                } catch (Exception e) {
                    logger.warning(e.getMessage());
                }
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Test step failed : ", iTestResult.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(testScreenShot()).build());
            }
        }

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.SKIP, iTestResult.getName() + "execution got skipped0");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("");
    }

    private void compressDirectory(String dir, String zipFile) {
        File directory = new File(dir);
        getFileList(directory);

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String filePath : fileList) {
                String name = filePath.substring(directory.getAbsolutePath().length() + 1);

                ZipEntry zipEntry = new ZipEntry(name);
                zos.putNextEntry(zipEntry);

                readFileWriteZipOut(zos, filePath);
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }


    public void readFileWriteZipOut(ZipOutputStream zos, String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            // Close the zip entry.
            zos.closeEntry();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }
}

