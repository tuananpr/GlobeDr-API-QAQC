package com.apis.globedr.model.response.order;

import com.apis.globedr.model.general.Geo;
import com.apis.globedr.model.general.Phone;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class MedicalOrgRS {
    private List<String> specialties;
    private List<String> orgs;
    private String name;
    private String avatar;

    private String sig;
    private String publicName;
    private String address;
    private String title;
    private Integer id;

    private Geo geo;
    private Integer gender;
    private String profession;
    private Integer connectionCount;
    private String orgAttributes;
    private Double kmDistance;
    private Double mileDistance;
    private Integer apptBeforeDays;
    private boolean hasVoucher;
    private Boolean isOrg;
    private Integer score;
    private List<Phone> phones;
    private Integer orgType;



}
