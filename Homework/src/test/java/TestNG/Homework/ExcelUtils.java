package TestNG.Homework;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public static void setExcelFile(String path, String sheetName) throws Exception {
        FileInputStream file=new FileInputStream(path);
        workbook=new XSSFWorkbook(file);
        sheet=workbook.getSheet(sheetName); }

    public static String getCellData(int rowNum, int colNum) {
        try{
            XSSFCell cell=sheet.getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        }catch (Exception e) {
            return ""; }
    }

    public static void setCellData(String value, int rowNum, int colNum, String path) throws Exception{
        XSSFRow row=sheet.getRow(rowNum);
        if(row == null)
            row=sheet.createRow(rowNum);

        XSSFCell cell=row.getCell(colNum);
        if(cell== null)
            cell=row.createCell(colNum);
          cell.setCellValue(value);
        FileOutputStream fileOut=new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.close();
    }

    public static int getRowCount() {
        return sheet.getLastRowNum()+1;
    }
}
