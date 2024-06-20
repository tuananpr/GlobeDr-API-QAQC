package com.apis.globedr.model.response.org;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoadDepartmentRS {
    private Integer deptId;
    private String name;
    private Integer countStaff;
}
