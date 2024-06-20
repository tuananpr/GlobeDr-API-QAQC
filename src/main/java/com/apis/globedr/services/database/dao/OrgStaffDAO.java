package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Org;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrgStaffDAO implements IDAO<Org, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public Org get(String gdrLogin) {
        String sql = String.format("select userId, userType, displayName, gdrLogin, phone, country from %s where gdrLogin=?", Table.USER);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setString(1, gdrLogin);


            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new Org();
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
    public List<Org> getAll() {
        String sql = String.format("select TOP 100 userId, userType, displayName, gdrLogin, phone, country from %s", Table.USER);
        PreparedStatement pst = null;
        List<Org> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                Org j = new Org();
                list.add(j);

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
        return list;
    }

    @Override
    public void add(Org user) {

    }

    @Override
    public void update(Org user) {

    }

    @Override
    public Boolean delete(String gdrLogin) {
        String sql = String.format("delete from %s where gdrLogin=?", Table.USER);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setString(1, gdrLogin);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


    public Boolean deleteByUserId(Integer userId) {
        String sql = String.format("delete from %s where userId = %s", Table.ORG_STAFF, userId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByOrgId(Integer orgId) {
        String sql = String.format("delete from %s where organizationId  = %d", Table.ORG_STAFF, orgId);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


}
