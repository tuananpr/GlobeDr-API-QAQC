package com.apis.globedr.model.step.userManager;


import com.apis.globedr.enums.UserFeatureAttributes;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFeatureAttributeMS {
    private String country;
    private String name;
    private String gdrLogin;

    private List<String> userSigList;
    private Integer userFeatureAttributes;

    @JsonUnwrapped
    Page page;

    public void setUserFeatureAttributes(Object value) {
        this.userFeatureAttributes = UserFeatureAttributes.value(value);
    }
}
