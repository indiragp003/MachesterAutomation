package ManUTDRegression.framework.generic;

import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;

import ManUTDRegression.framework.db.ObjectRepo;

public class WebElementUtil {

	public static By getElement(String name) {
		ObjectRepo repo = new ObjectRepo();
		String webElementName = repo.readProperty(name);
		String webElementType = repo.readPropLoc(name);
		By by = null;
		switch (webElementType) {
		case "NAME":
			by = AppiumBy.name(webElementName);
			break;
		case "ID":
			by = AppiumBy.id(webElementName);
			break;
		case "CLASSNAME":
			by = AppiumBy.className(webElementName);
			break;
		case "XPATH":
			by = AppiumBy.xpath(webElementName);
			break;
		case "LINKTEXT":
			by = AppiumBy.linkText(webElementName);
			break;
		case "TAGNAME":
			by = AppiumBy.tagName(webElementName);
			break;
		case "PARTIALLINKTEXT":
			by = AppiumBy.partialLinkText(webElementName);
			break;
		case "CSS_SELECTOR":
			by = AppiumBy.cssSelector(webElementName);
			break;
		case "ANDROID_UI_AUTOMATOR":
			by = AppiumBy.androidUIAutomator(webElementName);
			break;
		case "IOS_UI_AUTOMATION":
			by = AppiumBy.iOSClassChain(webElementName);
			break;
		case "ACCESIBILITY_ID":
			by = AppiumBy.id(webElementName);
			break;
		case "ANDROID_VIEW_TAG":
			by = AppiumBy.androidViewTag(webElementName);
			break;
		case "iOS_NsPREDICATE_STRING":
			by = AppiumBy.iOSNsPredicateString(webElementName);
			break;
		default:
			break;
		}
		return by;
	}
}

