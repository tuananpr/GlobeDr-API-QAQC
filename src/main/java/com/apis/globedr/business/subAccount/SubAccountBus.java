package com.apis.globedr.business.subAccount;

import com.apis.globedr.apis.SubAccountApi;
import com.apis.globedr.model.response.subAccount.SubAccountRS;
import com.apis.globedr.model.step.subAccount.SubAccountMS;


import java.util.List;
import java.util.stream.Collectors;


/**
 * Sub-account include my sub-account
 */

public class SubAccountBus extends AbsSubAccountBus {
    public SubAccountBus() {
    }


    public List<SubAccountRS> loads(String name) {
        return loads(SubAccountMS.builder().name(name).build()).stream().filter(sub -> sub.getDisplayName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }


    public List<SubAccountRS> loads(SubAccountMS body) {
        return body.getName() != null ? subAccountApi.loadSubAccounts(body)
                .stream()
                .filter(sub -> sub.getDisplayName().equalsIgnoreCase(body.getName()) || sub.getDisplayName().equalsIgnoreCase(body.getDisplayName()))
                .collect(Collectors.toList()) :
                subAccountApi.loadSubAccounts(body);
    }


}
