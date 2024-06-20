package com.apis.globedr.business;

import com.apis.globedr.apis.OrgApi;
import com.apis.globedr.apis.UserApi;
import com.apis.globedr.enums.NotiGroup;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.general.UserSig;
import com.apis.globedr.model.general.QRCode;
import com.apis.globedr.model.request.account.UpdateRequireInfoRQ;
import com.apis.globedr.model.request.user.*;
import com.apis.globedr.model.response.noti.NotificationsRS;
import com.apis.globedr.model.response.other.Specialty;
import com.apis.globedr.model.response.user.*;
import com.apis.globedr.model.step.noti.NotiMS;
import com.apis.globedr.model.step.org.JoinOrgMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.model.step.user.UserMS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.response.Response;

import java.util.List;
import java.util.stream.Collectors;

public class UserBus {

    UserApi userApi = UserApi.getInstant();

    public QRCode getQRCodeOrg(String orgSig) {
        return OrgApi.getInstant().qrCode(new OrgSig(orgSig));
    }

    public QRCode getQRCode(String userSig) {
        return userApi.qrCode(new UserSig(userSig));
    }

    public QRCodeInfoRS qrCodeScan(QRCodeScanRQ body) {
        return userApi.qrCodeScan(body);
    }

    public QRCodeInfoRS loadInfoQRCode(QRCodeScanRQ body) {
        return userApi.qrCodeGetDetail(body);
    }

    public Response joinOrg(JoinOrgMS body) {
        return userApi.requestJoinOrgs(body);
    }

    public Response leaveOrg(OrgMS body) {
        return userApi.removeJoinedOrg(body);
    }

    public List<LoadJoinedOrgsRS> loadJoinedOrgs(OrgMS body) {
        return userApi.loadJoinedOrgs(body);
    }

    public List<NotificationsRS> getListNoti(NotiMS body) {
        Response rs = userApi.getListNoti(body);
        if (body.getGroupType() != null && body.getGroupType().equals(NotiGroup.All.value())) {
            List<NotiGroupsRS> result = rs.extractAsModels("data.notiGroups", NotiGroupsRS.class);
            return result.stream().flatMap(group -> group.getList().stream()).collect(Collectors.toList());
        }

        return rs.extractAsModels("data.list", NotificationsRS.class );
    }


    public NotificationsRS getNotification(NotiMS body) {
        return getListNoti(body).stream()
                .filter(body.getMessage() != null ? n -> n.getMessage().contains(body.getMessage()) : n -> true)
                .findFirst().orElse(null);
    }


    public String getObjectSig(NotiMS body) {
        NotificationsRS noti = getNotification(body);

        assert noti != null;
        return noti.getObjectSig();
    }


    public void acceptJoinOrg() {
        loadJoinedOrgs(new OrgMS()).forEach(o -> userApi.acceptJoinOrg(o.getOrg().getOrgSig()));
    }

    public void declineJoinOrg() {
        loadJoinedOrgs(new OrgMS()).forEach(o -> userApi.declineJoinOrg(o.getOrg().getOrgSig()));
    }

    public Response getUserInfo(String userSig) {
        return userApi.getUserInfo(userSig);
    }

    public List<Specialty> loadSpecialties(GetSpecialtiesRQ body) {
        return userApi.getSpecialties(body);
    }

    public Response removeSpecialties(List<String> codes) {
        return userApi.removeSpecialties(codes);
    }

    public List<Specialty> loadSpecialties(UserMS body) {
        return userApi.getSpecialties(body);
    }

    public Response removeSpecialties(UserMS body) {
        List<String> codes = loadSpecialties(body).stream()
                .filter(s -> body.getSpecialtiesName().contains(s.getName()))
                .map(s -> s.getCode()).collect(Collectors.toList());
        return removeSpecialties(codes);
    }

    public Response feedback(FeedbackRQ body) {
        return userApi.feedback(body);
    }

    public DoctorInfoRS userInfo(UserInfoRQ body) {
        return userApi.userInfo(body);
    }

    public Response loadUserBioAsResponse(LoadUserBioRQ body) {
        return userApi.loadUserBio(body);
    }

    public UserBioRS loadUserBio(LoadUserBioRQ body) {
        return userApi.loadUserBio(body).extractAsModel("data", UserBioRS.class);
    }

    public Response loadUserBioAsResponse(Integer forLanguage) {
        return loadUserBioAsResponse(LoadUserBioRQ.builder().forLanguage(forLanguage).build());
    }

    public Response addUserBio(AddUserBioRQ body) {
        return userApi.addUserBio(body);
    }

    public Response updateUserBio(AddUserBioRQ body) {
        String bioSig = loadUserBioAsResponse(body.getForLanguage())
                .extractAsList(String.format("$..[?(@.type == %d)].bioSig", body.getType())).get(0);
        body.setBioSig(bioSig);
        return userApi.updateUserBio(body);
    }

    public Response removeUserBio(AddUserBioRQ body) {
        String bioSig = loadUserBioAsResponse(body.getForLanguage())
                .extractAsList(String.format("$..[?(@.type == %d)].bioSig", body.getType())).get(0);
        return userApi.removeUserBio(bioSig);
    }

    public Response uploadCertificate(UploadCertificateRQ body) {
        return userApi.uploadCertificate(body);
    }

    public void removeCertificate(LoadUserBioRQ body) {
        loadUserBio(body).getCertificateList().forEach(doc ->
                userApi.removeCertificate(doc.getDocSig()));
    }

    public Response updateRequireInfo(UpdateRequireInfoRQ body) {
        return userApi.updateRequireInfo(body);
    }

    public Response pageDashBoards(PageDashBoardsRQ body) {
        return userApi.pageDashBoards(body);
    }

    public Response featuresOfPage(FeaturesOfPageRQ body) {
        return userApi.featuresOfPage(body);
    }
    public Response addSpecialties(AddSpecialtiesRQ body) {
        return userApi.addSpecialties(body);
    }

    public Response addSpecialties(List<String> codes) {
        AddSpecialtiesRQ body = AddSpecialtiesRQ.builder().specialtyCodes(codes).build();
        return addSpecialties(body);
    }
}
