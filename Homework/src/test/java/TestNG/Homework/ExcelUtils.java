package TestNG.Homework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils{

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public static void open(String path,String sheetName)throws Exception{
        FileInputStream fis=new FileInputStream(path);
        workbook=new XSSFWorkbook(fis);
        sheet=workbook.getSheet(sheetName);
    }

    public static String getCell(int row,int col){
        try{
            return sheet.getRow(row).getCell(col).getStringCellValue();
        }catch (Exception e){
            return ""; }}

    public static void setCell(String value,int row,int col,String path) throws Exception{
        XSSFRow r=sheet.getRow(row);
        if(r == null) r=sheet.createRow(row);
        XSSFCell c=r.getCell(col);
        if(c == null) c=r.createCell(col);
        c.setCellValue(value);
        FileOutputStream out=new FileOutputStream(path);
        workbook.write(out);
        out.close();}

    public static int rows(){
        return sheet.getLastRowNum()+ 1;}}
