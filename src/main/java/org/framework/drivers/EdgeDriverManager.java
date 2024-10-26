package org.framework.drivers;


import org.apache.commons.lang3.StringUtils;
import org.framework.ConfigProvider.ConfigProvider;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.logging.Logger;

public class EdgeDriverManager extends DriverManager {

    HashMap<String, Object> EdgePrefs;
    //private static final String EDGE_CAPS = System.getProperty("edge.caps.list.of.strings", ConfigProvider.getAsString("edge.caps.list.of.strings"));
    private final String downloadPath = System.getProperty("edge.file.download.path", ConfigProvider.getAsString("edge.file.download.path"));
    private EdgeDriverService edgeDriverService;
    private EdgeOptions options;
    private Logger logger = Logger.getLogger(EdgeDriverManager.class.getName());


    public EdgeDriverManager() {
    }


    @Override
    protected void startService() {
        if (!isServiceInitialized()) {
            edgeDriverService = EdgeDriverService.createDefaultService();
        }
    }

    private boolean isServiceInitialized() {
        return null != edgeDriverService;
    }

    @Override
    public void stopService() {
        if (isServiceInitialized() && edgeDriverService.isRunning()) {
            edgeDriverService.stop();
        } else {
            driver.quit();
        }
    }

    @Override
    protected void createDriver() {
        logger.info("Launching Edge Driver START . . .");
        options = new EdgeOptions();

        if (headLessFlag.equalsIgnoreCase("true")) {
            String[] values = ConfigProvider.getAsString("headless.options.list.of.strings").split(",");
            options.addArguments(values);
            options.setCapability(CapabilityType.PLATFORM_NAME, getPlatform());
            if (StringUtils.isNoneEmpty(downloadPath)) {
                EdgePrefs = new HashMap<>();
                EdgePrefs.put("download.default_directory", downloadPath);
                options.setExperimentalOption("prefs", EdgePrefs);
            }
            fnInitateDriver(options);
        } else {
            String[] values = ConfigProvider.getAsString("options.list.of.strings").split(",");
            options.addArguments(values);
            driverFileDownload();
            driver = new EdgeDriver(edgeDriverService, options);
        }logger.info("Launching End Driver END . . .");
    }

    private void fnInitateDriver(EdgeOptions options) {
        driver = new EdgeDriver(options);
    }

    private void driverFileDownload() {
        HashMap<String, Object> edgePrefsVal = new HashMap<>();
        if (!autoFileDownload.isEmpty() && autoFileDownload.equalsIgnoreCase("true")) {
            edgePrefsVal.put("profile.default_content_setting_values.automatic_downloads", 1);
        } else {
            edgePrefsVal.put("profile.default_content_settings.popups", 0);
        }
        edgePrefsVal.put("safebrowsing.enabled", true);
        if (StringUtils.isNoneEmpty(downloadPath)) {
            edgePrefsVal.put("download.default_directory", downloadPath);
        }
        options.setExperimentalOption("prefs", edgePrefsVal);
        options.setCapability(CapabilityType.PLATFORM_NAME, getPlatform());
    }
}
