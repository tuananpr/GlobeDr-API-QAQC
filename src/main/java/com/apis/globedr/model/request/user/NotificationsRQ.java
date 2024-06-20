package com.apis.globedr.model.request.user;

import com.apis.globedr.enums.NotiGroup;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsRQ {
    private Integer groupType;

    @JsonUnwrapped
    Page page;
    public void setGroupType(Object info){
        groupType = NotiGroup.value(info);
    }
}
