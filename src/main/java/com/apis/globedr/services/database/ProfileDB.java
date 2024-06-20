package com.apis.globedr.services.database;


import com.apis.globedr.services.config.DbCfg;
import com.apis.globedr.services.database.entities.Org;
import com.apis.globedr.services.database.entities.Sponsor;
import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.services.redis.RedisUtil;
import com.rest.core.database.SqlDB;
import org.springframework.util.ReflectionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProfileDB extends SqlDB {
    private static ProfileDB instant;

    private ProfileDB() {
    }

    public static ProfileDB getInstant() {
        if (instant == null) {
            instant = new ProfileDB();
            instant.settings(new DbCfg().getDBProfile());
        }
        return instant;
    }

    public boolean deleteDataByUser(String users) {
        String userOnDBProfile =
                String.format("delete from walletTransPoint where userId IN (select ownById from wallet where ownById IN (%s))", users) + "\n" +
                        String.format("delete from walletTransGiftCard where toUserId IN (select ownById from wallet where ownById IN (%s))", users) + "\n" +
                        String.format("delete from wallet where ownById IN (%s)", users) + "\n" +
                        String.format("delete from gdr_orgStaff where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_orgUser where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_orgUserGroup where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_user_specialty where userId IN (%s)", users) + "\n" +
                        String.format("delete from appointment where doctorId IN (%s) OR byId IN (%s)", users, users) + "\n" +
                        String.format("delete from mgtRatingReview where byUserId IN (%s)", users) + "\n" +
                        String.format("delete from mgtRatingSum where byUserId IN (%s)", users) + "\n" +
                        String.format("delete from userHealthData where userId IN (%s)", users) + "\n" +
                        String.format("delete from userMedicalVisit where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_user_medicalDoc where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_user_medicalTrans where toUserId IN (%s) OR byUserId IN (%s)", users, users) + "\n" +
                        String.format("delete from gdr_user_verification where userId IN (%s)", users) + "\n" +
                        String.format("delete from gdr_user where userId IN (%s)", users);

        if (!users.isEmpty()) {
            PreparedStatement pst;
            try {
                pst = ProfileDB.getInstant().prepareStatement(userOnDBProfile);
                return pst.execute();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteDataByOrg(String orgs) {

        String orgOnDBProfile =
                String.format("delete from walletGiftCard where giftCardId In (select cardId from giftCards where lotId In (select lotId from giftCardLot where voucherId in (select voucherId from orgVoucher where orgId IN (%s))))", orgs) + "\n" +
                        String.format("delete from giftCards where lotId In (select lotId from giftCardLot where voucherId In (select voucherId from orgVoucher where orgId IN (%s)))", orgs) + "\n" +
                        String.format("delete from giftCardLot where voucherId in (select voucherId from orgVoucher where orgId IN (%s))", orgs) + "\n" +
                        String.format("delete from giftVoucher where voucherId in (select voucherId from orgVoucher where orgId IN (%s))", orgs) + "\n" +
                        String.format("delete from orgVoucher where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from sponsor where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from orgTopDeal where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from gdr_orgProducts where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete  from productCategory where createdById IN (%s)", orgs) + "\n" +
                        String.format("delete from orderDocs where orderId in (select id from orders where toId IN (%s))", orgs) + "\n" +
                        String.format("delete from orderProcess where orderId in (select id from orders where toId IN (%s))", orgs) + "\n" +
                        String.format("delete from orders where toId IN (%s)", orgs) + "\n" +
                        String.format("delete from OrgScheduleTime where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from orgDoctorSchedule where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete  from OrgRoomSchedule where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from gdr_orgUser where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from gdr_orgStaff where organizationId  IN (%s)", orgs) + "\n" +
                        String.format("delete from gdr_orgSpecialty where organizationId IN (%s)", orgs) + "\n" +
                        String.format("delete from gdr_orgDepartment where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from orgAppointmentConfig where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete from orgPmInfo where orgId IN (%s)", orgs) + "\n" +
                        String.format("delete FROM gdr_organization where organizationId IN (%s)", orgs);


        if (!orgs.isEmpty()) {
            PreparedStatement pst;
            try {

                pst = ProfileDB.getInstant().prepareStatement(orgOnDBProfile);
                return pst.execute();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }


}
