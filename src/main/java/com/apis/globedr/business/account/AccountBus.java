package com.apis.globedr.business.account;

import com.apis.globedr.apis.AccountApi;
import com.apis.globedr.model.request.account.*;
import com.apis.globedr.model.response.account.*;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.account.SignUpMS;
import com.rest.core.response.Response;

public class AccountBus {
    AccountApi accountApi = AccountApi.getInstant();

    public RequireInfoRS requireInfo(RequireInfoRQ body) {
        return accountApi.requireInfo(body);
    }

    public Response getPersonalInfoWithoutRefreshToken() {
        return accountApi.getPersonalInfoWithoutRefreshToken();
    }
    public GetPersonalInfoRS getPersonalInfo() {
        return accountApi.getPersonalInfo();
    }

    public Response changePassword(ChangePasswordRQ body) {
        return accountApi.changePassword(body);
    }

    public SignInRS signin(AccountMS body) {
        return accountApi.signIn(body);
    }

    public Response signInInfo() {
        return accountApi.signInInfo();
    }


    public Response logout(AccountMS body) {
        return accountApi.logout(body);
    }


    public SignUpRS signup(AccountMS info) {
        SignupRQ body = new SignupRQ();
        body.setCountry(info.getCountryName());
        body.setPassword(info.getPassword());
        body.setLanguage(info.getLanguage());
        body.setGdrLogin(info.getGdrLogin());
        body.setTokenCaptchaV3(info.getTokenCaptchaV3());
        body.setDisplayName(info.getDisplayName());
        body.setDeviceId(info.getDeviceId());
        body.setUserPlatform(info.getUserPlatform());
        body.setLanguage(info.getLanguage());
        return accountApi.signup(body);
    }

    public SignUpRS signup(SignUpMS body) {
        return accountApi.signup(body);
    }

    public VerifySignUpRS verifySignUp(VerifySignUpRQ body) {
        return accountApi.verifySignUp(body);
    }


    public Response verifyPhone(UpdatePhoneRS info, String code) {
        VerifyPhoneRQ body = VerifyPhoneRQ
                .builder()
                .phone(info.getPhone())
                .accessKey(info.getAccessKey())
                .code(code)
                .build();
        return accountApi.verifyPhone(body);
    }


    public Response updatePersonalInfo(AccountMS body) {
        return accountApi.updatePersonalInfo(body);
    }

    public Response recoveryPassword(AccountMS body) {
        return accountApi.recoveryPassword(body);
    }

    public Response confirmRecoveryPassword(ConfirmRecoveryPasswordRQ body) {
        return accountApi.confirmRecoveryPassword(body);
    }

    public UpdateLanguageRS changeLanguage(AccountMS body) {
        return accountApi.changeLanguage(body);
    }

    public Response config() {
        return accountApi.config();
    }

    public Response uploadAvatar(AccountMS body) {
        return accountApi.uploadAvatar(body);
    }

    public Response updatePassword(UpdateNewPasswordRQ body) {
        return accountApi.updatePassword(body);
    }

    public UpdatePhoneRS updatePhone(UpdatePhoneRQ body) {
        return accountApi.updatePhone(body);
    }
    public Response sendVerifyCode(SendVerifyCodeRQ body) {
        return accountApi.sendVerifyCode(body);
    }
    public Response exitSystem(ExitSystemRQ body) {
        return accountApi.exitSystem(body);
    }


}
