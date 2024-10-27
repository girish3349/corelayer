package org.framework.configprovider;


import org.framework.configprovider.exceptions.PropertyFileNotFountException;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigProvider {

    private static Properties props;
    private static Map<String, Properties> configMap = new HashMap<>();

    private static Logger logger = Logger.getLogger(ConfigProvider.class.getName());

    private ConfigProvider() {

    }

    public static Properties getInstance(String propertyFileName) {
        Properties props = null;
        if (configMap.isEmpty()) {
            props = loadProperties(propertyFileName);
            configMap.put(propertyFileName, props);
            return props;
        } else {
            Iterator var2 = configMap.entrySet().iterator();

            Entry entry;
            do {
                if (!var2.hasNext()) {
                    props = loadProperties(propertyFileName);
                    configMap.put(propertyFileName, props);
                    return props;
                }
                entry = (Entry) var2.next();
            } while (!((String) entry.getKey()).equals(propertyFileName));
            return (Properties) entry.getValue();
        }
    }

    private static Properties getInstance() {
        if (props == null) {
            props = loadProperties();
            return props;

        } else {
            return props;
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            InputStream inputStreamSms = loader.getClass().getResourceAsStream("/properties/sms.properties");
            props.load(inputStreamSms);
        } catch (NullPointerException npe) {
            System.out.println("Sms.properties file not found .... Searching again");
            try {
                InputStream inputStream = ConfigProvider.class.getResourceAsStream("/properties/sms.properties");
                props.load(inputStream);
                System.out.println("Sms.properties file found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InputStream in = ConfigProvider.class.getResourceAsStream("/properties");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String resource;
            while ((resource = br.readLine()) != null) {
                System.out.println("Properties file fount " + resource);
                InputStream is = ConfigProvider.class.getResourceAsStream("/properties" + "/" + resource);
                props.load(is);
            }
            br.close();
        } catch (NullPointerException var1) {
            throw new PropertyFileNotFountException(
                    "No Properties file found inside 'properties' folder under src/test/resources. Please add all your properties files under mentioned folder (create folder if doesn't exist).");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return props;
    }

    public static Properties loadProperties(String propertyFile) {
        Properties props = new Properties();
        InputStream is = ConfigProvider.class.getResourceAsStream("/properties" + "/" + propertyFile + ".properties");

        try {
            props.load(is);
        } catch (NullPointerException var5) {
            throw new PropertyFileNotFountException(
                    "No Properties file found inside 'properties' folder under src/test/resources. Please add all your properties files under mentioned folder (create folder if doesn't exist).");
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        return props;
    }

    public static String getAsString(String key) {
        return getInstance().getProperty(key);
    }

    public static String getAsString(String fileName, String key) {
        return getInstance(fileName).getProperty(key);

    }

    public static int getAsInt(String fileName, String key) {
        return Integer.parseInt(getInstance(fileName).getProperty(key));
    }

    public static int getAsInt(String key) {
        return Integer.parseInt(getInstance().getProperty(key));
    }
    public static String getAsString(String environment, String propertyFile, String key) {
        Properties props = new Properties();
        InputStream inputStream = ConfigProvider.class
                .getResourceAsStream("/properties" + File.separator + environment + "/" + propertyFile + ".properties");
        String value = null;

        try {
            props.load(inputStream);
            value = props.getProperty(key);
            props.clear();
            inputStream.close();
        } catch (IOException var8) {
            logger.log(Level.SEVERE, "An exception was thrown", var8);
        }
        return value;
    }
}
