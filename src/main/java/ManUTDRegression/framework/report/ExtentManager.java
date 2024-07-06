package ManUTDRegression.framework.report;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	final static Logger log = LogManager.getLogger(ExtentManager.class);

	private ExtentReports extent;
	private static volatile ExtentManager extentMgr = null;
	private final String name;
	
	private ExtentManager(String name) {
		this.name = name;
	}

	public static ExtentManager getInstance() {
		if (extentMgr == null) {
			throw new AssertionError("Call init method first");
		}
		return extentMgr;
	}
	
	public synchronized static ExtentManager init(String name) {
		if (extentMgr != null) { return extentMgr; }
		extentMgr = new ExtentManager(name);
		return extentMgr;
	}

	public synchronized ExtentReports getExtent() throws IOException {
		if (extent != null)
			return extent; // avoid creating new instance of html file
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private ExtentSparkReporter getHtmlReporter() throws IOException {
		
		Date date = new java.util.Date();
		String[] date1 = date.toString().split(" ");
		String[] date2 = date1[3].split(":");
		String dateval = date2[0] + date2[1] + date2[2];
		
		String basedir = System.getProperty("user.dir");
		basedir = URLDecoder.decode(basedir, "UTF-8");
		String filePath = basedir + File.separator + "reports" + File.separator;
		String filepath2 = filePath + this.name + date1[1] + date1[2] + dateval + ".html";
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filepath2);
		
		ClassLoader classLoader = ExtentManager.class.getClassLoader();
		String configPath = classLoader.getResource("templates/report/extent-config.xml").getFile();
		configPath = URLDecoder.decode(configPath, "UTF-8");
		File file = new File(configPath);
		htmlReporter.loadXMLConfig(file);
		return htmlReporter;
	}

}