package com.apis.globedr.business.subAccount;

import com.apis.globedr.apis.SubAccountApi;
import com.apis.globedr.model.response.subAccount.SubAccountRS;
import com.apis.globedr.model.step.subAccount.SubAccountMS;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use on Feature : SubAccount, Health
 * Family members include my sub-account and shared sub-account from other
 */

public class FamilyBus extends AbsSubAccountBus {
    public FamilyBus() {
    }

    @Override
    public List<SubAccountRS> loads(String name) {
        return loads(SubAccountMS.builder().name(name).build()).stream().filter(sub -> sub.getDisplayName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    @Override
    public List<SubAccountRS> loads(SubAccountMS body) {
        return body.getName() != null ? subAccountApi.familyMembers(body)
                .stream()
                .filter(sub -> sub.getDisplayName().equalsIgnoreCase(body.getName()) || sub.getDisplayName().equalsIgnoreCase(body.getDisplayName()))
                .collect(Collectors.toList()) :
                subAccountApi.familyMembers(body);
    }


}
