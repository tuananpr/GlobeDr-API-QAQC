package com.apis.globedr.business.search;

import com.apis.globedr.apis.SearchApi;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.org.OrgMS;

import java.util.List;

public class UserSearchBus extends SearchBus<MedicalOrgRS> {
    protected List<MedicalOrgRS> apiLoadOrgs(OrgMS body){
        return SearchApi.getInstant().searchMedicalServices(body);
    }
}
