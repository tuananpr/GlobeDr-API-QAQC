package com.apis.globedr.model.request.userManager;

import com.apis.globedr.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserStatusRQ {
    private List<String> userSigList;
    @JsonAlias({"changeUserStatus"})
    private Integer userStatus;

    public void setUserStatus(Object info) {
        this.userStatus = UserStatus.value(info);
    }
}
