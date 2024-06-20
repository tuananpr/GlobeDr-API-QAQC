package com.apis.globedr.apis;


import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.orgManager.*;
import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.request.orgManager.OrgPayment;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;

import java.util.*;


public class OrgManagerApi extends BaseApi {



    private OrgManagerApi() {
    }

    private static OrgManagerApi instant;

    public static OrgManagerApi getInstant() {
        if (instant == null) instant = new OrgManagerApi();
        return instant;
    }


    public Response contactOrg(Object body) {
        return RestCore.given().url(API.OrgManager.CONTACT_ORG()).auth(token)
                .body(body, ContactOrgRQ.class).post().send();
    }


    public List<LoadOrgsRS> loadOrgs(Object body) {
        return RestCore.given().url(API.OrgManager.LOAD_ORGS()).auth(token)
                .body(body, LoadOrgsRQ.class).post().send()
                .extractAsModels("data.list", LoadOrgsRS.class );
    }


    public Response changeOrgAttribute(Object body) {
        return RestCore.given().url(API.OrgManager.CHANGE_ORG_ATTRIBUTE()).auth(token)
                .body(body, ChangeOrgAttributeRQ.class).put().send();
    }

    public Response orgAppointmentType(Object body) {
        return RestCore.given().url(API.OrgManager.ORG_APPOINTMENT_TYPE()).auth(token)
                .params(body, OrgSig.class).get().send();
    }

    public Response changeOrgAppointmentType(Object body) {
        return RestCore.given().url(API.OrgManager.CHANGE_ORG_APPOINTMENT_TYPE()).auth(token)
                .body(body, ChangeOrgAppointmentTypeRQ.class).put().send();
    }

    public Response orgFeatureAttribute(Object body) {
        return RestCore.given().url(API.OrgManager.ORG_FEATURE_ATTRIBUTE()).auth(token).body(body, OrgFeatureAttributeRQ.class).put().send();

    }

    public Response customerCareSetting(Object body) {
        return RestCore.given().url(API.OrgManager.CUSTOMER_CARE_SETTING()).auth(token).bodyEncrypt(body, CustomerCareSettingRQ.class).put().send();
    }

    public Response customerCare(Object body) {
        return RestCore.given().url(API.OrgManager.CUSTOMER_CARE()).auth(token).bodyEncrypt(body, OrgSig.class).post().send();
    }

    public List<OrgPayment> orgPMs(Object body) {
        return RestCore.given().url(API.OrgManager.ORG_PMS()).auth(token).bodyEncrypt(body, OrgPayment.class).post().send()
                .extractAsModels("data", OrgPayment.class);
    }

    public Response orgPMUpdate(Object body) {
        return RestCore.given().url(API.OrgManager.ORG_PM_UPDATE()).auth(token).bodyEncrypt(body, OrgPayment.class).put().send();
    }

    public Response newOrgPM(Object body) {
        return RestCore.given().url(API.OrgManager.NEW_ORG_PM()).auth(token).bodyEncrypt(body, OrgPayment.class).post().send();
    }
}
