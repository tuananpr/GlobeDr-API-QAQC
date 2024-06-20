package com.apis.globedr.apis;

import com.apis.globedr.helper.Common;
import com.apis.globedr.model.request.system.*;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.JsonHelper;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class SystemApi extends BaseApi {



    private SystemApi(){}
    private static SystemApi instant;
    public static SystemApi getInstant(){
        if(instant == null) instant = new SystemApi();
        return instant;
    }

    public Response viewUsersGrowthChart(Object body) {
        return RestCore.given().url(API.System.USERS_GROWTH_CHART()).auth(token).body(body, UserGrowthChartRQ.class).post().send();
    }

    public Response viewOrgsGrowthChart(Object body) {
        return RestCore.given().url(API.System.ORGS_GROWTH_CHART()).auth(token).body(body, OrgsGrowthChartRQ.class).post().send();
    }

    public Response getOrgsByCountry(Object body) {
        return RestCore.given().url(API.System.ORGS_BY_COUNTRY()).auth(token)
                .body(body, OrgsByCountryRQ.class).post().send();
    }

    public Response getOrgsByType(Object body) {
        return RestCore.given().url(API.System.ORGS_BY_TYPE()).auth(token)
                .body(body, OrgsByTypeRQ.class).post().send();
    }

    public Response getUsersByCountry(Object body) {
        return RestCore.given().url(API.System.USERS_BY_COUNTRY()).auth(token)
                .body(body, UsersByCountryRQ.class).post().send();
    }

    public Response getUsersByGender(Object body) {
        return RestCore.given().url(API.System.USERS_BY_GENDER()).auth(token)
                .body(body , UsersByGenderRQ.class).post().send();
    }

    public Response loadFeedbacks(Object body) {
        return RestCore.given().url(API.System.FEEDBACKS()).auth(token).body(body, FeedbacksRQ.class).post().send();
    }


    public Response exportFeedbacks(Object body) {
        return RestCore.given().url(API.System.FEEDBACKS_Excel()).auth(token).body(body, FeedbacksExcelRQ.class).post().send();
    }

    public Response getVacInfo(GetVacInfoRQ body) {
        return RestCore.given().url(API.System.GET_VAC_INFO()).auth(token).params(body, GetVacInfoRQ.class).get().send();
    }

    public Response getSystemPost(Object body) {
        return RestCore.given().url(API.System.GET_SYSTEM_POST()).auth(token).params(body, GetSystemPostRQ.class).get().send();
    }


    public Response configGet() {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.PWD, "s4acMvW5X836qMtEgtZEbeBCqcCYPzrAXVcCSEkw8beFJDuWfM9kWrJmM");
        return RestCore.given().url(API.System.CONFIG_GET()).auth(token).params(body).get().send();
    }

}
