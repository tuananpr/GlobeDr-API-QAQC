package com.apis.globedr.business.telemedicine;

import com.apis.globedr.apis.TelemedicineApi;
import com.apis.globedr.enums.ChatType;
import com.apis.globedr.enums.UserPlatform;
import com.apis.globedr.model.request.telemedicine.TelemedicineDoctorsRQ;
import com.apis.globedr.model.response.noti.PusherTeleCall;
import com.apis.globedr.model.response.telemedicine.TelemedicineCallRS;
import com.apis.globedr.model.response.telemedicine.CallRecipientRS;
import com.apis.globedr.model.response.telemedicine.TelemedicineReceiveRS;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.telemedicine.VideoCallMS;
import com.rest.core.response.Response;

import java.util.List;

public class TelemedicineBus {
    TelemedicineApi telemedicineApi = new TelemedicineApi();

    public List<CallRecipientRS> doctors() {
        return doctors(TelemedicineDoctorsRQ.builder().build());
    }


    public List<CallRecipientRS> doctors(TelemedicineDoctorsRQ body) {
        return telemedicineApi.doctors(body);
    }

    public List<CallRecipientRS> doctors(VideoCallMS body) {
        return telemedicineApi.doctors(body);
    }

    private VideoCallMS prepare(VideoCallMS body) {
        body.setDeviceId(AccountMS.getDefaultDeviceId());
        body.setWebPlatform(true);
        body.setUserPlatform(UserPlatform.Web);
        return body;
    }

    public TelemedicineCallRS call(VideoCallMS body) {
        return telemedicineApi.call(prepare(body));
    }

    public Response miss(VideoCallMS body, TelemedicineCallRS info) {
        body.setChatType(ChatType.CustomerCare)
                .setPostSig(info.getPostSig())
                .setUserSigReceiver(info.getUserSigReceiver())
                .setChannelName(info.getChanelName());
        return telemedicineApi.miss(prepare(body));
    }


    public Response end(VideoCallMS body) {
        return telemedicineApi.end(prepare(body));
    }

    public String getDoctorSigToCall(VideoCallMS info) {
        return doctors(info).stream()
                .filter(i -> i.getName().equalsIgnoreCase(info.getDoctorName()))
                .map(i -> i.getSig()).findFirst().orElse(null);
    }

    public TelemedicineReceiveRS receive(PusherTeleCall info) {
        VideoCallMS body = new VideoCallMS();
        body.setOrgSig(info.getOrgSig()) // Telemedicine -> orgSig = null,  Customer Care -> orgSig = not null
                .setPostSig(info.getFunctionSig()) // Telemedicine -> postSig = postSig,  Customer Care -> postSig = conversationSig
                .setUserSigCaller(info.getUserSigCaller()) // sig of sender
                .setReceiverType(info.getSenderType()) // Customer Care SenderType is Org, User SenderType is User
                .setVideoCallType(info.getVideoCallType()) // VideoCallType.Chat
                .setChatType(info.getChatType()) // ChatType.CustomerCare
                .setChannelName(info.getChanelNameOnObj());
        return receive(body);
    }

    public TelemedicineReceiveRS receive(VideoCallMS info) {
        return telemedicineApi.receive(prepare(info));
    }

    public Response busy(PusherTeleCall info) {
        VideoCallMS body = new VideoCallMS();
        body.setOrgSig(info.getOrgSig())
                .setPostSig(info.getFunctionSig())
                .setUserSigCaller(info.getUserSigCaller())
                .setReceiverType(info.getSenderType())
                .setVideoCallType(info.getVideoCallType())
                .setChatType(info.getChatType())
                .setChannelName(info.getChanelNameOnObj());
        return telemedicineApi.busy(prepare(body));
    }

    public Response mode(VideoCallMS body) {
        return telemedicineApi.mode(body);
    }

}
