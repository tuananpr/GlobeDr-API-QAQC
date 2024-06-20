package com.apis.globedr.apis;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.apis.globedr.model.request.other.GetCitiesRQ;
import com.apis.globedr.model.request.other.GetDistrictsRQ;
import com.apis.globedr.model.request.other.GetWardsRQ;
import com.apis.globedr.model.request.other.LoadSpecialtiesRQ;
import com.apis.globedr.model.response.other.CountryRS;
import com.apis.globedr.model.response.other.Specialty;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherApi extends BaseApi {

    private OtherApi() {
    }

    private static OtherApi instant;

    public static OtherApi getInstant() {
        if (instant == null) instant = new OtherApi();
        return instant;
    }

    public List<Specialty> loadSpecialties(Object body) {
        return RestCore.given().url(API.Other.LOAD_SPECIALTIES()).auth(token)
                .body(body, LoadSpecialtiesRQ.class).post().send()
                .extractAsModels("data.list", Specialty.class );
    }

    public Response getRegions(String country) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.COUNTRY, country);
        return RestCore.given().url(API.Other.GET_REGIONS()).auth(token).params(body).get().send();
    }


    public Response loadMetaData() {
        return RestCore.given().url(API.Other.META_DATA()).auth(token).get().send();
    }

    public Response getDownloadApp() {
        return RestCore.given().url(API.Other.GET_DOWNLOAD_APP()).auth(token).get().send();
    }

    public Response getLandingPage(int language) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.LANGUAGE, language);
        return RestCore.given().url(API.Other.GET_LANDING_PAGE()).auth(token).params(body).get().send();
    }

    public Response checkPhone(String phone) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.PHONE, phone);
        return RestCore.given().url(API.Other.CHECK_PHONE()).auth(token).params(body).get().send();
    }

    public Response detectLocation() {
        Map<String, Object> body = new HashMap<>();
        return RestCore.given().url(API.Other.DETECT_LOCATION()).auth(token).get().send();
    }

    public Response getHosts(Map<String, Object> body) {
        return RestCore.given().url(API.Other.GET_HOSTS()).params(body).get().send();
    }

    public List<CountryRS> getCountries() {
        return RestCore.given().url(API.Other.GET_COUNTRIES()).get().send().extractAsModels("data", CountryRS.class);
    }

    public List<City> getCities(Object body) {
        return RestCore.given().url(API.Other.GET_CITIES()).params(body, GetCitiesRQ.class).get().send().extractAsModels("data", City.class);
    }

    public List<District> getDistricts(Object body) {
        return RestCore.given().url(API.Other.GET_DISTRICTS()).params(body, GetDistrictsRQ.class).get().send().extractAsModels("data", District.class);
    }

    public List<Ward> getWards(Object body) {
        return RestCore.given().url(API.Other.GET_WARDS()).params(body, GetWardsRQ.class).get().send().extractAsModels("data", Ward.class);
    }
}
