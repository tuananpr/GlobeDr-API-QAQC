package com.apis.globedr.services.geo;

import com.apis.globedr.services.config.GeoCfg;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Geo {

//    private static float[] getGeoByGoogle(String address) {
//
//        GeoCfg geoCfg = new GeoCfg();
//        String url = String.format(
//                geoCfg.getGoogleUri() + "/place/findplacefromtext/" + "json?input=%s&inputtype=%s&fields=%s&key=%s"
//                , address
//                , geoCfg.get("google.inputtype")
//                , geoCfg.get("google.fields")
//                , geoCfg.get("google.key")
//        );
//
//        Response response = RestCore.given().url(url).get().send();
//
//
//        float[] arr = new float[2];
//        arr[0] = response.path("candidates[0].geometry.location.lat");
//        arr[1] = response.path("candidates[0].geometry.location.lng");
//        return arr;
//    }

    public static com.apis.globedr.model.general.Geo getLatLongByAddress(Object address) {
        if (address == null) return null;
        GeoCfg geoCfg = new GeoCfg();
        Map<String, Object> body = new HashMap<>();
        body.put("q", address.toString());
        body.put("key", geoCfg.get("Geo.key").trim());

        String url = geoCfg.getOpenCaseUri() + "geocode/v1/json";
        Response rs = RestCore.given().url(url).params(body).get().send();

        com.apis.globedr.model.general.Geo geo = new com.apis.globedr.model.general.Geo();
        geo.setLatitude(rs.extract("results[0].geometry.lat"));
        geo.setLongitude(rs.extract("results[0].geometry.lng"));
        return geo;
    }

}
