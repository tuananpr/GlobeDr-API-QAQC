package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.PostDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostFeedbackDAO implements IDAO<Object, Boolean, String> {
    @Override
    public SqlDB getDB() {
        return PostDB.getInstant();
    }

    @Override
    public Object get(String gdrLogin) {
        return null;
    }

    @Override
    public List<Object> getAll() {
        return null;
    }

    @Override
    public void add(Object user) {

    }

    @Override
    public void update(Object user) {

    }

    @Override
    public Boolean delete(String gdrLogin) {
        String sql = String.format("delete from %s where gdrLogin=?", Table.USER);
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

    public Boolean deleteByUserId(Integer userId) {
        // deletePostMsgDocs
        List<String> cmds = new ArrayList<>();
        cmds.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById = '%d')", Table.POST_FEEDBACK, Table.POST, userId));
        PreparedStatement pst;

        try {
            int total = 0;
            for ( String cmd : cmds ) {
                pst = getDB().prepareStatement(cmd);
                total += pst.executeUpdate();
            }
            return total > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByUserId(List<Integer> userIds) {
        String users = "(" + userIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
        // deletePostMsgDocs
        List<String> cmds = new ArrayList<>();
        cmds.add(String.format("delete from %s where postId in (Select postId FROM %s WHERE createdById IN %s)", Table.POST_FEEDBACK, Table.POST, users));
        PreparedStatement pst;

        try {
            int total = 0;
            for ( String cmd : cmds ) {
                pst = getDB().prepareStatement(cmd);
                total += pst.executeUpdate();
            }
            return total > 0;
        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        return false;
    }

}