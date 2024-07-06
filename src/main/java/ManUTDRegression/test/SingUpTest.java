package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.singuppage.SingUp;


public class SingUpTest extends BaseTest{

	@DataProvider(name = "SingUp")
	public Object[][] dataSingUp() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "SingUp");
		return xl.getTestdata();
	}




	@Test(dataProvider = "SingUp")
	public void runSingUp(String UserNameField) throws Throwable {
		SingUp singup  = new SingUp();
		singup.beforemethod( driver,report,test);
		singup.test(UserNameField);
		singup.afterMethod();
		
	}



}
