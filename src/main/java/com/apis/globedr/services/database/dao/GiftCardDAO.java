package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Wallet;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GiftCardDAO implements IDAO<Wallet, Boolean, Integer> {



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

    public Boolean deleteByOrgId(Integer orgId) {


        String cmd = String.format(
                "delete from giftCards where lotId " +
                        "In (select lotId from giftCardLot where voucherId " +
                        "In (select voucherId from orgVoucher where orgId = %d))", orgId);

        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }

    public Boolean deleteByVoucherName(String name) {


        String cmd = String.format(
                "delete from giftCards where lotId " +
                        "In (select lotId from giftCardLot where voucherId " +
                        "In (select voucherId from giftVoucher where name like N'%s%%'))", name);


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
