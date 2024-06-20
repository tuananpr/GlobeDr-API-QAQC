package com.apis.globedr.apis;

import com.apis.globedr.model.request.noti.ConfigAzurePusherRQ;
import com.apis.globedr.model.request.noti.CountUnreadRQ;
import com.apis.globedr.model.request.noti.RegisterDeviceRQ;
import com.apis.globedr.model.response.noti.ConfigAzurePusherRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.HashMap;
import java.util.Map;


public class NotiApi extends BaseApi {

    private NotiApi(){}
    private static NotiApi instant;
    public static NotiApi getInstant(){
        if(instant == null) instant = new NotiApi();
        return instant;
    }

    public ConfigAzurePusherRS getConfig(Object body) {
        return RestCore.given().url(API.Noti.GET_CONFIG()).auth(token)
                .bodyEncrypt(body, ConfigAzurePusherRQ.class).post().send()
                .extractAsModel("data", ConfigAzurePusherRS.class);
    }


    public Response registerDevice(Object body) {
        return RestCore.given().url(API.Noti.REGISTER_DEVICE()).auth(token).bodyEncrypt(body, RegisterDeviceRQ.class).post().send();
    }

    public Response genInstallation(Object body) {
        return RestCore.given().url(API.Noti.GEN_INSTALLATION()).auth(token).body(body, RegisterDeviceRQ.class).post().send();
    }



    public Response countUnread(Object body) {
        return RestCore.given().url(API.Noti.COUNT_UNREAD()).auth(token).bodyEncrypt(body).get().send();
    }


    public Response countUnread() {
        return countUnread(new HashMap<>());
    }


}
