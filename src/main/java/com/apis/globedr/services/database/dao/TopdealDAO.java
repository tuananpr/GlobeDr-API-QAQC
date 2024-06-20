package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Wallet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TopdealDAO implements IDAO<Wallet, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }
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

    public Boolean deleteByOrgId(Integer orgId) {

        String cmd = String.format("delete from %s where orgId =%d", Table.ORG_TOPDEAL,orgId);

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
