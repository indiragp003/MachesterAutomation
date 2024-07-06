package ManUTDRegression.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import ManUTDRegression.framework.generic.DriverManager;
import ManUTDRegression.framework.report.ExtentManager;
import ManUTDRegression.framework.generic.GenericLib;
import ManUTDRegression.framework.generic.PropertyLoader;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features",
plugin = {"pretty", "json:target/reports/cucumber.json","html:target/reports/cucumber.html"},
glue =  "ManUTDRegression.glue")

public class BddBaseTest extends AbstractTestNGCucumberTests{
	
	/**
	 * This method indicates if you want to run your scenarios in parallel or not
	 */
	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}
	
	
}
