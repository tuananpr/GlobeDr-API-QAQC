package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.Org;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrgDepartmentDAO implements IDAO<Org, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public Org get(String gdrLogin) {
        return null;
    }

    @Override
    public List<Org> getAll() {

        return null;
    }

    @Override
    public void add(Org user) {

    }

    @Override
    public void update(Org user) {

    }

    @Override
    public Boolean delete(String gdrLogin) {
        return false;
    }

    public Boolean deleteByOrgId(Integer orgId) {
        String sql =  String.format("delete from %s where orgId = %d", Table.ORG_DEPARTMENT, orgId);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


}
