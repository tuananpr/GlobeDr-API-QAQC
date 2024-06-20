package com.apis.globedr.helper;

import com.apis.globedr.model.response.noti.PusherTeleCall;
import com.rest.core.observers.IObserverApi;
import com.rest.core.request.AbsRequest;
import com.rest.core.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import com.rest.core.debug.Logger;
import com.apis.globedr.pusher.PusherUtil;

public class Data implements IObserverApi {
    private static Map<String, Object> all;
    public static RequestSpecification request;
    public static com.rest.core.response.Response response;
    public static PusherTeleCall pusherTeleCall;
    public static String sig;
    public static String itemSig;
    //public static String empty = "";
    public static String userSig;
    public static String subSig;
    public static String orgSig;
    public static String productSig;
    public static String postSig;
    public static String categorySig;
    public static String voucherSig;
    //public static String qrCode;
    public static String docSig;
    public static String doctorSig;
    public static String visitSig;
    public static String apikey;
    public static Integer orgId;
    public static String startDate;
    public static String endDate;
    public static String tokenExpired;
    public static String aptSig;
    public static String orderSig;
    public static Map<String, PusherUtil> pushers;

    public static void clear() {
        Logger.getInstance().info("Data : clear all data");
        if (pushers != null && pushers.size() != 0 ){
            for (Map.Entry<String, PusherUtil> entry : pushers.entrySet()) {
                entry.getValue().unsubscribe();
            }
        }
        Common.setNullAllVariable(Data.class);
        initData();
    }

    @Override
    public void update(String content) {

    }

    @Override
    public void update(AbsRequest absRequest, Response response) {
        if (absRequest.getUrl().contains("es1")) return; // skip if request from elastic search
        Data.response = response;
    }

    public static void set(String key, String value) {
        if( all == null) initData();
        all.put(key, value);
    }

    public static <T> void set(String key, T value) {
        if( all == null) initData();
        all.put(key, value);
    }

    public static <T> T get(String key) {
        return (T)all.get(key);
    }

    private static void initData(){
        pushers = new HashMap<>();
        all = new HashMap<>();
        all.put("empty", "");
    }
}
