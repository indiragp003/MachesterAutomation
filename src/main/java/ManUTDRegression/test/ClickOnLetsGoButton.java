package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LaunchApp;
import ManUTDRegression.pom.loginpage.ClickOnLetsGo;


public class ClickOnLetsGoButton extends BaseTest{

	@DataProvider(name = "ClickOnLetsGoButton")
	public Object[][] dataClickOnLetsGoButton() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "ClickOnLetsGoButton");
		return xl.getTestdata();
	}



	@Test
public void runClickOnLetsGoButton() throws Exception {
	LaunchApp launchapp  = new LaunchApp();
		launchapp.beforemethod( driver,report,test);
		launchapp.test();
		launchapp.afterMethod();

		ClickOnLetsGo clickonletsgo  = new ClickOnLetsGo();
		clickonletsgo.beforemethod( driver,report,test);
		clickonletsgo.test();
		clickonletsgo.afterMethod();


}

}
