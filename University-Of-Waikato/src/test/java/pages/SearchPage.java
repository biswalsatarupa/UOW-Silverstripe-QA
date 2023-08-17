package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utility.ConfigReader;

public class SearchPage {

	private WebDriver driver;
	private ConfigReader configReaderObj;
	private WebDriverWait wait;

	@FindBy(xpath="//button[@class='header__button header__button--search']")
	private WebElement btn_searchInMainPage;
	
	@FindBy(xpath = "//input[@name='q']")
	private WebElement txt_keywordInSearch;
	
	@FindBy(css = "#SearchOverlaySubmit")
	private WebElement btn_searchInSearchResultsPage;
	
	@FindAll({
	@FindBy(css = "#main div.search-page div div div div h4 a span"),
	@FindBy(css = " #main div.search-page div div p")
	})
	private WebElement txt_searchedText;
	

	@FindBy(css = "#main div.search-page div div p")
	private WebElement txt_invalidSearchedText;
	
		
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		configReaderObj = new ConfigReader();
		wait = new WebDriverWait(driver, configReaderObj.getImpliciteWaitTime());
		PageFactory.initElements(driver, this);
	}

	public void SearchInSilverstripe(String url, String UserName, String Password) throws InterruptedException {
		driver.get(url);
		
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 */
		wait.until(ExpectedConditions.elementToBeClickable(btn_searchInMainPage));
		btn_searchInMainPage.click();		
		wait.until(ExpectedConditions.elementToBeClickable(txt_keywordInSearch));
		txt_keywordInSearch.click();
		txt_keywordInSearch.sendKeys(UserName);
		btn_searchInSearchResultsPage.click();
		wait.until(ExpectedConditions.elementToBeClickable(txt_searchedText));
		Assert.assertTrue(txt_searchedText.getText().contains(UserName));
		txt_searchedText.click();

		}
	public void InvalidSearchInSilvestripe(String url,String UserName, String ExpectedResult) {
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(btn_searchInMainPage));
		btn_searchInMainPage.click();		
		wait.until(ExpectedConditions.elementToBeClickable(txt_keywordInSearch));
		txt_keywordInSearch.click();
		txt_keywordInSearch.sendKeys(UserName);
		btn_searchInSearchResultsPage.click();
		Assert.assertTrue(txt_invalidSearchedText.getText().contains(ExpectedResult));
	
	}
	
	public void SearchTextWithSpecialCharacters(String url,String UserName, String ExpectedResult) {
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(btn_searchInMainPage));
		btn_searchInMainPage.click();		
		wait.until(ExpectedConditions.elementToBeClickable(txt_keywordInSearch));
		txt_keywordInSearch.click();
		txt_keywordInSearch.sendKeys(UserName);
		btn_searchInSearchResultsPage.click();
		Assert.assertTrue(txt_searchedText.getText().contains(UserName));
		txt_searchedText.click();
		
	}
	
	public void SearchWithCombinationOfSpecialCharactersAndText(String url, String UserName, String SearchedText) {
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(btn_searchInMainPage));
		btn_searchInMainPage.click();		
		wait.until(ExpectedConditions.elementToBeClickable(txt_keywordInSearch));
		txt_keywordInSearch.click();
		txt_keywordInSearch.sendKeys(UserName);
		btn_searchInSearchResultsPage.click();
		wait.until(ExpectedConditions.elementToBeClickable(txt_searchedText));
		Assert.assertTrue(txt_searchedText.getText().contains(SearchedText));
		txt_searchedText.click();

	}
	
	
}
