package org.framework.drivers;

public class DriverManagerFactory {

    private DriverManagerFactory(){}

    public static DriverManager getManager(String browserName){

        DriverManager driverManager = null;

        if(browserName.equalsIgnoreCase("chrome")){
            driverManager = new ChromeDriverManager();
        } else if(browserName.equalsIgnoreCase("edge")){
            driverManager = new EdgeDriverManager();
        }
        return driverManager;
    }
}
