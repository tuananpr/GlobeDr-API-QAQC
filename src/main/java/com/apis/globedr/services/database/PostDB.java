package com.apis.globedr.services.database;

import com.apis.globedr.services.config.DbCfg;
import com.apis.globedr.services.database.other.FieldType;
import com.rest.core.database.SqlDB;
import org.springframework.util.ReflectionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostDB extends SqlDB {
    private static PostDB instant;
    private PostDB(){}

    public static PostDB getInstant() {
        if(instant == null){
            instant = new PostDB();
            instant.settings(new DbCfg().getDBPost());
        }
        return instant;
    }

    public boolean deleteDataByUser(String users){

        String userOnDBPost = String.format("delete from gdr_postMsgDocs where msgId in (select msgId FROM gdr_postMsg where postId In (Select postId FROM gdr_post WHERE createdById IN (%s) ))", users) + "\n" +
                String.format("delete from gdr_postMsgLike where msgId in (select msgId FROM gdr_postMsg where postId In (Select postId FROM gdr_post WHERE createdById IN (%s) ))", users) + "\n" +
                String.format("delete from gdr_postMsgLike where userId IN (%s)", users) + "\n" +
                String.format("delete from gdr_postMsg where postId in (Select postId FROM gdr_post WHERE createdById IN (%s))", users) + "\n" +
                String.format("delete from gdr_postMsg where createdById IN (%s)", users) + "\n" +
                String.format("delete from gdr_postAccess where postId in (Select postId FROM gdr_post WHERE createdById IN (%s))", users) + "\n" +
                String.format("delete from gdr_postActivity where postId in (Select postId FROM gdr_post WHERE createdById IN (%s))", users) + "\n" +
                String.format("delete from gdr_postFeedback where postId in (Select postId FROM gdr_post WHERE createdById IN (%s))", users) + "\n" +
                String.format("delete from gdr_post where createdById IN (%s)", users) + "\n" +
                String.format("delete from gdr_category where createdById IN (%s)", users) + "\n" +
                String.format("delete from gdr_notificationGroup where notificationId IN ( Select notificationId FROM gdr_notification WHERE senderId IN (%s))", users) + "\n" +
                String.format("delete from gdr_notificationSeen where notificationId IN ( Select notificationId FROM gdr_notification WHERE senderId IN (%s))", users) + "\n" +
                String.format("delete from gdr_notificationUser where notificationId IN ( Select notificationId FROM gdr_notification WHERE senderId IN (%s))", users) + "\n" +
                String.format("delete from gdr_notification where senderId IN (%s)", users);



        if (!users.isEmpty()) {
            PreparedStatement pst;
            try {
                pst = PostDB.getInstant().prepareStatement(userOnDBPost);
                return pst.execute();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteDataByOrg(String orgs){
        String orgOnDBPost =
                String.format("delete from gdr_postMsgDocs where msgId in (select msgId FROM gdr_postMsg where postId In (Select postId FROM gdr_post WHERE createdById IN (%s)))", orgs) + "\n" +
                String.format("delete from gdr_category where createdById IN (%s)", orgs) + "\n" +

                String.format("delete from productServiceItem where productId IN (select productId from gdr_orgProducts where orgId IN (%s))", orgs) + "\n" +
                String.format("delete from gdr_orgProductDocs where productId IN (select productId from gdr_orgProducts where orgId IN (%s))", orgs) + "\n" +
                String.format("delete from gdr_orgProducts where orgId IN (%s)", orgs) + "\n" +
                String.format("delete from gdr_notificationGroup where notificationId IN ( Select notificationId FROM gdr_notification WHERE  senderId IN (%s) OR refId1 IN (%s))", orgs, orgs) + "\n" +
                String.format("delete from gdr_notificationSeen where notificationId IN ( Select notificationId FROM gdr_notification WHERE  senderId IN (%s) OR refId1 IN (%s))", orgs, orgs) + "\n" +
                String.format("delete from gdr_notificationUser where notificationId IN ( Select notificationId FROM gdr_notification WHERE  senderId IN (%s) OR refId1 IN (%s))", orgs, orgs) + "\n" +
                String.format("delete from gdr_notification where  senderId IN (%s) OR refId1 IN (%s)", orgs, orgs);


        if (!orgs.isEmpty()) {
            PreparedStatement pst;
            try {

                pst = PostDB.getInstant().prepareStatement(orgOnDBPost);
                return pst.execute();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }



}
