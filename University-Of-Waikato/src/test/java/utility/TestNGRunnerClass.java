package utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;

public class TestNGRunnerClass extends ReportingUtils {

	public static void main(String args[]) throws CustomException {

		// Create object of TestNG Class

		TestNG testng = new TestNG();

		// Create a list of String
		List<String> suites = new ArrayList<String>();

		// Add XML file which you have to execute
		String test = args[0];
		if (test.contentEquals("SilverstripeTest")) {
			suites.add("Silverstripe"+File.separator+"testng1.xml");// path to XML..
			} 
		// now set XML file for execution
		testng.setTestSuites(suites);

		// finally execute the runner using run method
		testng.run();

		/*
		 * try { //CreateZip.createZip(); } catch (IOException e) { e.printStackTrace();
		 * }
		 */
		if (Exceptionexists) {

			throw new CustomException(exceptionTrace);

		}

	}}
