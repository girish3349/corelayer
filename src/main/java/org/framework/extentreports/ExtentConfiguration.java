package org.framework.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ExtentConfiguration {

    private static ExtentReports extent;
    public static final String WORKING_DIR = System.getProperty("user.dir");
    private static final String TIME_STAMP = new SimpleDateFormat("dd.MM.yyyy.HH.mm").format(new Date());
    private static final String EXTENT_REPORT_FOLDER = WORKING_DIR + "/AutomationReports";
    private static final String REPORT_NAME = "ExtentReport_" + TIME_STAMP + "_" + Thread.currentThread().getId() + ".html";
    private static final String EXTENT_REPORTS_PATH = EXTENT_REPORT_FOLDER + File.separator + REPORT_NAME;
    private static Logger logger = Logger.getLogger(ExtentConfiguration.class.getName());


    private ExtentConfiguration() {
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createReportsFolder();
            attachReports();
        }
        return extent;
    }

    public static void createReportsFolder() {
        File file = new File(EXTENT_REPORT_FOLDER);
        if (!file.exists() && !file.mkdir()) {
            logger.warning("Failed to create directory!");
        }

    }

    private static ExtentSparkReporter[] initHtmlReporter() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(EXTENT_REPORTS_PATH);
        ExtentSparkReporter failReporter = new ExtentSparkReporter(EXTENT_REPORTS_PATH.replace(".html", "-failed.html")).filter().statusFilter().as(new Status[]{Status.FAIL}).apply();
 //LOAD FROM XML or JSON Config File
/*		try{
			htmlReporter.loadXMLConfig("spark-config.xml");
			failReporter.loadXMLConfig("spark-config.xml");
		}catch (IOException ioe){

		}catch (NullPointerException npe){
			npe.printStackTrace();
		}*/

        setConfig(htmlReporter);
        setConfig(failReporter);

        return new ExtentSparkReporter[]{htmlReporter, failReporter};
    }

    private static void setConfig(ExtentSparkReporter reporter) {
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setDocumentTitle(REPORT_NAME);
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName("Execution-Status");
        reporter.config().setCss("css-string");
        reporter.config().setJs("js-string");
        reporter.config().setProtocol(Protocol.HTTPS);
        reporter.config().setTimeStampFormat("dd-MMM-yyyy HH:mm:ss");
        reporter.config().setTimelineEnabled(true);
    }

    public static void attachReports() {
        extent = new ExtentReports();
        extent.attachReporter(initHtmlReporter()[0], initHtmlReporter()[1]);
    }
}