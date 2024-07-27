package org.framework.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ExtentTestManager {

    private ExtentTestManager() {
    }

    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static Set<Integer> extenThreadList = new HashSet<>();
    private static ExtentReports extent;
    private static Logger logger = Logger.getLogger(ExtentTestManager.class.getName());

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(getCurrentThread());
    }

    public static synchronized void endTest() {
        logger.info("end test start: " + Thread.currentThread().getId());
        extenThreadList.remove(getCurrentThread());
        if (!extentTestMap.isEmpty() && extenThreadList.isEmpty()) {
            Set<Integer> s1;
            s1 = extentTestMap.keySet();
            for (Integer i : s1) {
                extent.removeTest(extentTestMap.get(i));
            }
            logger.info("ExtentTestMap is : " + extentTestMap);
        }
    }


    public static synchronized ExtentTest startTest(String testName, final String desc) {
        logger.info("Start test : " + Thread.currentThread().getId());
        extent = ExtentConfiguration.getInstance();
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put(getCurrentThread(), test);
        extenThreadList.add(getCurrentThread());
        return test;
    }

    public void takeScreenShot(){

    }

    private static Integer getCurrentThread() {
        return (int) (Thread.currentThread().getId());
    }

}
