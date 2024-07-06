package ManUTDRegression.test;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import ManUTDRegression.framework.generic.DriverManager;
import ManUTDRegression.framework.generic.PropertyLoader;
import ManUTDRegression.framework.report.ExtentManager;

import io.appium.java_client.AppiumDriver;

public class BaseTest {
	AppiumDriver driver;
	ExtentTest test;
	ExtentReports report;
	
	@BeforeSuite
	public void beforeSuite(final ITestContext testContext) throws IOException {
		String suiteName = testContext.getCurrentXmlTest().getSuite().getName();
		ExtentManager.init(suiteName);
	}
	
	@BeforeClass
	public void init(final ITestContext testContext) throws Exception {
		DriverManager adm = new DriverManager();
		adm.initDriver(testContext.getCurrentXmlTest().getAllParameters());
		this.driver = adm.getDriver();
		String testName = this.getClass().getSimpleName();	
		report = ExtentManager.getInstance().getExtent();
		test = report.createTest(testName);
		test.assignAuthor(PropertyLoader.getUser());
	}
	
	@AfterClass
	public void cleanUp() {		
		this.driver.quit();	
	}
}
