package com.apis.globedr.model.response.noti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PusherTeleReceive extends PusherEvent<PusherData> {




    public String getPostSig() {
        return getDataAsModel().getMessage().getPostSig();
    }

    @Override
    public PusherData getDataAsModel() {
        JsonObject data = getDataAsJson();
        PusherData result = new PusherData();
        if (data.has("message")) {
            JsonObject message = new Gson().fromJson(data.get("message").getAsString(), JsonObject.class);
            result.setMessage(convert(message.toString(), PusherTeleMsg.class));
        }
        return result;
    }

}
