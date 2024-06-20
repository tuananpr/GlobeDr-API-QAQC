package com.apis.globedr.apis;

import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.branch.CreateBranchRQ;
import com.apis.globedr.model.request.branch.UpdateBranchRQ;
import com.apis.globedr.model.request.org.*;
import com.apis.globedr.model.response.branch.BranchRS;
import com.apis.globedr.model.response.org.LoadDepartmentRS;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.response.org.OrgsManageRS;
import com.apis.globedr.model.response.org.StaffRS;
import com.apis.globedr.model.response.other.Specialty;
import com.apis.globedr.model.general.QRCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.*;


public class OrgApi extends BaseApi {


    private OrgApi() {
    }

    private static OrgApi instant;

    public static OrgApi getInstant() {
        if (instant == null) instant = new OrgApi();
        return instant;
    }


    public List<StaffRS> loadUsersToAddStaffs(Object body) {
        return RestCore.given().url(API.Org.LOAD_USERS_TO_ADD_STAFFS()).auth(token)
                .body(body, LoadUsersToAddStaffsRQ.class).post().send()
                .extractAsModels("data.list", StaffRS.class );
    }


    public OrgRS createOrg(Object body) {
        return RestCore.given().url(API.OrgManager.CREATE_ORG()).auth(token)
                .body(body, CreateOrgRQ.class).post().send()
                .extractAsModel("data.newOrg", OrgRS.class);
    }

    public BranchRS createBranch(Object body) {
        return RestCore.given().url(API.Org.CREATE_BRANCH()).auth(token)
                .bodyEncrypt(body, CreateBranchRQ.class).post().send()
                .extractAsModel("data.newBranch", BranchRS.class);
    }

    public Response updateBranch(Object body) {
        return RestCore.given().url(API.Org.UPDATE_BRANCH()).auth(token)
                .bodyEncrypt(body, UpdateBranchRQ.class).post().send();
    }

    public List<BranchRS> loadBranch(Object body) {
        return RestCore.given().url(API.Org.LOAD_BRANCHS()).auth(token)
                .body(body, LoadBranchesRQ.class).post().send()
                .extractAsModels("data.list", BranchRS.class );
    }

    public Response removeBranch(Object body) {
        return RestCore.given().url(API.Org.REMOVE_BRANCH()).auth(token).body(body, RemoveBranchRQ.class).delete().send();
    }

    public List<OrgsManageRS> getOrgsManage() {
        return RestCore.given().url(API.Org.GET_ORGS_MANAGE()).auth(token).get().send()
                .extractAsModels("data.orgs", OrgsManageRS.class);
    }


    public Response addSpecialties(Object body) {
        return RestCore.given().url(API.Org.ADD_SPECIALTIES()).auth(token)
                .body(body, AddSpecialtiesRQ.class).post().send();
    }


    public Response removeSpecialties(Object body) {
        return RestCore.given().url(API.Org.REMOVE_SPECIALTIES()).auth(token)
                .body(body, AddSpecialtiesRQ.class).delete().send();
    }

    public List<StaffRS> loadStaffs(Object body) {
        return RestCore.given().url(API.Org.LOAD_STAFFS()).auth(token).body(body, LoadStaffsRQ.class).post().send()
                .extractAsModels("data.list", StaffRS.class );
    }


    public Response accessStaff(Object body) {
        return RestCore.given().url(API.Org.ACCESS_STAFF()).auth(token)
                .bodyEncrypt(body, AccessStaffRQ.class).post().send();
    }

    public Response addStaffs(Object body) {
        return RestCore.given().url(API.Org.ADD_STAFFS()).auth(token)
                .body(body, AddStaffsRQ.class).post().send();
    }

    public Response removeStaffs(Object body) {
        return RestCore.given().url(API.Org.REMOVE_STAFFS()).auth(token).body(body, RemoveStaffsRQ.class).delete().send();
    }

    public Response setOrgManager(Object body) {
        return RestCore.given().url(API.Org.SET_ORG_MANAGER()).auth(token).body(body, SetOrgManagerRQ.class).put().send();
    }


    public Response setOrgAdmin(Object body) {
        return RestCore.given().url(API.Org.SET_ORG_ADMIN()).auth(token).body(body, SetOrgAdminRQ.class).put().send();
    }


    public Response setTelemedicine(Object body) {
        return RestCore.given().url(API.Org.TELEMEDICINE_DOCTOR()).auth(token).body(body, SetTelemedicineRQ.class).put().send();
    }


    public Response setProvider(Object body) {
        return RestCore.given().url(API.Org.PROVIDER()).auth(token).body(body, SetProviderRQ.class).put().send();
    }

    public List<Specialty> getSpecialties(Object body) {
        return RestCore.given().url(API.Org.GET_SPECIALTIES()).auth(token)
                .params(body, GetSpecialtiesRQ.class).get().send()
                .extractAsModels("data.specialties", Specialty.class);
    }

    public Response getOrgAttributes(Object body) {
        return RestCore.given().url(API.Org.GET_ORG_ATTRIBUTES()).auth(token)
                .params(body, OrgSig.class).get().send();
    }

    public OrgRS getOrgInfo(Object body) {
        return RestCore.given().url(API.Org.GET_ORG_INFO()).auth(token)
                .params(body, OrgSig.class).get().send()
                .extractAsModel("data.orgInfo", OrgRS.class);
    }


