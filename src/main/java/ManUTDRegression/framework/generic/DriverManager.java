package ManUTDRegression.framework.generic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.ClientConfig;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverManager {
	
	private AppiumDriver driver;
	private DecryptionService decrypt;
	final static Logger log = LogManager.getLogger(DriverManager.class);
	public void initDriver(Map<String, String> map) throws Exception {
		boolean isRemoteExecution = Boolean.parseBoolean(PropertyLoader.getProperty("isRemoteExecution"));
		String executionType = PropertyLoader.getProperty("appiumTestType");
		if(executionType.isEmpty() || executionType.isBlank())
			executionType = "Browser";
		if(isRemoteExecution) {
			if(executionType.equalsIgnoreCase("App")) 
				getRemoteAppTestDriver();
			else
				getRemoteBrowserTestDriver();
		} else {
			if(executionType.equalsIgnoreCase("App")) 
				getAppTestDriver();
			else
				getBrowserTestDriver();
		}
	}
	
	public AppiumDriver getDriver() {
		return this.driver;
	}
	
	private AppiumDriver getRemoteAppTestDriver() throws Exception {
		String platformName = PropertyLoader.getProperty("platformName");
		log.debug("Remote execution driver requested getRemoteAppTestDriver " + platformName);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("deviceName", PropertyLoader.getProperty("deviceName"));
		desiredCapabilities.setCapability("platformName", PropertyLoader.getProperty("platformName"));
		setDesiredCapabilitiesFromProp(desiredCapabilities);
		String url = "";
		boolean proxy = false;
		AppiumClientConfig appiumClientConfig = null;
		if (!(PropertyLoader.getProperty("proxyAddress").isBlank()
				|| PropertyLoader.getProperty("proxyAddress").isEmpty())) {
			proxy = true;
		}
		try {
			decrypt = new DecryptionService();
			if (PropertyLoader.getProperty("username").isBlank() || PropertyLoader.getProperty("username").isEmpty()) {
				url = PropertyLoader.getProperty("appiumUrl");	
			} else {
				url ="https://" + PropertyLoader.getProperty("username") + ":"
						+ decrypt.checkEncryption(PropertyLoader.getProperty("accessKey"))
						+ PropertyLoader.getProperty("appiumUrl");
			}
			if (PropertyLoader.getProperty("platformName").equalsIgnoreCase("ios")) {
				if (proxy) {
					appiumClientConfig = getProxyDetails(url);
					driver = new IOSDriver(appiumClientConfig, desiredCapabilities);
				} else {
					driver = new IOSDriver(new URL(url), desiredCapabilities);
				}

			} else {
				if (proxy) {
					appiumClientConfig = getProxyDetails(url);
					driver = new AndroidDriver(appiumClientConfig, desiredCapabilities);
				} else {
					driver = new AndroidDriver(new URL(url), desiredCapabilities);
				}

			}
			return driver;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Unable to get driver session");
		}

	}

	private AppiumDriver getRemoteBrowserTestDriver() throws Exception {

		String platformName = PropertyLoader.getProperty("platformName");
		log.debug("Remote execution driver requested getRemoteBrowserTestDriver " + platformName);
		// load remote web drivers from cloud test
		// Set the desired capabilities (e.g., browser, device, OS)

		String executionPlatform = PropertyLoader.getProperty("executionPlatform");
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		switch (executionPlatform) {
		case "LambdaTest":
			HashMap<String, Object> ltOptions = new HashMap<String, Object>();
			ltOptions.put("w3c", true);
			ltOptions.put("platformName", platformName);
			ltOptions.put("deviceName", PropertyLoader.getProperty("deviceName"));
			ltOptions.put("platformVersion", PropertyLoader.getProperty("deviceVersion"));
			ltOptions.put("isRealMobile", true);
			desiredCapabilities.setCapability("LT:options", ltOptions);
			break;
		case "BrowserStack":
			HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
			bstackOptions.put("platformName", platformName);
			bstackOptions.put("deviceName", PropertyLoader.getProperty("deviceName"));
			bstackOptions.put("platformVersion", PropertyLoader.getProperty("deviceVersion"));
			desiredCapabilities.setCapability("bstack:options", bstackOptions);
			break;
		default:
			break;
		}
		setDesiredCapabilitiesFromProp(desiredCapabilities);
		String url = "";
		boolean proxy = false;
		AppiumClientConfig appiumClientConfig = null;
		if (!(PropertyLoader.getProperty("proxyAddress").isBlank()
				|| PropertyLoader.getProperty("proxyAddress").isEmpty())) {
			proxy = true;
		}
		try {
			decrypt = new DecryptionService();
			if (PropertyLoader.getProperty("username").isBlank() || PropertyLoader.getProperty("username").isEmpty()) {
				url = PropertyLoader.getProperty("appiumUrl");	
			} else {
				url ="https://" + PropertyLoader.getProperty("username") + ":"
						+ decrypt.checkEncryption(PropertyLoader.getProperty("accessKey"))
						+ PropertyLoader.getProperty("appiumUrl");
			}
			if (PropertyLoader.getProperty("platformName").equalsIgnoreCase("ios")) {
				if (proxy) {
					appiumClientConfig = getProxyDetails(url);
					driver = new IOSDriver(appiumClientConfig, desiredCapabilities);
				} else {
					driver = new IOSDriver(new URL(url), desiredCapabilities);
				}

			} else {
				if (proxy) {
					appiumClientConfig = getProxyDetails(url);
					driver = new AndroidDriver(appiumClientConfig, desiredCapabilities);
				} else {
					driver = new AndroidDriver(new URL(url), desiredCapabilities);
				}

			}
			return driver;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Unable to get driver session");
		}

	}

	private AppiumDriver getAppTestDriver() throws Exception {
		String platformName = PropertyLoader.getProperty("platformName");
		
		/*
		log.debug("getAppTestDriver " + platformName);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("deviceName", PropertyLoader.getProperty("deviceName"));
		desiredCapabilities.setCapability("platformName", PropertyLoader.getProperty("platformName"));
		desiredCapabilities.setCapability("platformVersion", PropertyLoader.getProperty("deviceVersion"));
		setDesiredCapabilitiesFromProp(desiredCapabilities);
		try {
			if (PropertyLoader.getProperty("platformName").equalsIgnoreCase("ios")) {
				driver = new IOSDriver(new URL(PropertyLoader.getProperty("appiumUrl")), desiredCapabilities);
			} else {
				driver = new AndroidDriver(new URL(PropertyLoader.getProperty("appiumUrl")), desiredCapabilities);
			}
			return driver;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Unable to get driver session");
		}
		*/
		
		DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserstack.user", "elamathivijayasa_BRaXh3");
        caps.setCapability("browserstack.key", "myzaL82yR4HMHGzpqghY");
        caps.setCapability("appium:device", "Google Pixel 7");
      //  caps.setCapability("os_version", "13.0");
        caps.setCapability("appium:app", "bs://e62d4891b4efd240f8a629ea3e3d67c33aaa0e41");
        caps.setCapability("deviceName", "Google Pixel 7");
        caps.setCapability("interactiveDebugging", true);
        caps.setCapability("BROWSERSTACK_IDLE_TIMEOUT", "600");
      // driver = new AndroidDriver(new URL("http://hub.browserstack.com/wd/hub"), caps);
        driver = new AndroidDriver(new URL("https://elamathivijayasa_BRaXh3:myzaL82yR4HMHGzpqghY@hub.browserstack.com/wd/hub"), caps);
         System.out.println("driver created succssufflu");
         return driver;
	}

	private AppiumDriver getBrowserTestDriver() throws Exception {

		String platformName = PropertyLoader.getProperty("platformName");
		log.debug("getBrowserTestDriver " + platformName);
		String appiumUrl = PropertyLoader.getProperty("appiumUrl");
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			String executionPlatform = PropertyLoader.getProperty("executionPlatform");
			switch (executionPlatform) {
			case "LambdaTest":
				HashMap<String, Object> ltOptions = new HashMap<String, Object>();
				ltOptions.put("w3c", true);
				ltOptions.put("platformName", platformName);
				ltOptions.put("deviceName", PropertyLoader.getProperty("deviceName"));
				ltOptions.put("platformVersion", PropertyLoader.getProperty("deviceVersion"));
				ltOptions.put("isRealMobile", true);
				capabilities.setCapability("LT:options", ltOptions);
				break;
			case "BrowserStack":
				HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
				bstackOptions.put("platformName", platformName);
				bstackOptions.put("deviceName", PropertyLoader.getProperty("deviceName"));
				bstackOptions.put("platformVersion", PropertyLoader.getProperty("deviceVersion"));
				capabilities.setCapability("bstack:options", bstackOptions);
				break;
			default:
				break;
			}
			setDesiredCapabilitiesFromProp(capabilities);
			if (PropertyLoader.getProperty("platformName").equalsIgnoreCase("ios")) {
				driver = new IOSDriver(new URL(PropertyLoader.getProperty("appiumUrl")), capabilities);
			} else {
				driver = new AndroidDriver(new URL(PropertyLoader.getProperty("appiumUrl")), capabilities);
			}
			return driver;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Unable to get driver session");
		}

	}

	private DesiredCapabilities setDesiredCapabilitiesFromProp(DesiredCapabilities desiredCapabilites) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(
					PropertyLoader.class.getClassLoader().getResourceAsStream("properties/desiredConfig.properties"));
		} catch (IOException e) {
			log.error("Unable to load desired config properties", e);
		}

		if (null != properties)
			for (Entry<Object, Object> entry : properties.entrySet()) {
				if (!entry.getKey().toString().equalsIgnoreCase("deviceName")
						&& !entry.getKey().toString().equalsIgnoreCase("deviceVersion")
						&& !entry.getKey().toString().equalsIgnoreCase("platformName")) {
					log.debug("Setting capability " + entry.getKey().toString() + " to value "
							+ entry.getValue().toString());
					desiredCapabilites.setCapability(entry.getKey().toString(), entry.getValue().toString());
				}
			}
		return desiredCapabilites;
	}

	private AppiumClientConfig getProxyDetails(String Url) throws Exception {
		ClientConfig clientConfig = ClientConfig.defaultConfig().baseUrl(new URL(Url	))
				.proxy(new java.net.Proxy(java.net.Proxy.Type.HTTP,
						new InetSocketAddress(PropertyLoader.getProperty("proxyAddress"),
								Integer.valueOf(PropertyLoader.getProperty("proxyAddress")))))
				.authenticateAs(new UsernameAndPassword(PropertyLoader.getProperty("proxyUserName"),
						decrypt.checkEncryption(PropertyLoader.getProperty("proxyPassword"))));
		AppiumClientConfig appiumClientConfig = AppiumClientConfig.fromClientConfig(clientConfig).directConnect(true)
				.readTimeout(Duration.ofSeconds(Integer.valueOf(PropertyLoader.getProperty("timeout"))));
		return appiumClientConfig;
	}
}