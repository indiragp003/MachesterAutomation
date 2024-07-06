package ManUTDRegression.pom.gmailloginpage;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import ManUTDRegression.framework.generic.GenericLib;
import ManUTDRegression.framework.generic.WebElementUtil;
import io.appium.java_client.AppiumDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import ManUTDRegression.framework.generic.PropertyLoader;
import ManUTDRegression.framework.report.ExtentManager;


public class GmailLogin {

	final static Logger log = LogManager.getLogger(GmailLogin.class);

	private GenericLib gl;
	private ExtentReports report;

	public void beforemethod(AppiumDriver driver, ExtentReports report, ExtentTest test) throws IOException {
		this.report = report;
		gl = new GenericLib(driver,test);
	}

	public void test() throws Exception {	
 		//gl.launchApplication("https://www.google.com/gmail/about/");
		//driver.navigate().to("https://www.google.com/gmail/about/");
		//Click Webelemnt
		//By SignInBtn = WebElementUtil.getElement("SignInBtn");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=");
		WebDriverManager.chromedriver().setup();
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\evijayasarat\\IAS\\driver\\chromedriver.exe");
	        // Instantiate a ChromeDriver class.
	        WebDriver driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
	        // Maximize the browser
	        driver.manage().window().maximize();
	 
	        // Launch Website
	        driver.get("https://www.google.com/gmail/about/");
		
		
		
 		//By SignInBtn = By.xpath("//*[text()='Sign in']");
 		//g//l.clickElement(SignInBtn,"SignInBtn");
 		
	    ClickJS(driver,"//*[text()='Sign in']");
 		
 		
 		
 		//By UseAnotherAccBtn = By.xpath("//*[text()='Use another account']");
 		//gl.clickElement(UseAnotherAccBtn,"UseAnotherAccBtn");
 		
 		
 		By UNTxt = By.xpath("//*[@id='identifierId']");
 		driver.findElement(UNTxt).sendKeys("hi3335484@gmail.com");
 		//gl.inputText(UNTxt, "UNTxt", "jyvgsj@gmail.com");
 		
 		By NextBtn = By.xpath("//*[text()='Next']");
 		ClickJS(driver,"//*[text()='Next']");
 		//gl.clickElement(NextBtn,"NextBtn");
 		
 		By PWTxt = By.xpath("//*[@name='Passwd']");
 		driver.findElement(PWTxt).sendKeys("Indu@101220");
 		//gl.inputText(PWTxt, "PWTxt", "Indu@101220");
 		
 		
 		//By NextBtn1 = By.xpath("//*[text()='Next']");
 		ClickJS(driver,"//*[text()='Next']");
 		
 		
 		Thread.sleep(7000);
 		//By EmailVerificationLnk = By.xpath("(//span[text()='Email Verification'])[last()]");
 		ClickJS(driver,"(//span[text()='Email Verification'])[last()]");
 		//gl.clickElement(EmailVerificationLnk,"EmailVerificationLnk");
 		
 		
 		ClickJS(driver,"(//span[contains(text(),'CONFIRM MY EMAIL')])[last()]");
 		//Inbox (1) - hi3335484@gmail.com - Gmail.html
 				


 					//*[text()='Sign in']
 					//*[text()='Use another account']
 					//*[@id='identifierId']
 					//*[text()='Next']
 					//*[text()='Enter your password']
 					//*[text()='Next']
		
		//Click Button
		
	}
	
	public void ClickJS(WebDriver driver,String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
 		JavascriptExecutor executor = (JavascriptExecutor)driver;
 		executor.executeScript("arguments[0].click();", element);
	}



	public void afterMethod() {
		gl.setTestPassedStatus();
		report.flush();
	}

}