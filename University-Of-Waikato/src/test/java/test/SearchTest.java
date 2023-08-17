package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.Assert;
import org.testng.ITestContext;


import pages.SearchPage;
import testbase.BaseTest;
import utility.ConfigReader;
import utility.DriverUtils;

public class SearchTest extends BaseTest {

	private WebDriver driver;
	private SearchPage searchPageObj;
	
	private ConfigReader configReader;
	private String url;

	public SearchTest() {
		configReader = new ConfigReader();
		url = configReader.getTestURL();
	}
	
	@BeforeTest()
	public void setupInitial() {
		driver = new DriverUtils().getDriver();
		searchPageObj = new SearchPage(driver);
		
	}
	
	

	/*
	 *This method will verify the Search In Silverstripe Application 
	 */
	@Test(dataProvider = "getTestData",priority = 1, enabled = true,description= "To verify the Search in Silverstripe Application")
	public void verifySearchInSilverStripe(ITestContext context,String UserName,String Password) throws Throwable {
		driver = new DriverUtils().getDriver();
        searchPageObj.SearchInSilverstripe(url, UserName, Password);
        
        test.log(Status.PASS, MarkupHelper.createLabel("Search  is successful in Silverstripe Application", ExtentColor.GREEN));
			
	}

	
	@Test(dataProvider = "getTestData", priority = 2,enabled = true,description="To verify the invalid Search in Silvertstripe Application")
	public void verifyInvalidSearchInSilverstripe(ITestContext context,String UserName, String ExpectedResult) {
		driver = new DriverUtils().getDriver();
		searchPageObj.InvalidSearchInSilvestripe(url, UserName, ExpectedResult);
	}
	
	/*
	 * @Test(dataProvider = "getTestData", priority = 3,enabled = true,
	 * description="To verify the keyword search with Special characters in Silvertstripe Application"
	 * ) public void
	 * verifyKeywordSearchWithSpecialCharactersInSilverstripe(ITestContext
	 * context,String UserName, String ExpectedResult) { driver = new
	 * DriverUtils().getDriver(); searchPageObj.SearchTextWithSpecialCharacters(url,
	 * UserName, ExpectedResult); }
	 * 
	 * @Test(dataProvider = "getTestData", priority = 4, enabled = true,description=
	 * "To verify the keyword search with combination of special characters and text in Silverstripe application"
	 * ) public void
	 * verifyKeywordSearchWithCombinationOfSpeacialCharactersAndText(ITestContext
	 * context, String UserName, String SearchedText){ driver = new
	 * DriverUtils().getDriver();
	 * searchPageObj.SearchWithCombinationOfSpecialCharactersAndText(url, UserName,
	 * SearchedText); }
	 */
	
}

	
		
	
		


	