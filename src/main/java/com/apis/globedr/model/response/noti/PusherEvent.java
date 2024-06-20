package com.apis.globedr.model.response.noti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public abstract class PusherEvent<T>{

    private String channelName;
    private Integer userId;
    private String eventName;
    private String data;

    public <T> T convert(String jsonString, Class<T> cls) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject getDataAsJson() {
        return new Gson().fromJson(getData(), JsonObject.class);
    }


    public JsonObject getMessage() {
        JsonObject j = getDataAsJson();
        if(j.has("message")){
            return new Gson().fromJson(j.get("message").getAsString(), JsonObject.class);
        }
        return null;
    }

    public abstract T getDataAsModel();
}
