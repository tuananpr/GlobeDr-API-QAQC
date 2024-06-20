package com.apis.globedr.apis;

import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.HashMap;
import java.util.Map;

public class HL7Api extends BaseApi {

    private HL7Api(){}
    private static HL7Api instant;
    public static HL7Api getInstant(){
        if(instant == null) instant = new HL7Api();
        return instant;
    }

    public void infoInsuranceCard(String strFromQRCodeOnInsuranceCard) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.HL7_STRING, strFromQRCodeOnInsuranceCard);
        RestCore.given().url(API.HL7.INFO_INSURANCE_CARD()).auth(token).params(body).get().send();
    }

}
