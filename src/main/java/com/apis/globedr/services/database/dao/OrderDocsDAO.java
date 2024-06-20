package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.OrderDocs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDocsDAO implements IDAO<OrderDocs, Boolean, Integer> {


    @Override
    public OrderDocs get(Integer i) {
        return null;
    }

    @Override
    public List<OrderDocs> getAll() {
        return null;
    }

    @Override
    public void add(OrderDocs entity) {

    }

    @Override
    public void update(OrderDocs entity) {

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

        String sql = String.format("delete from %s where orderId in (select id from orders where toId=?)", Table.ORDER_DOCS);
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
