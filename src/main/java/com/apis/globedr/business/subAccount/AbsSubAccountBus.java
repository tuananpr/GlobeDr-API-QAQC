package com.apis.globedr.business.subAccount;

import com.apis.globedr.apis.SubAccountApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.request.connection.LoadRequestConnectionsRQ;
import com.apis.globedr.model.request.subAccount.*;
import com.apis.globedr.model.response.connection.ConnectionDetailRS;
import com.apis.globedr.model.response.subAccount.SharedAccountRS;
import com.apis.globedr.model.response.subAccount.SubAccountRS;
import com.apis.globedr.model.step.subAccount.SubAccountMS;
import com.rest.core.response.Response;

import java.util.ArrayList;
import java.util.List;


public abstract class AbsSubAccountBus extends AbsBus {

    SubAccountApi subAccountApi = SubAccountApi.getInstant();

    public AbsSubAccountBus() {
    }

    public SubAccountRS create(SubAccountMS body) {
        return subAccountApi.create(body);
    }

    public void update(String name, SubAccountMS newData) {
        loads(name).forEach(sub -> {
            newData.setUserSig(sub.getUserSignature());
            subAccountApi.update(newData);
        });
    }

    public abstract List<SubAccountRS> loads(String name);

    public abstract List<SubAccountRS> loads(SubAccountMS body);

    public SubAccountRS getMain(SubAccountMS body) {
        return loads(body).stream().filter(SubAccountRS::getIsMainAccount).findFirst().orElse(null);
    }

    public SubAccountRS getFirstSubAccount(SubAccountMS body) {
        return loads(body).stream().filter(sub -> !sub.getIsMainAccount()).findFirst().orElse(null);
    }


    public void remove(String name) {
        loads(name).forEach(subAccountApi::remove);
    }


    public void sharedAccount(String name) {
        loads(name).forEach(subAccountApi::sharedAccount);
    }

    public List<ConnectionDetailRS> loadConnectionToShare(SubAccountMS body) {
        List<ConnectionDetailRS> result = new ArrayList<>();
        loads(body).forEach(
                sub -> {
                    LoadRequestConnectionsRQ request = mapping(body, LoadRequestConnectionsRQ.class);
                    request.setSubSig(sub.getUserSignature());
                    request.setName(body.getSharedConnectionName());
                    result.addAll(subAccountApi.connectionToShare(request));
                }

        );
        return result;
    }

    public void shareToConnection(SubAccountMS body) {
        List<ShareInfoRQ> shareInfos = new ArrayList<>();

        // get friends to share
        loadConnectionToShare(body).forEach(con -> {
            shareInfos.add(ShareInfoRQ.builder()
                    .userSig(con.getUser().getUserSig())
                    .userManageType(body.getSharedType())
                    .build());
        });

        shareToConnection(body.getName(), shareInfos);
    }


    public void shareToConnection(String sharedAccountName, List<ShareInfoRQ> toConnection) {

        loads(sharedAccountName).forEach(sub -> {
            AccountShareRQ request = new AccountShareRQ();
            request.setUserSig(sub.getUserSignature());
            request.setShareInfos(toConnection);
            subAccountApi.accountShare(request);
        });
    }



    public List<SharedAccountRS> listSharedAccount(SubAccountMS body){
        return subAccountApi.listSharedAccount(body);

    }

    public Response sharedAccountInformation(SubAccountMS body){
        return subAccountApi.sharedAccountInformation(body);
    }


}
