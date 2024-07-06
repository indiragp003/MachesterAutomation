package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginToManUApp;
import ManUTDRegression.pom.videopage.PlayNonMUTV;


public class ValidateNonMUTVPlay extends BaseTest{

	@DataProvider(name = "ValidateNonMUTVPlay")
	public Object[][] dataValidateNonMUTVPlay() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "ValidateNonMUTVPlay");
		return xl.getTestdata();
	}



	@Test
public void runValidateNonMUTVPlay() throws Exception {
	LoginToManUApp logintomanuapp  = new LoginToManUApp();
		logintomanuapp.beforemethod( driver,report,test);
		logintomanuapp.test();
		logintomanuapp.afterMethod();

		PlayNonMUTV playnonmutv  = new PlayNonMUTV();
		playnonmutv.beforemethod( driver,report,test);
		playnonmutv.test();
		playnonmutv.afterMethod();


}

}
