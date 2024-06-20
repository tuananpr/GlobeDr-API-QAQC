package observers;

import com.apis.globedr.helper.Path;
import com.rest.core.observers.IObserverApi;
import com.rest.core.request.AbsRequest;
import com.rest.core.response.Response;
import org.apache.poi.ss.util.CellRangeAddress;
import util.excel.Excel;
import util.excel.dataObject.Row;
import util.excel.dataObject.Sheet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CollectApiByExcel implements IObserverApi {

    private final Sheet sheetAPIs = new Sheet("APIs");
    private final Sheet sheetThongKe = new Sheet("ThongKe");

    private final Excel excel = initExcel();
    private int indexRow = 1;

    private final String uniqueAPI = "IFERROR(LOOKUP(2,1/(COUNTIF($A$1:A%s,APIs!$A$2:$A$50000)=0),APIs!$A$2:$A$50000),\"\")";
    private final String totalCalledAPI = "COUNTIFS(APIs!$A$2:$A$50000,A%s,APIs!$A$2:$A$50000,\"<>\")";
    private final String avgResponseTime = "IFERROR(SUMIF(APIs!$A$2:$A$50000,A%s,APIs!$B$2:$B$50000)/B%s,\"\")";
    private final String maxResponseTime = "MAX(IF(APIs!$A$2:$A$50000=ThongKe!A%s,APIs!$B$2:$B$50000))";
    private final String minResponseTime = "MIN(IF(APIs!$A$2:$A$50000=ThongKe!A%s,APIs!$B$2:$B$50000))";

    private Excel initExcel() {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        return new Excel(Path.TEST_RESOURCE + "statistics-api.xlsx", getFolderCucumber() + "statistics-api-" + dateInString + ".xlsx");
    }


    private String getFolderCucumber() {
        File folderCucumber = new File(Path.TARGET + "cucumber-html-reports");
        folderCucumber.mkdirs();
        return folderCucumber.getPath() + "/";
    }

    public List<Row> getUniqueApi() {
        List<Row> unique = new ArrayList<>();

        for (Row r : sheetAPIs.getRows()) {
            String apiName = r.getCells().get(0).getValue().toString();
            boolean iExisted = unique.stream().anyMatch(row -> row.getCells().get(0).getValue().toString().equalsIgnoreCase(apiName));
            if (!iExisted) {
                unique.add(r);
            }
        }
        return unique;
    }

    private void statisticApi() {
        // Adding statistic code
        List<Row> uniqueApi = getUniqueApi();

        // avg response time per api
        for (int index = 0; index < uniqueApi.size(); index++) {
            Row currentRow = new Row(index + 1);
            currentRow.addCell(0, uniqueApi.get(index).getCells().get(0).getValue().toString(), false);
            currentRow.addCell(1, String.format(totalCalledAPI, index + 2), true);
            currentRow.addCell(2, String.format(avgResponseTime, index + 2, index + 2), true);
            sheetThongKe.addRow(Arrays.asList(currentRow));
        }
        excel.add(sheetThongKe);

        // max response time per api
        for (int index = 0; index < uniqueApi.size(); index++) {
            excel.getSheet(sheetThongKe.getName()).setArrayFormula(String.format(maxResponseTime, index + 2),
                    CellRangeAddress.valueOf(String.format("D%s:D%s", index + 2, index + 2)));
        }

        // min response time per api

        for (int index = 0; index < uniqueApi.size(); index++) {
            excel.getSheet(sheetThongKe.getName()).setArrayFormula(String.format(minResponseTime, index + 2),
                    CellRangeAddress.valueOf(String.format("E%s:E%s", index + 2, index + 2)));
        }
    }

    public void statisticApiAndSave() {
        // Adding statistic code
        statisticApi();
        excel.save();
    }


    @Override
    public void update(String content) {

    }

    @Override
    public void update(AbsRequest absRequest, Response response) {

        // Collect response api to excel
        Row currentRow = new Row(indexRow);
        currentRow.addCell(0, absRequest.getUrl(), false);
        currentRow.addCell(1, response.getTime(), false);
        sheetAPIs.addRow(currentRow);
        excel.add(sheetAPIs);
        indexRow++;

    }


}
