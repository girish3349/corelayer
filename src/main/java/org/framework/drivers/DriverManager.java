package org.framework.drivers;

import org.framework.ConfigProvider.ConfigProvider;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public abstract class DriverManager {

    protected WebDriver driver;
    private static Logger logger = Logger.getLogger(DriverManager.class.getName());

    protected String autoFileDownload = System.getProperty("auto.file.download", ConfigProvider.getAsString("auto.file.download"));

    protected static String headLessFlag = System.getProperty("headless", ConfigProvider.getAsString("headless"));

    protected abstract void startService();

    protected abstract void createDriver();

    public void stopService() {
    }

    public WebDriver getDriver() {
        if (null == driver) {
            if (!isSeleniumGripRequired()) {
                DriverExecutables.setBrowserExe();
                startService();
            }
            createDriver();
        }
        return driver;
    }

    boolean isSeleniumGripRequired() {
        String value = System.getProperty("SeleniumGrid", ConfigProvider.getAsString("SeleniumGrid"));
        return Boolean.parseBoolean(value);
    }

    Platform getPlatform() {
        String platformValue = System.getProperty("platform", ConfigProvider.getAsString("platform"));
        if (platformValue.equalsIgnoreCase("Windows")) {
            return Platform.WINDOWS;
        } else if (platformValue.equalsIgnoreCase("mac")) {
            return Platform.MAC;
        } else if (platformValue.equalsIgnoreCase("Linux")) {
            return Platform.LINUX;
        } else if (platformValue.equalsIgnoreCase("Unix")) {
            return Platform.UNIX;
        } else {
            return Platform.ANY;
        }
    }
}
