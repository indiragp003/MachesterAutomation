package ManUTDRegression.framework.generic;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.interactions.Sequence;

public class GenericLib {

	final static Logger log = LogManager.getLogger(GenericLib.class);

	private ExtentTest test;
	private AndroidDriver driver;
	boolean isRemoteExecution;
	private WebDriver dr;
	boolean checkFailed;
	String gridURL;

	private final int DEFAULT_TIMEOUT = 60;
	private final int IMPLICIT_WAIT = 10;

	public GenericLib(AppiumDriver driver, ExtentTest test) {
		this.driver = (AndroidDriver) driver;
		this.test = test;
		this.isRemoteExecution = Boolean.parseBoolean(PropertyLoader.getProperty("isRemoteExecution"));
		this.gridURL = PropertyLoader.getProperty("gridURL");
		this.checkFailed = false;
	}

	public void launchApplication(String url) throws Exception {
		// Launch Browser
		try {
			test.log(Status.INFO, "launch Application");
			driver.get(url);
			driver.manage().window().maximize();
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in launch Application");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in launchApplication");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("LaunchAPP")));
			}
			log.error("Error in launchApplication:", e);

		}
	}

	// Enter Value in edit field
	public void inputText(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Enter " + data + " in" + elementname + "field");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				e1.clear();
				e1.sendKeys(data);
				test.log(Status.PASS, data + " entered in " + elementname + " field Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter " + data + " in" + elementname + " field.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to enter " + data + " in" + elementname + " field.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in inputText:", e);
		}
	}

	public void inputText(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Enter " + data + " in" + elementname + "field");
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				element.clear();
				element.sendKeys(data);
				test.log(Status.PASS, data + " entered in " + elementname + " field Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to enter " + data + " in" + elementname + " field.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to enter " + data + " in" + elementname + " field.");
			log.error("Error in inputText:", e);
		}
	}

	// Click Button
	public void clickButton(By by, String elementname) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickbutton:", e);
		}
	}

	public void clickButton(WebElement element, String elementName) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementName);
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			System.out.println("Click Button After Wait");
			if (element.isDisplayed()) {
				System.out.println("Click Button Displayed");
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementName + " Successfully.");
			}
		} catch (Exception e) {

			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementName + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementName)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementName + ".");
			log.error("Error in clickbutton:", e);
		}
	}

	// Click Link
	public void clickLink(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {

			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickLink:", e);
		}
	}

	public void clickLink(WebElement element, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickLink:", e);
		}
	}

	// Get Text
	public String getText(By by, String elementname) throws IOException {
		String elementText = null;
		try {
			test.log(Status.INFO, "Get text from " + elementname);
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				elementText = e1.getText();
				test.log(Status.PASS, "Got Text from " + elementname + " Successfully.");
			}
		} catch (Exception e) {

			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Get text from " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Get text from " + elementname + ".");
			log.error("Error in getText:", e);
		}
		return elementText;
	}

	public String getText(WebElement element, String elementname) throws IOException {
		String elementText = null;
		try {
			test.log(Status.INFO, "Get text from " + elementname);
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				elementText = element.getText();
				test.log(Status.PASS, "Got Text from " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Get text from " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Get text from " + elementname + ".");
			log.error("Error in getText:", e);
		}
		return elementText;
	}

	public void isEnabled(By by, String elementname) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isEnabled()) {
				test.log(Status.PASS, elementname + "isEnabled");
			} else {
				test.log(Status.FAIL, elementname + "isDisabled.");
				setCloudTestFailedStatus(elementname + "isDisabled.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus(elementname + "isDisabled.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, elementname + "isDisabled.");
			log.error("Error in isEnabled:", e);
		}
	}

	public void isEnabled(WebElement element, String elementname) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isEnabled()) {
				test.log(Status.PASS, elementname + "isEnabled");
			} else {
				test.log(Status.FAIL, elementname + "isDisabled.");
				setCloudTestFailedStatus(elementname + "isDisabled.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus(elementname + "isDisabled.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, elementname + "isDisabled.");
			log.error("Error in isEnabled:", e);
		}
	}

	public void elementShouldContain(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				String actualString = e1.getText();
				assertTrue(actualString.contains(data));
				test.log(Status.PASS, data + " presents in element");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, data + " is not present in element");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(data + " is not present in element");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ElementShouldContain:", e);
		}
	}

	public void elementShouldContain(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			if (element.isDisplayed()) {
				String actualString = element.getText();
				assertTrue(actualString.contains(data));
				test.log(Status.PASS, data + " presents in element");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, data + " is not present in element");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(data + " is not present in element");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ElementShouldContain:", e);
		}
	}

	public void elementShouldNotContain(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				String actualString = e1.getText();
				assertFalse(actualString.contains(data));
				test.log(Status.PASS, data + " is not presents in element");
				test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, data + " is present in element");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(data + " is not present in element");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ElementShouldNotContain:", e);
		}
	}

	public void elementShouldNotContain(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			if (element.isDisplayed()) {
				String actualString = element.getText();
				assertFalse(actualString.contains(data));
				test.log(Status.PASS, data + " is not presents in element");
				test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, data + " is present in element");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(data + " is not present in element");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ElementShouldNotContain:", e);
		}
	}

	public void isDisabled(By by, String elementname) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isEnabled()) {
				test.log(Status.FAIL, elementname + "isEnabled");
				setCloudTestFailedStatus(elementname + "isEnabled");
			} else {
				test.log(Status.PASS, elementname + "isDisabled");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus(elementname + "isDisabled.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, elementname + "isDisabled.");
			log.error("Error in isDisabled:", e);
		}
	}

	public void isDisabled(WebElement element, String elementname) throws IOException {
		// Click Button
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isEnabled()) {
				test.log(Status.FAIL, elementname + "isEnabled");
				setCloudTestFailedStatus(elementname + "isEnabled");
			} else {
				test.log(Status.PASS, elementname + "isDisabled");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus(elementname + "isDisabled.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, elementname + "isDisabled.");
			log.error("Error in isDisabled:", e);
		}
	}

	// Click Image
	public void clickImage(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickImage:", e);
		}
	}

	public void clickImage(WebElement element, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickImage:", e);
		}
	}

	// Click Element
	public void clickElement(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickElement:", e);
		}
	}

	public void clickElement(WebElement element, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				test.log(Status.PASS, "Clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Click on " + elementname + ".");
			log.error("Error in clickElement:", e);
		}
	}

	public void scrollPageDown() throws Exception {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(100);
			test.log(Status.PASS, "page successfully scrolled down");
		} catch (Exception e) {
			test.log(Status.FAIL, "unable to scroll page down");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("unable to scroll page down");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Switch")));
			}
			log.error("Error in ScrollPageDown:", e);
		}
	}

	public void scrollPageUp() throws Exception {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-250)", "");

			Thread.sleep(100);
			test.log(Status.PASS, "page successfully scrolled up");
		} catch (Exception e) {
			test.log(Status.FAIL, "unable to scroll page up");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("unable to scroll page down");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Switch")));
			}
			log.error("Error in ScrollPageUp:", e);
		}
	}

	public void closeBrowser() throws Exception {
		try {
			test.log(Status.INFO, "Close current Browser");
			driver.close();
			test.log(Status.PASS, "Application closed Successfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to close current Browser");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to close current Browser");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Close All Browser")));
			}
			log.error("Error in closeBrowser:", e);
		}
	}

	public void closeAllBrowser() throws Exception {
		try {
			test.log(Status.INFO, "Close Application");
			driver.quit();
			test.log(Status.PASS, "Application Closed successfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to close Application");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to close Application");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("CloseApplication")));
			}
			log.error("Error in closeAllBrowser:", e);
		}
	}

	public void confirmAlert() throws Exception {
		test.log(Status.INFO, "Accept Alert Popup");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alt = driver.switchTo().alert();
			Thread.sleep(1000);
			alt.accept();
			test.log(Status.PASS, "Alert Popup Accecpted successfully");
			test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Accept Alert")));

		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Accept Alert");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Accept Alert");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Accept Alert")));
			}
			log.error("Error in confirmAlert:", e);
		}
	}

	public void dismissAlert() throws Exception {
		test.log(Status.INFO, "Dismiss Alert Popup");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alt = driver.switchTo().alert();
			Thread.sleep(1000);
			alt.dismiss();
			test.log(Status.PASS, "Dismissed Alert Popup successfully");
			test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Dismiss Alert")));
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Dismiss Alert");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Dismiss Alert");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Dismiss Alert")));
			}
			log.error("Error in dismissAlert:", e);
		}
	}

	public void doubleClick(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Double click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions builder = new Actions(driver);
				builder.doubleClick(e1).build().perform();
				test.log(Status.PASS, "Double clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to double click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to doulbe click on " + elementname + ".");
			log.error("Error in doubleClick:", e);
		}
	}

	public void doubleClick(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Double click on" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions builder = new Actions(driver);
				builder.doubleClick(element).build().perform();
				test.log(Status.PASS, "Double clicked on  " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to double click on " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to double click on " + elementname + ".");
			log.error("Error in doubleClick:", e);
		}
	}

	// Click link in webtable
	// Pass the xpath of table and then search all links inside table and click link
	public void clickLinkinWebTable(By by, String elementname, String link) throws IOException {
		try {
			test.log(Status.INFO, "Headers");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement webtable1 = driver.findElement(by);
				List<WebElement> links = webtable1.findElements(By.tagName("a"));
				int totallinks = links.size();

				List<String> value = new ArrayList<String>();
				for (int j = 0; j < totallinks; j++) {

					value.add(links.get(j).getText());
				}
				if (value.contains(link)) {
					test.log(Status.PASS, link + "clicked successfully ");

				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, link + "Link not found in table");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(link + "Link not found in table");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ClickLinkinWebTable:", e);
		}

	}

	public void reloadPage() throws Exception {
		// Refresh
		try {
			test.log(Status.INFO, "Refresh Current Page");
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(getImplicitWait());
			test.log(Status.PASS, "Application closeCurrent page should refreshed Successfully");
		} catch (Exception e) {
			test.log(Status.INFO, "Failed to Refresh page");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Refresh page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Close All Browser")));
			}
			log.error("Error in reloadPage:", e);
		}
	}

	public void selectCheckbox(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Select " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				if (e1.isSelected()) {
					test.log(Status.FAIL, elementname + " is already selected");
					setCloudTestFailedStatus(elementname + " is already selected");
				} else {
					e1.click();
					Thread.sleep(2000);
					test.log(Status.PASS, elementname + " selected Successfully.");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectCheckbox:", e);
		}
	}

	public void selectCheckbox(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Select " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					test.log(Status.FAIL, elementname + " is already selected");
					setCloudTestFailedStatus(elementname + " is already selected");
				} else {
					element.click();
					Thread.sleep(2000);
					test.log(Status.PASS, elementname + " selected Successfully.");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectCheckbox:", e);
		}
	}

	public void unselectCheckbox(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Select " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				if (e1.isSelected()) {
					e1.click();
					Thread.sleep(2000);
					test.log(Status.PASS, " checkbox Unchecked Successfully.");
				} else {
					test.log(Status.FAIL, "checkbox is already Unchecked");
					setCloudTestFailedStatus("checkbox is already Unchecked");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "checkbox is already Unchecked");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("checkbox is already Unchecked");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in unselectCheckbox:", e);
		}
	}

	public void unselectCheckbox(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Select " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					element.click();
					Thread.sleep(2000);
					test.log(Status.PASS, " checkbox Unchecked Successfully.");
				} else {
					test.log(Status.FAIL, "checkbox is already Unchecked");
					setCloudTestFailedStatus("checkbox is already Unchecked");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "checkbox is already Unchecked");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("checkbox is already Unchecked");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in unselectCheckbox:", e);
		}
	}

	public void selectByIndex(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Select se = new Select(e1);
				int val = Integer.parseInt(data.trim());
				se.selectByIndex(val);
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByIndex:", e);
		}
	}

	public void selectByIndex(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			if (element.isDisplayed()) {
				Select se = new Select(element);
				int val = Integer.parseInt(data.trim());
				se.selectByIndex(val);
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByIndex:", e);
		}
	}

	public void selectByText(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Select se = new Select(e1);
				se.selectByVisibleText(data.trim());
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByText:", e);
		}
	}

	public void selectByText(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			if (element.isDisplayed()) {
				Select se = new Select(element);
				se.selectByVisibleText(data.trim());
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByText:", e);
		}
	}

	public void verifyTitle(String Text) throws Exception {
		try {
			test.log(Status.INFO, "Verify title of page is" + Text);
			if (driver.getTitle().contains(Text))
				test.log(Status.PASS, "Verify title of page is " + Text);
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Verify title of page " + Text + " is Failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(Text)));
			}
			test.log(Status.FAIL, "Verify title of page " + Text + " is Failed");
			log.error("Error in verifyTitle:", e);
		}
	}

	public void selectByValue(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Select se = new Select(e1);
				se.selectByVisibleText(data.trim());
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByValue:", e);
		}
	}

	public void selectByValue(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Select " + data + " from the dropdown");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			// wait.until(ExpectedConditions.elementToBeSelected(by));
			if (element.isDisplayed()) {
				Select se = new Select(element);
				se.selectByVisibleText(data.trim());
				test.log(Status.PASS, data + " is selected from dropdown Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select " + data + " from the dropdown.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select " + data + " from the dropdown.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in selectByValue:", e);
		}
	}

	public void selectWindow(String input) throws Exception {
		try {
			test.log(Status.INFO, "Switch control from current Window");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
			Set<String> handles = driver.getWindowHandles();
			Iterator<String> it = handles.iterator();
			if (input.equalsIgnoreCase("1")) {
				input = "parent";
			} else {
				input = "Child";
			}
			while (it.hasNext()) {
				String parent = it.next();
				String child = it.next();
				driver.switchTo().window(input);
				Thread.sleep(2000);
				test.log(Status.PASS, "control should Switch from current window Successfully");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Pass control");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Pass control");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(input)));
			}
			log.error("Error in selectWindow:", e);
		}
	}

	public void switchDefault() throws Exception {
		// Switch Default Window
		try {
			test.log(Status.INFO, "Switch Default Window");
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
			test.log(Status.PASS, "control should Switch to current window Successfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Pass control");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Pass control");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Switch")));
			}
			log.error("Error in switchDefault:", e);
		}
	}

	public void frameByIndex(int Input) throws Exception {
		try {
			test.log(Status.INFO, "Switch in to Frame");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Input));
			driver.switchTo().frame(Input);
			Thread.sleep(2000);
			test.log(Status.PASS, "Switched in to frame successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to perform operation inside frame.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to perform operation inside frame.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXT")));
			}
			log.error("Error in frameByIndex:", e);
		}
	}

	public void frameByelement(By by) throws Exception {
		try {
			test.log(Status.INFO, "Switch in to Frame");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
			WebElement e1 = driver.findElement(by);
			driver.switchTo().frame(e1);
			Thread.sleep(2000);
			test.log(Status.PASS, "Switched to WEBELEMENTNAME frame successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to locate WEBELEMENTNAME frame.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to locate WEBELEMENTNAME frame.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("WEBELEMENTNAMEFrame")));
			}
			log.error("Error in frameByelement:", e);
		}
	}

	public void frameByelement(WebElement element) throws Exception {
		try {
			test.log(Status.INFO, "Switch in to Frame");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
			driver.switchTo().frame(element);
			Thread.sleep(2000);
			test.log(Status.PASS, "Switched to WEBELEMENTNAME frame successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to locate WEBELEMENTNAME frame.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to locate WEBELEMENTNAME frame.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("WEBELEMENTNAMEFrame")));
			}
			log.error("Error in frameByelement:", e);
		}
	}

	public void waitTillElementEnable(By by) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Enabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.elementToBeClickable(by));
			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXT")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementEnable:", e);
		}
	}

	public void waitTillElementEnable(WebElement element) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Enabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.elementToBeClickable(element));

			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXT")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementEnable:", e);
		}
	}

	public void waitTillElementDisable(By by) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Disabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			 wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));
			test.log(Status.PASS, "VISIBLE_TEXT is Disabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXT")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is enabled in the page");
			log.error("Error in waitTillElementDisable:", e);
		}
	}

	public void waitTillElementDisable(WebElement element) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Disabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			 wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
			test.log(Status.PASS, "VISIBLE_TEXT is Disabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXT")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is enabled in the page");
			log.error("Error in waitTillElementDisable:", e);
		}
	}

	public void waitTillElementVisible(By by) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Enabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXTWait")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementVisible:", e);
		}
	}

	public void waitTillElementVisible(WebElement element) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is Enabled");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXTWait")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementVisible:", e);
		}
	}

	public void waitTillElementinVisible(By by) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is not-enabled in the page");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXTinWait")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementVisible:", e);
		}
	}

	public void waitTillPageContains(String data) throws Exception {
		test.log(Status.INFO, "Page Contains data");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[contains(text(), 'data')]"), "data"));
			test.log(Status.PASS, "Page Contains data");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Page does not Contain data");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("data")));
			}
			test.log(Status.FAIL, "Page does not Contain data");
			log.error("Error in waitTillPageContains:", e);
		}
	}

	public void waitTillElementinVisible(WebElement element) throws Exception {
		test.log(Status.INFO, "Wait until VISIBLE_TEXT is not-enabled in the page");
		try {
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.invisibilityOf(element));
			test.log(Status.PASS, "VISIBLE_TEXT is enabled in the page");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("VISIBLE_TEXT is not-enabled in the page");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("VISIBLE_TEXTinWait")));
			}
			test.log(Status.FAIL, "VISIBLE_TEXT is not-enabled in the page");
			log.error("Error in waitTillElementVisible:", e);
		}
	}

	protected String takeScreenShot(String methodName) throws IOException {

		Date date = new java.util.Date();
		String[] date1 = date.toString().split(" ");
		String[] date2 = date1[3].split(":");
		String dateval = date2[0] + date2[1] + date2[2];

		String basedir = System.getProperty("user.dir");
		basedir = URLDecoder.decode(basedir, "UTF-8");
		String filePath = basedir + File.separator + "reports" + File.separator + "screenshots" + File.separator
				+ methodName + "-" + date1[1] + date1[2] + dateval + ".png";
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(filePath));
		} catch (Exception e) {
			log.error("Error in takeScreenShot:", e);
		}
		return filePath;
	}

	public void clickRadioButton(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Click " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			 WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			 wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					test.log(Status.FAIL, elementname + " is already Clicked");
					setCloudTestFailedStatus(elementname + " is already Clicked");
				} else {
					element.click();

					test.log(Status.PASS, elementname + " Clicked Successfully.");
					test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to CLick on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to CLick on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ClickRadioButton:", e);
		}
	}
	
	public void clickRadioButton(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Click " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement element = driver.findElement(by);
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					test.log(Status.FAIL, elementname + " is already Clicked");
					setCloudTestFailedStatus(elementname + " is already Clicked");
				} else {
					element.click();

					test.log(Status.PASS, elementname + " Clicked Successfully.");
					test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to CLick on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to CLick on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in ClickRadioButton:", e);
		}
	}

	// Need to add this keyword in the Keyword List
	
	public void clickRadioButtonByValue(By by, String elementName, String data) throws Exception {

		try {
			test.log(Status.INFO, "Select Radio Button "+ elementName +" by " + data + " .");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			List<WebElement> radios = driver.findElements(by);
			System.out.println("No " + radios.size());

			for (int i = 0; i < radios.size(); i++) {
				System.out.println("value  " + i + "   " + radios.get(i).getAttribute("value"));
				if (radios.get(i).getAttribute("value").contains(data)) {

					radios.get(i).click();
				}
				test.log(Status.PASS, data + " is Clicked Successfully.");
				test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Select data in " + elementName + " value of " + data + " .");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Select data in " + elementName + " value of " + data + " .");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
			log.error("Error in ClickRadioButton:", e);
		}
	}

	public void clickRadioButtonByValue(WebElement element, String elementName, String data) throws Exception {

		try {
			test.log(Status.INFO, "Select Radio Button "+ elementName +" by " + data + " .");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			List<WebElement> radios = driver.findElements((By) element);
			System.out.println("No " + radios.size());

			for (int i = 0; i < radios.size(); i++) {
				System.out.println("value  " + i + "   " + radios.get(i).getAttribute("value"));
				if (radios.get(i).getAttribute("value").contains(data)) {

					radios.get(i).click();
				}
				test.log(Status.PASS, data + " is Clicked Successfully.");
				test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Select data in " + elementName + " value of " + data + " .");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Select data in " + elementName + " value of " + data + " .");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
			log.error("Error in ClickRadioButton:", e);
		}
	}

	public void unCheckAll() throws Exception {
		int i = 0;
		try {
			test.log(Status.INFO, "UnCheck all Check boxes in the Page");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			List<WebElement> Check = driver.findElements(By.xpath("//input[@type='checkbox']"));
			for (i = 0; i < Check.size(); i++) {
				System.out.println("value  " + i + "   " + Check.get(i).getText());
				if (Check.get(i).isSelected()) {
					Check.get(i).click();
				}
			}
			test.log(Status.PASS, "All Check Boxes are UnChecked Successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to UnSelect .");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to UnSelect .");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("")));
			}
			log.error("Error in UnCheckAll:", e);
		}
	}

	// Added on 04192018
	public void verifyIsCheckboxSelected(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is CHeckBox Selected for a " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				if (e1.isSelected()) {
					test.log(Status.PASS, elementname + " is selected");
					test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
				} else {

					test.log(Status.FAIL, elementname + " is not selected ");
					setCloudTestFailedStatus(elementname + " is not selected ");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyIsCheckboxSelected:", e);
		}
	}

	public void verifyIsCheckboxSelected(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is CHeckBox Selected for a " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					test.log(Status.PASS, elementname + " is selected");
					test.pass("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
				} else {

					test.log(Status.FAIL, elementname + " is not selected ");
					setCloudTestFailedStatus(elementname + " is not selected ");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyIsCheckboxSelected:", e);
		}
	}

	public void verifyIsCheckboxUnSelected(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is CHeckBox Selected for a " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				if (e1.isSelected()) {
					test.log(Status.FAIL, elementname + " is selected ");
					setCloudTestFailedStatus(elementname + " is selected ");
				} else {
					test.log(Status.PASS, elementname + " is not selected");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyIsCheckboxUnSelected:", e);
		}
	}

	public void verifyIsCheckboxUnSelected(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is CHeckBox Selected for a " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				if (element.isSelected()) {
					test.log(Status.FAIL, elementname + " is selected ");
					setCloudTestFailedStatus(elementname + " is selected ");
				} else {
					test.log(Status.PASS, elementname + " is not selected");
				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select on " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select on " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyIsCheckboxUnSelected:", e);
		}
	}

	public void verifyElementVisible(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}

	public void verifyElementVisible(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}

	public void verifyElementNotVisible(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element is not Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				test.log(Status.FAIL, elementname + " is Visible ");
				setCloudTestFailedStatus(elementname + " is Visible ");
			} else {
				test.log(Status.PASS, elementname + " is  not Visible");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementNotVisible:", e);
		}
	}

	public void verifyElementNotVisible(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element is not Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				test.log(Status.FAIL, elementname + " is Visible ");
				setCloudTestFailedStatus(elementname + " is Visible ");
			} else {
				test.log(Status.PASS, elementname + " is  not Visible");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementNotVisible:", e);
		}
	}

	public void pageShouldContainsText(String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify Page contains_  " + data);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			// wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (driver.getPageSource().contains(data)) {
				test.log(Status.PASS, "Page contains the data " + data);
			} else {
				test.log(Status.FAIL, "Page does not contains the data " + data);
				setCloudTestFailedStatus("Page does not contains the data " + data);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Page does not contains the data " + data);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Page does not contains the data " + data);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
			log.error("Error in PageShouldContainsText:", e);
		}
	}

	public void pageShouldContainsImage(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify Page contains_Image  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			WebElement ImageFile = driver.findElement(by);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					ImageFile);
			if (!ImagePresent) {
				test.log(Status.PASS, "Page contains the Image " + elementname);
			} else {
				test.log(Status.FAIL, "Page does not contains the Image " + elementname);
				setCloudTestFailedStatus("Page does not contains the Image " + elementname);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Page does not contains the Image " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Page does not contains the Image " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in PageShouldContainsImage:", e);
		}
	}

	public void pageShouldContainsImage(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify Page contains_Image  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					element);
			if (!ImagePresent) {
				test.log(Status.PASS, "Page contains the Image " + elementname);
			} else {
				test.log(Status.FAIL, "Page does not contains the Image " + elementname);
				setCloudTestFailedStatus("Page does not contains the Image " + elementname);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Page does not contains the Image " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Page does not contains the Image " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in PageShouldContainsImage:", e);
		}
	}

	public void howerMouse(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, elementname + "  Hower mouse");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {

				Actions actions = new Actions(driver);
				actions.moveToElement(e1).build().perform();

				test.log(Status.PASS, "Successfully Mouseover on" + elementname);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to mouseover on element");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to mouseover on element");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in HowerMouse:", e);
		}
	}

	public void menuSelectionHowerMouse(By by, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Menu Select " + elementname + " By Hower mouse");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement element = driver.findElement(By.linkText(data));
				WebElement el = driver.findElement(by);
				Actions actions = new Actions(driver);
				actions.moveToElement(element).perform();
				actions.moveToElement(el).click();

				test.log(Status.PASS, " Menu selected  Successfully.");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select .");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to select .");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in MenuSelection_HowerMouse:", e);
		}
	}

	// Robotclass for sendkeys
	public void keyBoardEvents(String data) throws Exception {
		try {
			test.log(Status.INFO, "Press teh KeyBoardb Key : " + data + " .");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			Robot r = new Robot();
			if (data.equalsIgnoreCase("Enter")) {
				r.keyPress(KeyEvent.VK_ENTER);
			} else if ((data.equalsIgnoreCase("Tab"))) {
				r.keyPress(KeyEvent.VK_TAB);
			}
			test.log(Status.PASS, " Key pressed Successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to Click  the Keyboard.");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Click  the Keyboard.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(data)));
			}
			log.error("Error in KeyBoard_Events:", e);
		}
	}

