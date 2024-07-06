package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginToManUApp;


public class ValidateManUAppLogin extends BaseTest{

	

	@Test
	public void runLoginToManUApp() throws Exception {
		LoginToManUApp logintomanuapp  = new LoginToManUApp();
		logintomanuapp.beforemethod( driver,report,test);
		logintomanuapp.test();
		logintomanuapp.afterMethod();
	}



}
