package com.apis.globedr.model.request.userManager;

import com.apis.globedr.enums.UserFeatureAttributes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeUserFeatureAttributeRQ {
    private List<String> userSigList;
    private Integer userFeatureAttributes;


    public void setUserFeatureAttributes(Integer userFeatureAttributes) {
        this.userFeatureAttributes = UserFeatureAttributes.value(userFeatureAttributes);
    }
}
