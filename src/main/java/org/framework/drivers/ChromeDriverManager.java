package org.framework.drivers;

import org.apache.commons.lang3.StringUtils;
import org.framework.ConfigProvider.ConfigProvider;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.logging.Logger;

public class ChromeDriverManager extends DriverManager {

    HashMap<String, Object> chromePrefs;

    ChromeOptions options;
    private ChromeDriverService chService;
    private String chromeCaps = System.getProperty("chrome.caps.list.of.strings", ConfigProvider.getAsString("chrome.caps.list.of.strings"));
    private String downloadPath = System.getProperty("chrome.file.download.path", ConfigProvider.getAsString("chrome.file.download.path"));
    private static Logger logger = Logger.getLogger(ChromeDriverManager.class.getName());

    public ChromeDriverManager() {
    }


    @Override
    protected void startService() {
        if (!isServiceInitialized()) {
            chService = ChromeDriverService.createDefaultService();
        }

    }

    private boolean isServiceInitialized() {
        return null != chService;
    }

    @Override
    public void stopService() {
        if (isServiceInitialized() && chService.isRunning()) {
            chService.stop();
        } else {
            driver.quit();
        }
    }

    @Override
    protected void createDriver() {
        logger.info("Launching Chrome Driver START . . .");
        MutableCapabilities capabilities = new MutableCapabilities();
        options = new ChromeOptions();

        if (headLessFlag.equalsIgnoreCase("true")) {
            String[] values = ConfigProvider.getAsString("headless.options.list.of.strings").split(",");
            options.addArguments(values);
            options.setCapability(CapabilityType.PLATFORM_NAME, getPlatform());
            if (StringUtils.isNoneEmpty(downloadPath)) {
                chromePrefs = new HashMap<>();
                chromePrefs.put("download.default_directory", downloadPath);
                options.setExperimentalOption("prefs", chromePrefs);
            }
            fnInitateDriver(options);
        } else {
            String[] values = ConfigProvider.getAsString("options.list.of.strings").split(",");
            options.addArguments(values);
            driverFileDownload();
            driver = new ChromeDriver(chService, options);
        }
        logger.info("Launching Chrome Driver END . . .");
    }

    private void fnInitateDriver( ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    private void driverFileDownload() {
        HashMap<String, Object> chromePrefsVal = new HashMap<>();
        if(!autoFileDownload.isEmpty() && autoFileDownload.equalsIgnoreCase("true")){
            chromePrefsVal.put("profile.default_content_setting_values.automatic_downloads", 1);
        } else {
            chromePrefsVal.put("profile.default_content_settings.popups", 0);
        }
        chromePrefsVal.put("safebrowsing.enabled", true);
        if(StringUtils.isNoneEmpty(downloadPath)){
            chromePrefsVal.put("download.default_directory", downloadPath);
        }
        options.setExperimentalOption("prefs", chromePrefsVal);
        options.setCapability(CapabilityType.PLATFORM_NAME, getPlatform());
    }
}
