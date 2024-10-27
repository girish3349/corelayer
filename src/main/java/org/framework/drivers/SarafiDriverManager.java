package org.framework.drivers;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;

import java.util.logging.Logger;

public class SarafiDriverManager extends DriverManager {

    private static final Logger logger = Logger.getLogger(SarafiDriverManager.class.getName());
    private SafariDriverService instance;

    public boolean isServiceInitialized() {
        return null != instance;
    }

    @Override
    public void startService() {
        if (!isServiceInitialized()) {
            instance = SafariDriverService.createDefaultService();
        }
    }

    @Override
    public void stopService() {
        if (isServiceInitialized() && instance.isRunning()) {
            instance.stop();
        } else {
            driver.quit();
        }
    }

    @Override
    protected void createDriver() {
        logger.info("Launching Sarafi driver");
        SafariOptions options = new SafariOptions();

        if (headLessFlag.equalsIgnoreCase("true")){
            driver = new SafariDriver(options);
        }else{
            options.setCapability(CapabilityType.PLATFORM_NAME, getPlatform());
            driver = new SafariDriver(options);
        }
        logger.info("Launching sarafi driver");
    }
}
