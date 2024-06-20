package com.apis.globedr.model.response.noti;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PusherTeleCall extends PusherEvent<PusherData> {

    /*
     * Obj4 : contains information : User Caller
     * Obj3 : contains information : Org
     * Obj2 : contains information : Post, Conversation
     * Obj1 : contains information : Channel
     *
     * Type1: contains information : chatType
     * Type2: contains information : VideoCallType
     * Type3: contains information : senderType
     */


    public String getUserSigCaller() {
        if (getDataAsModel().getMessage().getObj4() == null) return null;
        return getDataAsModel().getMessage().getObj4().getSig();

    }

    public String getFunctionSig() {
        if (getDataAsModel().getMessage().getObj2() == null) return null;
        return getDataAsModel().getMessage().getObj2().getSig();
    }

    public String getOrgSig() {
        if (getDataAsModel().getMessage().getObj3() == null) return null;
        return getDataAsModel().getMessage().getObj3().getSig();
    }

    public String getChatType() {
        if (getDataAsModel().getMessage() == null) return null;
        return getDataAsModel().getMessage().getType1();
    }

    public String getVideoCallType() {
        if (getDataAsModel().getMessage() == null) return null;
        return getDataAsModel().getMessage().getType2();
    }

    public String getSenderType() {
        if (getDataAsModel().getMessage() == null) return null;
        return getDataAsModel().getMessage().getType3();
    }

    public String getChanelNameOnObj(){
        if (getDataAsModel().getMessage() == null) return null;
        return getDataAsModel().getMessage().getObj1().getSig();
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
