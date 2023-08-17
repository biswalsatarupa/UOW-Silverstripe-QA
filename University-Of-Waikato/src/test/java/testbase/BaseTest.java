package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.perfecto.reportium.client.ReportiumClient;
//import com.perfecto.reportium.client.ReportiumClientFactory;
//import com.perfecto.reportium.model.PerfectoExecutionContext;
//import com.perfecto.reportium.test.TestContext;
//import com.perfecto.reportium.test.result.TestResultFactory;

import utility.ConfigReader;
import utility.DriverUtils;
import utility.ExcelUtils;
import utility.ReportingUtils;

public class BaseTest {
	
	public DriverUtils driverUtils= new DriverUtils(); 
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest>  extentTestThreadSafe = new ThreadLocal<ExtentTest>(); 
	public String methodName;
	public String previousmethodName = "";
	public static int iteration = 1;
	
	ConfigReader configReader = new ConfigReader();
	
	@BeforeTest
	public void setup() throws FileNotFoundException, IOException {
		driverUtils.createWebDriver();
		
		
		
	}
	
	
	@BeforeMethod
	public void getMethodName(Method method,ITestContext context,ITestResult result)
	{
		methodName = method.getName(); 
		test = extent.createTest(result.getMethod().getDescription());
		test.log(Status.INFO, MarkupHelper.createLabel(methodName + " STARTED ", ExtentColor.BLUE));
		if (iteration == 1) {
			previousmethodName = method.getName();
		}
		if (!previousmethodName.equalsIgnoreCase(methodName)) {
			iteration = 1;
			previousmethodName = method.getName();
		}
		

		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Throwable  {		
		try {
			new ReportingUtils().updateStatusToReport(test,result,iteration);
			iteration++;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		
	}
	
	@BeforeSuite
	public void beforeTest(ITestContext context) throws FileNotFoundException, IOException {
		extent= ReportingUtils.setupExtentReport(extent,context);
		
		
	}
	
	

	
	@AfterTest
	public void AfterTest()
	{
		extent.flush();
		driverUtils.tearDown();
	}
	
	@DataProvider(name = "getTestData")	 
	public Object[][] getTestData(Method method) throws IOException {		
		return new ExcelUtils().getData(method.getName());
	}

	
	 public static synchronized ExtentTest getTest() 
	 { 
	   return extentTestThreadSafe.get(); 
	 }
	 public static void setTest(ExtentTest tst) 
	 { 
	    extentTestThreadSafe.set(tst); 
	 }
	 
		
		public static String getEpochTimeStamp() {
			Instant instant = Instant.now();
			long timeStampSeconds = instant.getEpochSecond();
			return String.valueOf(timeStampSeconds);
		}
	 
}
