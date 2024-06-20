package com.apis.globedr.business.search;

import com.apis.globedr.apis.SearchApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.org.JoinOrgMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.services.geo.Geo;

import java.util.List;

public abstract class SearchBus<T> extends AbsBus {

    private void prepare(OrgMS body){
        if(body.getAddress()!= null){
            com.apis.globedr.model.general.Geo geo = Geo.getLatLongByAddress(body.getAddress());
            body.setLongitude(geo.getLongitude());
            body.setLatitude(geo.getLatitude());
        }
    }

    protected abstract List<T> apiLoadOrgs(OrgMS body);
    public List<T> loadOrgs(JoinOrgMS body) {
        return loadOrgs(mapping(body, OrgMS.class));
    }

    public List<T> loadOrgs(OrgMS body) {
        prepare(body);
        return apiLoadOrgs(body);
    }

    public List<T> loadOrgsByName(String name) {
        OrgMS orgMS = new OrgMS();
        orgMS.setName(name);
        return apiLoadOrgs(orgMS);
    }

    public List<MedicalOrgRS> loadMedicalOrgs(OrgMS body) {
        prepare(body);
        return SearchApi.getInstant().searchMedicalOrgs(body);
    }

    public List<MedicalOrgRS> loadWorkers(OrgMS body) {
        prepare(body);
        return SearchApi.getInstant().searchMedicalWorkers(body);
    }

}
