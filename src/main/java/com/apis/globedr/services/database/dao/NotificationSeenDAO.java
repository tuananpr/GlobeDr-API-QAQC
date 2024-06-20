package com.apis.globedr.services.database.dao;

import com.rest.core.database.SqlDB;
import com.apis.globedr.services.database.other.Table;
import com.apis.globedr.services.database.PostDB;
import com.apis.globedr.services.database.entities.Notification;
import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.helper.Common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationSeenDAO implements IDAO<Notification, Boolean, Integer> {
    @Override
    public SqlDB getDB() {
        return PostDB.getInstant();
    }


    @Override
    public Notification get(Integer i) {
        return null;
    }

    @Override
    public List<Notification> getAll() {
        return null;
    }

    @Override
    public void add(Notification notification) {

    }

    @Override
    public void update(Notification notification) {

    }

    @Override
    public Boolean delete(Integer userId) {
        String where = String.format("notificationId IN ( Select notificationId FROM %s WHERE senderId = '%d')", Table.NOTIFICATION, userId);
        List<String> cmds = new ArrayList<>();
        // delete notification of post
        cmds.add(String.format("delete from %s where %s", Table.NOTIFICATION_SEEN, where ));
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

        return null;
    }

    public Boolean delete(Notification notification, FieldType fieldType) {
        String where = String.format("notificationId IN ( Select notificationId FROM %s WHERE %s)", Table.NOTIFICATION, Common.addingConditionFieldsDB(notification, fieldType));
        String sql = String.format("delete from %s where %s", Table.NOTIFICATION_SEEN , where);
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
