package com.apis.globedr.stepdefinition;


import com.apis.globedr.business.UserBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.model.request.account.UpdateRequireInfoRQ;
import com.apis.globedr.model.request.user.*;
import com.apis.globedr.model.response.noti.PusherEvent;
import com.apis.globedr.model.response.product.Customer;
import com.apis.globedr.model.response.product.Shops;
import com.apis.globedr.model.step.order.OrderMS;
import com.apis.globedr.model.step.org.JoinOrgMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.model.step.user.UserMS;
import com.apis.globedr.enums.*;
import com.apis.globedr.helper.Data;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.core.RestCore;
import com.rest.core.websocket.WSSClientService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStep extends Data {


    OtherBus otherBus = new OtherBus();
    UserSearchBus userSearchBus = new UserSearchBus();
    UserBus userBus = new UserBus();

    private String bioSig;

    @And("^User role should be (patient|provider|sysAdmin|coordinator|approver)$")
    public void verifyUserType(String type) {
        int actual = response.extract("data.userType");

        if (type.equalsIgnoreCase("patient") || type.equalsIgnoreCase("SysAdmin")) {
            int expected = UserType.value(type);
            Assert.assertEquals(actual, expected);
        } else {
            int expected = UserType.value(type) + UserType.Patient.value();
            Assert.assertEquals(actual, expected);
        }

    }

    @And("^User role should be \"([^\"]*)\"$")
    public void verifyUserTp(String type) {
        verifyUserType(type);
    }

    @And("^User should be (verified|unverified)$")
    public void verifyUserVerified(String isVerify) {
        Boolean value = response.extract("data.isVerified");
        if (isVerify.equals("verified")) {
            Assert.assertTrue(value, ">>>>> User is not verified");
        } else {
            Assert.assertFalse(value);
        }
    }

    @Then("^My followed list should have '(\\d+)' organization$")
    public void verifyNumberOfFollowedOrg(int expectedNo) {
        List<JsonObject> orgList = response.extract("data.list[*]");
        Assert.assertEquals(orgList.size(), expectedNo);
    }


    @And("^I want to add \"([^\"]*)\" as my specialty$")
    public void addSpecialty(String specialty) {
        List<String> specialtiesCode = otherBus.loadSubSpecialtiesCode(Arrays.asList(specialty.split(",")));
        userBus.addSpecialties(specialtiesCode);
    }

    @And("^I get list specialty of above user$")
    public void iGetListSpecialtyOfAboveUser() {
        UserMS body = UserMS.builder().userSig(userSig).language(0).build();
        userBus.loadSpecialties(body);
    }

    @And("^I want to remove specialty$")
    public void iWantToRemoveSpecialty(List<UserMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            userBus.removeSpecialties(info);
        });
    }


    @And("^I leave org$")
    public void iLeaveToOrganization(List<OrgMS> list) {
        list.forEach(info -> {
            orgSig = userSearchBus.loadOrgs(info).get(0).getSig();
            info.setOrgSig(orgSig);
            userBus.leaveOrg(info);
        });
    }


    @And("^I load joined orgs$")
    public void iLoadListOfJoinedOrganization(List<OrgMS> list) {
        userBus.loadJoinedOrgs(list.get(0));
    }


    @And("^I (accept|decline) join organization$")
    public void iAcceptJoinOrganization(String type) {
        if (type.equals("accept")) {
            userBus.acceptJoinOrg();
        } else {
            userBus.declineJoinOrg();
        }
    }


    @And("^Token should be expired$")
    public void tokenShouldBeExpired() {
        Assert.assertEquals(tokenExpired, "true", "Header 'Token-Expired' must be true");
    }

    @And("^I get information user$")
    public void iGetInformationUser() {
        userBus.getUserInfo(userSig);
    }

    @And("^I want to send feedback to the app with below infor$")
    public void iWantToSendFeadbackForApp(List<FeedbackRQ> list) {
        list.forEach(userBus::feedback);
    }


    @And("^I load bio information$")
    public void iLoadBioInformation(List<LoadUserBioRQ> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            userBus.loadUserBio(info);
        });
    }


    @And("^I add Bio information$")
    public void iAddBioInformation(List<AddUserBioRQ> list) {
        list.forEach(userBus::addUserBio);
    }

    @And("^I update Bio information$")
    public void iUpdateAboveBioWithInformation(List<AddUserBioRQ> list) {
        list.forEach(info -> {
            info.setBioSig(bioSig);
            userBus.updateUserBio(info);
        });
    }

    @And("^I remove Bio$")
    public void iRemoveAboveBio(List<AddUserBioRQ> list) {
        list.forEach(userBus::removeUserBio);
    }

    @And("^I upload Bio document$")
    public void iUploadForMyProfile(List<UploadCertificateRQ> list) {
        list.forEach(userBus::uploadCertificate);
    }

    @And("^I remove Bio document$")
    public void iRemoveDocumentForDocTypeBio(List<LoadUserBioRQ> list) {
        list.forEach(userBus::removeCertificate);
    }

    @And("^I load full information of above user$")
    public void iLoadFullInformationOfAboveUser(List<UserInfoRQ> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            userBus.userInfo(info);
        });
    }

    @And("^I join org$")
    public void iJoinToOrganizationAsStaff(List<JoinOrgMS> list) {
        list.forEach(info -> {
            orgSig = userSearchBus.loadOrgs(info).get(0).getSig();
            info.setOrgSig(orgSig);
            userBus.joinOrg(info);
        });
    }


    @And("I update the below info for requirement")
    public void iUpdateTheBelowInfoForRequirement(List<UpdateRequireInfoRQ> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            userBus.updateRequireInfo(info);
        });
    }

    @And("I get page dashboard")
    public void iGetPageDashBoard(List<PageDashBoardsRQ> list) {
        list.forEach(userBus::pageDashBoards);
    }

    @And("As user, I get features of page")
    public void userFeaturesOfPage(List<FeaturesOfPageRQ> list) {
        list.forEach(userBus::featuresOfPage);
    }

    @And("As org, I get features of page")
    public void iFeaturesOfPage(List<FeaturesOfPageRQ> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            userBus.featuresOfPage(info);
        });
    }

    @And("test")
    public void test() {
        WSSClientService.getInstance()
                .connect("wss://demo.piesocket.com/v3/channel_1?api_key=oCdCMcMPQpbvNjUIzqtvF1d2X2okWpDQj4AwARJuAgtjhzKxVEjQU6IdCjwm&notify_self");

        try {
            System.out.println("start");
            Thread.sleep(5000);
            System.out.println(WSSClientService.getInstance().getData().getMsg());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