    public Response loadRating(Object body) {
        return RestCore.given().url(API.Org.LOAD_RATING()).auth(token).body(body, LoadRatingRQ.class).post().send();
    }

    public Response defaultCover(Object body) {
        return RestCore.given().url(API.Org.DEFAULT_COVER()).auth(token).body(body, DefaultCoverRQ.class).post().send();
    }


    public List<LoadDepartmentRS> loadDepartment(Object body) {
        return RestCore.given().url(API.Org.LOAD_DEPARTMENTS()).auth(token)
                .body(body, LoadDepartmentsRQ.class).post().send()
                .extractAsModels("data.list", LoadDepartmentRS.class );
    }

    public Response updateOrgType(Object body) {
        return RestCore.given().url(API.Org.UPDATE_ORG_TYPE()).auth(token).body(body, UpdateOrgTypeRQ.class).put().send();
    }

    public Response updateIntro(Object body) {
        return RestCore.given().url(API.Org.UPDATE_INTRO()).auth(token).body(body, UpdateInfoRQ.class).post().send();
    }

    public Response updateOrgInfo(Object body) {
        return RestCore.given().url(API.Org.UPDATE_ORG_INFO()).auth(token).body(body, UpdateOrgInfoRQ.class).put().send();
    }

    public Response addDepartment(Object body) {
        return RestCore.given().url(API.Org.ADD_DEPARTMENT()).auth(token).body(body,
                AddDepartmentRQ.class).post().send();
    }

    public Response removeDepartment(Object body) {
        return RestCore.given().url(API.Org.REMOVE_DEPARTMENT()).auth(token)
                .body(body, RemoveDepartmentRQ.class).delete().send();
    }

    public Response moveStaffs(Object body) {
        return RestCore.given().url(API.Org.MOVE_STAFFS()).auth(token)
                .body(body, MoveStaffsRQ.class).put().send();
    }

    public Response hideStaffs(Object body) {
        return RestCore.given().url(API.Org.SET_HIDE_STAFFS()).auth(token)
                .body(body, HideStaffsRQ.class).put().send();
    }


    public Response refreshApiKey(OrgSig body) {
        return RestCore.given().url(API.Org.REFRESH_API_KEY()).auth(token).params(body, OrgSig.class).put().send();
    }

    public Response cover(Object body) {
        return RestCore.given().url(API.Org.COVER()).auth(token).multipart(body, CoverRQ.class).post().send();
    }


    public Response newDoctor(Object body) {
        return RestCore.given().url(API.Org.NEW_DOCTOR()).auth(token)
                .body(body, NewDoctorRQ.class).post().send();
    }

    public Response pwdDoctor(Object body) {
        return RestCore.given().url(API.Org.PWD_DOCTOR()).auth(token).body(body, PwdDoctorRQ.class).put().send();
    }

    public Response uiType(Object body) {
        return RestCore.given().url(API.Org.UI_TYPE()).auth(token).body(body, UITypeRQ.class).put().send();

    }

    public Response featureAttributes(Object body) {
        return RestCore.given().url(API.Org.FEATURE_ATTRIBUTES()).auth(token).params(body, OrgSig.class).get().send();

    }

    public QRCode qrCode(Object body) {
        return RestCore.given().url(API.Org.QR_CODE()).auth(token).params(body, OrgSig.class).get().send()
                .extractAsModel("data", QRCode.class);
    }

    public Response staffFeatureAttributes(Object body) {
        return RestCore.given().url(API.Org.STAFF_FEATURE_ATTRIBUTES()).auth(token)
                .body(body, StaffFeatureAttributesRQ.class).put().send();
    }

    public Response checkPickUpChat(Object body) {
        return RestCore.given().url(API.Org.CHECK_PICK_UP_CHAT()).auth(token)
                .bodyEncrypt(body, CheckPickUpChatRQ.class).post().send();
    }

    public Response deliveryTypes(Object body) {
        return RestCore.given().url(API.Order.DELIVERY_TYPES()).auth(token).bodyEncrypt(body, OrgSig.class).post().send();
    }


    public Response setCurrency(Object body) {
        return RestCore.given().url(API.Org.CURRENCY()).auth(token).body(body, CurrencyRQ.class).put().send();
    }


    public Response orgFeatureAttributeForStaff(Object body) {
        return RestCore.given().url(API.Org.ORG_FEATURE_ATTRIBUTE_FOR_STAFF()).auth(token).bodyEncrypt(body, OrgSig.class).post().send();
    }

    public Response consultConfig(Object body) {
        return RestCore.given().url(API.Org.CONSULT_CONFIG()).auth(token).bodyEncrypt(body, ConsultConfigRQ.class).put().send();
    }


    public Response consultConfigValue(Object body) {
        return RestCore.given().url(API.Org.CONSULT_CONFIG_VALUE()).auth(token).bodyEncrypt(body, ConsultConfigValueRQ.class).post().send();
    }

    public Response paymentTypes(Object body) {
        return RestCore.given().url(API.Org.PAYMENT_TYPES()).auth(token).bodyEncrypt(body, OrgSig.class).post().send();
    }

    public Response userTrialAdd(Object body) {
        return RestCore.given().url(API.Org.USER_TRIAL_ADD()).auth(token).bodyEncrypt(body, UserTrialAddRQ.class).put().send();
    }

    public Response userTrialRemove(Object body) {
        return RestCore.given().url(API.Org.USER_TRIAL_REMOVE()).auth(token).bodyEncrypt(body, UserTrialAddRQ.class).put().send();
    }

}
