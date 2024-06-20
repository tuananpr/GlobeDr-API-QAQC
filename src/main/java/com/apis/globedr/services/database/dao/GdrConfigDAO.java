package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.GdrConfig;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;
import org.bouncycastle.util.Strings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GdrConfigDAO implements IDAO<GdrConfig, Boolean, Integer> {

    @Override
    public GdrConfig get(Integer i) {
        return null;
    }

    @Override
    public List<GdrConfig> getAll() {
        return getAll("");
    }


    public boolean unAllowUserIdConfig(List<String> newId) {
        List<String> ids = getAllowUserIdConfig();
        ids.removeAll(newId);
        String sql = String.format("update %s set [value] = '%s' where name like 'AllowUserIdConfig'", Table.GDR_CONFIG, ids.toString());
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean allowUserIdConfig(List<String> newId) {
        List<String> ids = getAllowUserIdConfig();
        ids.addAll(newId);

        String sql = String.format("update %s set [value] = '%s' where name like 'AllowUserIdConfig'", Table.GDR_CONFIG, ids.toString());
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public List<String> getAllowUserIdConfig() {
        String ids = getAll("where name like 'AllowUserIdConfig'").get(0).getValue()
                .replace("[", "")
                .replace("]", "");
        List<String> list = new ArrayList<>();
        for (String id : Strings.split(ids, ',')) {
            list.add(id.trim());
        }
        return list;
    }

    public List<GdrConfig> getAll(String where) {
        String sql = String.format("select * from %s %s", Table.GDR_CONFIG, where);
        PreparedStatement pst = null;
        List<GdrConfig> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                GdrConfig j = new GdrConfig(
                        rs.getString(i++),
                        rs.getString(i++),
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
    public void add(GdrConfig gdrConfig) {

    }

    @Override
    public void update(GdrConfig gdrConfig) {

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
