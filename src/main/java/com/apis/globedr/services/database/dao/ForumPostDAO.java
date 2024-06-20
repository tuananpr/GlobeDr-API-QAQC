package com.apis.globedr.services.database.dao;

import com.apis.globedr.helper.Common;
import com.apis.globedr.services.database.PostDB;
import com.apis.globedr.services.database.entities.ForumPost;
import com.apis.globedr.services.database.entities.Post;
import com.apis.globedr.services.database.other.Table;
import com.rest.core.database.SqlDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForumPostDAO implements IDAO<ForumPost, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return PostDB.getInstant();
    }


    @Override
    public ForumPost get(Integer i) {
        return null;
    }

    public List<ForumPost> getPostSystem() {
        String sql = String.format("select postId, type, status, toApptId, onDate, createdById, tags, categoryId, " +
                "postSource, subject, createdDate, createdByType from %s where postSource = 2 AND toApptId = 0", Table.FORUM_POST);

        PreparedStatement pst = null;
        List<ForumPost> list = new ArrayList<>();
        ResultSet rs = null;
        try {

            pst = getDB().prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                int i = 1;
                ForumPost j = new ForumPost(
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
    public List<ForumPost> getAll() {
        return null;
    }

    @Override
    public void add(ForumPost post) {

    }

    @Override
    public void update(ForumPost post) {

    }

    @Override
    public Boolean delete(Integer postId) {
        String sql = String.format("delete from %s where postId=%d", Table.FORUM_POST, postId);
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
        String sql = String.format("delete from %s where gdrLogin=?", Table.FORUM_POST);
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
        String cmd = String.format("delete from %s where categoryId in (%s)", Table.FORUM_POST, Common.listToString(list));
        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByCategoryName(String name) {
        String sql = String.format(" from forumPost where [subject] IN (select [subject] from forumPost where categoryId in (select categoryId from gdr_category where categoryName like N'%s%%'))", name);
        String postId = "select postId" + sql;

        String msgId = String.format("select msgId from ForumPostMsg  where postId IN (%s)", postId, name);
        String cmd = "delete from forumPostMsgLike  where msgId IN (" + msgId + ")"+ "\n" +
                "delete from forumPostMsgDocs where msgId IN (" + msgId + ")" + "\n" +
                "delete from forumPostUserFollow where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostUserSeen where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostMsg where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostAccess where postId IN (" + postId + ")" + "\n" +
                "delete " + sql;

        PreparedStatement pst;

        try {
            pst = getDB().prepareStatement(cmd);
            return pst.executeUpdate() > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }


    public Boolean deleteBySubject(String name) {

        String postId = String.format("Select postId from forumPost Where [subject] like N'%s%%'", name);
        String msgId = String.format("select msgId from forumPostMsg  where postId IN (%s)", postId, name);
        String cmd = "delete from forumPostMsgLike  where msgId IN (" + msgId + ")" + "\n" +
                "delete from forumPostMsgDocs where msgId IN (" + msgId + ")"+ "\n" +
                "delete from forumPostUserFollow where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostUserSeen where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostMsg where postId IN (" + postId + ")" + "\n" +
                "delete from forumPostAccess where postId IN (" + postId + ")" + "\n" +
                "delete from forumPost where [subject] like '" + name + "'";

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
        String cmd = String.format("delete from %s where createdById = '%d'", Table.FORUM_POST, userId);
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
