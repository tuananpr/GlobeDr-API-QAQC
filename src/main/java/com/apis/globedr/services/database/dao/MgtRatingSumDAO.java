package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.RatingSum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MgtRatingSumDAO implements IDAO<RatingSum,Boolean,String>{

    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }

    @Override
    public RatingSum get(String i) {
        return null;
    }

    @Override
    public List<RatingSum> getAll() {
        return null;
    }

    @Override
    public void add(RatingSum ratingSum) {

    }

    @Override
    public void update(RatingSum ratingSum) {

    }

    @Override
    public Boolean delete(String e) {
        return null;
    }
    public Boolean deleteByUserId(Integer userId) {
        String sql = String.format("delete from %s where byUserId = %s and entitytype = 16", Table.MGT_RATING_SUM, userId);
        PreparedStatement pst;
        pst = getDB().prepareStatement(sql);
        try {
            return pst.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public RatingSum getRatingScore(Integer userId) {
        String sql = String.format("select * from %s where entitytype = 16 and byUserId = %s", Table.MGT_RATING_SUM, userId);
        PreparedStatement pst;
        pst = getDB().prepareStatement(sql);
        ResultSet rs = null;
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new RatingSum(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
