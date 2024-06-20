package com.apis.globedr.model.response.noti;

import com.apis.globedr.model.response.noti.NotificationsRS;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PusherTeleMsg extends NotificationsRS {
    @JsonAlias({"accessToken", "AccessToken"})
    private String accessToken;
    @JsonAlias({"roomSID", "RoomSID"})
    private String roomSID;
    @JsonAlias({"roomName", "RoomName"})
    private String roomName;
    @JsonAlias({"postId", "PostId"})
    private String postId;
    @JsonAlias({"postSig", "PostSig"})
    private String postSig;
}
