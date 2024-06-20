package com.apis.globedr.business.userManager;

import com.apis.globedr.apis.UserManagerApi;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.request.userManager.AppUsageTimeRQ;
import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.apis.globedr.model.response.userManager.LoadUsersRS;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.sysAdmin.AppUsageTimeMS;
import com.apis.globedr.model.step.userManager.*;
import com.apis.globedr.model.step.wallet.WalletMS;
import com.rest.core.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class UserManagerBus {
    UserManagerApi userManagerApi = UserManagerApi.getInstant();
    public List<LoadUsersRS> loadUsers(LoadUsersRQ body){
        return userManagerApi.loadUsers(body);
    }

    public List<String> loadUsersAndGetSigs(AccountMS body){
        LoadUsersRQ loadUsersRQ = LoadUsersRQ.builder().name(body.getDisplayName()).country(body.getCountryName()).build();
        return loadUsersAndGetSigs(loadUsersRQ);
    }

    public List<String> loadUsersAndGetSigs(LoadUsersRQ body){
        return loadUsers(body).stream().map(u->u.getUserSignature()).collect(Collectors.toList());
    }

    public List<String> loadUsersAndGetSigs(String name){
        LoadUsersRQ body = LoadUsersRQ.builder().name(name).build();
        List<String> sigs = loadUsersAndGetSigs(body);
        Assert.assertTrue(sigs.size() > 0);
        return sigs;
    }

    public Response verifyUser(VerifyUserMS body){
        List<String> userSigs =loadUsersAndGetSigs(body.getUsers());
        body.getVerifyUser().setUserSigList(userSigs);
        return userManagerApi.verifyUser(body.getVerifyUser());
    }

    public Response removeUsers(RemoveUserMS body){
        List<String> userSigs =loadUsersAndGetSigs(body.getUsers());
        body.getRemoveUser().setUserSigList(userSigs);
        return userManagerApi.removeUsers(body.getRemoveUser());
    }
    public Response pwdReset(PwdResetMS body){
        List<String> userSigs =loadUsersAndGetSigs(body.getUsers());
        body.getPwdReset().setUserSigList(userSigs);
        return userManagerApi.pwdReset(body.getPwdReset());
    }


    public Response changeUserStatus(ChangeUserStatusMS body){
        List<String> userSigs =loadUsersAndGetSigs(body.getUsers());
        body.getChangeUserStatus().setUserSigList(userSigs);
        return userManagerApi.changeUserStatus(body.getChangeUserStatus());
    }

    public Response giftPoint(GiftPointMS body){
        return userManagerApi.giftPoint(body);
    }

    public Response changeUserFeatureAttribute(UserFeatureAttributeMS body){
        List<String> userSigs = loadUsersAndGetSigs(body.getName());
        body.setUserSigList(userSigs);
        return userManagerApi.changeUserFeatureAttribute(body);
    }

    public Response pointRules(PointRuleMS body){
        return userManagerApi.pointRules(body);
    }
    public Response newPointRule(PointRuleMS body){
        return userManagerApi.newPointRule(body);
    }

    public Response appUsageTime(AppUsageTimeMS body){
        return userManagerApi.appUsageTime(body);
    }

    public Response usedPointHistory(FilterDate body){
        return userManagerApi.usedPointHistory(body);
    }

}
