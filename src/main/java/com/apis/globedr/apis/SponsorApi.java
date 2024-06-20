package com.apis.globedr.apis;

import com.apis.globedr.helper.Common;
import com.apis.globedr.model.request.sponsor.DurationRQ;
import com.apis.globedr.model.response.sponsor.SponsorRS;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.SponsorStatus;
import com.apis.globedr.helper.JsonHelper;
import com.google.gson.JsonObject;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SponsorApi extends BaseApi {

    private final JsonObject iSponsorList = JsonHelper.getJson(Path.SPONSOR + "list.json");
    private SponsorApi(){}
    private static SponsorApi instant;
    public static SponsorApi getInstant(){
        if(instant == null) instant = new SponsorApi();
        return instant;
    }


    public void approveSponsor(String orgSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);
        RestCore.given().url(API.Sponsor.APPROVE()).auth(token).body(body).post().send();
    }

    public void updateStatusForSponsor(String orgSig, int id, String status) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ID, id);
        body.put(Text.STATUS, SponsorStatus.value(status));
        body.put(Text.ORG_SIG, orgSig);
        RestCore.given().url(API.Sponsor.STATUS()).auth(token).body(body).put().send();
    }

    public void setTimeDuration(Integer id, String orgSig, String startDate, String endDate) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ID, id);
        body.put(Text.ORG_SIG, orgSig);
        body.put(Text.START_DATE, startDate);
        body.put(Text.END_DATE, endDate);
        RestCore.given().url(API.Sponsor.DURATION()).auth(token).body(body).put().send();
    }

    public Response setTimeDuration(DurationRQ body) {
        return RestCore.given().url(API.Sponsor.DURATION()).auth(token).body(body).put().send();
    }


    public void setTimeDuration(Integer id, String orgSig, Map<String, Object> dataTable) {
        Map<String, Object> body = Common.getMap(dataTable);
        body.put(Text.ID, id);
        body.put(Text.ORG_SIG, orgSig);
        RestCore.given().url(API.Sponsor.DURATION()).auth(token).body(body).put().send();
    }

    public void deleteSponsor(Integer id, String orgSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ID, id);
        body.put(Text.ORG_SIG, orgSig);
        RestCore.given().url(API.Sponsor.REMOVE()).auth(token).body(body).delete().send();
    }

    public void loadListSponsor(String name, String startDate, String endDate) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.PAGE, 1);
        body.put(Text.PAGE_SIZE, 10);
        body.put(Text.ORG_NAME, name);
        body.put(Text.START_DATE, startDate);
        body.put(Text.END_DATE, endDate);
        loadListSponsor(body);
    }

    public List<SponsorRS> loadListSponsor(int page) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.PAGE, page);
        body.put(Text.PAGE_SIZE, 10);
        return loadListSponsor(body);
    }

    public List<SponsorRS> loadListSponsor() {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.PAGE, 1);
        body.put(Text.PAGE_SIZE, 10);
        return loadListSponsor(body);
    }

    public List<SponsorRS> loadListSponsor(Map<String, Object> body) {
        return RestCore.given().url(API.Sponsor.LIST()).auth(token)
                .body(JsonHelper.update(iSponsorList, body)).post().send()
                .extractAsModels("data.list", SponsorRS.class);
    }

    public void getListSponsor() {
        RestCore.given().url(API.Sponsor.SPONSORS()).auth(token).get().send();
    }
}
