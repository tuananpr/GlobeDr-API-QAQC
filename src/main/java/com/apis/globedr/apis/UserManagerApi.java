package com.apis.globedr.apis;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.request.userManager.*;
import com.apis.globedr.model.response.userManager.LoadUsersRS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagerApi extends BaseApi {

    private UserManagerApi() {
    }

    private static UserManagerApi instant;

    public static UserManagerApi getInstant() {
        if (instant == null) instant = new UserManagerApi();
        return instant;
    }


    public Response pwdReset(Object body) {
        return RestCore.given().url(API.UserManager.PWD_RESET()).auth(token).body(body, PwdResetRQ.class).put().send();
    }


    public List<LoadUsersRS> loadUsers(Object body) {
        return RestCore.given().url(API.UserManager.LOAD_USERS()).auth(token)
                .bodyEncrypt(body, LoadUsersRQ.class).post().send()
                .extractAsModels("data.list", LoadUsersRS.class );
    }

    public Response changeUserType(List<String> listUserSig, int userType) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.USER_SIG_LIST, listUserSig);
        body.put(Text.USER_TYPE, userType);
        return RestCore.given().url(API.UserManager.CHANGE_USER_TYPE()).auth(token).body(body).put().send();
    }

    public void setGlobedrDoctor(List<String> userSignatureArray) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.USER_SIG_LIST, userSignatureArray);
        body.put(Text.IS_GLOBEDR_DOCTOR, true);
        RestCore.given().url(API.UserManager.SET_GLOBEDR_DOCTOR()).auth(token).bodyEncrypt(body).put().send();

    }

    public Response changeUserStatus(Object body) {
        return RestCore.given().url(API.UserManager.CHANGE_USER_STATUS()).auth(token)
                .bodyEncrypt(body, ChangeUserStatusRQ.class).put().send();
    }

    public Response verifyUser(Object body) {
        return RestCore.given().url(API.UserManager.VERIFY_USER()).auth(token).bodyEncrypt(body, VerifyUserRQ.class).put().send();
    }


    public Response removeUsers(Object body) {
        return RestCore.given().url(API.UserManager.REMOVE_USERS()).auth(token)
                .bodyEncrypt(body, RemoveUserRQ.class).delete().send();
    }

    public Response giftPoint(Object body) {
        return RestCore.given().url(API.UserManager.GIFT_POINT()).auth(token)
                .bodyEncrypt(body, GiftPointRQ.class).post().send();
    }


    public Response changeUserFeatureAttribute(Object body) {
        return RestCore.given().url(API.UserManager.CHANGE_USER_FEATURE_ATTRIBUTE()).auth(token)
                .bodyEncrypt(body, ChangeUserFeatureAttributeRQ.class).put().send();
    }


    public Response pointRules(Object body) {
        return RestCore.given().url(API.UserManager.POINT_RULES()).auth(token)
                .bodyEncrypt(body, Page.class).post().send();
    }

    public Response newPointRule(Object body) {
        return RestCore.given().url(API.UserManager.NEW_POINT_RULE()).auth(token)
                .bodyEncrypt(body, NewPointRuleRQ.class).post().send();
    }

    public Response appUsageTime(Object body) {
        return RestCore.given().url(API.UserManager.APP_USAGE_TIME()).auth(token)
                .bodyEncrypt(body, AppUsageTimeRQ.class).post().send();
    }

    public Response usedPointHistory(FilterDate body) {
        return RestCore.given().url(API.UserManager.USED_POINT_HISTORY()).auth(token)
                .bodyEncrypt(body, FilterDate.class).post().send();
    }




}
