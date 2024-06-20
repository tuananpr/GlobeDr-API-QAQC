package stepdefinition;

import com.apis.globedr.business.chat.OrgChatBus;
import com.apis.globedr.business.chat.UserChatBus;
import com.apis.globedr.business.consult.ConsultDoctor;
import com.apis.globedr.business.consult.ConsultRecipient;
import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.business.telemedicine.TelemedicineBus;
import com.apis.globedr.enums.*;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Data;
import com.apis.globedr.helper.Wait;
import com.apis.globedr.model.request.telemedicine.TelemedicineDoctorsRQ;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.response.telemedicine.TelemedicineCallRS;
import com.apis.globedr.model.response.telemedicine.TelemedicineReceiveRS;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.apis.globedr.model.step.telemedicine.VideoCallMS;
import io.cucumber.java.en.And;

public class TelemedicineStep extends Data {

    TelemedicineBus telemedicineBus = new TelemedicineBus();
    UserSearchBus userSearchBus = new UserSearchBus();
    UserChatBus userChatBus = new UserChatBus();
    OrgBus orgBus = new OrgBus();
    OrgChatBus orgChatBus = new OrgChatBus();
    OtherBus otherBus = new OtherBus();
    TelemedicineCallRS telemedicineCallRS;
    TelemedicineReceiveRS telemedicineReceiveRS;
    ConsultRecipient consultDoctor = new ConsultDoctor();
    String userSigCaller;
    String linkSig;
    String userCall;

    @And("^I turn (on|off) telemedicine mode$")
    public void iTurnOnTelemedicineMode(String action) {
        VideoCallMS info = VideoCallMS.builder()
                .enableTelemedicine(action.equalsIgnoreCase("on"))
                .deviceId(AccountMS.getDefaultDeviceId())
                .build();
        telemedicineBus.mode(info);
    }

    @And("^I load doctors to call by specialty \"([^\"]*)\"$")
    public void iLoadListTelemedicineDoctorsWithSpecialty(String specialty) {
        TelemedicineDoctorsRQ body = TelemedicineDoctorsRQ.builder().specialties(otherBus.loadSpecialties(specialty)).build();
        telemedicineBus.doctors(body);
    }


    @And("^As user, I call video to doctor$")
    public void userCallToDoctor(VideoCallMS info) {
        info.setSpecialties(otherBus.loadSpecialties(info.getSpecialtyName()));
        info.setUserSigReceiver(telemedicineBus.getDoctorSigToCall(info));
        info.setVideoCallType(VideoCallType.Telemedicine);

        info.setSenderType(SenderType.User);
        info.setChatType(ChatType.CustomerCare);
        telemedicineCallRS = telemedicineBus.call(info);
        userCall = "user";
        
    }

    @And("^As user, I call video to customer care$")
    public void iCallToCustomerCare(VideoCallMS info) {
        info.setOrgSig(orgBus.getOrgInfo(userSearchBus.loadOrgsByName(info.getOrgName()).get(0).getSig()).getOrgSig());
        info.setUserSigReceiver(userChatBus.customerCares(info).get(0).getSig());
        info.setVideoCallType(VideoCallType.Chat);

        info.setSenderType(SenderType.User);
        info.setChatType(ChatType.CustomerCare);
        telemedicineCallRS = telemedicineBus.call(info);
        userCall = "user";
        
    }

    @And("^As user, I call video to org$")
    public void iCallToOrg(VideoCallMS info) {
        info.setOrgSig(orgBus.getOrgInfo(userSearchBus.loadOrgsByName(info.getOrgName()).get(0).getSig()).getOrgSig());
        info.setVideoCallType(VideoCallType.Chat);
        info.setSenderType(SenderType.User);
        info.setChatType(ChatType.CustomerCare);
        telemedicineCallRS = telemedicineBus.call(info);
        userCall = "user";

    }

    @And("^As doctor, I call video to user into consult")
    public void doctorCallToUser(ConsultMS info) {
        QuestionRS questionRS = consultDoctor.getQuestion(info);

        VideoCallMS videoCallMS = new VideoCallMS();
        videoCallMS.setUserSigReceiver(questionRS.getPatientSig());
        videoCallMS.setPostSig(questionRS.getPostSig());
        videoCallMS.setVideoCallType(VideoCallType.Telemedicine);
        videoCallMS.setSenderType(SenderType.Org);
        videoCallMS.setChatType(ChatType.CustomerCare);
        telemedicineCallRS = telemedicineBus.call(videoCallMS);
        userCall = "doctor";
        
    }

