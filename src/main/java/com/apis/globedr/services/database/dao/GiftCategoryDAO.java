package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GiftCategoryDAO implements IDAO<Object, Boolean, Integer> {



    @Override
    public Object get(Integer i) {
        return null;
    }

    @Override
    public List<Object> getAll() {
        return null;
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public Boolean delete(Integer categoryId) {
        return false;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    public Boolean deleteBy(String column, String value) {
        String cmd = String.format(
                "delete from %s WHERE %s like N'%s%%'", Table.GIFT_CATEGORY, column, value);
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
