package com.apis.globedr.apis;

import com.apis.globedr.helper.*;
import com.apis.globedr.model.request.account.*;
import com.apis.globedr.model.response.account.*;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;


public class AccountApi extends BaseApi {

    private AccountApi() {
    }

    private static AccountApi instant;

    public static AccountApi getInstant() {
        if (instant == null) instant = new AccountApi();
        return instant;
    }

    private <T extends AccountToken> void saveAuthen(T t) {
        token.save(t.getAccessToken(), t.getRefreshToken(), t.getTokenType());
    }

    private <T extends SignInRS>  void saveInfoUser(T t) {
        saveAuthen(t);
        Data.userSig = t.getUserSignature();
    }


    public Response logout(Object body) {
        Response rs = RestCore.given().url(API.Account.LOG_OUT()).auth(token).bodyEncrypt(body, LogoutRQ.class).post().send();
        token.clear();
        return rs;
    }


    public SignInRS signIn(Object body) {
        SignInRS rs = RestCore.given()
                .url(API.Account.SIGN_IN())
                .bodyEncrypt(body, SigninRQ.class)
                .post().send().extractAsModel("data", SignInRS.class);
        // Verify request will be succeeded
        if (Data.response.isSuccess()) {
            saveInfoUser(rs);
        }
        return rs;
    }

    public Response signInInfo() {
        Response rs = RestCore.given()
                .url(API.Account.SIGN_IN_INFO())
                .auth(token)
                .get().send();
        return rs;
    }

    public Response recoveryPassword(Object body) {
        return RestCore.given()
                .url(API.Account.RECOVERY_PASSWORD())
                .bodyEncrypt(body, RecoveryPasswordRQ.class)
                .post().send();
    }


    public Response confirmRecoveryPassword(Object body) {
        return RestCore.given().url(API.Account.CONFIRM_RECOVERY_PASSWORD()).auth(token)
                .bodyEncrypt(body, ConfirmRecoveryPasswordRQ.class).post().send();
    }

    public Response updatePassword(Object body) {
        return RestCore.given().url(API.Account.UPDATE_NEW_PASSWORD()).auth(token).bodyEncrypt(body, UpdateNewPasswordRQ.class).put().send();
    }

    public UpdatePhoneRS updatePhone(Object body) {
        return RestCore.given().url(API.Account.UPDATE_PHONE()).auth(token).body(body, UpdatePhoneRQ.class).put().send()
                .extractAsModel("data", UpdatePhoneRS.class);
    }

    public Response changePassword(Object body) {
        return RestCore.given().url(API.Account.CHANGE_PASSWORD()).auth(token).bodyEncrypt(body, ChangePasswordRQ.class).put().send();
    }

    public Response verifyPhone(Object body) {
        return RestCore.given().url(API.Account.VERIFY_PHONE()).auth(token).body(body, VerifyPhoneRQ.class).post().send();
    }

    public VerifySignUpRS verifySignUp(Object body) {
        VerifySignUpRS rs = RestCore.given().url(API.Account.VERIFY_SIGN_UP()).auth(token)
                .bodyEncrypt(body, VerifySignUpRQ.class)
                .post().send().extractAsModel("data", VerifySignUpRS.class);
        saveInfoUser(rs);
        return rs;
    }


    public Response sendVerifyCode(Object body) {
        return RestCore.given().url(API.Account.SEND_VERIFY_CODE()).auth(token).bodyEncrypt(body, SendVerifyCodeRQ.class).post().send();
    }

    public SignUpRS signup(Object body) {
        return RestCore.given()
                .url(API.Account.SIGN_UP())
                .bodyEncrypt(body, SignupRQ.class)
                .post().send()
                .extractAsModel("data", SignUpRS.class);
    }


    public UpdateLanguageRS changeLanguage(Object body) {
        UpdateLanguageRS rs = RestCore.given().url(API.Account.UPDATE_LANGUAGE()).auth(token).body(body).put().send()
                .extractAsModel("data", UpdateLanguageRS.class);
        if (rs.getAccessToken() != null) saveAuthen(rs);
        return rs;
    }

    public Response config() {
        return RestCore.given().url(API.User.CONFIG()).auth(token).get().send();
    }


    public Response uploadAvatar(Object body) {
        return RestCore.given().url(API.Account.UPLOAD_AVATAR()).auth(token).multipart(body, UploadAvatarRQ.class).post().send();
    }

    public GetPersonalInfoRS getPersonalInfo() {
        return RestCore.given().url(API.Account.GET_PERSONAL_INFO()).auth(token).get().send().extractAsModel("data", GetPersonalInfoRS.class);
    }

    public Response getPersonalInfoWithoutRefreshToken() {
        return RestCore.given().url(API.Account.GET_PERSONAL_INFO()).auth(token).get().sendWithoutRefreshToken();
    }


    public Response updatePersonalInfo(Object body) {
        return RestCore.given().url(API.Account.UPDATE_PERSONAL_INFO()).auth(token)
                .bodyEncrypt(body, UpdatePersonalInfoRQ.class).put().send();
    }


    public RequireInfoRS requireInfo(Object body) {
        return RestCore.given().url(API.User.REQUIRE_INFO()).auth(token)
                .bodyEncrypt(body, RequireInfoRQ.class).post().send()
                .extractAsModel("data", RequireInfoRS.class);
    }

    public Response exitSystem(Object body) {
        return RestCore.given().url(API.Account.EXIT_SYSTEM()).auth(token)
                .bodyEncrypt(body, ExitSystemRQ.class).post().send();
    }

}
