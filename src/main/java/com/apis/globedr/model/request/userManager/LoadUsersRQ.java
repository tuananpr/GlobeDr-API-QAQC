package com.apis.globedr.model.request.userManager;

import com.apis.globedr.enums.UserStatus;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadUsersRQ {

    private Integer userType;
    private Integer userId;
    private Integer userStatus;
    private Integer language;
    private Integer gender;
    private String country;
    private String city;
    private String region;
    private String name;
    private String gdrLogin;
    private String address;
    private boolean azureDB;
    private Boolean verified;
    @JsonUnwrapped
    Page page;


    public void setUserStatus(Object info) {
        this.userStatus = UserStatus.value(info);
    }
}
