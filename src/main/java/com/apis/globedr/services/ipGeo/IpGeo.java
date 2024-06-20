package com.apis.globedr.services.ipGeo;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IpGeo {

    public String getExternalIP() {

        BufferedReader in = null;
        try {

            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            return in.readLine(); //you get the IP as a String
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }


    public String detectMyCountry() {

        // Create IPGeolocationAPI object, passing your valid API key
        //get apikey from https://app.ipgeolocation.io/
        String apiKey = "38934f781eca4e59a305fddb886b2728";
        IPGeolocationAPI api = new IPGeolocationAPI(apiKey);
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(getExternalIP());
        geoParams.setFields("geo,time_zone,currency");
        geoParams.setIncludeSecurity(true);
        Geolocation geolocation = api.getGeolocation(geoParams);

        // Check if geolocation lookup was successful
        if (geolocation.getStatus() == 200) {
            return geolocation.getCountryCode2();

        } else {
            Assert.fail(String.format("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage()));
        }
        return null;
    }


}
