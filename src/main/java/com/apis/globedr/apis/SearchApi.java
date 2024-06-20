package com.apis.globedr.apis;



import com.apis.globedr.model.request.org.MedicalOrgsRQ;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;

import java.util.List;

public class SearchApi  extends BaseApi {

    private SearchApi(){}
    private static SearchApi instant;
    public static SearchApi getInstant(){
        if(instant == null) instant = new SearchApi();
        return instant;
    }


    public List<MedicalOrgRS> searchMedicalOrgs(String name) {
        OrgMS org = new OrgMS();
        org.setName(name);
        return searchMedicalOrgs(org);
    }

    public List<MedicalOrgRS> searchMedicalOrgs(Object body) {
        return RestCore.given().url(API.Search.MEDICAL_ORGS()).auth(token)
                .body(body, MedicalOrgsRQ.class).post().send().extractAsModels("data.list", MedicalOrgRS.class );
    }

    public List<MedicalOrgRS> searchMedicalWorkers(Object body) {
        return RestCore.given().url(API.Search.MEDICAL_WORKERS()).auth(token)
                .body(body, MedicalOrgsRQ.class).post().send().extractAsModels("data.list", MedicalOrgRS.class );
    }

    public List<MedicalOrgRS> searchMedicalServices(Object body) {
        return RestCore.given().url(API.Search.MEDICAL_SERVICES()).auth(token)
                .body(body, MedicalOrgsRQ.class).post().send().extractAsModels("data.list", MedicalOrgRS.class );
    }


}
