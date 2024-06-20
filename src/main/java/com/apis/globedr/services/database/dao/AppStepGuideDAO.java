package com.apis.globedr.services.database.dao;

import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.AppStepGuide;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AppStepGuideDAO implements IDAO<AppStepGuide, Boolean, Integer> {


    @Override
    public AppStepGuide get(Integer i) {
        return null;
    }

    @Override
    public List<AppStepGuide> getAll() {
        return null;
    }

    @Override
    public void add(AppStepGuide appStepGuide) {

    }

    @Override
    public void update(AppStepGuide appStepGuide) {

    }

    @Override
    public Boolean delete(Integer e) {
        return null;
    }

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }


    public Boolean deleteByAppScreen(Integer id) {


        String cmd = String.format("delete from %s where appScreen = %d", Table.APP_STEP_GUIDE, id);

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
