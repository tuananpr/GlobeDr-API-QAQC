package com.apis.globedr.business.imminization;

import com.apis.globedr.apis.VaccinationApi;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.model.request.immunization.UpdateVaccineRecordRQ;
import com.apis.globedr.model.step.immunization.ImmunizationMS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ImmunizationBus {
    VaccinationApi vaccinationApi = VaccinationApi.getInstant();


    public Response viewLogImmunization(ImmunizationMS body) {
        return vaccinationApi.viewLogImmunization(body);
    }


    public Response getImmunizationByAge(ImmunizationMS body) {
        return vaccinationApi.getImmunizationByAge(body);
    }

    public Response getImmunizationByVaccine(ImmunizationMS body) {
        return vaccinationApi.getImmunizationByVaccine(body);
    }

    public Response updateVaccineRecord(ImmunizationMS body) {
        return vaccinationApi.updateVaccineRecord(body);
    }

    public Response updateVaccineRecord(UpdateVaccineRecordRQ body) {
        return vaccinationApi.updateVaccineRecord(body);
    }

    public Response removeVaccineRecord(ImmunizationMS body) {
        return vaccinationApi.removeVaccineRecord(body);
    }

    public Response getImmunization(ImmunizationMS body) {
        return vaccinationApi.getImmunization(body);
    }

    public Response loadMedicines(ImmunizationMS body) {
        return vaccinationApi.loadMedicines(body);
    }

    public Response loadVaccineByMed(ImmunizationMS body) {
        return vaccinationApi.loadVaccineByMed(body);
    }


}
