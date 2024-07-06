package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.ClickOnLetsGoTwo;


public class ClickOnLetsGoTwoTestcase extends BaseTest{

	

	@Test
	public void runClickOnLetsGoTwo() throws Exception {
		ClickOnLetsGoTwo clickonletsgotwo  = new ClickOnLetsGoTwo();
		clickonletsgotwo.beforemethod( driver,report,test);
		clickonletsgotwo.test();
		clickonletsgotwo.afterMethod();
	}



}
