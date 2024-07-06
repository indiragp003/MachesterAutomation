package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LaunchApp;


public class LaunchManUApp extends BaseTest{

	

	@Test
	public void runLaunchApp() throws Exception {
		LaunchApp launchapp  = new LaunchApp();
		launchapp.beforemethod( driver,report,test);
		launchapp.test();
		launchapp.afterMethod();
	}



}
