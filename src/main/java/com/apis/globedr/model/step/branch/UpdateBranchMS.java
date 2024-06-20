package com.apis.globedr.model.step.branch;

import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.step.org.OrgMS;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class UpdateBranchMS {

    @JsonAlias({"orgName", "name"})
    private String name;

    private Integer zipCode;
    private String website;
    private String address;

    private Double latitude;
    private Double longitude;
    private Integer status;
    private String fax;
    private String email;
    private Integer orgAttribute;
    private Integer orgFeatureAttributes;
    private String orgSig;
    private Integer currency;
    private Integer imgId;
    private Integer orgId;
    private String intro;
    private Integer language;
    private Integer maxCustomerCare;
    private Boolean callRoundRobin;
    private String workHours;

    private Boolean isFollow;
    private List<Phone> phones;
    private List<String> specialties = new ArrayList<>();
    private List<String> specialtyCodes = new ArrayList<>();

    private Integer type;
    private Integer appointmentType;
    private String orgUIType;


    @JsonUnwrapped
    Page page;


    District district;
    Ward ward;
    Country country;
    City city;

    @JsonUnwrapped
    ImageFile file;

    private String parentOrgSig;
    private String tokenCaptcha;
    private String orgName;

    public void setDistrictName(String value) {
        if (this.district == null) this.district = new District();
        this.district.setName(value);
    }

    public void setWardName(String value) {
        if (this.ward == null) this.ward = new Ward();
        this.ward.setName(value);
    }


    public String getDistrictName() {
        if (this.district == null) return null;
        return this.district.getName();
    }

    public String getWardName() {
        if (this.ward == null) return null;
        return this.ward.getName();
    }


    public void setOrgAttribute(Object info) {
        if (info instanceof String) {
            orgAttribute = OrgAttributes.convert(Arrays.asList(((String) info).split(",")));
        }

        if (info instanceof Integer) {
            orgAttribute = (Integer) info;
        }

    }

    public UpdateBranchMS setOrgFeatureAttributes(Object info) {
        if (info instanceof String) {
            orgFeatureAttributes = OrgFeatureAttributes.convert(Arrays.asList(((String) info).split(",")));
            return this;
        }

        if (info instanceof Integer) {
            orgFeatureAttributes = (Integer) info;
            return this;
        }

        Assert.fail("Set value for this case");
        return this;
    }

    public UpdateBranchMS setCurrency(Object info) {
        currency = Currency.value(info);
        return this;
    }

    public UpdateBranchMS setStatus(Object value) {
        this.status = OrgStatus.value(value);
        return this;
    }

    public UpdateBranchMS setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }

    public UpdateBranchMS setAppointmentType(Object info) {
        appointmentType = AppointmentType.value(info);
        return this;
    }

    public UpdateBranchMS setType(Object info) {
        type = OrgType.value(info);
        return this;
    }

    public UpdateBranchMS setLanguage(Object info) {
        language = Language.value(info);
        return this;
    }

    public UpdateBranchMS setSpecialties(Object info) {
        if (info instanceof String) {
            specialties = Arrays.asList(((String) info).split(","));
        } else {
            specialties = (List<String>) info;
        }
        return this;
    }


    @JsonProperty("phones")
    public UpdateBranchMS setPhones(Object object) {
        if (object instanceof String) {
            try {
                phones = new ObjectMapper().readValue(object.toString(), new TypeReference<List<Phone>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            phones = (List<Phone>) object;
        }
        return this;
    }
}
