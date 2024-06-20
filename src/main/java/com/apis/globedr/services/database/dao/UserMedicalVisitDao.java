package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.UserMedicalVisit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserMedicalVisitDao implements IDAO<UserMedicalVisit, Boolean, Integer> {


    @Override
    public UserMedicalVisit get(Integer i) {
        return null;
    }

    @Override
    public List<UserMedicalVisit> getAll() {
        return null;
    }

    @Override
    public void add(UserMedicalVisit userMedicalVisit) {

    }

    @Override
    public void update(UserMedicalVisit userMedicalVisit) {

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
        String sql = String.format("delete from %s where userId=?", Table.USER_MEDICAL_VISIT);
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
