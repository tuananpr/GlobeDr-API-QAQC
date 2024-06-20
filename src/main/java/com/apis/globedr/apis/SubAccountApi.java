package com.apis.globedr.apis;

import com.apis.globedr.model.request.connection.LoadRequestConnectionsRQ;
import com.apis.globedr.model.request.subAccount.*;
import com.apis.globedr.model.response.connection.ConnectionDetailRS;
import com.apis.globedr.model.response.subAccount.SharedAccountRS;
import com.apis.globedr.model.response.subAccount.SubAccountRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;

import java.util.List;

public class SubAccountApi extends BaseApi {

    private SubAccountApi() {
    }

    private static SubAccountApi instant;

    public static SubAccountApi getInstant() {
        if (instant == null) instant = new SubAccountApi();
        return instant;
    }

    public SubAccountRS create(Object body) {
        return RestCore.given().url(API.SubAccount.CREATE_SUB_ACCOUNT()).auth(token)
                .bodyEncrypt(body, CreateSubAccountRQ.class).post().send()
                .extractAsModel("data.subAccount", SubAccountRS.class);
    }

    public Response update(Object body) {
        return RestCore.given().url(API.SubAccount.UPDATE_SUB_ACCOUNT()).auth(token)
                .bodyEncrypt(body, UpdateSubAccountRQ.class).put().send();
    }

    public List<SubAccountRS> loadSubAccounts(Object body) {
        return RestCore.given().url(API.SubAccount.LOAD_SUB_ACCOUNT()).auth(token)
                .bodyEncrypt(body, LoadSubAccountsRQ.class).post().send()
                .extractAsModels("data.list", SubAccountRS.class );
    }

    public List<SubAccountRS> familyMembers(Object body) {
        //same object with LoadSubAccountsRQ
        return RestCore.given().url(API.SubAccount.FAMILY_MEMBERS()).auth(token)
                .bodyEncrypt(body, LoadSubAccountsRQ.class).post().send()
                .extractAsModels("data.list", SubAccountRS.class );
    }

    public Response remove(Object body) {
        return RestCore.given().url(API.SubAccount.REMOVE_SUB_ACCOUNT()).auth(token)
                .bodyEncrypt(body, RemoveSubAccountRQ.class).delete().send();
    }


    public Response accountShare(Object body) {
        return RestCore.given().url(API.SubAccount.ACCOUNT_SHARE()).auth(token).bodyEncrypt(body, AccountShareRQ.class).put().send();

    }

    public List<SharedAccountRS> listSharedAccount(Object body) {
        return RestCore.given().url(API.SubAccount.LIST_SHARED_ACCOUNT()).auth(token)
                .bodyEncrypt(body, ListSharedAccount.class).post().send()
                .extractAsModels("data.list", SharedAccountRS.class );
    }

    public List<ConnectionDetailRS> connectionToShare(Object body) {
        // same object with LoadRequestConnectionsRQ
        return RestCore.given().url(API.SubAccount.CONNECTIONS_TO_SHARE()).auth(token)
                .bodyEncrypt(body, LoadRequestConnectionsRQ.class).post().send()
                .extractAsModels("data.list", ConnectionDetailRS.class );
    }


    public Response sharedAccount(Object body) {
        return RestCore.given().url(API.SubAccount.SHARED_ACCOUNT()).auth(token).bodyEncrypt(body, SharedAccountRQ.class).delete().send();

    }


    public Response sharedAccountInformation(Object body) {
        return RestCore.given().url(API.SubAccount.SHARED_ACCOUNT_INFORMATION()).auth(token)
                .bodyEncrypt(body, SharedAccountInformationRQ.class)
                .post().send();
    }


}
