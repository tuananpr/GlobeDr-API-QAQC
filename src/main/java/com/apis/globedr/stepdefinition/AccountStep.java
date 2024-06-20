package com.apis.globedr.stepdefinition;

import com.apis.globedr.apis.UserManagerApi;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.userManager.UserManagerBus;
import com.apis.globedr.business.account.AccountBus;
import com.apis.globedr.enums.UserType;
import com.apis.globedr.enums.VerifyType;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.account.*;
import com.apis.globedr.model.response.account.SignUpRS;
import com.apis.globedr.model.response.account.UpdatePhoneRS;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.account.SignUpMS;
import com.apis.globedr.services.authorization.Token;
import com.apis.globedr.services.config.Environment;
import com.apis.globedr.constant.Text;
import com.apis.globedr.services.database.dao.UserVerificationDAO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;

public class AccountStep extends Data {

    UserVerificationDAO userVerificationDAO = new UserVerificationDAO();
    AccountBus accountBus = new AccountBus();

    UserManagerBus userManagerBus = new UserManagerBus();
    OtherBus otherBus = new OtherBus();
    private String tokenKicked;

    private AccountMS accountMS;
    private SignUpRS signUpRS;

    @And("^I login account on (\\d+) devices$")
    public void iLoginAccountOnDevices(int arg0) {
        String deviceIdIOS = "3m4ihc9y8TpMtaYXpT86t19irVWLMAjUXRoWoz0CrJcZ6VT5S4jFv9FHWQAxycUF";
        String deviceIdAndroid = "3m4ihc9y8TqrHyG6rHumwq3U0yJTDrHu6ohmasp5/us=";
        accountMS = AccountMS.getExistedAccount("user_1");

        accountMS.setDeviceId(deviceIdIOS);
        accountBus.signin(accountMS);
        tokenKicked = Token.getInstance().getAccessToken();

        accountMS.setDeviceId(deviceIdAndroid);
        accountBus.signin(accountMS);

    }


