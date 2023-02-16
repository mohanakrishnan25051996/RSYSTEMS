package Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class DataHandler {
	private static String filePath;
	private static String SheetName;
	private static String TestCaseName;
	private static String ScenarioName;

	public DataHandler(String filePath, String SheetName, String TestCaseName) {
		this.filePath = filePath;
		this.SheetName = SheetName;
		this.TestCaseName = TestCaseName;
	}

	public DataHandler(Map<String, String> testData) {
		this(filePath, SheetName, TestCaseName);
	}

	public static String getCellValueAsString(Cell cell) {
		String cellValue = null;
//System.out.println(cell.getCellType());
		switch (cell.getCellType()) {
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
				cellValue = fmt.format(date);
			} else
				cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case FORMULA:
			cellValue = cell.getCellFormula();
			break;
		case BLANK:
			cellValue = "BLANK";
		default:
			cellValue = null;
		}
		return cellValue;
	}

	public static HashMap<String, String> ReadExcel() throws Throwable {
		int TestCasrow = 0;
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = null;
		wb = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheet(SheetName);
		List<String> columnHeader = new ArrayList<String>();
		List<String> TestcaseValues = new ArrayList<String>();
		Row row = sheet.getRow(0);
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			columnHeader.add(cellIterator.next().getStringCellValue());
		}
		int rowCount = sheet.getLastRowNum();
		int columnCount = row.getLastCellNum();
		for (int i = 1; i <= rowCount; i++) {
			Row row1 = sheet.getRow(i);
			String testcase = row1.getCell(0).getStringCellValue().toString();
			if (TestCaseName.equals(testcase)) {
				TestCasrow = i;
				Row Scenariorow = sheet.getRow(TestCasrow);
				String scenariorow = null;
				for (int j = 0; j < Scenariorow.getLastCellNum(); j++) {
					Cell cell = Scenariorow.getCell(j);
					if (cell == null) {
						scenariorow = null;
					} else {
						scenariorow = getCellValueAsString(cell);
					}
					TestcaseValues.add(scenariorow);
				}
			}
		}
		HashMap<String, String> singleRowData = new HashMap<String, String>();
		for (int i = 0; i < columnHeader.size(); i++) {
			for (int j = 0; j < TestcaseValues.size(); j++) {
				if (i == j) {
					singleRowData.put(columnHeader.get(i), TestcaseValues.get(i));
					;
				}
			}
		}
		return singleRowData;
	}

	public static String getMapData(String key) throws Throwable {
		Map<String, String> m = ReadExcel();
		String value = m.get(key);
		return value;
	}
}
