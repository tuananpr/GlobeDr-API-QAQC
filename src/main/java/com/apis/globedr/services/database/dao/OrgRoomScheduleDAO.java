package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.OrgRoomSchedule;

import java.util.List;

public class OrgRoomScheduleDAO extends DAO<OrgRoomSchedule, Boolean, Integer> {


    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public OrgRoomSchedule get(Integer i) {
        return null;
    }

    @Override
    public List<OrgRoomSchedule> getAll() {
        return null;
    }

    @Override
    public void add(OrgRoomSchedule orgRoomSchedule) {

    }

    @Override
    public void update(OrgRoomSchedule orgRoomSchedule) {

    }

    @Override
    public Boolean delete(Integer e) {
        return null;
    }


}
