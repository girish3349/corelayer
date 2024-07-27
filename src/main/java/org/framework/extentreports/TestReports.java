package org.framework.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;

public class TestReports {

    public void extendTest() throws IOException {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("Report.html");

        final File CONF = new File("spark-config.xml");
        ExtentSparkReporter spark = new ExtentSparkReporter("target/spark/spark.html");
        spark.loadXMLConfig(CONF);
    }

}
