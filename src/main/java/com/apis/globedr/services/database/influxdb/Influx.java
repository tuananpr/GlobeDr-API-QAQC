package com.apis.globedr.services.database.influxdb;

import com.apis.globedr.services.ServiceException;
import com.apis.globedr.services.config.DbCfg;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

public class Influx {

    private String host;
    private String dbName;
    private String policy;

    public Influx() {
        DbCfg conf = new DbCfg();
        host = conf.getConfig().getProperty("influx.host");
        dbName = conf.getConfig().getProperty("influx.db");
        policy = conf.getConfig().getProperty("influx.policy");
        createDatabase();
        createRetentionPolicy();
    }

    public org.influxdb.InfluxDB connect() {
        InfluxDB influxDB = InfluxDBFactory.connect(host);
        try{
            influxDB.ping();
        }catch (InfluxDBException e){
            throw new ServiceException(String.format("Can't connect to influxDB information Host: %s, dbName: %s, policy: %s", host, dbName, policy), e);
        }
        return influxDB;
    }

    public void createDatabase() {
        connect().createDatabase(dbName);
    }

    public void createRetentionPolicy() {
        connect().createRetentionPolicy(policy, dbName, "30d", 1, true);
    }

    public BatchPoints getBatchPoints() {
        return BatchPoints.database(dbName)
                .retentionPolicy(policy)
                .build();
    }

    public void write(BatchPoints batchPoints) {
        connect().write(batchPoints);
    }

    public void write(Point point) {
        connect().write(point);
    }

    public void write(String content) {
        connect().write(content);
    }


}
