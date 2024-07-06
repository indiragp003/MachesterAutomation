package ManUTDRegression.pom.videopage;

import java.io.IOException;
import java.util.List;

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


public class PlayVideo {

	final static Logger log = LogManager.getLogger(PlayVideo.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 		//Click Webelemnt
		//By UserName = WebElementUtil.getElement("UserName");
	//	gl.clickElement(UserName,"UserName");
		
		double totalVideoLength = 0;
		double currentVideoLenght = 0;
		double videoLenghAfterPause=0;
		gl.switchToNativeView();
		By unitedNow= AppiumBy.accessibilityId("United Now");
		gl.clickElement(unitedNow,"unitedNow");
		gl.deadWait();
		//gl.swipeIntially();
		gl.clickOnFirstElement();
		
		//gl.clickOnVideo();
		totalVideoLength=gl.getTotalVideoTime();
		currentVideoLenght=gl.getCurrentVideoTime();
	
	
		
		
		System.out.println("Total video duration " + totalVideoLength);
		System.out.println("Cureent video duration " + currentVideoLenght);
	//	System.out.println("Video duration after pause " + totalVideoLength);
		
		gl.ValidateVideoPlay(currentVideoLenght);
		
	
		
		
	
		

	}

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}