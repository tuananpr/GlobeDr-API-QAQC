package com.apis.globedr.services.database.entities;

public class User {

    private Integer userId;
    private Integer UserType;
    private String displayName;
    private String gdrLogin;
    private String phone;
    private String country;
    private String dob;
    private String gender;
    private String email;

    public User(Integer userId, Integer userType, String displayName, String gdrLogin, String phone, String country) {
        this.userId = userId;
        UserType = userType;
        this.displayName = displayName;
        this.gdrLogin = gdrLogin;
        this.phone = phone;
        this.country = country;
    }



    public User(){}

    public User(String phone, String country){
        this.setPhone(phone);
        this.setCountry(country);
        this.setGdrLogin(convertFullPhone(phone, country));
    }


    public String convertFullPhone(String numberPhone, String countryCode) {

        String code = "";
        countryCode = countryCode.replace("+", "");
        switch (countryCode.toUpperCase()) {
            case "VN":
                code = "84";
                break;
            case "US":
                code = "1";
                break;
            default:
                break;
        }
        if (numberPhone.substring(0, 1).equalsIgnoreCase("0")) {
            return code + numberPhone.substring(1);
        }
        return code + numberPhone;
    }

    @Override
    public String toString() {
        String s = String.format("%d %d %s %s %s %s", getUserId(), getUserType(), getDisplayName(), getGdrLogin(), getPhone(), getCountry());
        System.out.println(s);
        return s;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(Integer userType) {
        UserType = userType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGdrLogin() {
        return gdrLogin;
    }

    public void setGdrLogin(String gdrLogin) {
        this.gdrLogin = gdrLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
