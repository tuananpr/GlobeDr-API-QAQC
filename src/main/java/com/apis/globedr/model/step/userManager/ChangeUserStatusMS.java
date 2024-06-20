package com.apis.globedr.model.step.userManager;


import com.apis.globedr.enums.UserStatus;
import com.apis.globedr.model.request.userManager.ChangeUserStatusRQ;
import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.apis.globedr.model.request.userManager.VerifyUserRQ;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserStatusMS {
    @JsonUnwrapped
    LoadUsersRQ users;
    @JsonUnwrapped
    ChangeUserStatusRQ changeUserStatus;
}
