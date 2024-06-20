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

public class OrgDAO implements IDAO<Org, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public Org get(String name1) {
        String cmd = String.format("SELECT organizationId, name1, type, address FROM gdr_organization WHERE name1 like N'%s%%'", name1);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = getDB().prepareStatement(cmd);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new Org(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++));

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

    public List<Org> getAll(String name1) {
        String cmd = String.format("SELECT organizationId, name1, type, address FROM gdr_organization WHERE name1 like N'%s%%'", name1);
        List<Org> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = getDB().prepareStatement(cmd);
            rs = pst.executeQuery();

            while (rs.next()) {
                int i = 1;
                Org j = new Org(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++));
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
    public List<Org> getAll() {
        String cmd = String.format("SELECT Top 100 organizationId, name1, type, address FROM gdr_organization");
        List<Org> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = getDB().prepareStatement(cmd);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                Org j = new Org(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++));
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
        return false;
    }

    public Boolean delete(Integer orgId) {
        String cmd = String.format("delete FROM %s where organizationId = '%d'", Table.ORG, orgId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    public Boolean deleteByUserId(Integer userId) {
        List<String> cmds = new ArrayList<>();
        cmds.add(String.format("delete from %s where userId = %s", Table.ORG, userId));
        PreparedStatement pst;
        try {
            int total = 0;
            for (String cmd : cmds) {
                pst = getDB().prepareStatement(cmd);
                total += pst.executeUpdate();
            }
            return total > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

}