//	public void VerifyTextinImage(By by, String elementname,String data) throws Exception {
//		try {
//			test.log(Status.INFO, "Verify image contains text  " + elementname);
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
//			//WebElement ImageFile = driver.findElement(by);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//	        String imageUrl=driver.findElement(by).getAttribute("src");
// 			System.out.println("Image source path : \n"+ imageUrl);
//
// 			URL url = new URL(imageUrl);
//			 Image image = ImageIO.read(url);
// 			String s = new Ocr().recognizeCharacters((RenderedImage) image);
//			if(s.contains(data))
//			{
//				test.log(Status.PASS, "Successfully verified the text in image " +elementname );
//			}
//			else
//			{
//				test.log(Status.FAIL, "unable to verify the text in image " +elementname );
//			}
//		
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			test.log(Status.FAIL, "unable to verify the text in image " +elementname );
//			test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
//			e.printStackTrace();
//		}
//	}

	// Verify Header Count
	public void headerCountShouldBe(By by, String elementname, int headercount) throws IOException {
		try {
			test.log(Status.INFO, "Headers");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			List<WebElement> allHeadersOfTable = driver.findElements(by);
			int totalHeaders = allHeadersOfTable.size();
			assertTrue(totalHeaders == headercount);
			test.log(Status.PASS, headercount + " is same");
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to get header count ");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to get header count ");
			log.error("Error in KeyBoard_Events:", e);
		}
	}

	// Verify Table Existence
	public void verifyTableExistence(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Headers");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement webtable1 = driver.findElement(by);
				List<WebElement> rows = webtable1.findElements(By.tagName("tr"));
				List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to find table ");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to find table ");
			log.error("Error in VerifyTableExistence:", e);
		}

	}

	public void uploadFile(String filename) throws AWTException {
		StringSelection ss = new StringSelection(filename);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void clearElementText(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Clear " + elementname + "field");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e1).click().build().perform();
				e1.clear();
				e1.sendKeys(" ");
				test.log(Status.PASS, " Cleared" + elementname + " field Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to clear " + elementname + " field.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to clear " + elementname + " field.");
			log.error("Error in clearElementText:", e);
		}
	}

	public void clearElementText(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Clear " + elementname + "field");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
				element.clear();
				element.sendKeys(" ");
				test.log(Status.PASS, " Cleared" + elementname + " field Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to clear " + elementname + " field.");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to clear " + elementname + " field.");
			log.error("Error in clearElementText:", e);
		}
	}

	public void verifyLinkExistence(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Verify Link" + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				test.log(Status.PASS, "Link " + elementname + "exists");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Link  " + elementname + "does not exist");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Link  " + elementname + "does not exist");
			log.error("Error in VerifyLinkExistence:", e);
		}
	}

	// Verify Table Should contain
	public void tableShouldContain(By by, String elementname, String data) throws IOException {
		try {
			test.log(Status.INFO, "Headers");
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement webtable1 = driver.findElement(by);
				List<WebElement> rows = webtable1.findElements(By.tagName("tr"));
				int totalrows = rows.size();
				List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
				int totalcolumns = columns.size();
				List<String> value = new ArrayList<String>();
				for (int j = 0; j < totalcolumns; j++) {
					value.add(columns.get(j).getText());
				}
				if (value.contains(data)) {
					test.log(Status.PASS, data + "found in table ");
				}
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to find  " + data + "in table");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to find  " + data + "in table");
			log.error("Error in TableShouldContain:", e);
		}

	}

	// Click link in webtable
	// Pass the xpath of table and then search all links inside table and click link
	public void clickLinkinWebTable1(By by, String elementname, String link) throws IOException {
		try {
			test.log(Status.INFO, "links");
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement webtable1 = driver.findElement(by);
				List<WebElement> links = webtable1.findElements(By.tagName("a"));
				int totallinks = links.size();

				List<String> value = new ArrayList<String>();
				for (int j = 0; j < totallinks; j++) {

					value.add(links.get(j).getText());
				}
				if (value.contains(link)) {
					test.log(Status.PASS, link + "clicked successfully ");

				}
			}
		} catch (Exception e) {
			test.log(Status.FAIL, link + "Link not found in table");
			if (isRemoteExecution) {
				setCloudTestFailedStatus(link + "Link not found in table");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in TableShouldContain:", e);
		}

	}

	// TableShouldNotContain
	public void tableShouldNotContain(By by, String elementname, String data) throws IOException {
		try {
			test.log(Status.INFO, "webtable should not contain data");
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				WebElement webtable1 = driver.findElement(by);
				List<WebElement> rows = webtable1.findElements(By.tagName("tr"));
				int totalrows = rows.size();
				List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
				int totalcolumns = columns.size();
				List<String> value = new ArrayList<String>();
				for (int j = 0; j < totalcolumns; j++) {

					value.add(columns.get(j).getText());
				}
				if (value.contains(data)) {
					test.log(Status.PASS, data + "found in table ");
				}
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to find  " + data + "in table");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to find  " + data + "in table");
			log.error("Error in TableShouldNotContain:", e);
		}

	}

	public void scrollToElement(By by, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Scroll to " + elementname);
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement element = driver.findElement(by);
			if (element.isDisplayed()) {
		        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
				test.log(Status.PASS, "Scrolled to " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Scroll to " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Scroll to " + elementname + ".");
			log.error("Error in scrollToElement:", e);
		}
	}

	public void scrollToElement(WebElement element, String elementname) throws IOException {
		try {
			test.log(Status.INFO, "Scroll to " + elementname);
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
		        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
				test.log(Status.PASS, "Scrolled to " + elementname + " Successfully.");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed to Scroll to " + elementname + ".");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			test.log(Status.FAIL, "Failed to Scroll to " + elementname + ".");
			log.error("Error in scrollToElement:", e);
		}
	}
	
	public void goBack() throws Exception {
		try {
			test.log(Status.INFO, "Navigate back by browser history");
			driver.navigate().back();
			test.log(Status.PASS, "Browser back passed");
		} catch (Exception e) {
			test.log(Status.FAIL, "Browser back failed");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Browser back failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Go Back")));
			}
			log.error("Error in browser go back:", e);
		}
	}
	
	public Duration getTimeout() {
		int iTimeout = this.DEFAULT_TIMEOUT;
		try {
			String timeout = PropertyLoader.getProperty("timeout");
			if (timeout != null && timeout.length() > 0) {
				iTimeout = Integer.parseInt(timeout);
			}
		} catch (Exception e) {
			log.error("Error in getting timeout :", e);
		}
		return Duration.ofSeconds(iTimeout);
	}

	public Duration getImplicitWait() {
		int iWaitTime = this.IMPLICIT_WAIT;
		try {
			String timeout = PropertyLoader.getProperty("implicit.wait");
			if (timeout != null && timeout.length() > 0) {
				iWaitTime = Integer.parseInt(timeout);
			}
		} catch (Exception e) {
			log.error("Error in getting timeout :", e);
		}
		return Duration.ofSeconds(iWaitTime);
	}

	public void implicitWait(String data) throws Exception {
		try {
			test.log(Status.INFO, "Implicit Wait");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(data)));
			test.log(Status.PASS, "Implicit Wait completed");
		} catch (Exception e) {
			test.log(Status.FAIL, "Implicit Wait failed");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Implicit Wait failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Implicit Wait")));
			}
			log.error("Error in Implicit Wait:", e);
		}
	}

	public void explicitWait(By by, String webElementName, String data) throws Exception {
		try {
			test.log(Status.INFO, "Explicit Wait");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(data)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			test.log(Status.PASS, "Explicit Wait completed for " + webElementName);
		} catch (Exception e) {
			test.log(Status.FAIL, "Explicit Wait failed for " + webElementName);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Explicit Wait failed for " + webElementName);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Explicit Wait")));
			}
			log.error("Error in Explicit Wait:", e);
		}
	}

	public void explicitWait(WebElement webelement, String webElementName, String data) throws Exception {
		try {
			test.log(Status.INFO, "Explicit Wait");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(data)));
			wait.until(ExpectedConditions.visibilityOfElementLocated((By) webelement));
			test.log(Status.PASS, "Explicit Wait completed for " + webElementName);
		} catch (Exception e) {
			test.log(Status.FAIL, "Explicit Wait failed for " + webElementName);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Explicit Wait failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Explicit Wait")));
			}
			log.error("Error in Explicit Wait:", e);
		}
	}

	public void fluentWait(By by, String webElementName, String data) throws Exception {
		try {
			test.log(Status.INFO, "Fluent Wait");
			FluentWait wait = new FluentWait(driver);
			wait.withTimeout(getTimeout());
			wait.pollingEvery(Duration.ofSeconds(Integer.valueOf(data)));
			wait.ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			test.log(Status.PASS, "Fluent Wait completed for " + webElementName);
		} catch (Exception e) {
			test.log(Status.FAIL, "Fluent Wait failed");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Fluent Wait failed for " + webElementName);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Fluent Wait")));
			}
			log.error("Error in Fluent Wait:", e);
		}
	}

	public void fluentWait(WebElement webelement, String webElementName, String data) throws Exception {
		try {
			test.log(Status.INFO, "Fluent Wait");
			FluentWait wait = new FluentWait(driver);
			wait.withTimeout(getTimeout());
			wait.pollingEvery(Duration.ofSeconds(Integer.valueOf(data)));
			wait.ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By) webelement));
			test.log(Status.PASS, "Fluent Wait completed for " + webElementName);
		} catch (Exception e) {
			test.log(Status.FAIL, "Fluent Wait failed");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Fluent Wait failed for " + webElementName);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Fluent Wait")));
			}
			log.error("Error in Fluent Wait:", e);
		}
	}

	// mobile actions

	public void setCloudTestFailedStatus(String failReason) {
		if (isRemoteExecution) {
			if (!checkFailed) {
				if (gridURL.contains("lambdatest")) {
					((JavascriptExecutor) driver).executeScript("lambda-status=failed");
				} else if (gridURL.contains("browserstack")) {
					((JavascriptExecutor) driver).executeScript(
							"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
									+ failReason + "\"}}");
				}
			}
			this.checkFailed = true;
		}
	}

	public void setTestPassedStatus() {
		if (isRemoteExecution && !checkFailed) {
			if (gridURL.contains("lambdatest")) {
				((JavascriptExecutor) driver).executeScript("lambda-status=passed");
			} else if (gridURL.contains("browserstack")) {
				((JavascriptExecutor) driver).executeScript(
						"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test Passed!\"}}");
			}
		}
	}

	public void installMobileApp(String path) throws Exception {
		try {
			test.log(Status.INFO, "install Mobile App");
			driver.installApp(path);
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in install Mobile App");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in installMobileApp");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("installApp")));
			}
			log.error("Error in installMobileApp:", e);
		}
	}

	public void verifyMobileAppInstalled(String packageName) throws IOException {
		try {
			test.log(Status.INFO, "App Installed " + packageName);
			Boolean exists = driver.isAppInstalled(packageName);
			if (exists) {
				test.log(Status.PASS, "App " + packageName + " is installed");
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("App " + packageName + "is not installed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(packageName)));
			}
			test.log(Status.FAIL, "App" + packageName + " is not installed");
			log.error("Error in verifyMobileAppInstalled:", e);
		}
	}

	public void terminateMobileApp(String path) throws Exception {
		try {
			test.log(Status.INFO, "terminate Mobile App");
			driver.terminateApp(path);
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in terminate Mobile App");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in terminateMobileApp");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("terminateApp")));
			}
			log.error("Error in terminateMobileApp:", e);
		}
	}

	public void scrollDownMobile() throws IOException {
		scroll("down");
	}

	public void scrollUpMobile() throws IOException {
		scroll("up");
	}

	private void scroll(String direction) throws IOException {
		try {
			test.log(Status.INFO, "Scroll " + direction + " on mobile");
			JavascriptExecutor js = driver;
			HashMap<String, String> scrollObject = new HashMap<>();
			scrollObject.put("direction", direction);
			js.executeScript("mobile: scroll", scrollObject);
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Scroll " + direction + " on mobile failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("scroll" + direction)));
			}
			test.log(Status.FAIL, "Scroll " + direction + " on mobile failed");
			log.error("Error in scroll " + direction + "+:", e);
		}
	}

	public void swipeByCoordinatesMobile(String data) throws IOException {
		try {
			String[] inputs = data.split(",");
			if (inputs.length > 0) {
				int xStart = Integer.valueOf(inputs[0]);
				int yStart = Integer.valueOf(inputs[1]);
				int xEnd = Integer.valueOf(inputs[2]);
				int yEnd = Integer.valueOf(inputs[3]);
				test.log(Status.INFO, "swipe on mobile");
				Dimension size = driver.manage().window().getSize();

				int startPointX = size.getWidth() * (int) xStart;
				int startPointY = size.getHeight() * (int) yStart;
				int endPointX = size.getWidth() * (int) xEnd;
				int endPointY = size.getHeight() * (int) yEnd;
				JavascriptExecutor js = driver;
				Map<String, Object> params = new HashMap<>();
				params.put("duration", getImplicitWait());
				params.put("fromX", startPointX);
				params.put("fromY", startPointY);
				params.put("toX", endPointX);
				params.put("toY", endPointY);
				js.executeScript("mobile: dragFromToForDuration", params);
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("swipe by coordinates on mobile failed");
			} else {
				test.fail("Screenshot below: "
						+ test.addScreenCaptureFromPath(takeScreenShot("swipeByCoordinatesMobile")));
			}
			test.log(Status.FAIL, "swipe by coordinates on mobile failed");
			log.error("Error in swipe by coordinates : ", e);
		}
	}

	public void swipeByElementMobile(By object) throws IOException {
		try {
			test.log(Status.INFO, "swipe by element on mobile");
			if (object != null) {
				JavascriptExecutor js = driver;
				Map<String, Object> params = new HashMap<>();
				params.put("element", ((RemoteWebElement) driver.findElement(object)).getId());
				js.executeScript("mobile: dragFromToForDuration", params);
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("swipe by element on mobile failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("swipeByElementMobile")));
			}
			test.log(Status.FAIL, "swipe by element on mobile failed");
			log.error("Error in swipe by element: ", e);
		}
	}

	public void swipeByElementMobile(WebElement object) throws IOException {
		try {
			test.log(Status.INFO, "swipe by element on mobile");
			if (object != null) {
				JavascriptExecutor js = driver;
				Map<String, Object> params = new HashMap<>();
				params.put("element", ((RemoteWebElement) object));
				js.executeScript("mobile: dragFromToForDuration", params);
			}
		} catch (Exception e) {
			if (isRemoteExecution) {
				setCloudTestFailedStatus("swipe by element on mobile failed");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("swipeByElementMobile")));
			}
			test.log(Status.FAIL, "swipe by element on mobile failed");
			log.error("Error in swipe by element: ", e);
		}
	}

	public void launchMobileApplication() throws Exception {
		try {
			test.log(Status.INFO, "launch mobile Application");
			String appPackage = DesiredConfigLoader.getProperty("appPackage");
			String appActivity = DesiredConfigLoader.getProperty("appActivity");
			JavascriptExecutor js = driver;
			js.executeScript("mobile: startActivity", ImmutableMap.of("intent",appPackage+"/"+appActivity));
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in launch mobile Application");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in launchMobileApplication");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("LaunchMobileAPP")));
			}
			log.error("Error in launchMobileApplication:", e);

		}
	}

	public void closeMobileApplication() throws Exception {
		try {
			test.log(Status.INFO, "close mobile Application");
			String appPackage = DesiredConfigLoader.getProperty("appPackage");
			JavascriptExecutor js = driver;
			js.executeScript("mobile: clearApp", ImmutableMap.of("appId",appPackage));
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in close mobile Application");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in closeMobileApplication");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("closeMobileAPP")));
			}
			log.error("Error in closeMobileApplication:", e);

		}
	}

	public void resetMobileApplication() throws Exception {
		try {
			test.log(Status.INFO, "reset mobile Application");
			String appPackage = DesiredConfigLoader.getProperty("appPackage");
			String appActivity = DesiredConfigLoader.getProperty("appActivity");
			JavascriptExecutor js = driver;
			js.executeScript("mobile: clearApp", ImmutableMap.of("appId",appPackage));
			js.executeScript("mobile: startActivity", ImmutableMap.of("intent",appPackage+"/"+appActivity));
		} catch (Exception e) {
			test.log(Status.FAIL, "Error in reset mobile Application");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Error in resetMobileApplication");
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("resetMobileAPP")));
			}
			log.error("Error in resetMobileApplication:", e);

		}
	}

	public void verifyElementVisibleAndSwipe(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
				swipeByElementMobile(by);
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}

	public void verifyElementVisibleAndSwipe(WebElement element, String elementname, String data) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
				swipeByElementMobile(element);
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}

	public void verifyElementVisibleAndClick(By by, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement e1 = driver.findElement(by);
			if (e1.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
				clickElement(by, elementname);
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}

	public void verifyElementVisibleAndClick(WebElement element, String elementname) throws Exception {
		try {
			test.log(Status.INFO, "Verify is Element Visible_  " + elementname);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, getTimeout());
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				test.log(Status.PASS, elementname + " is Visible");
				clickElement(element, elementname);
			} else {
				test.log(Status.FAIL, elementname + " is not Visible ");
				setCloudTestFailedStatus(elementname + " is not Visible ");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed as Element is  " + elementname);
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Failed as Element is  " + elementname);
			} else {
				test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot(elementname)));
			}
			log.error("Error in VerifyElementVisible:", e);
		}
	}
	
	public void deadWait()
	{
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void switchToWebViewContext() {
        // Get available context handles
        Set<String> contextHandles = driver.getContextHandles();
        
        // Print out available context handles (optional)
        for (String context : contextHandles) {
            System.out.println("Context: " + context);
        }

        // Switch to webview context (assuming there's only one webview context)
        for (String context : contextHandles) {
            //if (context.contains("WEBVIEW_com.mu.muclubapp.staging_mu_dxc")) {
        	if (context.equals("WEBVIEW_chrome")) {
                driver.context(context);
              //  System.out.println("source code " + driver.getPageSource());
                System.out.println("Switched to webview context: " + context);
                break;
               
            }
        }
    }
	
	
	public void switchToNativeView()
	{
		 driver.context("NATIVE_APP");
		 System.out.println("Switched to native context");
	}
	
	public void selectDropdown(By by, String elementname)
	{
		
		System.out.println(driver.findElement(by).getAttribute("innerHTML"));
		
	}
	
	public void clickRadioButton()
	{
		if(driver.findElement(By.name("MarketingOptIn")).isDisplayed())
		{
			
			driver.activateApp("");
			System.out.println("name located");
		}
		else if(driver.findElement(By.id("MarketingOptIn")).isDisplayed())
		{
			System.out.println("id located");
		}
		else 
		{
			List<WebElement> el = driver.findElements(By.tagName("input"));
			System.out.println("e"+  el.size());
		}
	}
	
	
	public void clickRadioButtonTwo()
	{
		if(driver.findElement(By.name("MarketingOptIn")).isDisplayed())
		{
			System.out.println("name located");
		}
		else if(driver.findElement(By.id("MarketingOptIn")).isDisplayed())
		{
			System.out.println("id located");
		}
		else 
		{
			List<WebElement> el = driver.findElements(By.tagName("input"));
			System.out.println("e"+  el.size());
		}
	}
	
	   public  void clickOnRadioButton(String radioButtonId) {
	        // Execute JavaScript to click on the radio button element
	        ((JavascriptExecutor) driver).executeScript("document.getElementById('" + radioButtonId + "').click();");
	    }
	   
	   public boolean  checkIFLoginButtonPresent()
	   {
		   try
		   {
		   boolean isPresent = false ;
		   By loginButton= AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/secondary_text");
		   if(driver.findElement(loginButton).isDisplayed())
		   {
			   System.out.println("Login button present ");
			   isPresent =true;
		   }
		   
		   return isPresent;
		   }
		   
		   catch (Exception e) {
			   return false;
		   }
	   }
	   
	   public void gmailVerification(String usreName, String password)
	   {
		   WebDriver dr = null  ;
		   
		   try
		   {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=");
			WebDriverManager.chromedriver().setup();
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\pn60\\IAS\\driver\\chromedriver.exe");
		        // Instantiate a ChromeDriver class.
		         dr = new ChromeDriver();
		        dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		        // Maximize the browser
		        dr.manage().window().maximize();
		 
		        // Launch Website
		        dr.get("https://www.google.com/gmail/about/");
		        try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
	 		//By SignInBtn = By.xpath("//*[text()='Sign in']");
	 		//g//l.clickElement(SignInBtn,"SignInBtn");
	 		
		  //  ClickJS(dr,"//*[text()='Sign in']");
		        dr.findElement(By.xpath("//*[text()='Sign in']")).click();
	 		
	 		
	 		
	 		//By UseAnotherAccBtn = By.xpath("//*[text()='Use another account']");
	 		//gl.clickElement(UseAnotherAccBtn,"UseAnotherAccBtn");
	 		
	 		
	 		By UNTxt = By.xpath("//*[@id='identifierId']");
	 		dr.findElement(UNTxt).sendKeys(usreName);
	 		//gl.inputText(UNTxt, "UNTxt", "jyvgsj@gmail.com");
	 		
	 		By NextBtn = By.xpath("//*[text()='Next']");
	 		dr.findElement(By.xpath("//*[text()='Next']")).click();
	 		//ClickJS(dr,"//*[text()='Next']");
	 		//gl.clickElement(NextBtn,"NextBtn");
	 		deadWait();
	 		//By PWTxt = By.xpath("//*[@name='Passwd']");
	 		dr.findElement(By.name("Passwd")).sendKeys(password);
	 		//gl.inputText(PWTxt, "PWTxt", "Indu@101220");
	 		
	 	
	 		//By NextBtn1 = By.xpath("//*[text()='Next']");
	 		//ClickJS(dr,"//*[text()='Next']");
	 		dr.findElement(By.xpath("//*[text()='Next']")).click();
	 		
	 		deadWait();
	 		dr.findElement(By.xpath("//*[text()='Not now']")).click();
	 		
	 
	 		//By EmailVerificationLnk = By.xpath("(//span[text()='Email Verification'])[last()]");
	 	//	ClickJS(driver,"(//span[text()='Email Verification'])[last()]");
	 		dr.findElement(By.xpath("(//span[text()='Email Verification'])[last()]")).click();
	 		//gl.clickElement(EmailVerificationLnk,"EmailVerificationLnk");
	 		
	 		
	 		deadWait();
	 		
	// 	ClickJS(dr,"(//span[contains(text(),'CONFIRM MY EMAIL')])[last()]");
	 	dr.findElement(By.xpath("(//span[contains(text(),'CONFIRM MY EMAIL')])[last()]")).click();
	 	
		   }
		   catch (Exception e) {
			   dr.close();
		   }
	   }
	   
	   public void ClickJS(WebDriver dr,String xpath)
		{
			WebElement element = driver.findElement(By.xpath(xpath));
	 		JavascriptExecutor executor = (JavascriptExecutor)dr;
	 		executor.executeScript("arguments[0].click();", element);
		}
	   
	   public String getEmailID()
	   {
		  // WebDriver dr = null  ;
		   deadWait();
		   String emailID="";
		   
		   try
		   {
			   getWebDriverInstance();   
			
		dr.findElement(By.id("login")).sendKeys("pradeeptestKK5678788");
	
		dr.findElement(By.id("refreshbut")).click();

		 emailID=dr.findElement(By.className("bname")).getText();
		
		 dr.close();
			return emailID;
			
	 	
		   }
		   catch (Exception e) {
				return emailID;
			   
		   }
	   }
	   
	   public void getWebDriverInstance()
	   {
		   
		   try
		   {
		   ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=");
			WebDriverManager.chromedriver().setup();
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\pn60\\IAS\\driver\\chromedriver.exe");
		        // Instantiate a ChromeDriver class.
		         dr = new ChromeDriver();
		        dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		        // Maximize the browser
		        dr.manage().window().maximize();
		 
		        // Launch Website
		        dr.get("https://yopmail.com/en/");
		        try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		      
		   }
		        
		        catch (Exception e) {
					  
					 
				   }
	   }
	   
	   public void AuthorizeEmailID(String emailID)
	   {
		   getWebDriverInstance();   
		   
		   dr.findElement(By.id("login")).sendKeys(emailID);
	
			dr.findElement(By.id("refreshbut")).click();
			deadWait();
			System.out.println(dr.getPageSource());
			dr.findElement(By.xpath("//*[@id='mail']//a/span")).click();
			
			deadWait();
			
			dr.close();
			
			
			
		   
	   }
	   
	   
	   public void swipe( ) {
		   
		   try
		   {
		   
		   Dimension size = driver.manage().window().getSize();
		    int startX = size.getWidth() / 2;
		    int startY = size.getHeight() / 2;
		    int endX = startX;
		    int endY = (int) (size.getHeight() * 0.25);
		    PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		    Sequence sequence = new Sequence(finger1, 1)
		        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
		        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
		        .addAction(new Pause(finger1, Duration.ofMillis(200)))
		        .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
		        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		    driver.perform(Collections.singletonList(sequence));

	        
		    
		   }
		   catch(Exception e) {
			   
			   System.out.println("swipe exception"+ e.getMessage());
			   
		   }
	    }
	   
	   public Boolean checkIfVideoIsPresent()
	   {
		   
			By videoList= AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/framelayout_video_parent_container");
		//	By videoList= AppiumBy.id("Play");
		//	By videoList= AppiumBy.id("Video Player");
		 //  By videoList= AppiumBy.accessibilityId("Video Player");
		   By testvideo= AppiumBy.accessibilityId("Video to test new asset videodouble tap to activate");
		   
		   
			
			

			Boolean isFound=false;
		   int numberOfIteration =200;
		   
		   for(int i=1;i<numberOfIteration;i++ )
		   {
			   
			   swipe();
			   try
			   {
				   if(driver.findElement(videoList).isDisplayed())
				   {
					   if(driver.findElement(testvideo).isDisplayed())
					   {
					   
					   return true;
					   }
				   }
				   
			   }
			   catch (Exception e) {
					  
					 System.out.println("Elememt not found in " + i +"attempt");
			   }
 			   
		   }
		return isFound;
	   }
	   
	   public void clickOnVideo() throws IOException
	   {
		   By videoList= AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/framelayout_video_parent_container");
		   By videoFace= AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/brightcove_video_view");
		   By listennow= AppiumBy.accessibilityId("Listen Now button");
		   
		   
		   if(checkIfVideoIsPresent())
		   {
			   driver.findElement(videoList).click();	
			   
			   try
			   {
				   if(driver.findElement(listennow).isDisplayed())
				   {
					   driver.findElement(listennow).click();
				   }
			   }
			   
			   catch (Exception e) {
					  
					 
			   }
			 //  driver.findElement(videoFace).click();
			   clickElement(videoFace,"videoFace");
			  
			   		   
		   }
	   }
	   
	   public  double  getCurrentVideoTime() {
	        // Locate the element representing the current time of the video by its ID
		   By currentPlaytime = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/current_time");

	        // Get the text of the current time element
	        String currentTimeText = driver.findElement(currentPlaytime).getText();
	        System.out.println(currentTimeText);

	        // Extract the number from the text (assuming it's in seconds)
	        double currentTime = Double.parseDouble(currentTimeText.replaceAll("[^0-9.]", ""));

	        return currentTime;
	    }
	   
	   public  double  getTotalVideoTime() {
	        // Locate the element representing the current time of the video by its ID
		   By totalTime = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/end_time");

	        // Get the text of the current time element
	        String totalTimeText = driver.findElement(totalTime).getText();
	        
	        System.out.println(totalTimeText);

	        // Extract the number from the text (assuming it's in seconds)
	        double currentTime = Double.parseDouble(totalTimeText.replaceAll("[^0-9.]", ""));

	        return currentTime;
	    }
	   
	   public void pauseAutomationExecution(int seconds)
	   {
		   try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	   public void deadWait(int seconds)
	   {
		   try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	   public void ValidateVideoPlay(double intialPlayTime )
	   {
		   
		   if(intialPlayTime>0)
		   {
		   
		   test.log(Status.PASS, "Current runtime is greater that pause of the video" );
		
		   
		   
		   }
		   else
		   {
	
			test.log(Status.FAIL, "Video is not playing");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Current runtime is greater that pause of the video");
			} else {
				try {
					test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Video")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   }
		
		   
	   }
	   
	   public void ValidateVideoPause(double intialPlayTime,double afterPause )
	   {
		   
		   if(intialPlayTime==afterPause)
		   {
		   
		   test.log(Status.PASS, "Video is successfully puased" );
		
		   
		   
		   }
		   else
		   {
	
			test.log(Status.FAIL, "Video is not playing");
			if (isRemoteExecution) {
				setCloudTestFailedStatus("Current runtime is greater that pause of the video");
			} else {
				try {
					test.fail("Screenshot below: " + test.addScreenCaptureFromPath(takeScreenShot("Video")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   }
		
		   
	   }
	   
	   public void swipeIntially()
	   {
		   int maxNum=3;
		   
		   System.out.println(driver.getPageSource());
		   
		   for(int i =0;i<=maxNum; i++)
		   {
			   swipe();
		   }
		   
	   }

	  public void handleMatchScore() 
	  {
		   By currentPlaytime = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/textview_score");
		   By nextTinme = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/tertiary_text");
		   By newpopup = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/framelayout_tabwidget_parent");
		   By notNow = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/tertiary_text");
		   
		 try
		 {
		   if(isThere(currentPlaytime))
		   {
			   
			   driver.findElement(notNow).click();
			   
		   }
		   if(isThere(nextTinme))
		   {
			   driver.findElement(nextTinme).click(); 
		   }
		    if(isThere(newpopup))
		   {
			   driver.findElement(newpopup).click();  
		   }
		 }
		 catch (Exception e) {
			 
		 }
	  }
	  
	  public boolean isThere(By by) throws InterruptedException
	  {
		  boolean isPresent =false;
		  
		  if(driver.findElement(by).isDisplayed())
		  {
			  isPresent=true;
			  return isPresent;
			  
		  }
		return isPresent;
		  
	  }
	  
	  public void clickOnNonMUVideo() throws IOException
	  {
		    By nonmuTv = AppiumBy.accessibilityId("MUTV");
			By watchButton = AppiumBy.accessibilityId("Watch Now Button");
			By exploreButton = AppiumBy.accessibilityId("Explore");
			
			clickElement(nonmuTv,"nonmuTv");
			clickElement(exploreButton,"exploreButton");
			clickElement(watchButton,"watchButton");
			
	  }
	   
	  public  List<String> retrieveContentDesc( ) {
		  
		//  By listOfVideo = AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/rv_hightlights");
	        // Find the RecyclerView element by ID
	      //  WebElement recyclerView = driver.findElement(By.id("com.mu.muclubapp.staging_mu_dxc:id/rv_hightlights"));
	     // Find the RecyclerView by XPath
	        WebElement recyclerView = driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.mu.muclubapp.staging_mu_dxc:id/rv_hightlights']"));


	        // Find all elements within the RecyclerView
	        List<WebElement> elements = recyclerView.findElements(By.xpath(".//*"));

	        // Create a list to store content-desc attributes
	        List<String> contentDescList = new ArrayList<>();

	        // Iterate over each element and retrieve its content-desc attribute
	        for (WebElement element : elements) {
	            String contentDesc = element.getAttribute("content-desc");
	            if (contentDesc != null && !contentDesc.isEmpty()) {
	                contentDescList.add(contentDesc);
	            }
	        }

	        return contentDescList;
	    }
	  
	  
	  public  void clickOnFirstElement() throws IOException {
		 //  By videoFace= AppiumBy.id("com.mu.muclubapp.staging_mu_dxc:id/brightcove_video_view");
		  By videoFace= AppiumBy.accessibilityId("videodouble tap to activate");
		  
		  WebElement recyclerView = driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.mu.muclubapp.staging_mu_dxc:id/rv_hightlights']"));


	        // Find all elements within the RecyclerView
	        List<WebElement> elements = recyclerView.findElements(By.xpath(".//*"));

	        // Create a list to store content-desc attributes
	     //   List<String> contentDescList = new ArrayList<>();

	        // Iterate over each element and retrieve its content-desc attribute
	        for (WebElement element : elements) {
	            String contentDesc = element.getAttribute("content-desc");
	            System.out.println(contentDesc);
	            if (contentDesc != null && !contentDesc.isEmpty()) {
	            	if(contentDesc.contains("video  Content posted"))
	 	            {	
	                By ele = AppiumBy.accessibilityId(contentDesc);           
	               driver.findElement(ele).click() ;
	               clickElement(videoFace,"videoFace");
	               return;
	               }
	            }
	        }
		 
	    }
	  
	  public boolean checkVideoTitleTag(By Ele)
	  {
		
		  boolean isVideo = false;
		  
		  try
		  {
		// Find all elements within the RecyclerView
			  WebElement webEle=driver.findElement(Ele);
	        List<WebElement> elements = webEle.findElements(By.xpath(".//*"));


	        // Iterate over each element and retrieve its content-desc attribute
	        for (WebElement element : elements) {
	            String contentDesc = element.getAttribute("content-desc");
	            System.out.println(contentDesc);
	            if (contentDesc.contains("com.mu.muclubapp.staging_mu_dxc:id/tv_title")) {
	           
	            	isVideo=true;
	               return isVideo;
	            }
	        }
		  }
		  
		  catch (Exception ex)
		  {
			  return false;
		  }
		return isVideo;
		  
		  
	  }
	  
	  public  String generateRandomAlphanumeric(int length) {
	        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        
	        for (int i = 0; i < length; i++) {
	            // Select a random character from the characters string and append it to the StringBuilder
	            int randomIndex = random.nextInt(characters.length());
	            sb.append(characters.charAt(randomIndex));
	        }
	        
	        return sb.toString();
	    }
	  
	  public void getWebDriverInstanceForMailNator()
	   {
		   
		   try
		   {
		   ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=");
			WebDriverManager.chromedriver().setup();
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\pn60\\IAS\\driver\\chromedriver.exe");
		        // Instantiate a ChromeDriver class.
		         dr = new ChromeDriver();
		        dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		        // Maximize the browser
		        dr.manage().window().maximize();
		 
		        // Launch Website
		        dr.get("https://www.mailinator.com/");
		        try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        dr.findElement(By.xpath("//*[@id=\"menu-item-7937\"]/a/span")).click();
		        deadWait(5000);
		        dr.findElement(By.id("many_login_email")).sendKeys("pradeep.n@dxc.com");
		        dr.findElement(By.id("many_login_password")).sendKeys("KeepCalm19!");
		        dr.findElement(By.xpath("//a[@class='btn btn-default submit']")).click();
		        deadWait(5000);	
		        dr.findElement(By.xpath("//table[@class='table-striped jambo_table']/tbody/tr[1]")).click();
		        deadWait(5000);	
		        dr.switchTo().frame("html_msg_body");
		        dr.findElement(By.xpath("//td[@class='btnlinkwhite']//a")).click();
		        deadWait(10000);
		        Set<String> windowHandles = dr.getWindowHandles();

		        // Switch to the new tab (assuming it's the last one opened)
		        for (String handle : windowHandles) {
		            dr.switchTo().window(handle);
		        }

		        // Now you're in the new tab, you can perform actions as needed
		        // For example, you can refresh the page
		        dr.navigate().refresh();
		       // dr.findElement(By.xpath(" //p/a")).click();
		        deadWait(5000);	
		        
		       if( dr.findElement(By.xpath("//a[@class='btn']")).isDisplayed())
		       {
		    	   System.out.println("Email verification successfull ");
		       }
		        dr.quit();

		      
		   }
		        
		        catch (Exception e) {
		        	  dr.quit();
					  
					System.out.println( e.getMessage());
				   }
	   }
	  
	  public void writepageSource()
	  {
		  System.out.println("source code " + driver.getPageSource());
	  }
	  
	  
}
