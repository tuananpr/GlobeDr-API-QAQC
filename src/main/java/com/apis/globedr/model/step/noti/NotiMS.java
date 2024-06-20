package com.apis.globedr.model.step.noti;

import com.apis.globedr.enums.NotiGroup;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotiMS {
    private Integer groupType;
    private String message;

    @JsonUnwrapped
    Page page;
    public void setGroupType(Object info){
        groupType = NotiGroup.value(info);
    }

}
