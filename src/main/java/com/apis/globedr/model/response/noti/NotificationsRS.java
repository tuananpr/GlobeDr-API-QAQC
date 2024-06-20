package com.apis.globedr.model.response.noti;

import com.apis.globedr.model.general.Obj;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationsRS {
    @JsonAlias({"screen", "Screen"})
    private Integer screen;
    @JsonAlias({"type", "Type"})
    private Integer type;
    @JsonAlias({"notiId", "NotiId"})
    private Integer notiId;
    @JsonAlias({"actorType", "ActorType"})
    private Integer actorType;
    @JsonAlias({"notiSig", "NotiSig"})
    private String notiSig;
    @JsonAlias({"userName", "UserName"})
    private String userName;
    @JsonAlias({"userAvatar", "UserAvatar"})
    private String userAvatar;
    @JsonAlias({"message", "Message"})
    private String message;
    @JsonAlias({"url", "Url"})
    private String url;
    @JsonAlias({"onDate", "OnDate"})
    private String onDate;
    @JsonAlias({"obj1", "Obj1"})
    private Obj obj1;
    @JsonAlias({"obj2", "Obj2"})
    private Obj obj2;
    @JsonAlias({"obj3", "Obj3"})
    private Obj obj3;
    @JsonAlias({"obj4", "Obj4"})
    private Obj obj4;
    @JsonAlias({"type1", "Type1"})
    private String type1;
    @JsonAlias({"type2", "Type2"})
    private String type2;
    @JsonAlias({"type3", "Type3"})
    private String type3;
    @JsonAlias({"isLoad", "IsLoad"})
    private Boolean isLoad;
    @JsonAlias({"seen", "Seen"})
    private boolean seen;
    @JsonAlias({"deviceId", "DeviceId"})
    private String deviceId;

    public String getObjectSig(){
        if ( getObj1() != null ) return getObj1().getSig();
        if ( getObj2() != null ) return getObj2().getSig();
        if ( getObj3() != null ) return getObj3().getSig();
        return null;
    }
}
