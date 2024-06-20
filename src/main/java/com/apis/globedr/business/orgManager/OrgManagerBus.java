package com.apis.globedr.business.orgManager;

import com.apis.globedr.apis.OrgManagerApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.orgManager.ChangeOrgAttributeRQ;
import com.apis.globedr.model.request.orgManager.ContactOrgRQ;
import com.apis.globedr.model.request.orgManager.OrgFeatureAttributeRQ;
import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.request.orgManager.OrgPayment;
import com.apis.globedr.model.step.org.CreateOrgMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.model.step.payment.PaymentConfigMS;
import com.rest.core.response.Response;

import java.util.Arrays;
import java.util.List;

public class OrgManagerBus extends AbsBus {
    OrgManagerApi orgManagerApi = OrgManagerApi.getInstant();

    public List<LoadOrgsRS> loadOrgs(OrgMS body) {
        return orgManagerApi.loadOrgs(body);
    }

    public Response contactOrg(ContactOrgRQ body) {
        return orgManagerApi.contactOrg(body);
    }

    public List<LoadOrgsRS> loadOrgsByName(String name) {
        OrgMS body = new OrgMS();
        body.setName(name);
        return loadOrgs(body);
    }

    public String getOrgSig(String orgName) {
        return loadOrgsByName(orgName).get(0).getOrgSig();
    }

    public Response changeOrgAttribute(OrgMS body) {
        ChangeOrgAttributeRQ request = new ChangeOrgAttributeRQ();
        request.setOrgSigList(Arrays.asList(body.getOrgSig()));
        request.setOrgAttribute(body.getOrgAttribute());
        return orgManagerApi.changeOrgAttribute(request);
    }

    public Response changeOrgAttribute(CreateOrgMS body) {
        ChangeOrgAttributeRQ request = new ChangeOrgAttributeRQ();
        request.setOrgSigList(Arrays.asList(body.getOrgSig()));
        request.setOrgAttribute(body.getOrgAttribute());
        return orgManagerApi.changeOrgAttribute(request);
    }

    public Response orgFeatureAttribute(OrgMS body) {
        OrgFeatureAttributeRQ request = new OrgFeatureAttributeRQ();
        request.setOrgSigList(Arrays.asList(body.getOrgSig()));
        request.setOrgFeatureAttributes(body.getOrgFeatureAttributes());
        return orgManagerApi.orgFeatureAttribute(request);
    }

    public Response orgFeatureAttribute(CreateOrgMS body) {
        OrgFeatureAttributeRQ request = new OrgFeatureAttributeRQ();
        request.setOrgSigList(Arrays.asList(body.getOrgSig()));
        request.setOrgFeatureAttributes(body.getOrgFeatureAttributes());
        return orgManagerApi.orgFeatureAttribute(request);
    }

    public Response orgAppointmentType(String orgSig) {
        return orgManagerApi.orgAppointmentType(new OrgSig(orgSig));
    }

    public Response changeOrgAppointmentType(OrgMS body) {
        return orgManagerApi.changeOrgAppointmentType(body);
    }


    public Response customerCareSetting(OrgMS body) {
        return orgManagerApi.customerCareSetting(body);
    }

    public <T extends OrgRS> Response getTotalCustomerCare(T body) {
        return orgManagerApi.customerCare(body);
    }

    public List<OrgPayment> orgPMs(PaymentConfigMS body) {
        return orgManagerApi.orgPMs(body);
    }

    public Response orgPMUpdate(PaymentConfigMS body) {
        return orgManagerApi.orgPMUpdate(body);
    }

    public Response newOrgPM(PaymentConfigMS body) {
        return orgManagerApi.newOrgPM(body);
    }


}
