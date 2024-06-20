package com.apis.globedr.services.database.dao;

import com.apis.globedr.helper.Common;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Wallet;
import com.apis.globedr.services.database.entities.WalletTransPointRule;
import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WalletTransPointRuleDAO implements IDAO<WalletTransPointRule, Boolean, WalletTransPointRule> {


    @Override
    public WalletTransPointRule get(WalletTransPointRule i) {
        return null;
    }

    @Override
    public List<WalletTransPointRule> getAll() {
        return null;
    }

    @Override
    public void add(WalletTransPointRule walletTransPointRule) {

    }

    @Override
    public void update(WalletTransPointRule walletTransPointRule) {

    }

    @Override
    public Boolean delete(WalletTransPointRule e) {
        String cmd = String.format("delete from %s where %s", Table.WALLET_TRANS_POINT_RULE, Common.addingConditionFieldsDB(e, FieldType.WHERE_AND));
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


}