    @And("I check user information before use feature")
    public void iCheckUserInformationBeforeUseFeature(List<RequireInfoRQ> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            accountBus.requireInfo(info);
        });
    }

    @And("^I get personal information$")
    public void iGetPersonalInformation() {
        accountBus.getPersonalInfo();
    }


    @And("^I getting personal information with token (expired|kicked)$")
    public void iWaitForTokenExpredGetPersonalInformation(String type) {
        if (type.equalsIgnoreCase("expired")) {
            Token.getInstance().setAccessToken(tokenExpired);
        } else {
            Token.getInstance().setAccessToken(tokenKicked);
        }

        accountBus.getPersonalInfoWithoutRefreshToken();
    }

    @And("^I upload avatar$")
    public void iUploadAvatar(AccountMS info) {
        accountBus.uploadAvatar(info);
    }


    @And("^I confirm recovery password for user \"([^\"]*)\" and country \"([^\"]*)\" with (VALID|INVALID) code$")
    public void iConfirmRecoveryPasswordForUserAndCountryWithVALIDCode(String sdt, String country, String isValidCode) {
        ConfirmRecoveryPasswordRQ body = ConfirmRecoveryPasswordRQ.builder().userSignature(response.extract("data.userSignature")).build();
        if (isValidCode.equalsIgnoreCase("VALID")) {
            body.setCode(userVerificationDAO.get(Common.convertFullPhone(sdt, country), VerifyType.RecoveryPasswd.value()).getVerifyCode());
        } else {
            body.setCode("invalid");
        }

        accountBus.confirmRecoveryPassword(body);
    }

    @And("^I change language is (VN|US)$")
    public void iChangeLanguageIsVN(String language) {
        AccountMS info = AccountMS.builder().deviceId(AccountMS.getDefaultDeviceId()).build().setLanguage(language);
        accountBus.changeLanguage(info);
    }

    @When("^I load config$")
    public void iLoadConfig() {
        accountBus.config();
    }


    @When("^I recovery password$")
    public void recoveryPassword(AccountMS info) {
        accountBus.recoveryPassword(info);
    }

    @And("^I update new phone \"([^\"]*)\" with country \"([^\"]*)\" for loginId \"([^\"]*)\"(| and send valid code| and send invalid code)$")
    public void iUpdateNewPhoneWithCountryForUsernameAndSendValidCode(String phone, String country, String user, String sendCode) {
        UpdatePhoneRQ info = UpdatePhoneRQ.builder().country(country).phone(phone).build();
        UpdatePhoneRS rs = accountBus.updatePhone(info);
        String code = "";
        if (sendCode.toLowerCase().contains(Text.INVALID)) {
            code = Text.INVALID;
        } else {
            code = userVerificationDAO.get(user, VerifyType.UpdateUserPhone.value()).getVerifyCode();
        }

        accountBus.verifyPhone(rs, code);
    }


    @And("^I update \"([^\"]*)\"$")
    public void updateNewPassword(String newPassword) {
        UpdateNewPasswordRQ info = UpdateNewPasswordRQ.builder()
                .newPassword(newPassword)
                .accessKey(response.extract("data.accessKey"))
                .language(0)
                .deviceId(AccountMS.getDefaultDeviceId()).build();
        accountBus.updatePassword(info);
    }

    @When("^I re-signup \"(.*?)\" account (auditor|approver|coordinator|provider|editor|appConfig|auditor|patient) and update profile?$")
    public void signupAsRoleAndUpdateProfile(String role, String userType) {
        AccountMS body = resignupAndUpdateProfile(role);

        // set user type
        if (!userType.isEmpty()) {
            accountBus.signin(AccountMS.getNewAccount("system_admin_1"));
            List<String> userSigs = userManagerBus.loadUsersAndGetSigs(body);
            UserManagerApi.getInstant().setGlobedrDoctor(userSigs);

            UserManagerApi.getInstant().changeUserType(userSigs,
                    userType.equalsIgnoreCase("patient") ? UserType.value(userType) : UserType.convert(Arrays.asList("patient", userType))
            ).mustSucceed();
        }
    }


    @When("^Please resend verify code for signup?$")
    public void pleaseResendVerifyCodeForSignup() {
        SendVerifyCodeRQ body = SendVerifyCodeRQ.builder()
                .userSignature(signUpRS.getUserSignature())
                .verifyType(VerifyType.SignUp.value())
                .language(0)
                .build();
        accountBus.sendVerifyCode(body);
    }

    @When("^I enter verification code?$")
    public void enterValidCodeToSignup() {
        verifySignUp(signUpRS);
    }


    @And("^I log out$")
    public void logOut() {
        Environment environment = new Environment();
        accountBus.logout(AccountMS.builder().deviceId(environment.get("deviceId")).build());
    }


    @When("^I login account$")
    public void loginAccount(AccountMS info) {
        info.setDeviceId(AccountMS.getDefaultDeviceId());
        accountBus.signin(info);
    }


    @When("^I get information of signed in account$")
    public void getInfoOfSignedInAccount() {
        accountBus.signInInfo();
    }

    private void verifySignUp(SignUpRS rs) {

        VerifySignUpRQ body = VerifySignUpRQ.builder()
                .language(rs.getLanguage())
                .userSignature(rs.getUserSignature())
                .code(userVerificationDAO.get(rs.getGdrLogin(), VerifyType.SignUp.value()).getVerifyCode())
                .deviceId(AccountMS.getDefaultDeviceId())
                .build();
        accountBus.verifySignUp(body);
    }

    @When("^I signup new account with below info( and verify it)?$")
    public void signupAccount(String verify, List<SignUpMS> accounts) {
        for (SignUpMS accountMS : accounts) {
            accountMS.setDefaultDeviceId();
            accountMS.setDefaultUserPlatform();

            signUpRS = accountBus.signup(accountMS);
            if (verify != null) {
                verifySignUp(signUpRS);
            }
        }
    }


    @When("^I signup \"(.*?)\" account?$")
    public AccountMS signupAsRole(String role) {
        AccountMS body = AccountMS.getNewAccount(role);


        verifySignUp(accountBus.signup(body));
        // set username
        new SqlDatabaseStep().setUserName(body.getDisplayName(), body.getGdrLogin(), body.getCountryName());
        return body;
    }

    @When("^I re-signup \"(.*?)\" account$")
    public AccountMS resignup(String role) {
        // clear account
        new SqlDatabaseStep().onSqlServerDeleteAccount(AccountMS.getExistedAccount(role));
        // signup account
        return signupAsRole(role);
    }

    @When("^I re-signup \"(.*?)\" account and update profile$")
    public AccountMS resignupAndUpdateProfile(String role) {
        AccountMS body = resignup(role);
        // update profile

        accountBus.updatePersonalInfo(body).mustSucceed();
        return body;
    }


    @When("^I login as \"(.*?)\"$")
    public void loginRole(String role) {
        Data.set(Text.CURRENT_USER, accountBus.signin(AccountMS.getExistedAccount(role)));
    }

    @When("^I change my password$")
    public void changePassword(ChangePasswordRQ info) {
        info.setDeviceId(AccountMS.getDefaultDeviceId());
        accountBus.changePassword(info);
    }

    @And("^I load my profile$")
    public void loadProfile() {
        accountBus.getPersonalInfo();
    }

    @When("^I update my profile with below info$")
    public void updateProfile(AccountMS info) {
        accountBus.updatePersonalInfo(info);
    }

    @And("^I update profile of logged user with dob is '(.*?)'$")
    public void updateDOBAndDefaultProfile(String time) {
        AccountMS info = AccountMS.getExistedAccount("user_test");
        info.setDob(Common.getDobByAge(time));
        accountBus.updatePersonalInfo(info);
    }

    @And("^I update profile of logged user with dob is '(.*?)' and below info$")
    public void updateDOBAndProfile(String time, List<AccountMS> accounts) {
        AccountMS accountMS = accounts.get(0);
        accountMS.setDob(Common.getDobByAge(time));
        accountBus.updatePersonalInfo(accountMS);
    }


    @And("I remove my account from system")
    public void iRemoveMyAccountFromSystem() {
        ExitSystemRQ info = new ExitSystemRQ();
        info.setDeviceId(Environment.INSTANCE.get("deviceId"));
        accountBus.exitSystem(info);
    }
}
