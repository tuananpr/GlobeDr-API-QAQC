package com.apis.globedr.model.step.org;


import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.response.other.CountryRS;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class OrgMS {

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
    @Builder.Default
    private List<String> specialties = new ArrayList<>();
    @Builder.Default
    private List<String> specialtyCodes = new ArrayList<>();
    @Builder.Default
    private List<String> owner = new ArrayList<>();
    @Builder.Default
    private List<String> admin = new ArrayList<>();
    @Builder.Default
    private List<String> doctor = new ArrayList<>();
    @Builder.Default
    private List<String> doctorTelemedicine = new ArrayList<>();
    @Builder.Default
    private List<String> staff = new ArrayList<>();


    private Integer type;
    private Integer appointmentType;
    private String orgUIType;


    @JsonUnwrapped
    Page page;


    District district;
    Ward ward;
    String country;
    String city;

    @JsonUnwrapped
    ImageFile file;


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

    public OrgMS setOrgFeatureAttributes(Object info) {
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

    public OrgMS setCurrency(Object info) {
        currency = Currency.value(info);
        return this;
    }

    public OrgMS setStatus(Object value) {
        this.status = OrgStatus.value(value);
        return this;
    }

    public OrgMS setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }

    public OrgMS setAppointmentType(Object info) {
        appointmentType = AppointmentType.value(info);
        return this;
    }

    public OrgMS setType(Object info) {
        type = OrgType.value(info);
        return this;
    }

    public OrgMS setLanguage(Object info) {
        language = Language.value(info);
        return this;
    }

    public OrgMS setSpecialties(Object info) {
        if (info instanceof String) {
            specialties = Arrays.asList(((String) info).split(","));
        } else {
            specialties = (List<String>) info;
        }
        return this;
    }


    public OrgMS setStaff(Object info) {
        if (info instanceof String) {
            staff = Arrays.asList(((String) info).split(","));
        } else {
            staff = (List<String>) info;
        }
        return this;
    }


    public OrgMS setOwner(Object info) {
        if (info instanceof String) {
            owner = Arrays.asList(((String) info).split(","));
        } else {
            owner = (List<String>) info;
        }
        return this;
    }

    public OrgMS setAdmin(Object info) {
        if (info instanceof String) {
            admin = Arrays.asList(((String) info).split(","));
        } else {
            admin = (List<String>) info;
        }
        return this;
    }

    public OrgMS setDoctor(Object info) {
        if (info instanceof String) {
            doctor = Arrays.asList(((String) info).split(","));
        } else {
            doctor = (List<String>) info;
        }
        return this;
    }

    public OrgMS setDoctorTelemedicine(Object info) {
        if (info instanceof String) {
            doctorTelemedicine = Arrays.asList(((String) info).split(","));
        } else {
            doctorTelemedicine = (List<String>) info;
        }
        return this;
    }

    @JsonProperty("phones")
    public OrgMS setPhones(Object object) {
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
