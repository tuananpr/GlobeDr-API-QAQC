package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.PostDB;
import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Wallet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDAO implements IDAO<Wallet, Boolean, Integer> {
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

        String cmd = String.format("delete from %s where orgId =%d", Table.BV_MY_THANHS, orgId);

        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }

    public Boolean deleteCategoryByOrgId(Integer orgId) {

        String cmd = String.format("delete from %s where createdById=%d", Table.PRODUCTS_CATEGORY, orgId);
        PreparedStatement pst;
        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }

    public Boolean deleteProductAndServicesByName(String productName) {
        String cmd =
                String.format("delete from productServiceItem where productId IN (select productId from gdr_orgProducts where name like N'%s')", productName) + "\n" +
                        String.format("delete from gdr_orgProductDocs where productId IN (select productId from gdr_orgProducts where name like N'%s')", productName) + "\n" +
                        String.format("delete from gdr_orgProducts where name like N'%s'", productName) + "\n";
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
