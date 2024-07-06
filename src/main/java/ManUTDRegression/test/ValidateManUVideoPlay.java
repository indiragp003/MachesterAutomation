package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginToManUApp;
import ManUTDRegression.pom.videopage.PlayVideo;


public class ValidateManUVideoPlay extends BaseTest{

	@DataProvider(name = "ValidateManUVideoPlay")
	public Object[][] dataValidateManUVideoPlay() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "ValidateManUVideoPlay");
		return xl.getTestdata();
	}



	@Test
public void runValidateManUVideoPlay() throws Exception {
	LoginToManUApp logintomanuapp  = new LoginToManUApp();
		logintomanuapp.beforemethod( driver,report,test);
		logintomanuapp.test();
		logintomanuapp.afterMethod();

		PlayVideo playvideo  = new PlayVideo();
		playvideo.beforemethod( driver,report,test);
		playvideo.test();
		playvideo.afterMethod();


}

}
