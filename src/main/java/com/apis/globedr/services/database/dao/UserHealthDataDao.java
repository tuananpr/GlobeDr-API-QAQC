package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.UserHealthData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserHealthDataDao implements IDAO<UserHealthData, Boolean, Integer> {


    @Override
    public UserHealthData get(Integer i) {
        return null;
    }

    @Override
    public List<UserHealthData> getAll() {
        return null;
    }

    @Override
    public void add(UserHealthData userMedicalVisit) {

    }

    @Override
    public void update(UserHealthData userMedicalVisit) {

    }

    @Override
    public Boolean delete(Integer e) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    public Boolean deleteByUserId(Integer byId) {
        String sql = String.format("delete from %s where userId=?", Table.USER_HEALTH_DATA);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setInt(1, byId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


}
