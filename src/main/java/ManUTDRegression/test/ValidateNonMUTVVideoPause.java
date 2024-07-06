package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginToManUApp;
import ManUTDRegression.pom.videopage.PauseNonMUTV;


public class ValidateNonMUTVVideoPause extends BaseTest{

	@DataProvider(name = "ValidateNonMUTVVideoPause")
	public Object[][] dataValidateNonMUTVVideoPause() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "ValidateNonMUTVVideoPause");
		return xl.getTestdata();
	}



	@Test
public void runValidateNonMUTVVideoPause() throws Exception {
	LoginToManUApp logintomanuapp  = new LoginToManUApp();
		logintomanuapp.beforemethod( driver,report,test);
		logintomanuapp.test();
		logintomanuapp.afterMethod();

		PauseNonMUTV pausenonmutv  = new PauseNonMUTV();
		pausenonmutv.beforemethod( driver,report,test);
		pausenonmutv.test();
		pausenonmutv.afterMethod();


}

}
