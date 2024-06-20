package com.apis.globedr.business.search;

import com.apis.globedr.apis.OrgManagerApi;

import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.services.geo.Geo;

import java.util.List;

public class SysAdminSearchBus extends SearchBus<LoadOrgsRS> {
    @Override
    protected List<LoadOrgsRS> apiLoadOrgs(OrgMS body) {
        return OrgManagerApi.getInstant().loadOrgs(body);
    }
}
