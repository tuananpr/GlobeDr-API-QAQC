package com.apis.globedr.services.es;

import com.apis.globedr.services.authorization.Basic;
import com.apis.globedr.services.config.ElasticCfg;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticSearchApi {

    ElasticCfg elasticCfg = new ElasticCfg();

    final String QUERY = "query";
    final String MACTH = "match";
    final String PERSONNAL_DISPLAY_NAME = "personal.displayName";

    final String NAME1 = "name1";
    final String _ID = "_id";
    int max = 1;


    public ElasticSearchApi() {
        Basic.getInstance().save(
                elasticCfg.get("es.user"),
                elasticCfg.get("es.password"));
    }

    public String getMethodName() {
        // getStackTrace() method return current
        // method name at 0th index
        String nameofCurrMethod = new Exception()
                .getStackTrace()[1]
                .getMethodName();

        return nameofCurrMethod;
    }

    private Response post(String url, JSONObject json) {
        return RestCore.given().url(url).auth(Basic.getInstance()).body(json).post().send(max);
    }

    private Response get(String url, JSONObject json) {
        return RestCore.given().url(url).auth(Basic.getInstance()).body(json).get().send(max);
    }

    private Response put(String url, Map<String, Object> json) {
        return RestCore.given().url(url).auth(Basic.getInstance()).body(json).put().send(max);
    }


    public Response getUser(String name) {

        String url = Url.Elastic.User.SEARCH;

        JSONObject json = new JSONObject();
        JSONObject jsonO = new JSONObject();
        JSONObject jsonOb = new JSONObject();
        json.put(QUERY, jsonO);
        jsonO.put(MACTH, jsonOb);
        jsonOb.put(PERSONNAL_DISPLAY_NAME, name);

        return get(url, json);

    }

    public Response deleteUser(String name) {
        Response response = null;
        String url = Url.Elastic.User.DELETE;

        JSONObject json = new JSONObject();
        JSONObject jsonO = new JSONObject();
        JSONObject jsonOb = new JSONObject();
        json.put(QUERY, jsonO);
        jsonO.put(MACTH, jsonOb);
        jsonOb.put(PERSONNAL_DISPLAY_NAME, name);

        response = post(Url.Elastic.User.REFRESH, json); // fix elastic error : VersionConflictEngineException , "status" : 409
        return post(url, json);

    }

    public void deleteUserId(List<Integer> listUserId) {
        for (int id : listUserId) {
            deleteUserId(id);
        }
    }


    public Response deleteUserId(int id) {
        Response response = null;

        JSONObject json = new JSONObject();
        JSONObject jsonO = new JSONObject();
        JSONObject jsonOb = new JSONObject();
        json.put(QUERY, jsonO);
        jsonO.put(MACTH, jsonOb);
        jsonOb.put(_ID, id);


        response = post(Url.Elastic.User.REFRESH, json); // fix elastic error : VersionConflictEngineException , "status" : 409
        return post(Url.Elastic.User.DELETE, json);

    }

    public void deleteUser(List<String> listName) {
        for (String name : listName) {
            deleteUser(name);
        }
    }

    public Response deleteOrg(String name) {
        Response response = null;

        JSONObject json = new JSONObject();
        JSONObject jsonO = new JSONObject();
        JSONObject jsonOb = new JSONObject();
        json.put(QUERY, jsonO);
        jsonO.put(MACTH, jsonOb);
        jsonOb.put(NAME1, name);

        response = post(Url.Elastic.Org.REFRESH, json); // fix elastic error : VersionConflictEngineException , "status" : 409
        return post(Url.Elastic.Org.DELETE, json);
    }

    public Response getOrg(String name) {

        String url = Url.Elastic.Org.SEARCH;

        JSONObject json = new JSONObject();
        JSONObject jsonO = new JSONObject();
        JSONObject jsonOb = new JSONObject();
        json.put(QUERY, jsonO);
        jsonO.put(MACTH, jsonOb);
        jsonOb.put(NAME1, name);

        return get(url, json);

    }

    public boolean hasOrg(String name) {
        return getOrg(name).extractAsInt("hits.total") > 0;
    }

    /* use this method when es appear error with "reason": "blocked by: [FORBIDDEN/12/index read-only / allow delete (api)];"
     *
     */
    public Response allowDeleteMode() {

        Map<String, Object> body = new HashMap<>();
        body.put("index.blocks.read_only_allow_delete", false);
        return put(Url.Elastic.All.SETTINGS, body);
    }

}
