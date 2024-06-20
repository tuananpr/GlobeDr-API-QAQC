package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrdersDAO implements IDAO<Orders, Boolean, Integer> {


    @Override
    public Orders get(Integer i) {
        return null;
    }

    @Override
    public List<Orders> getAll() {
        return null;
    }

    @Override
    public void add(Orders entity) {

    }

    @Override
    public void update(Orders entity) {

    }

    @Override
    public Boolean delete(Integer byId) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    public Boolean deleteByOrgId(Integer byId) {

        String sql = String.format("delete from %s where toId=?", Table.ORDERS);
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
