package util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {

    private XSSFWorkbook fileTemplate;
    private final String fileOut;

    public Excel(String templatePath, String outputPath) {
        try {
            fileTemplate = new XSSFWorkbook(new FileInputStream(templatePath));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        fileOut = outputPath;

    }


    public XSSFWorkbook getWorkbook() {
        return fileTemplate;
    }

    public XSSFSheet getSheet(String name) {
        return getWorkbook().getSheet(name);
    }


    public int getLastRow(String sheetName) {
        Sheet sheet = getWorkbook().getSheet(sheetName);
        return getLastRow(sheet);
    }

    public int getLastRow(int sheetIndex) {
        Sheet sheet = getWorkbook().getSheetAt(sheetIndex);
        return getLastRow(sheet);
    }

    public int getLastRow(Sheet sheet) {
        for (int rowNum = sheet.getLastRowNum(); rowNum >= 0; rowNum--) {
            final Row row = sheet.getRow(rowNum);
            if (row != null && row.getCell(0) != null) {
                return rowNum;
            }
        }
        return -1;
    }

    public void add(util.excel.dataObject.Sheet sheet) {
        Sheet xssfSheet = null;

        xssfSheet = (sheet.getIndex() != null) ? getWorkbook().getSheetAt(sheet.getIndex()) : getWorkbook().getSheet(sheet.getName());
        xssfSheet = (xssfSheet == null) ? getWorkbook().createSheet(sheet.getName()) : xssfSheet;
        for (util.excel.dataObject.Row r : sheet.getRows()) {
            Row row = xssfSheet.createRow(r.getIndexRow());
            for (util.excel.dataObject.Cell c : r.getCells()) {
                Cell cell = row.createCell(c.getIndexCol());

                if (c.getValue() instanceof String) {
                    if(c.isFormula()){
                        cell.setCellFormula((String) c.getValue());
                    }else{
                        cell.setCellValue((String) c.getValue());
                    }

                }

                if (c.getValue() instanceof Long) {
                    cell.setCellValue(((Long)c.getValue()).longValue());
                }

            }
        }
    }



    public void save() {
        try {
            getWorkbook().write(new FileOutputStream(this.fileOut));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
