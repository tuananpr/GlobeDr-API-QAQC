package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.OrgScheduleTime;

import java.util.List;

public class OrgScheduleTimeDAO extends DAO<OrgScheduleTime, Boolean, Integer> {


    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public OrgScheduleTime get(Integer i) {
        return null;
    }

    @Override
    public List<OrgScheduleTime> getAll() {
        return null;
    }

    @Override
    public void add(OrgScheduleTime orgScheduleTime) {

    }

    @Override
    public void update(OrgScheduleTime orgScheduleTime) {

    }

    @Override
    public Boolean delete(Integer e) {
        return null;
    }
}
