package com.apis.globedr.apis;

import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;

import com.apis.globedr.helper.JsonHelper;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommunicationApi extends BaseApi {


    private final JsonObject iThongTu = JsonHelper.getJson(Path.COMMUNICATION + "dhyd_thong_tu_14.json");
    private CommunicationApi(){}
    private static CommunicationApi instant;
    public static CommunicationApi getInstant(){
        if(instant == null) instant = new CommunicationApi();
        return instant;
    }

    public void dhyd2(String apikey, Map<String, Object> input) {
        Map<String, Object> bodyFile = Common.getMap(input);
        bodyFile.put(Text.API_KEY, apikey);


        // Update data from scenario to json. And write to file txt for request api
        String data = JsonHelper.update(iThongTu, bodyFile).toString();
        Common.writeJsonToFile(Path.TEXT_TEMP, data);
        System.out.println(data);
        Map<String, Object> body = new HashMap<>();
        body.put(Text.FILE, new File(Path.TEXT_TEMP));
        RestCore.given().url(API.Communication.DHYD2()).multipart(body).post().send();
    }

}
