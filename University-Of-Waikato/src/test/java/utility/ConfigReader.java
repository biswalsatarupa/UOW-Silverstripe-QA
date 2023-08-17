package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private Properties prop;
	
	
	public ConfigReader() {
		prop=new Properties();
		try {
			prop.load(new FileInputStream("config"+File.separator+"config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUserName() {
		String userName = System.getProperty("userName");
		if(userName==null|| userName.isEmpty()) {
			String userName2 = prop.getProperty("userName");
			return userName2;
		}
		else {
			return userName;
		}
	}
	
	public String getPassword() {
		String Password = System.getProperty("Password");
		if(Password==null|| Password.isEmpty()) {
			String Password2 = prop.getProperty("Password");
			return Password2;
		}
		else {
			return Password;
		}
	}
	
	public String getBrowserName() {
		String browserNameFromMvn = System.getProperty("browserName");
		if(browserNameFromMvn==null|| browserNameFromMvn.isEmpty()) {
			String browserNameFromConfig = prop.getProperty("browserName");
			return browserNameFromConfig;
		}
		else {
			return browserNameFromMvn;
		}
	}
	
	public String getHeadLessMode() {
		String headLessFromMvn = System.getProperty("browserName");
		if(headLessFromMvn==null|| headLessFromMvn.isEmpty()) {
			return prop.getProperty("headless");
		}
		else {
			return headLessFromMvn;
		}
	}
	
	public String getHostName() {
		String hostNameFromMvn = System.getProperty("hostName");
		if(hostNameFromMvn==null|| hostNameFromMvn.isEmpty()) {
			String hostNameFromConfig = prop.getProperty("hostName");
			return hostNameFromConfig;
		}
		else {
			return hostNameFromMvn;
		}
	}
	
	public String getportNum() {
		String portNumFromMvn = System.getProperty("hostName");
		if(portNumFromMvn==null|| portNumFromMvn.isEmpty()) {
			return prop.getProperty("portNum");
		}
		else {
			return portNumFromMvn;
		}
	}
	
	public Long getImpliciteWaitTime() {
		String impliciteWaitTimeFromMvn = System.getProperty("impliciteWaitTime");
		if(impliciteWaitTimeFromMvn==null|| impliciteWaitTimeFromMvn.isEmpty()) {
			return Long.parseLong(prop.getProperty("impliciteWaitTime"));
		}
		else {
			return Long.parseLong(impliciteWaitTimeFromMvn);
		}
	}
	
	public Long getShortWaitTime() {
		String shortWaitTimeFromMvn = System.getProperty("shortWaitTime");
		if(shortWaitTimeFromMvn==null|| shortWaitTimeFromMvn.isEmpty()) {
			return Long.parseLong(prop.getProperty("shortWaitTime"));
		}
		else {
			return Long.parseLong(shortWaitTimeFromMvn);
		}
	}
	
	public Long getPageLoadTimeOut() {
		String pageLoadTimeOutFromMvn = System.getProperty("pageLoadTimeOut");
		if(pageLoadTimeOutFromMvn==null|| pageLoadTimeOutFromMvn.isEmpty()) {
			return Long.parseLong(prop.getProperty("pageLoadTimeOut"));
		}
		else {
			return Long.parseLong(pageLoadTimeOutFromMvn);
		}
	}
	
	public String getReportPath() {
		String reportPathFromMvn = System.getProperty("reportPath");
		if(reportPathFromMvn==null|| reportPathFromMvn.isEmpty()) {
			return prop.getProperty("reportPath");
		}
		else {
			return reportPathFromMvn;
		}
	}
	
	public String getTestDataPath() {
		String testDataPathFromMvn = System.getProperty("testData");
		if(testDataPathFromMvn==null|| testDataPathFromMvn.isEmpty()) {
			return prop.getProperty("testData");
		}
		else {
			return testDataPathFromMvn;
		}
	}
	
	public String getTestURL() {
		String appURL = System.getProperty("appURL");
		if(appURL==null|| appURL.isEmpty()) {
			return prop.getProperty("appURL");
		}
		else {
			return appURL;
		}
	}

	public String getPerfectoURL() {
		String perfectoURL = System.getProperty("perfectoUrl");
		if(perfectoURL==null|| perfectoURL.isEmpty()) {
			return prop.getProperty("perfectoUrl");
		}
		else {
			return perfectoURL;
		}
	}
	
	public String getSecurityToken() {
		String securityToken = System.getProperty("securityToken");
		if(securityToken==null|| securityToken.isEmpty()) {
			return prop.getProperty("securityToken");
		}
		else {
			return securityToken;
		}
	}
	
	public String getDrivingLicenceFrontPath() {
		String DLFrontPath = System.getProperty("drivingLicenceFront");
		if(DLFrontPath==null|| DLFrontPath.isEmpty()) {
			return prop.getProperty("drivingLicenceFront");
		}
		else {
			return DLFrontPath;
		}
	}
	
	public String getDrivingLicenceBackPath() {
		String DLBackPath = System.getProperty("drivingLicenceBack");
		if(DLBackPath==null|| DLBackPath.isEmpty()) {
			return prop.getProperty("drivingLicenceBack");
		}
		else {
			return DLBackPath;
		}
	}
	
	public String getSelfieImagePath() {
		String selfieImagePath = System.getProperty("selfieImage");
		if(selfieImagePath==null|| selfieImagePath.isEmpty()) {
			return prop.getProperty("selfieImage");
		}
		else {
			return selfieImagePath;
		}
	}
	
}
