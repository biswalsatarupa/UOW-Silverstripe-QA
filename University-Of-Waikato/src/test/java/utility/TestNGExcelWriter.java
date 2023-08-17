package utility;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestNGExcelWriter {

	public void createReport() {

		try {
			String lastStatus = null;
			File inputFile = new File("test-output"+File.separator+"testng-results.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("Test Results");
			XSSFRow row;

			doc.getDocumentElement().normalize();
			System.out.println("TestNG Results");

			NodeList testList = doc.getElementsByTagName("test");
			System.out.println("----------------------------");

			Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();

			empinfo.put(Integer.toString(1), new Object[] { "S.No", "Test Case Name", "Result" });
			int exCount = 2;
			for (int temp = 0; temp < testList.getLength(); temp++) {
				Node nNode = testList.item(temp);

				Element eElement = (Element) nNode;
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					System.out.println(eElement.getAttribute("name"));
					NodeList methodsList = eElement.getElementsByTagName("test-method");

					for (int count = 0; count < methodsList.getLength(); count++) {
						Node node1 = methodsList.item(count);

						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element method = (Element) node1;

							if (method.getAttribute("status").contentEquals("FAIL")) {
								lastStatus = "FAIL";
								break;
							} else
								lastStatus = "PASS";
						}
					}
				}

				empinfo.put(Integer.toString(exCount++),
				new Object[] { Integer.toString(temp + 1), eElement.getAttribute("name"), lastStatus });
				System.out.println(lastStatus);
			}

			//write test results to sheet
			Set<String> keyid = empinfo.keySet();
			int rowid = 0;

			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = empinfo.get(key);
				int cellid = 0;

				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}

			FileOutputStream out = new FileOutputStream(new File("TestReport"+File.separator+"TestNGResults.xlsx"));

			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("Test Results Created Successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}