package com.apis.globedr.model.step.account;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.apis.globedr.services.config.Environment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpMS {

    private String country;
    private String password;
    private String tokenCaptcha;
    private String tokenCaptchaV3;
    private String gdrLogin;
    private String displayName;
    private String deviceId;
    private Integer userPlatform;
    private Integer language;
    private Integer gender;


    public void setDefaultDeviceId() {
        this.setDeviceId(new Environment().get("deviceId"));
    }

    public void setDefaultUserPlatform() {
        this.setUserPlatform(2);
    }


    public static String getDefaultDeviceId() {
        return new Environment().get("deviceId");
    }


}
