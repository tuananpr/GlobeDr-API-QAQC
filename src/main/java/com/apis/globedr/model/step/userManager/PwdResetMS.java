package com.apis.globedr.model.step.userManager;


import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.apis.globedr.model.request.userManager.PwdResetRQ;
import com.apis.globedr.model.request.userManager.RemoveUserRQ;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PwdResetMS {
    @JsonUnwrapped
    LoadUsersRQ users;
    @JsonUnwrapped
    PwdResetRQ pwdReset;
}
