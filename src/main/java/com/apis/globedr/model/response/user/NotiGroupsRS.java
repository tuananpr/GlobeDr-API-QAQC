package com.apis.globedr.model.response.user;

import com.apis.globedr.model.response.noti.NotificationsRS;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotiGroupsRS {
    private List<NotificationsRS> list;
    private Integer totalCount;
    private Integer pageSize;
    private Integer groupType;
    private String groupName;


}