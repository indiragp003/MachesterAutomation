package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginPageCheck;


public class ValidateLogin extends BaseTest{

	

	@Test
	public void runLoginPageCheck() throws Exception {
		LoginPageCheck loginpagecheck  = new LoginPageCheck();
		loginpagecheck.beforemethod( driver,report,test);
		loginpagecheck.test();
		loginpagecheck.afterMethod();
	}



}
