package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.UserMedicalDoc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserMedicalDocDAO implements IDAO<UserMedicalDoc, Boolean, Integer> {


    public Boolean deleteByUserId(Integer byId) {
        String sql = String.format("delete from %s where userId=?", Table.USER_MEDICAL_DOC);
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

    @Override
    public UserMedicalDoc get(Integer i) {
        return null;
    }

    @Override
    public List<UserMedicalDoc> getAll() {
        return null;
    }

    @Override
    public void add(UserMedicalDoc userMedicalDoc) {

    }

    @Override
    public void update(UserMedicalDoc userMedicalDoc) {

    }

    @Override
    public Boolean delete(Integer e) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }
}
