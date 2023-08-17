package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.genetics.ChromosomePair;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverUtils{
	
	private static ThreadLocal<WebDriver> threadLocal;
	
	
	public void createWebDriver() throws FileNotFoundException, IOException {
		ConfigReader configReader = new ConfigReader();
		String browserName= configReader.getBrowserName();
		WebDriver driver;
		
		threadLocal=new ThreadLocal<WebDriver>();
		
		if(browserName.equalsIgnoreCase("chrome")) {
			driver=createChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			driver=createFirefoxDriver();
		}
		
		else {
			throw new WebDriverException("Driver is not configured for browser \"" + browserName + "\"");
		}
		
		driver.manage().timeouts().implicitlyWait(configReader.getImpliciteWaitTime(), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(configReader.getPageLoadTimeOut(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		threadLocal.set(driver);
		
	}
	
	private WebDriver createChromeDriver() throws FileNotFoundException, IOException {
		ChromeOptions options = new ChromeOptions();
		ConfigReader configreader=new ConfigReader();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--ignore-certificate-errors");
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//Drivers//Chromedriver.exe");
		if(configreader.getHeadLessMode().equalsIgnoreCase("true")) {
			options.setHeadless(true);
		}
		
	
		
		if(configreader.getHostName().equalsIgnoreCase("localhost")) {
			return new ChromeDriver(options);
		}
		else if(configreader.getHostName().equalsIgnoreCase("Perfecto")) 
		{
			DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
			capabilities.setCapability("securityToken", configreader.getSecurityToken());
			capabilities.setCapability("browserName", "Chrome");		
			String url=configreader.getPerfectoURL();
			return new RemoteWebDriver(new URL(url), capabilities);
		}	
		else
		{
			System.out.println("Incorrect Host Name");
		}
		return null;
	}
	
	private WebDriver createFirefoxDriver() throws FileNotFoundException, IOException {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		ProfilesIni prof = new ProfilesIni();
		ConfigReader configreader=new ConfigReader();
		profile.setPreference("network.proxy.type",4);
		profile = prof.getProfile("default"); //name of the profile(user firefox.exe -p in run prompt window)

		/*options.addArguments("--ignore-certificate-errors");
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);*/
		options.setProfile(profile);
		if(configreader.getHeadLessMode().equalsIgnoreCase("true")) {
			options.setHeadless(true);
		}
		
		
		
		if(configreader.getHostName().equalsIgnoreCase("localhost")) {
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver(options);
		}
		else if(configreader.getHostName().equalsIgnoreCase("Perfecto")) 
		{
			DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
			capabilities.setCapability("securityToken", configreader.getSecurityToken());
			capabilities.setCapability("browserName", "Firefox");		
			String url=configreader.getPerfectoURL();
			return new RemoteWebDriver(new URL(url), capabilities);
		}	
		else
		{
			System.out.println("Incorrect Host Name");
		}
		return null;
	}
	
	public WebDriver getDriver() {
		return threadLocal.get();
	}
	
	public void tearDown() {
		getDriver().quit();
		threadLocal.set(null);
		
	}
	
	

}
