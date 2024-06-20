package com.apis.globedr.model.step.userManager;


import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.apis.globedr.model.request.userManager.VerifyUserRQ;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class VerifyUserMS {
    @JsonUnwrapped
    LoadUsersRQ users;
    @JsonUnwrapped
    VerifyUserRQ verifyUser;
}
