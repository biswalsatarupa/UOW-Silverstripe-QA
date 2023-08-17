 package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportingUtils {
	
	public static boolean Exceptionexists = false;
	public static String exceptionTrace = null;
	public synchronized static ExtentReports setupExtentReport(ExtentReports extent, ITestContext context) throws FileNotFoundException, IOException {		
		String reportName= context.getCurrentXmlTest().getSuite().getName()+"_"+new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); 
		String reportPath = System.getProperty("user.dir") + File.separator+ new ConfigReader().getReportPath() + File.separator +reportName+".html";
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
		 htmlReporter.config().setDocumentTitle("Automation Execution Report");
		 htmlReporter.config().setReportName(context.getCurrentXmlTest().getSuite().getName()+" Automation Execution Report");
		 extent = new ExtentReports();
	     extent.attachReporter(htmlReporter);
	     
	     extent.setSystemInfo("OS", "Windows");
	     extent.setSystemInfo("Browser", new ConfigReader().getBrowserName());
	     extent.setSystemInfo("AUT", "QA");
	     
	     return extent;
	}
	
	public void updateStatusToReport(ExtentTest test, ITestResult result,int iteration) throws FileNotFoundException, IOException {
		if(result.getStatus() == ITestResult.FAILURE) {
			Exceptionexists = true;
			
			String imagePath = captureScreenshot("final-step");

            test.log(Status.FAIL, "final step",  MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
            test.fail(result.getThrowable());
            exceptionTrace = result.getThrowable().toString();
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {            
            String imagePath = captureScreenshot("final-step");
            test.log(Status.PASS, "final step",  MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));

        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
	}
	
	public String captureScreenshot(String name) throws FileNotFoundException, IOException {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		//String reportPath = System.getProperty("user.dir") + "/"+ new ConfigReader().getReportPath();
		String reportPath  = new ConfigReader().getReportPath();
		String filename = name + "-" + timeStamp+ ".png";
		File scrFile = ((TakesScreenshot)new DriverUtils().getDriver()).getScreenshotAs(OutputType.FILE);
		String screenshotPath=reportPath + File.separator + name + "-" + timeStamp+ ".png";
		FileUtils.copyFile(scrFile, new File(screenshotPath));
		return filename;
	}
	
	public void addScreenshotToReport(ExtentTest test,String screenshotName) {
		String imagePath = null;
		try {
			imagePath = captureScreenshot(screenshotName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			test.log(Status.INFO, screenshotName,  MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}		
	}
	
	public void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

            //Move image file to new destination

                File DestFile=new File(fileWithPath);

                //Copy file at destination

                FileUtils.copyFile(SrcFile, DestFile);

    }

}
