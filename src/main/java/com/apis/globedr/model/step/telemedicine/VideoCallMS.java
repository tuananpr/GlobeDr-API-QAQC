package com.apis.globedr.model.step.telemedicine;

import com.apis.globedr.enums.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class VideoCallMS {
    private String doctorName;
    private String userName;
    private String roomName;
    private String channelName;
    private String specialtyName;
    private List<String> specialties;
    private String userSigReceiver;
    private String userSigCaller;
    private String deviceId;
    private String postSig;
    private Boolean webPlatform;
    private Boolean enableTelemedicine;

    private Integer seconds;
    private Integer videoCallType;

    private String orgName;
    private String orgSig;
    private String customerCareName;
    private Integer chatType;
    private Integer senderType;
    private Integer receiverType;
    private Integer userPlatform;
    private boolean anotherCall;

    public VideoCallMS setChatType(Object value) {
        this.chatType = ChatType.value(value);
        return this;
    }

    public VideoCallMS setSenderType(Object value) {
        this.senderType = SenderType.value(value);
        return this;
    }

    public VideoCallMS setReceiverType(Object value) {
        this.receiverType = SenderType.value(value);
        return this;
    }

    public VideoCallMS setUserPlatform(Object value) {
        this.userPlatform = UserPlatform.value(value);
        return this;
    }

    public VideoCallMS setVideoCallType(Object value){
        this.videoCallType = VideoCallType.value(value);
        return this;
    }

}
