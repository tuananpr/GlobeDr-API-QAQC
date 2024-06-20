package com.apis.globedr.model.request.orgManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ContactOrgRQ {
    private String businessName;
    private Integer orgType;
    private String name;
    private String phone;
    private String email;
}
