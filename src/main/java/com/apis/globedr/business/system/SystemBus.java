package com.apis.globedr.business.system;

import com.apis.globedr.apis.SystemApi;
import com.apis.globedr.model.request.system.*;
import com.rest.core.response.Response;

public class SystemBus {
    SystemApi systemApi = SystemApi.getInstant();

    public Response viewUsersGrowthChart(UserGrowthChartRQ body) {
        return systemApi.viewUsersGrowthChart(body);
    }


    public Response viewOrgsGrowthChart(OrgsGrowthChartRQ body) {
        return systemApi.viewOrgsGrowthChart(body);
    }

    public Response getOrgsByCountry(OrgsByCountryRQ body) {
        return systemApi.getOrgsByCountry(body);
    }


    public Response getOrgsByType(OrgsByTypeRQ body) {
        return systemApi.getOrgsByType(body);
    }


    public Response getUsersByCountry(UsersByCountryRQ body) {
        return systemApi.getUsersByCountry(body);
    }

    public Response getUsersByGender(UsersByGenderRQ body) {
        return systemApi.getUsersByGender(body);
    }
    public Response loadFeedbacks(FeedbacksRQ body) {
        return systemApi.loadFeedbacks(body);
    }
    public Response exportFeedbacks(FeedbacksExcelRQ body) {
        return systemApi.exportFeedbacks(body);
    }
    public Response getVacInfo(GetVacInfoRQ body) {
        return  systemApi.getVacInfo(body);
    }

    public Response getSystemPost(GetSystemPostRQ body) {
        return  systemApi.getSystemPost(body);
    }

}
