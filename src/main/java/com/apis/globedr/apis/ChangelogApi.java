package com.apis.globedr.apis;

import com.apis.globedr.model.request.changelog.LogsRQ;
import com.apis.globedr.model.request.changelog.NewRQ;
import com.apis.globedr.model.request.changelog.RemoveRQ;
import com.apis.globedr.model.request.changelog.ListRQ;
import com.apis.globedr.model.response.changelog.ChangelogRS;
import com.apis.globedr.model.response.changelog.NewRS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.rest.core.response.Response;

import java.util.List;
import java.util.Map;

public class ChangelogApi extends BaseApi {

    private ChangelogApi(){}
    private static ChangelogApi instant;
    public static ChangelogApi getInstant(){
        if(instant == null) instant = new ChangelogApi();
        return instant;
    }

    public NewRS create(Object body) {
        return RestCore.given().url(API.Changelog.NEW()).auth(token)
                .body(body, NewRQ.class).post().send()
                .extractAsModel("data.info", NewRS.class);
    }


    public List<ChangelogRS> list(Object body) {
        return RestCore.given().url(API.Changelog.LIST()).auth(token)
                .body(body, ListRQ.class).post().send().extractAsModels("data.list", ChangelogRS.class );
    }

    public NewRS update(Object body) {
        return RestCore.given().url(API.Changelog.UPDATE()).auth(token)
                .body(body, NewRQ.class).put().send()
                .extractAsModel("data.info", NewRS.class);
    }


    public Response remove(Object body) {
        return RestCore.given().url(API.Changelog.REMOVE()).auth(token).body(body, RemoveRQ.class).delete().send();
    }

    public Response logs(Object body) {
        return RestCore.given().url(API.Changelog.LOGS()).auth(token).body(body, LogsRQ.class).post().send();
    }
}
