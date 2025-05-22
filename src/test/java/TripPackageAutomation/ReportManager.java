package TripPackageAutomation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ExtentTest test;

    public static void setup() {
        sparkReporter = new ExtentSparkReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    public static void flush() {
        extent.flush();
    }
	public static ExtentReports getExtent() {
		return ReportManager.extent;
	}
}
