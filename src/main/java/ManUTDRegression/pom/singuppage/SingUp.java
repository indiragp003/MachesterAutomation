package ManUTDRegression.pom.singuppage;

import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.command.query.Select;
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


public class SingUp {

	final static Logger log = LogManager.getLogger(SingUp.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test(String UserNameField) throws Throwable {	
		
		//gl.getWebDriverInstanceForMailNator();
 		//Click Webelemnt
		
		//String userName=gl.getEmailID();
		gl.deadWait();
		String userName= "manu"+gl.generateRandomAlphanumeric(5)+"@dxcteam805881.testinator.com";
		
		System.out.println(userName);
		String passwordVal="Indu@123";
		//gl.AuthorizeEmailID(userName);
	
		
	
		By LetsGoButtonID = WebElementUtil.getElement("LetsGoButtonID");
		gl.clickElement(LetsGoButtonID,"LetsGoButtonID");
		
		By AllowBtn= AppiumBy.xpath("//android.widget.TextView[@content-desc=\"ALLOW\"]");
		gl.clickElement(AllowBtn,"AllowBtn");
		
		By AllowButtonButtonTwo = WebElementUtil.getElement("AllowButtonButtonTwo");
		gl.clickElement(AllowButtonButtonTwo,"AllowButtonButtonTwo");

	
		//Click Webelemnt
		By SkipButtonID = WebElementUtil.getElement("SkipButtonID");
		gl.clickElement(SkipButtonID,"SkipButtonID");

		//Click Webelemnt
		By SingUpForUnitedManuButtonID = WebElementUtil.getElement("SingUpForUnitedManuButtonID");
		gl.clickElement(SingUpForUnitedManuButtonID,"SingUpForUnitedManuButtonID");
		gl.deadWait();
		
	
	
		// Switch to Webview
	
		gl.switchToWebViewContext();
		//Enter Value in UserName Field
	
		//By UserName = WebElementUtil.getElement("UserName");
		//gl.inputText(UserName,"UserName",UserNameField);
		
		By SignUpFNtxt= AppiumBy.xpath("//label[text()='First Name']/following-sibling::input[@id='FirstName']");
		By surnametxt= AppiumBy.xpath("//label[text()='Surname']/following-sibling::input[@id='LastName']");
		By email= AppiumBy.xpath("//label[text()='Email Address']/following-sibling::input[@id='Email']");
		By password= AppiumBy.xpath("//label[text()='Password']/following-sibling::input[@id='Password']");
		By confirmPassword= AppiumBy.xpath("//label[text()='Confirm Password']/following-sibling::input[@id='PasswordConfirm']");
		By dayOfBirth=By.id("DayOfBirth");
		
		By monthOfBirth=By.id("MonthOfBirth");
		By yearOfBirth= By.id("YearOfBirth");
		By country= By.id("CountryISO");
		//By marketingOptOut=By.xpath("//div[@class='field-item']//input[@id=\"MarketingOptOut\"]");
	//	By marketingOptOut=By.id("MarketingOptOut");
	//	By marketingOptOut=By.id("MarketingOptOut");
		By createAccount=By.xpath("//button[@type='submit']");
		
		
		
	//	By surnametxt= AppiumBy.xpath("//label[text()='First Name']/following-sibling::input[@id='Surname']");
		
		gl.inputText(SignUpFNtxt, "SignUpFNtxt", "Ajay");
		gl.inputText(surnametxt, "surnametxt", "Rider");
		gl.inputText(email, "email", userName);
		gl.inputText(password, "password", passwordVal);
		gl.inputText(confirmPassword, "confirmPassword", passwordVal);
		
		
		gl.selectByValue(dayOfBirth, "dayOfBirth", "10");
		gl.selectByText(monthOfBirth, "monthOfBirth", "Jul");
		gl.selectByText(yearOfBirth, "yearOfBirth", "1967");
		gl.selectByText(country, "country", "India");
		
		gl.scrollPageDown();
		gl.scrollPageDown();
		//gl.scrollDownMobile();
		
		//gl.deadWait();
		gl.deadWait();
		
		gl.clickOnRadioButton("MarketingOptOut");
	//	gl.clickRadioButtonTwo();
		
	//	gl.clickRadioButton();
	
	//	gl.clickElement(marketingOptOut, "marketingOptOut");
		gl.deadWait();
		gl.clickElement(createAccount, "createAccount");
	
		gl.deadWait();
		//gl.deadWait();
		gl.writepageSource();		
		gl.getWebDriverInstanceForMailNator();
		
		loginToApp(userName,passwordVal);
		gl.deadWait();
	
	//	gl.gmailVerification(userName,passwordVal);
		
		/*
		
		// Login to  app
		
		gl.deadWait();
		
		gl.switchToNativeView();
		
		

		
		try {
			loginToApp(userName,passwordVal);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		*/
		

	}
	
	public void loginToApp(String userName, String passwordVal) throws Throwable
	{
		//gl.switchToNativeView();
	//	By LogInBtn= AppiumBy.accessibilityId("LOG IN");
		By LogInBtn= By.xpath("//a[@class='btn' and text()='LOG IN']");
		
		
		By MembershipLoginBtn= By.id("loginButton");
		//gl.clickElement(MembershipLoginBtn,"MembershipLoginBtn");
		
	//	gl.switchToWebViewContext();
		By MembershipUNtxt= By.id("Username");
		By MembershipPWtxt= By.id("Password");
		gl.clickElement(LogInBtn,"LogInBtn");
		gl.inputText(MembershipUNtxt, "MembershipUNtxt", userName);
		gl.inputText(MembershipPWtxt, "Membershiptxt", passwordVal);
		gl.clickElement(MembershipLoginBtn,"MembershipLoginBtn");
	}
	
	
	

	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}