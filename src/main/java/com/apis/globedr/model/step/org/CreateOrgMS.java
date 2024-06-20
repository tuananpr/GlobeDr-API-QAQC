package com.apis.globedr.model.step.org;


import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
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
public class CreateOrgMS {

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
    Country country;
    City city;

    @JsonUnwrapped
    ImageFile file;


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

    public CreateOrgMS setOrgFeatureAttributes(Object info) {
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

    public CreateOrgMS setCurrency(Object info) {
        currency = Currency.value(info);
        return this;
    }

    public CreateOrgMS setStatus(Object value) {
        this.status = OrgStatus.value(value);
        return this;
    }

    public CreateOrgMS setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }

    public CreateOrgMS setAppointmentType(Object info) {
        appointmentType = AppointmentType.value(info);
        return this;
    }

    public CreateOrgMS setType(Object info) {
        type = OrgType.value(info);
        return this;
    }

    public CreateOrgMS setLanguage(Object info) {
        language = Language.value(info);
        return this;
    }

    public CreateOrgMS setSpecialties(Object info) {
        if (info instanceof String) {
            specialties = Arrays.asList(((String) info).split(","));
        } else {
            specialties = (List<String>) info;
        }
        return this;
    }


    public CreateOrgMS setStaff(Object info) {
        if (info instanceof String) {
            staff = Arrays.asList(((String) info).split(","));
        } else {
            staff = (List<String>) info;
        }
        return this;
    }


    public CreateOrgMS setOwner(Object info) {
        if (info instanceof String) {
            owner = Arrays.asList(((String) info).split(","));
        } else {
            owner = (List<String>) info;
        }
        return this;
    }

    public CreateOrgMS setAdmin(Object info) {
        if (info instanceof String) {
            admin = Arrays.asList(((String) info).split(","));
        } else {
            admin = (List<String>) info;
        }
        return this;
    }

    public CreateOrgMS setDoctor(Object info) {
        if (info instanceof String) {
            doctor = Arrays.asList(((String) info).split(","));
        } else {
            doctor = (List<String>) info;
        }
        return this;
    }

    public CreateOrgMS setDoctorTelemedicine(Object info) {
        if (info instanceof String) {
            doctorTelemedicine = Arrays.asList(((String) info).split(","));
        } else {
            doctorTelemedicine = (List<String>) info;
        }
        return this;
    }

    @JsonProperty("phones")
    public CreateOrgMS setPhones(Object object) {
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


    public List<String> allStaff() {
        List<String> staffs = new ArrayList<>();
        if (getOwner() != null) staffs.addAll(getOwner());
        if (getAdmin() != null) staffs.addAll(getAdmin());
        if (getDoctor() != null) staffs.addAll(getDoctor());
        if (getDoctorTelemedicine() != null) staffs.addAll(getDoctorTelemedicine());
        if (getStaff() != null) staffs.addAll(getStaff());
        return staffs;
    }

    static public CreateOrgMS initDetault(String name) {
        //520 Cách Mạng Tháng Tám, Quận 3, Thành phố Hồ Chí Minh
        //10.785729039069945, 106.66766245536387
        //"520 CMT8, Quan 3, Tp.HCM"
        CreateOrgMS org = new CreateOrgMS();

        org.setZipCode(68437);
        org.setStatus(OrgStatus.Active.value());
        org.setType(OrgType.Hospital.value());
        org.setWebsite("www.globedr.com");
        org.setAddress("520 CMT8, Quan 3, Tp.HCM");
//        org.setCountry("VN");
//        org.setCity("HCM");
        org.setCountry(new Country("VN", "Việt Nam", "84"));
        org.setCity(new City("HCM", "Hồ Chí Minh"));
        org.setDistrict(new District("1444", "Quận 3"));
        org.setWard(new Ward("20311", "Phường 11"));
        org.setLatitude(10.785729039069945);
        org.setLongitude(106.66766245536387);
        org.setName(name);
        org.setFax("13235551234");
        org.setEmail("info@globedr.com.vn");
        return org;
    }


    public void updateDefaultForNullField() {
        //520 Cách Mạng Tháng Tám, Quận 3, Thành phố Hồ Chí Minh
        //10.785729039069945, 106.66766245536387
        //"520 CMT8, Quan 3, Tp.HCM"


        if (getZipCode() == null) this.setZipCode(68437);
        if (getStatus() == null) this.setStatus(OrgStatus.Active.value());
        if (getType() == null) this.setType(OrgType.Hospital.value());
        if (getWebsite() == null) this.setWebsite("www.globedr.com");
        if (getAddress() == null) this.setAddress("520 CMT8, Quan 3, Tp.HCM");
        //if (getCountry() == null) this.setCountry("VN");
        if (getCountry() == null) this.setCountry(new Country("VN", "Việt Nam", "84"));
        if (getCity() == null) this.setCity(new City("HCM", "Hồ Chí Minh"));
        //if (getCity() == null) this.setCity("HCM");
        if (getDistrict() == null) this.setDistrict(new District("1444", "Quận 3"));
        if (getWard() == null) this.setWard(new Ward("20311", "Phường 11"));
        if (getLatitude() == null) this.setLatitude(10.785729039069945);
        if (getLongitude() == null) this.setLongitude(106.66766245536387);
        if (getFax() == null) this.setFax("13235551234");
        if (getEmail() == null) this.setEmail("info@globedr.com.vn");

    }

    private <T> T mapping(Object fromValue, Class<T> toClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(fromValue, toClass);
    }


}
