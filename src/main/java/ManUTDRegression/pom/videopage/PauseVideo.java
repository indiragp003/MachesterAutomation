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


public class PauseVideo {

	final static Logger log = LogManager.getLogger(PauseVideo.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 		//Click Webelemnt
		//By LetsGoBTN = WebElementUtil.getElement("LetsGoBTN");
		
		double currentVideoLenght = 0;
		double videoLenghAfterPause=0;
		By pauseButton= AppiumBy.accessibilityId("Pause");
		
		gl.switchToNativeView();
		By unitedNow= AppiumBy.accessibilityId("United Now");
		gl.clickElement(unitedNow,"unitedNow");
		gl.deadWait();
		//gl.swipeIntially();
	
		//gl.clickOnVideo();
		gl.clickOnFirstElement();
		gl.pauseAutomationExecution(10000);
		gl.clickElement(pauseButton,"pauseButton");
		
		//totalVideoLength=gl.getTotalVideoTime();
		currentVideoLenght=gl.getCurrentVideoTime();
		gl.pauseAutomationExecution(10000);
		videoLenghAfterPause=gl.getCurrentVideoTime();
		gl.ValidateVideoPause(currentVideoLenght, videoLenghAfterPause);
		

	}

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}