package com.apis.globedr.apis;

import com.apis.globedr.model.general.UserSig;
import com.apis.globedr.model.request.immunization.*;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.google.gson.JsonObject;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.JsonHelper;

import java.util.HashMap;
import java.util.Map;

public class VaccinationApi extends BaseApi {

    private VaccinationApi(){}
    private static VaccinationApi instant;
    public static VaccinationApi getInstant(){
        if(instant == null) instant = new VaccinationApi();
        return instant;
    }

    public Response viewLogImmunization(Object body) {
        return RestCore.given().url(API.Vaccination.BUILD_REPORT()).auth(token).bodyEncrypt(body, BuildReportRQ.class).post().send();
    }


    public Response getImmunizationByAge(Object body){
        return RestCore.given().url(API.Vaccination.GET_IMMUNIZATION_BY_AGE()).auth(token).bodyEncrypt(body, GetImmunizationByAgeRQ.class).post().send();
    }

    public Response getImmunizationByVaccine(Object body){
        return RestCore.given().url(API.Vaccination.GET_IMMUNIZATION_BY_VACCINE()).auth(token).bodyEncrypt(body, GetImmunizationByVaccineRQ.class).post().send();
    }

    public Response updateVaccineRecord(Object body){
        return RestCore.given().url(API.Vaccination.UPDATE_VACCINE_RECORD()).auth(token).bodyEncrypt(body, UpdateVaccineRecordRQ.class).put().send();
    }

    public Response removeVaccineRecord(Object body){
        return RestCore.given().url(API.Vaccination.REMOVE_VACCINE_RECORD()).auth(token).bodyEncrypt(body, RemoveVaccineRecordRQ.class).delete().send();
    }

    public Response getImmunization(Object body){
        return RestCore.given().url(API.Vaccination.GET_IMMUNIZATION()).auth(token).params(body, GetImmunizationRQ.class).get().send();
    }

    public Response loadMedicines(Object body){
        return RestCore.given().url(API.Vaccination.LOAD_MEDICINES()).auth(token).bodyEncrypt(body, LoadMedicinesRQ.class).post().send();
    }

    public Response loadVaccineByMed(Object body){
        return  RestCore.given().url(API.Vaccination.LOAD_VACCINE_BY_MED()).auth(token).bodyEncrypt(body, LoadVaccineByMedRQ.class).post().send();
    }




}
