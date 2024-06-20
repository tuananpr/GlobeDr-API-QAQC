package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.UserMedicalTrans;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserMedicalTransDAO implements IDAO<UserMedicalTrans, Boolean, Integer> {


    @Override
    public UserMedicalTrans get(Integer i) {
        return null;
    }

    @Override
    public List<UserMedicalTrans> getAll() {
        return null;
    }

    @Override
    public void add(UserMedicalTrans userMedicalTrans) {

    }

    @Override
    public void update(UserMedicalTrans userMedicalTrans) {

    }

    @Override
    public Boolean delete(Integer byId) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    public Boolean deleteByUserId(Integer byId) {
        String sql = String.format("delete from %s where toUserId=? OR byUserId=?", Table.USER_MEDICAL_TRANS);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setInt(1, byId);
            pst.setInt(2, byId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }
}
