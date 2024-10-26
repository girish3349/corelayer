package org.framework.drivers;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.framework.ConfigProvider.ConfigProvider;

public class DriverExecutables {

    public static final String PROXY_URL = ConfigProvider.getAsString("proxy.url") + ":" + ConfigProvider.getAsString("proxy.port");
    //static String edgeDriverDownloadPath = "";
    //private static final String EDGE_DRIVER_TYPE = System.getProperty("edge.driver", ConfigProvider.getAsString("edge.driver"));
    //private static final String SAFARI_DRIVER_TYPE = System.getProperty("safari.driver", ConfigProvider.getAsString("safari.driver"));
    private static final String CHROMEDRIVER_PATH = System.getProperty("chromeDriver.path", ConfigProvider.getAsString("chromeDriver.path"));
    private static final String FIREFOX_PATH = System.getProperty("firefoxDriver.path", ConfigProvider.getAsString("firefoxDriver.path"));
    private static final String EDGE_DRIVER_PATH = System.getProperty("edgeDriver.path", ConfigProvider.getAsString("edgeDriver.path"));

    private static final String SAFARI_DRIVER_PATH = System.getProperty("safariDriver.path", ConfigProvider.getAsString("safariDriver.path"));

    protected static void setBrowserExe() {
        String browserName = System.getProperty("browser", ConfigProvider.getAsString("browser"));
        if (browserName.equalsIgnoreCase("chrome")) {
            executeChrome();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            executeFirefox();
        } else if (browserName.equalsIgnoreCase("edge")) {
            executeEdge();
        } else if (browserName.equalsIgnoreCase("safari")) {
            executeSafari();
        }
    }

    private static void executeChrome() {
        if (CHROMEDRIVER_PATH != null && !CHROMEDRIVER_PATH.isEmpty()) {
            System.setProperty("webdriver.safari.driver", CHROMEDRIVER_PATH);
        } else {
            fnChromeDriverManager();
        }
    }

    private static void executeFirefox() {
        if (FIREFOX_PATH != null && !FIREFOX_PATH.isEmpty()) {
            System.setProperty("webdriver.safari.driver", FIREFOX_PATH);
        } else {
            fnFireFoxWebDriverManager();
        }
    }

    private static void executeEdge() {
        if (EDGE_DRIVER_PATH != null && !EDGE_DRIVER_PATH.isEmpty()) {
            System.setProperty("webdriver.safari.driver", EDGE_DRIVER_PATH);
        } else {
            fnEdgeWebDriverManager();
        }
    }


    private static void executeSafari() {
        if (SAFARI_DRIVER_PATH != null && !SAFARI_DRIVER_PATH.isEmpty()) {
            System.setProperty("webdriver.safari.driver", SAFARI_DRIVER_PATH);
        } else {
            fnSafariWebDriverManager();
        }
    }

    private static void fnChromeDriverManager() {
        if (!PROXY_URL.isEmpty()) {
            WebDriverManager.chromedriver().clearDriverCache().proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.chromedriver().clearDriverCache().setup();
        }
    }


    private static void fnEdgeWebDriverManager() {
        if (!PROXY_URL.isEmpty()) {
            WebDriverManager.edgedriver().clearDriverCache().proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.edgedriver().clearDriverCache().setup();
        }
    }

    private static void fnSafariWebDriverManager() {
        if (!PROXY_URL.isEmpty()) {
            WebDriverManager.safaridriver().clearDriverCache().proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.safaridriver().clearDriverCache().setup();
        }
    }

    private static void fnFireFoxWebDriverManager() {
        if (!PROXY_URL.isEmpty()) {
            WebDriverManager.firefoxdriver().clearDriverCache().proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.firefoxdriver().clearDriverCache().setup();
        }
    }
}
