package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.gmailloginpage.GmailLogin;


public class AuthorizeFromGmail extends BaseTest{

	@DataProvider(name = "GmailLogin")
	public Object[][] dataGmailLogin() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "GmailLogin");
		return xl.getTestdata();
	}




	@Test(dataProvider = "GmailLogin")
	public void runGmailLogin(String EmailID) throws Exception {
		
		/*GmailLogin gmaillogin  = new GmailLogin();
		gmaillogin.beforemethod( driver,report,test);
		gmaillogin.test(EmailID);
		gmaillogin.afterMethod();
		*/
	}



}
