package com.apis.globedr.business.noti;

import com.apis.globedr.apis.NotiApi;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.model.request.noti.ConfigAzurePusherRQ;

import com.apis.globedr.model.request.noti.RegisterDeviceRQ;
import com.apis.globedr.model.response.noti.ConfigAzurePusherRS;
import com.rest.core.response.Response;

import java.util.Map;

public class NotiBus {

    NotiApi notiApi = NotiApi.getInstant();

    public ConfigAzurePusherRS getConfigWeb() {
        ConfigAzurePusherRQ body = ConfigAzurePusherRQ.builder().forWeb(true).build();
        return notiApi.getConfig(body);
    }

    public ConfigAzurePusherRS getConfig(ConfigAzurePusherRQ body) {
        return notiApi.getConfig(body);
    }


    public Response registerDevice(RegisterDeviceRQ body) {
        return notiApi.registerDevice(body);
    }

    public Response genInstallation(RegisterDeviceRQ body) {
        return notiApi.genInstallation(body);
    }

    public Response countUnread() {
        return notiApi.countUnread();
    }

}
