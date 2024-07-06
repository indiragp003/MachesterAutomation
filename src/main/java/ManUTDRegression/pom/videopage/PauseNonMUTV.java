package ManUTDRegression.pom.videopage;

import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import ManUTDRegression.framework.generic.GenericLib;
import ManUTDRegression.framework.generic.WebElementUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import ManUTDRegression.framework.generic.PropertyLoader;
import ManUTDRegression.framework.report.ExtentManager;


public class PauseNonMUTV {

	final static Logger log = LogManager.getLogger(PauseNonMUTV.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 			
		double currentVideoLenght = 0;
		double videoLenghAfterPause=0;
		gl.switchToNativeView();
		gl.clickOnNonMUVideo();
		gl.pauseAutomationExecution(10000);
		By pauseButton= AppiumBy.accessibilityId("Pause");
		gl.clickElement(pauseButton,"pauseButton");	
		//totalVideoLength=gl.getTotalVideoTime();
		currentVideoLenght=gl.getCurrentVideoTime();
		gl.pauseAutomationExecution(100000);
		videoLenghAfterPause=gl.getCurrentVideoTime();
		gl.ValidateVideoPause(currentVideoLenght, videoLenghAfterPause);

	}

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}