package com.apis.globedr.model.step.account;

import com.apis.globedr.enums.Gender;
import com.apis.globedr.enums.Language;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.services.config.Environment;
import com.apis.globedr.model.general.City;
import com.apis.globedr.services.database.dao.UserDAO;
import com.apis.globedr.services.database.entities.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountMS {

    private String deviceId;
    private String gdrLogin;
    private String password;
    private Integer language;
    private Integer userPlatform;
    private String tokenCaptchaV3;
    private String avatar;
    private String sig;
    private String countryName;
    private String displayName;
    private String dob;
    private String email;
    private String address;

    private Double latitude;
    private Double longitude;

    private Integer gender;
    private String phone;
    private Integer carerType;
    private Double height;
    private Double weight;
    private String name;
    private Boolean telemedicine;
    private String country;
    //private Country country;
    private City city;
    private District district;
    private Ward ward;
    private String userSig;
    private String userSignature;
    private String insuranceCode;

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


    public String getCityName() {
        if (this.city == null) return null;
        return this.city.getName();
    }

    public void setCityName(String value) {
        if (this.city == null) this.city = new City();
        this.city.setName(value);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName.equalsIgnoreCase("[blank]") ? "" : displayName;
    }


    public String getUserSig() {
        if (userSig != null) return userSig;
        return userSignature;
    }

    public String getUserSignature() {
        if (userSig != null) return userSig;
        return userSignature;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static String randomPhone() {
        Random rand = new Random();
        int num1 = (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
        String phoneNumber = df3.format(num1) + df3.format(num2) + df4.format(num3);

        return phoneNumber;
    }

    private static AccountMS getAccount(String role) {
        Environment environment = new Environment();
        if (environment.get(role.toLowerCase() + ".username") != null) {
            return AccountMS.builder()
                    .deviceId(environment.get(role.toLowerCase() + ".deviceId"))
                    .gdrLogin(environment.get(role.toLowerCase() + ".username"))
                    .password(environment.get(role.toLowerCase() + ".password"))
                    .language(Integer.parseInt(environment.get(role.toLowerCase() + ".language")))
                    .countryName(environment.get(role.toLowerCase() + ".country"))
                    .country(environment.get(role.toLowerCase() + ".country"))
                    .displayName(environment.get(role.toLowerCase() + ".displayName"))
                    .dob(environment.get(role.toLowerCase() + ".dob"))
                    .email(environment.get(role.toLowerCase() + ".email"))
                    .address(environment.get(role.toLowerCase() + ".address"))
                    .city(new City("HCM", "Hồ Chí Minh"))
                    .district(new District("1457", "Quận Phú Nhuận"))
                    .ward(new Ward("21710", "Phường 11"))
                    .longitude(Double.parseDouble(environment.get(role.toLowerCase() + ".longitude")))
                    .latitude(Double.parseDouble(environment.get(role.toLowerCase() + ".latitude")))
                    .gender(Integer.parseInt(environment.get(role.toLowerCase() + ".gender")))
                    .phone(environment.get(role.toLowerCase() + ".phone"))
                    .userPlatform(2)
                    .build();
        }

        return null;
    }

    public static AccountMS getNewAccount(String role) {
        Environment environment = new Environment();
        AccountMS existed = getAccount(role);
        if (existed != null){
            return existed;
        }else{
            String phone = randomPhone();
            return AccountMS.builder()
                    .deviceId(environment.get("deviceId"))
                    .gdrLogin(phone)
                    .password(environment.get("password"))
                    .language(Integer.parseInt(environment.get("language")))
                    .countryName("VN")
                    .country("VN")
                    .displayName(role)
                    .dob(environment.get("dob"))
                    .email(role + "@bacsitoancau.com")
                    .address(environment.get("address"))
                    .city(new City("HCM", "Hồ Chí Minh"))
                    .district(new District("1457", "Quận Phú Nhuận"))
                    .ward(new Ward("21710", "Phường 11"))
                    .longitude(Double.parseDouble(environment.get("longitude")))
                    .latitude(Double.parseDouble(environment.get("latitude")))
                    .gender(Integer.parseInt(environment.get("gender")))
                    .phone(phone)
                    .userPlatform(Integer.parseInt(environment.get("userPlatform")))
                    .build();
        }
    }

    public static AccountMS getExistedAccount(String role) {
        Environment environment = new Environment();
        AccountMS result = getAccount(role);

        if (result != null) {
            return result;
        }
        List<User> users = new UserDAO().getAllByDisplayName(role);
        if (users.size() == 0) return null;

        String gdrLogin = users.get(0).getGdrLogin();
        return AccountMS.builder()
                .deviceId(environment.get("deviceId"))
                .gdrLogin(gdrLogin)
                .password(environment.get("password"))
                .language(Integer.parseInt(environment.get("language")))
                .countryName("VN")
                .country("VN")
                //.country(new Country("VN", "Việt Nam", "84"))
                .displayName(role)
                .dob(environment.get("dob"))
                .email(role + "@bacsitoancau.com")
                .address(environment.get("address"))
                .city(new City("HCM", "Hồ Chí Minh"))
                .district(new District("1457", "Quận Phú Nhuận"))
                .ward(new Ward("21710", "Phường 11"))
                .longitude(Double.parseDouble(environment.get("longitude")))
                .latitude(Double.parseDouble(environment.get("latitude")))
                .gender(Integer.parseInt(environment.get("gender")))
                .phone(role)
                .userPlatform(Integer.parseInt(environment.get("userPlatform")))
                .build();

    }


    public void setDefaultDeviceId() {
        this.setDeviceId(new Environment().get("deviceId"));
    }

    public void setDefaultUserPlatform() {
        this.setUserPlatform(2);
    }


    public static String getDefaultDeviceId() {
        return new Environment().get("deviceId");
    }


    public String fullPhone() {
        String code = "";

        String country = (getCountryName() != null)? getCountryName() : getCountry();
        switch (country) {
            case "VN":
                code = "84";
                break;
            case "US":
                code = "1";
                break;
            default:
                break;
        }
        if (getGdrLogin().startsWith(code)) return getGdrLogin();
        if (getGdrLogin().substring(0, 1).equalsIgnoreCase("0")) return code + getGdrLogin().substring(1);
        return code + getGdrLogin();
    }


    public AccountMS setLanguage(Object value) {
        this.language = Language.value(value);
        return this;
    }


    public void setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
    }

    public void setGender(Object value) {
        this.gender = Gender.value(value);
    }
}
