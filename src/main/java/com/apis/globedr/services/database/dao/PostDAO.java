package com.apis.globedr.services.database.dao;

import com.apis.globedr.helper.Common;
import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.PostDB;
import com.apis.globedr.services.database.entities.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO implements IDAO<Post, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return PostDB.getInstant();
    }


    @Override
    public Post get(Integer i) {
        return null;
    }

    public List<Post> getPostSystem() {
        String sql = String.format("select postId, type, status, toApptId, onDate, createdById, tags, categoryId, " +
                "postSource, subject, createdDate, createdByType from %s where postSource = 2 AND toApptId = 0", Table.POST);

        PreparedStatement pst = null;
        List<Post> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                Post j = new Post(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getDate(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getDate(i++),
                        rs.getInt(i++)
                );
                list.add(j);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public void add(Post post) {

    }

    @Override
    public void update(Post post) {

    }

    @Override
    public Boolean delete(Integer postId) {
        String sql = String.format("delete from %s where postId=%d", Table.POST, postId);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByGdrLogin(String gdrLogin) {
        String sql = String.format("delete from %s where gdrLogin=?", Table.POST);
        PreparedStatement pst;
        try {

            pst = getDB().prepareStatement(sql);
            pst.setString(1, gdrLogin);
            return pst.executeUpdate() > 0;

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByCategoryId(List<Integer> list) {
        String cmd = String.format("delete from %s where categoryId in (%s)", Table.POST, Common.listToString(list));
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


    public Boolean deleteByCategoryName(String categoryName) {
        String cmd = String.format("delete from %s where categoryId in (select categoryId from gdr_category where categoryName like N'%s%%')", Table.POST, categoryName);
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


    public Boolean deleteByUserId(Integer userId) {
        // deletePostMsgDocs
        String cmd = String.format("delete from %s where createdById = '%d'", Table.POST, userId);
        PreparedStatement pst;

        try {
            int total = 0;

            pst = getDB().prepareStatement(cmd);
            total += pst.executeUpdate();

            return total > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

}
