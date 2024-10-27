package org.framework.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static DataFormatter dataFormatter;
    private static Logger logger = Logger.getLogger(ExcelUtils.class.getName());
    private static XSSFSheet sheet;
    private static InputStream fis;

    private ExcelUtils() {
    }

    public static Object[][] readData(String[] excelInfo) {
        String excelName = excelInfo[0];
        String sheetName = excelInfo[1];
        List<Object[]> results = new ArrayList<>();
        try {
            setup(excelName, sheetName);
            int numRows = sheet.getLastRowNum();
            for (int i =0; i<= numRows; i++){
                Map<String, String> inputValues; //= //getHashMapDataFromRow(sheetName,i);
                //results.add(new Object[]{inputValues});
            }
//        }catch (IOException e){
//            logger.warning(e.getMessage());
        }finally {
            IOUtils.closeQuietly(fis);
        }
        return results.toArray(new Object[0][]);
    }

    private static void setup(String excelName, String sheetName) {
    }
}
