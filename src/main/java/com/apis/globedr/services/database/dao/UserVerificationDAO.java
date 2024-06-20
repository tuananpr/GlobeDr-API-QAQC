package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.UserVerification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserVerificationDAO implements IDAO<UserVerification, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public UserVerification get(Integer i) {
        return null;
    }

    @Override
    public List<UserVerification> getAll() {
        return null;
    }

    @Override
    public void add(UserVerification user) {

    }

    @Override
    public void update(UserVerification user) {

    }

    public UserVerification get(String gdrLogin, int verifyType) {
        String cmd = String.format("select * from %s where type='%d' and userId in (SELECT userId FROM gdr_user where gdrLogin ='%s')",
                Table.USER_VERIFICATION, verifyType, gdrLogin);

        PreparedStatement pst;
        ResultSet rs = null;
        try {
            pst = getDB().prepareStatement(cmd);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new UserVerification(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getDate(i++),
                        rs.getInt(i++));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Boolean delete(Integer userId) {

        String cmd = String.format("delete from %s where %s", Table.USER_VERIFICATION, String.format("userId = '%s'", userId));

        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }


}
