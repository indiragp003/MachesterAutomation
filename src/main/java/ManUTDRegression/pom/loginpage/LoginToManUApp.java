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
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import ManUTDRegression.framework.generic.PropertyLoader;
import ManUTDRegression.framework.report.ExtentManager;


public class LoginToManUApp {

	final static Logger log = LogManager.getLogger(LoginToManUApp.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 		//Click Webelemnt
		//By SkipButton = WebElementUtil.getElement("SkipButton");
		gl.deadWait();
		//LoginPreSteps();
		LoginPreStepsBySkippingNotifications();
		By SkipButton= AppiumBy.accessibilityId("Skip button");
		By loginButton= AppiumBy.xpath("(//android.widget.TextView[@resource-id=\"com.mu.muclubapp.staging_mu_dxc:id/already_signed_txt\"])[2]");
		By badgeEle=AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/badge");
		By userName= By.id("Username");
		By password= By.id("Password");
		By loginBtn= By.id("loginButton");
		
	
		
		
	//	gl.clickElement(SkipButton,"SkipButton");
		
		if(gl.checkIFLoginButtonPresent())
		{
			gl.clickElement(loginButton,"loginButton");
		}
		else
		{
			gl.clickElement(badgeEle,"badgeEle");
			gl.deadWait();
			gl.clickElement(loginButton,"loginButton");
			
		}
		
		
		
		gl.deadWait();
		gl.deadWait();
		gl.switchToWebViewContext();
		
		gl.inputText(userName,"userName","iu398146@gmail.com");
		gl.inputText(password,"password","Indu@101220");
		gl.clickElement(loginBtn,"loginBtn");
		
		gl.deadWait();
		

	}
	
	public void LoginPreSteps()
	{
		
		By LetsGoButtonID = WebElementUtil.getElement("LetsGoButtonID");
		try {
			gl.clickElement(LetsGoButtonID,"LetsGoButtonID");
		
		
		//Click Webelemnt
	
	//	By AllouwButtonID = WebElementUtil.getElement("AllouwButtonID");
		By AllowBtn= AppiumBy.xpath("//android.widget.TextView[@content-desc=\"ALLOW\"]");
		gl.clickElement(AllowBtn,"AllowBtn");
	
	//	gl.clickElement(AllouwButtonID,"AllouwButtonID");
		//Click Webelemnt
		
		By AllowButtonButtonTwo = WebElementUtil.getElement("AllowButtonButtonTwo");
		gl.clickElement(AllowButtonButtonTwo,"AllowButtonButtonTwo");

	
		//Click Webelemnt
		By SkipButtonID = WebElementUtil.getElement("SkipButtonID");
		gl.clickElement(SkipButtonID,"SkipButtonID");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		public void LoginPreStepsBySkippingNotifications()
		{
			
			By LetsGoButtonID = WebElementUtil.getElement("LetsGoButtonID");
			By SkipButton= AppiumBy.accessibilityId("Skip button");
			try {
				
			
			gl.clickElement(LetsGoButtonID,"LetsGoButtonID");
			
			gl.clickElement(SkipButton,"SkipButton");
	
			// Ask me later
			gl.clickElement(SkipButton,"SkipButton");

			By SkipButtonID = WebElementUtil.getElement("SkipButtonID");
			gl.clickElement(SkipButtonID,"SkipButtonID");
			gl.deadWait();
			
			gl.handleMatchScore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}
	
	

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}