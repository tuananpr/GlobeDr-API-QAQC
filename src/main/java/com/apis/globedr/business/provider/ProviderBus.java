package com.apis.globedr.business.provider;

import com.apis.globedr.apis.ProviderApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.request.provider.LogReceiverFeesRQ;
import com.apis.globedr.model.request.provider.ReportFeesRQ;
import com.apis.globedr.model.request.provider.SearchUserRS;
import com.apis.globedr.model.step.provider.GiftPointMS;
import com.rest.core.response.Response;

import java.util.List;

public class ProviderBus extends AbsBus {
    private ProviderApi providerApi = ProviderApi.getInstant();

    public List<SearchUserRS> searchUser(Object info){
        return providerApi.searchUser(info);
    }

    public String searchAndGetUserSig(Object info){
        return searchUser(info).stream().findFirst().orElse(null).getUserSig();
    }

    public Response giftPoint(GiftPointMS info){
        return providerApi.giftPoint(info);
    }

    public Response reportOrgs(){
        return providerApi.reportOrgs();
    }
    public Response reportFees(ReportFeesRQ info){
        return providerApi.reportFees(info);
    }

    public Response logReceiverFees(LogReceiverFeesRQ info){
        return providerApi.logReceiverFees(info);
    }


}
