package observers;

import com.apis.globedr.helper.Path;
import com.apis.globedr.services.database.influxdb.Influx;
import com.rest.core.observers.IObserverApi;
import com.rest.core.request.AbsRequest;

import com.rest.core.response.Response;
import io.cucumber.plugin.event.Result;
import org.apache.poi.ss.util.CellRangeAddress;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import util.excel.Excel;
import util.excel.dataObject.Row;
import util.excel.dataObject.Sheet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InfluxDbObs implements IObserverApi {

    Influx influxDB = null;
    BatchPoints batchPoints = null;

    public InfluxDbObs() {
        influxDB = new Influx();
        batchPoints = influxDB.getBatchPoints();
    }

    @Override
    public void update(AbsRequest absRequest, Response response) {
        Point point = Point.measurement("apis")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name", absRequest.getUrl())
                .addField("url", absRequest.getUrl())
                .tag("name", absRequest.getUrl())
                .addField("status", response.getStatusCode())
                .addField("responseTime", response.getTime())
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    @Override
    public void update(String s) {

    }


}
