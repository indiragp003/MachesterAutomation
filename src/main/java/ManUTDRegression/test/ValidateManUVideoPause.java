package ManUTDRegression.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ManUTDRegression.framework.excel.ExcelLib;
import ManUTDRegression.pom.loginpage.LoginToManUApp;
import ManUTDRegression.pom.videopage.PauseVideo;


public class ValidateManUVideoPause extends BaseTest{

	@DataProvider(name = "ValidateManUVideoPause")
	public Object[][] dataValidateManUVideoPause() throws Exception{
		ExcelLib xl = new ExcelLib("Sheet1", "ValidateManUVideoPause");
		return xl.getTestdata();
	}



	@Test
public void runValidateManUVideoPause() throws Exception {
	LoginToManUApp logintomanuapp  = new LoginToManUApp();
		logintomanuapp.beforemethod( driver,report,test);
		logintomanuapp.test();
		logintomanuapp.afterMethod();

		PauseVideo pausevideo  = new PauseVideo();
		pausevideo.beforemethod( driver,report,test);
		pausevideo.test();
		pausevideo.afterMethod();


}

}
