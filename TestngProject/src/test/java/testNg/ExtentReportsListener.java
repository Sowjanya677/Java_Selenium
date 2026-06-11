package testNg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportsListener implements ITestListener {

    private ExtentReports extentReports;
    private ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {
        // Create reports directory if it doesn't exist
        String reportPath = "target/extent-reports";
        File reportsDir = new File(reportPath);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        // Create timestamp for unique report name
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(formatter);
        String reportFile = reportPath + "/ExtentReport_" + timestamp + ".html";

        // Initialize ExtentReports with SparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Test Automation Report");
        sparkReporter.config().setReportName("TestNG Test Results");

        // Initialize ExtentReports
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Tester", "Yashwanth");
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Browser", "Firefox");
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));

        System.out.println("ExtentReports initialized: " + reportFile);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest = extentReports.createTest(testName);
        extentTest.info("Test Started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long duration = result.getEndMillis() - result.getStartMillis();
        extentTest.pass("Test Passed: " + testName + " [Duration: " + duration + "ms]");
        System.out.println("✓ " + testName + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest.fail("Test Failed: " + testName);
        extentTest.fail("Failure: " + result.getThrowable().getMessage());
        System.out.println("✗ " + testName + " FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest.skip("Test Skipped: " + testName);
        System.out.println("⊗ " + testName + " SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("ExtentReports flushed successfully!");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }
}