    @And("^As customer care, I call video to user into conversation$")
    public void customerCareCallToUser(ChatMS info) {
        info.setOrgSig(orgSig);
        info.setViewerSig(userSig);

        VideoCallMS videoCallMS = new VideoCallMS();
        videoCallMS.setOrgSig(orgSig);
        videoCallMS.setUserSigReceiver(orgChatBus.loadConversation(info).getUserSig());
        videoCallMS.setVideoCallType(VideoCallType.Chat);
        videoCallMS.setSenderType(SenderType.Org);
        videoCallMS.setChatType(ChatType.CustomerCare);
        telemedicineCallRS = telemedicineBus.call(videoCallMS);
        userCall = "customer care";
        
    }

    @And("As customer care, I receive video call from chat")
    public void iReceiveVideoCallFromChat() {
        telemedicineReceiveRS = telemedicineBus.receive(pusherTeleCall);
    }

    @And("As user, I receive video call from chat")
    public void userReceiveVideoCallFromChat() {
        telemedicineReceiveRS = telemedicineBus.receive(pusherTeleCall);
    }

    @And("As doctor, I receive video call from consult$")
    public void doctorReceiveVideoCall() {
        telemedicineReceiveRS = telemedicineBus.receive(pusherTeleCall);
    }

    @And("As user, I receive video call from consult$")
    public void iReceiveVideoCall() {
        telemedicineReceiveRS = telemedicineBus.receive(pusherTeleCall);
    }


    @And("^I recall video to doctor$")
    public void iReCallToDoctor(VideoCallMS info) {
        info.setPostSig(telemedicineCallRS.getPostSig());
        telemedicineCallRS = telemedicineBus.call(info);
    }

    private void busyVideoCall(VideoCallMS info) {
        info.setPostSig(pusherTeleCall.getFunctionSig())
                .setUserSigCaller(pusherTeleCall.getUserSigCaller())
                .setReceiverType(pusherTeleCall.getSenderType())
                .setVideoCallType(pusherTeleCall.getVideoCallType())
                .setChatType(pusherTeleCall.getChatType())
                .setChannelName(pusherTeleCall.getChanelNameOnObj());

    }

    @And("I busy video call from consult$")
    public void iBusyVideoCall() {
        telemedicineBus.busy(pusherTeleCall);
        Wait.seconds(5);
    }

    @And("I busy video call from chat$")
    public void iBusyVideoCallFromChat() {
        telemedicineBus.busy(pusherTeleCall);
        Wait.seconds(5);
    }


    @And("As (doctor|customer care|user), I end video call$")
    public void doctorEndVideoCall(String userEndCall, VideoCallMS info) {
        Wait.seconds(5);
        if (userCall.equalsIgnoreCase(userEndCall)) {
            info.setPostSig(telemedicineCallRS.getPostSig());
        } else {
            info.setPostSig(pusherTeleCall.getFunctionSig());
        }
        info.setRoomName(telemedicineReceiveRS.getRoomName());
        telemedicineBus.end(info);
        Wait.seconds(5);
    }


    @And("As user, I miss call from consult$")
    public void userMissCallFromDoctor() {
        VideoCallMS info = new VideoCallMS();
        info.setVideoCallType(VideoCallType.Telemedicine).setSenderType(SenderType.User);
        telemedicineBus.miss(info, telemedicineCallRS);
    }

    @And("As doctor, I miss call from consult$")
    public void doctorMissCallFromDoctor() {
        VideoCallMS info = new VideoCallMS();
        info.setVideoCallType(VideoCallType.Telemedicine).setSenderType(SenderType.Org);
        telemedicineBus.miss(info, telemedicineCallRS);
    }

    @And("As user, I miss call from customer care$")
    public void userMissCallFromCustomerCare() {
        VideoCallMS info = new VideoCallMS();
        info.setOrgSig(orgSig).setVideoCallType(VideoCallType.Chat).setSenderType(SenderType.User);
        telemedicineBus.miss(info, telemedicineCallRS);
    }

    @And("As customer care, I miss call from customer care$")
    public void customerCareMissCallFromCustomerCare() {
        VideoCallMS info = new VideoCallMS();
        info.setOrgSig(orgSig).setVideoCallType(VideoCallType.Chat).setSenderType(SenderType.Org);
        telemedicineBus.miss(info, telemedicineCallRS);
    }

}
