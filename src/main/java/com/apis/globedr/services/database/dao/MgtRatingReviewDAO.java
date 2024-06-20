package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.entities.RatingReview;
import com.apis.globedr.services.database.other.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MgtRatingReviewDAO implements IDAO<RatingReview, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return ProfileDB.getInstant();
    }
    @Override
    public RatingReview get(String i) {
        return null;
    }

    @Override
    public List<RatingReview> getAll() {
        return null;
    }

    @Override
    public void add(RatingReview ratingReview) {

    }

    @Override
    public void update(RatingReview ratingReview) {

    }

    @Override
    public Boolean delete(String e) {
        return null;
    }

    public Boolean deleteByUserId(Integer userId) {
        String sql = String.format("delete from %s where byUserId = %s and entitytype = 16", Table.MGT_RATING_REVIEW, userId);
        PreparedStatement pst;
        pst = getDB().prepareStatement(sql);
        try {
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public RatingReview getRatingReviewByUserId(Integer userId) {
        String sql = String.format("select * from %s where entitytype = 16 and byUserId = %s", Table.MGT_RATING_REVIEW, userId);
        PreparedStatement pst;
        pst = getDB().prepareStatement(sql);
        ResultSet rs = null;
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                return new RatingReview(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getDate(i++),
                        rs.getInt(i++),
                        rs.getString(i++));
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
