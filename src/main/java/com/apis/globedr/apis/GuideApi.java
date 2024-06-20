package com.apis.globedr.apis;

import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.google.gson.JsonObject;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.JsonHelper;

import java.util.Map;

public class GuideApi extends BaseApi {

    private final JsonObject iGuide = JsonHelper.getJson(Path.GUIDE + "newGuide.json");
    private GuideApi(){}
    private static GuideApi instant;
    public static GuideApi getInstant(){
        if(instant == null) instant = new GuideApi();
        return instant;
    }

    public Response get(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.GET()).auth(token).params(body).get().send();
    }

    public Response guides(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.GUIDES()).auth(token).body(body).post().send();
    }

    public Response newScreen(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.NEW_APP_SCREEN()).auth(token).body(body).post().send();
    }


    public Response newGuide(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.NEW_GUIDE()).auth(token)
                .body( JsonHelper.update(iGuide, body)).post().send();
    }

    public Response updateGuide(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.GUIDE_UPDT())
                .auth(token).body(JsonHelper.update(iGuide, body)).put().send();
    }

    public Response deleteGuide(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.GUIDE_DEL()).auth(token).body(body).delete().send();
    }

    public Response loadScreens(Map<String, Object> body) {
        body.put(Text.PAGE, 1);
        body.put(Text.PAGE_SIZE, 20);
        return RestCore.given().url(API.Guide.APP_SCREENS()).auth(token).body(body).post().send();
    }

    public Response deleteScreen(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.APP_SCREEN_DEL()).auth(token).body(body).delete().send();
    }

    public Response updateScreen(Map<String, Object> body) {
        return RestCore.given().url(API.Guide.APP_SCREEN_UPDT()).auth(token).body(body).put().send();
    }

}
