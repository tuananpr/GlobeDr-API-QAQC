package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Wallet;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChangelogDAO implements IDAO<Wallet, Boolean, Integer> {



    @Override
    public Wallet get(Integer i) {
        return null;
    }

    @Override
    public List<Wallet> getAll() {
        return null;
    }

    @Override
    public void add(Wallet user) {

    }

    @Override
    public void update(Wallet user) {

    }

    @Override
    public Boolean delete(Integer userId) {
        return false;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    public Boolean deleteByName(String name) {

        String cmd = String.format("delete from %s where [name] LIKE N'%s%%'", Table.CHANGELOG, name);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }

    public Boolean deleteAppVersion(Integer language) {

        String cmd = String.format("delete from %s where linkType = 4 AND language = %d", Table.CHANGELOG, language);
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
