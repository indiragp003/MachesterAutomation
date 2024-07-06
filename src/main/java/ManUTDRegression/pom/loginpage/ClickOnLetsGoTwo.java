package ManUTDRegression.pom.loginpage;

import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import ManUTDRegression.framework.generic.GenericLib;
import ManUTDRegression.framework.generic.WebElementUtil;
import io.appium.java_client.AppiumDriver;
import ManUTDRegression.framework.generic.PropertyLoader;
import ManUTDRegression.framework.report.ExtentManager;


public class ClickOnLetsGoTwo {

	final static Logger log = LogManager.getLogger(ClickOnLetsGoTwo.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 		//Click Webelemnt
		By LetsGoBtn = WebElementUtil.getElement("LetsGoBtn");
		gl.clickElement(LetsGoBtn,"LetsGoBtn");
		//Fluent Wait
		gl.fluentWait(LetsGoBtn,"LetsGoBtn","30");
		//Click Webelemnt
		gl.clickElement(LetsGoBtn,"LetsGoBtn");

	}

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}