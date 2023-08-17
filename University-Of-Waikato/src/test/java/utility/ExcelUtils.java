package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private  XSSFSheet excelWSheet;
	 
	 private XSSFWorkbook excelWBook;                                            
	 
	 private  XSSFCell cell;
	 
	 //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	 
	 public void setExcelFile() throws IOException  {
		 
		 String testDataPath = new ConfigReader().getTestDataPath();
		 String excelPath=testDataPath.split("@")[0];
		 String sheetName=testDataPath.split("@")[1];
		 
		 // Open the Excel file
		 FileInputStream excelFile = new FileInputStream(excelPath);
		 // Access the required test data sheet
		 excelWBook = new XSSFWorkbook(excelFile);
		 excelWSheet = excelWBook.getSheet(sheetName);		 	
	 
	 }
	 
	 public Object[][] getTableArray(int[] iTestCaseRow) throws IOException{   
	 
		 	String[][] tabArray = null;	 
		 	
		 	String testDataPath = new ConfigReader().getTestDataPath();
			String filePath=testDataPath.split("@")[0];
			String sheetName=testDataPath.split("@")[1];

		    FileInputStream ExcelFile = new FileInputStream(filePath);		 
		    // Access the required test data sheet		 
		    excelWBook = new XSSFWorkbook(ExcelFile);		 
		    excelWSheet = excelWBook.getSheet(sheetName);		 
		    int startCol = 1;		 
		    int ci=0,cj=0;
		    int startRow=iTestCaseRow[0];
		    int endRow=iTestCaseRow[1];
		    int totalRows = endRow-startRow+1;		 
		    //int totalCols = excelWSheet.getRow(startRow).getPhysicalNumberOfCells() -1;	
		    int totalCols = excelWSheet.getRow(startRow).getLastCellNum()-1;
		    String cellvalue;
		    for(int r=1;r<=totalCols;r++) {
		    	Cell cell=excelWSheet.getRow(startRow).getCell(r);
		    	try {
		    		cellvalue = cell.getStringCellValue();
		    	}catch(IllegalStateException e) {cellvalue = String.valueOf(cell.getNumericCellValue());}
		    	
		    	if(cell==null||cell.getCellType()== CellType.BLANK || 
		    			cellvalue.length()== 0) {
		    		totalCols=r-1;
		    		break;
		    	}
		    }
		    
		    	    
		    tabArray=new String[totalRows][totalCols];
		    //System.out.println(totalRows +" "+  totalCols);
		    for(int r=startRow;r<=endRow;r++) {
		    	for (int j=startCol;j<=totalCols;j++, cj++)		 
			    {		 
			    	tabArray[ci][cj]=getCellData(r,j);		 
			    }
		    	cj=0;
		    	ci++;
		    }
		    return(tabArray);
	 
	 }
	 
	 //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num	 
	 public String getCellData(int RowNum, int ColNum) {	 
		 cell = excelWSheet.getRow(RowNum).getCell(ColNum);
		 String CellData;
		 
		 try
		 {
		       CellData = cell.getStringCellValue(); 
		     
		    
		   return CellData;
		   }
		   catch (NullPointerException e){
			   
			   return "";
		   }
		 catch(IllegalStateException e) {
			 DecimalFormat df = new DecimalFormat("0.00");
			 return df.format(cell.getNumericCellValue());
		 }
	 }
	 
	 public String getTestCaseName(String sTestCase){
		 String value = sTestCase;
		 int posi = value.indexOf("@");
		 value = value.substring(0, posi);
		 posi = value.lastIndexOf("."); 
		 value = value.substring(posi + 1);
		 return value;
	 }
	 
	 public int[] getRowContains(String sTestCaseName, int colNum){
	 
		 int i; 
		 int rowCount = getRowUsed();
		 boolean flag=false;
		 int testCaseStartRow=0;
		 int testCaseEndRow=0;
		 for ( i=0 ; i<rowCount; i++){
			 if  (!flag && getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
				 flag=true;
				 testCaseStartRow=i+1;
			 }
			 if(flag) {
				 if(getCellData(i,colNum)==null || getCellData(i,colNum).isEmpty()) {
					 testCaseEndRow=i;
					 break;
				 }
				 
			 }
		 }
		 
		 if(testCaseEndRow==0) {
			 testCaseEndRow=testCaseStartRow;
		 }
		 int[] testCaseStartEndRow=new int[2];
		 testCaseStartEndRow[0]=testCaseStartRow;
		 testCaseStartEndRow[1]=testCaseEndRow;
		 return testCaseStartEndRow;
	 }
	 
	 public int getRowUsed() {
		 int rowCount = excelWSheet.getLastRowNum();
		 return rowCount+1;
	 }
	 
	 public Object[][] getData(String methodName) throws IOException{
		 setExcelFile();
		 int[] iTestCaseRow = getRowContains(methodName,0);
		 Object[][] testObjArray = getTableArray(iTestCaseRow);
		 return (testObjArray);
	 }
}
