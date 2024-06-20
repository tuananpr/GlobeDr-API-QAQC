package com.apis.globedr.apis;

import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.general.UserSig;
import com.apis.globedr.model.request.account.UpdateRequireInfoRQ;
import com.apis.globedr.model.request.user.*;
import com.apis.globedr.model.response.other.Specialty;
import com.apis.globedr.model.general.QRCode;
import com.apis.globedr.model.response.user.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserApi extends BaseApi {

    private UserApi() {
    }

    private static UserApi instant;

    public static UserApi getInstant() {
        if (instant == null) instant = new UserApi();
        return instant;
    }

    public Response requestJoinOrgs(Object body) {
        return RestCore.given().url(API.User.REQUEST_JOIN_ORGS()).auth(token)
                .bodyEncrypt(body, OrgSig.class).post().send();
    }

    public List<LoadJoinedOrgsRS> loadJoinedOrgs(Object body) {
        return RestCore.given().url(API.User.LOAD_JOINED_ORGS()).auth(token)
                .bodyEncrypt(body, LoadJoinedOrgsRQ.class).post().send()
                .extractAsModels("data.list", LoadJoinedOrgsRS.class);
    }

    public Response removeJoinedOrg(Object body) {
        return RestCore.given().url(API.User.REMOVE_JOINED_ORGS()).auth(token).bodyEncrypt(body, OrgSig.class).delete().send();
    }

    public Response getListNoti(Object body) {
        return RestCore.given().url(API.Noti.NOTIFICATIONS()).auth(token)
                .bodyEncrypt(body, NotificationsRQ.class).post().send();
    }

    public Response acceptJoinOrg(String orgSig) {
        return RestCore.given().url(API.User.ACCEPT_JOIND_ORG()).auth(token).bodyEncrypt(new OrgSig(orgSig)).put().send();
    }

    public void declineJoinOrg(String orgSig) {
        RestCore.given().url(API.User.DECLINE_JOIND_ORG()).auth(token).bodyEncrypt(new OrgSig(orgSig)).put().send();
    }

    public QRCodeInfoRS qrCodeScan(Object body) {
        return RestCore.given().url(API.User.QR_CODE_SCAN()).auth(token)
                .bodyEncrypt(body, QRCodeScanRQ.class).post().send()
                .extractAsModel("data.info", QRCodeInfoRS.class);
    }

    public QRCodeInfoRS qrCodeGetDetail(Object body) {
        return RestCore.given().url(API.User.QR_CODE_GET_DETAIL()).auth(token)
                .bodyEncrypt(body, QRCodeScanRQ.class).post().send()
                .extractAsModel("data.info", QRCodeInfoRS.class);
    }

    public QRCode qrCode(Object body) {
        return RestCore.given().url(API.User.QR_CODE()).auth(token)
                .bodyEncrypt(body, UserSig.class).post().send()
                .extractAsModel("data", QRCode.class);
    }

    public Response getUserInfo(String userSig) {
        return RestCore.given().url(API.User.GET_USER_INFO()).auth(token).bodyEncrypt(new UserSig(userSig)).post().send();
    }

    public DoctorInfoRS userInfo(Object body) {
        return RestCore.given().url(API.User.USER_INFO()).auth(token)
                .bodyEncrypt(body, UserInfoRQ.class).post().send()
                .extractAsModel("data", DoctorInfoRS.class);
    }

    public Response feedback(Object body) {
        return RestCore.given().url(API.User.FEEDBACK()).auth(token).bodyEncrypt(body, FeedbackRQ.class).post().send();
    }

    public List<Specialty> getSpecialties(Object body) {
        return RestCore.given().url(API.User.GET_SPECIALTIES()).auth(token)
                .bodyEncrypt(body, GetSpecialtiesRQ.class).post().send()
                .extractAsModels("data.specialties", Specialty.class);
    }

    public Response addSpecialties(Object body) {
        return RestCore.given().url(API.User.ADD_SPECIALTIES()).auth(token).bodyEncrypt(body, AddSpecialtiesRQ.class).post().send();
    }

    public Response removeSpecialties(List<String> specialty) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.SPECIALTY_CODES, specialty);
        return RestCore.given().url(API.User.REMOVE_SPECIALTIES()).auth(token).bodyEncrypt(body).delete().send();
    }

    public Response loadUserBio(Object body) {
        return RestCore.given().url(API.User.LOAD_USER_BIO()).auth(token)
                .bodyEncrypt(body, LoadUserBioRQ.class).post().send();
    }


    public Response addUserBio(Object body) {
        return RestCore.given().url(API.User.ADD_USER_BIO()).auth(token)
                .bodyEncrypt(body, AddUserBioRQ.class)
                .post().send();
    }

    public Response updateUserBio(AddUserBioRQ body) {
        return RestCore.given().url(API.User.UPDATE_USER_BIO())
                .auth(token).bodyEncrypt(body, AddUserBioRQ.class)
                .put().send();
    }

    public Response removeUserBio(String bioSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.BIO_SIG, bioSig);
        return RestCore.given().url(API.User.REMOVE_USER_BIO()).auth(token).bodyEncrypt(body).delete().send();
    }


    public Response uploadCertificate(UploadCertificateRQ body) {
        return RestCore.given().url(API.User.UPLOAD_CERTIFICATE()).auth(token).multipart(body).post().send();
    }

    public Response removeCertificate(String docSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.DOC_SIG, docSig);
        return RestCore.given().url(API.User.REMOVE_CERTIFICATE()).auth(token).bodyEncrypt(body).delete().send();
    }


    public Response updateRequireInfo(Object body) {
        return RestCore.given().url(API.User.UPDATE_REQUIRE_INFO()).auth(token)
                .bodyEncrypt(body, UpdateRequireInfoRQ.class).put().send();
    }


    public Response pageDashBoards(PageDashBoardsRQ body) {
        return RestCore.given().url(API.User.PAGE_DASH_BOARDS()).auth(token)
                .bodyEncrypt(body, PageDashBoardsRQ.class).post().send();
    }

    public Response featuresOfPage(FeaturesOfPageRQ body) {
        return RestCore.given().url(API.User.FEATURES_OF_PAGE()).auth(token).bodyEncrypt(body).post().send();
    }


}
