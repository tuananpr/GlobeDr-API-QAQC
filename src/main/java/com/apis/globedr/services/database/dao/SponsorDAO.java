package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Sponsor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SponsorDAO implements IDAO<Sponsor, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    @Override
    public Sponsor get(Integer i) {
        return null;
    }

    @Override
    public List<Sponsor> getAll() {
        return null;
    }

    @Override
    public void add(Sponsor sponsor) {

    }

    @Override
    public void update(Sponsor sponsor) {

    }

    @Override
    public Boolean delete(Integer userId) {
        return false;
    }

    public Sponsor getSponsorByOrgId(Integer orgId) {

        String cmd = String.format("select id, description, orgId, weight, value, currency, type, status from sponsor where orgId =%d", orgId);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = getDB().prepareStatement(cmd);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new Sponsor(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
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

    public List<Sponsor> getSponsorByOrgId(String where) {

        String cmd = String.format("select id, description, orgId, weight, value, currency, type, status from sponsor where %s", where);

        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Sponsor> list = new ArrayList<>();
        try {
            pst = getDB().prepareStatement(cmd);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                list.add(new Sponsor(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++)));
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

    public Boolean deleteByOrgId(Integer orgId) {

        String cmd = String.format("delete from sponsor where orgId =%d", orgId);

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
