package com.apis.globedr.stepdefinition;

import com.apis.globedr.helper.Wait;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.services.database.PostDB;
import com.apis.globedr.services.database.ProfileDB;
import com.apis.globedr.services.database.dao.*;
import com.apis.globedr.services.database.entities.*;
import com.apis.globedr.services.database.other.FieldType;
import com.apis.globedr.enums.Language;
import com.apis.globedr.enums.VerifyType;
import com.apis.globedr.services.es.ElasticSearchApi;
import com.apis.globedr.helper.Common;
import com.apis.globedr.services.redis.RedisUtil;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;

public final class SqlDatabaseStep {

    ElasticSearchApi elasticSearchApi = new ElasticSearchApi();
    UserDAO userDAO = new UserDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    UserVerificationDAO userVerificationDAO = new UserVerificationDAO();
    OrgDAO orgDAO = new OrgDAO();
    PostDAO postDAO = new PostDAO();
    WalletGiftCardDAO walletGiftCardDAO = new WalletGiftCardDAO();
    WalletTransPointRuleDAO walletTransPointRuleDAO = new WalletTransPointRuleDAO();
    GiftCardDAO giftCardDAO = new GiftCardDAO();
    GiftCardLotDAO giftCardLotDAO = new GiftCardLotDAO();
    GiftVoucherDAO giftVoucherDAO = new GiftVoucherDAO();
    OrgVoucherDAO orgVoucherDAO = new OrgVoucherDAO();
    SponsorDAO sponsorDAO = new SponsorDAO();
    GiftCategoryDAO giftCategoryDAO = new GiftCategoryDAO();
    ChangelogDAO changelogDAO = new ChangelogDAO();
    MgtRatingReviewDAO mgtRatingReviewDAO = new MgtRatingReviewDAO();
    MgtRatingSumDAO mgtRatingSumDAO = new MgtRatingSumDAO();
    AppScreenGuideDAO appScreenGuideDAO = new AppScreenGuideDAO();
    AppStepGuideDAO appStepGuideDAO = new AppStepGuideDAO();
    GdrConfigDAO gdrConfigDAO = new GdrConfigDAO();
    ForumPostDAO forumPostDAO = new ForumPostDAO();
    ForumPostMsgDAO forumPostMsgDAO = new ForumPostMsgDAO();
    ProductDAO productDAO = new ProductDAO();

    private void sql_deleteUser(String loginId) {
        List<User> list = userDAO.getAll(loginId);
        // Delete all user on sql
        sql_deleteUser(list);
    }


    @Given("^On SqlServer, I account with phone \"([^\"]*)\"$")
    public void onSqlServerIAccountWithPhone(String phonework) {
        List<User> list = userDAO.getAllByPhone(phonework);
        // Delete all user on sql
        sql_deleteUser(list);
    }

    private void sql_deleteUser(List<User> list) {
        // Delete all user on sql

        for (User user : list) {
            int i = user.getUserId();
            // Delete all user by name and id on ES
            elasticSearchApi.deleteUser(user.getDisplayName());
            elasticSearchApi.deleteUserId(i);
            // Delete all user by name on Cache
            RedisUtil.getInstant().clearCache(i);
        }

        sql_deleteAllDataOfUser(list);
    }

    private void sql_deleteAllDataOfUser(List<User> list) {
        // Delete all user on sql
        String users = "";
        String names = "";
        for (User user : list) {
            users += user.getUserId() + ",";
        }

        for (User user : list) {
            names += user.getDisplayName() + ",";
        }

        for (Integer id : new UserDAO().getUserIdByDisplayName(StringUtils.chop(names))) {
            if (users.contains(id.toString())) users += id + ",";
        }

        users = StringUtils.chop(users);

        PostDB.getInstant().deleteDataByUser(users);
        ProfileDB.getInstant().deleteDataByUser(users);
    }

    @Given("^On SqlServer, I delete articles category by name \"([^\"]*)\"$")
    public void deleteArticleCategory(String name) {

        postDAO.deleteByCategoryName(name);
        forumPostDAO.deleteByCategoryName(name);
        categoryDAO.deleteByName(name);

    }

    @Given("^On SqlServer, I delete all hot english articles category$")
    public void deleteArticleCategory() {
        Category category = new Category();
        category.setType(1);
        category.setLanguage(0);
        categoryDAO.delete(category, FieldType.WHERE_AND);
    }


    @Given("^On SqlServer, I delete organization by name \"([^\"]*)\"$")
    public void deleteOrganization(String orgName) {
        elasticSearchApi.deleteOrg(orgName);
        // wait for elastic delete this org
        Wait.until(elasticSearchApi.hasOrg(orgName), false);

        List<Org> list = orgDAO.getAll(orgName);
        String orgs = list.stream().map(o -> o.getOrganizationId() + "").collect(Collectors.joining(","));

        list.forEach(org -> {
            Sponsor sponsor = sponsorDAO.getSponsorByOrgId(org.getOrganizationId());
            if (sponsor != null) RedisUtil.getInstant().clearCacheSponsor(sponsor.getId());
            RedisUtil.getInstant().clearCache(org.getOrganizationId());
        });

        PostDB.getInstant().deleteDataByOrg(orgs);
        ProfileDB.getInstant().deleteDataByOrg(orgs);

    }

    @Given("^On SqlServer, I delete voucher category that have \"([^\"]*)\" is \"([^\"]*)\"$")
    public void deleteVoucherCategory(String column, String value) {
        giftCategoryDAO.deleteBy(column, value);
    }

    @And("^On SqlServer, I delete all posts in system$")
    public void deleteSystemPost() {
        for (ForumPost forumPost : forumPostDAO.getPostSystem()) {
            forumPostMsgDAO.deleteByPostId(forumPost.getPostId());
            forumPostDAO.delete(forumPost.getPostId());
        }
    }

    @When("^get verify code of number phone \"([^\"]*)\" with country \"([^\"]*)\"$")
    public void getVerifyCodeOfNumberPhoneWithCountry(String phone, String country) {
        System.out.println("SHOW VERIFY CODE : " + userVerificationDAO.get(Common.convertFullPhone(phone, country)
                , VerifyType.SignUp.value()).getVerifyCode());
    }

    @When("^get verify code while changing number phone \"([^\"]*)\" with country \"([^\"]*)\"$")
    public void getVerifyCodeWhileUploadNumberPhoneWithCountry(String oldPhone, String country) {
        String code = userVerificationDAO.get(Common.convertFullPhone(oldPhone, country),
                VerifyType.UpdateUserPhone.value()).toString();
        System.out.println("SHOW sendCode " + code);
    }

    @When("^On SqlServer, I set \"(.*?)\" account to become SYSTEM ADMIN$")
    public void onSqlServerISetAccountToBecomeSYSTEMADMIN(String role) {
        AccountMS body = AccountMS.getExistedAccount(role);
        String gdrLogin = Common.convertFullPhone(body.getGdrLogin(), body.getCountryName());
        userDAO.setAdmin(gdrLogin);
        Assert.assertEquals(userDAO.getUserType(gdrLogin), "255", "Account SYSTEM musts have user type is 4");
    }

    @Given("^On SqlServer, I delete \"(.*?)\" account$")
    public void onSqlServerDataBaseProfileIDeleteUserByUsernameAndCountry(String role) {
        AccountMS body = AccountMS.getNewAccount(role);
        List<User> list = userDAO.getAll(body.fullPhone());
        // Delete all user on sql
        sql_deleteUser(list);
    }

    public void onSqlServerDeleteAccount(AccountMS account) {
        if (account == null) return;
        sql_deleteUser(userDAO.getAllByGdrLoginOrName(account.fullPhone(),account.getDisplayName()));
    }


    @Given("^On SqlServer, I delete user by username \"(.*?)\" and country \"(.*?)\"$")
    public void onSqlServerDataBaseProfileIDeleteUserByUsernameAndCountry(String user, String country) {
        AccountMS body = AccountMS.builder()
                .gdrLogin(user)
                .countryName(country)
                .build();
        List<User> list = userDAO.getAll(body.fullPhone());
        // Delete all user on sql
        sql_deleteUser(list);
    }


    @Given("^On SqlServer, I delete all data by username \"(.*?)\" and country \"(.*?)\"$")
    public void onSqlServerDataBaseProfileIDeletealldataByUsernameAndCountry(String user, String country) {
        List<User> list = userDAO.getAll(Common.convertFullPhone(user, country));
        sql_deleteAllDataOfUser(list);
    }

    @Given("^On SqlServer, I delete user$")
    public void onSqlServerDeleteUser(List<User> users) {
        users.forEach(user -> {
            List<User> list = userDAO.getAll(user, null);
            // Delete all user on sql
            sql_deleteUser(list);
        });

    }

    @Given("^On SqlServer, I delete user by gdrLogin \"(.*?)\"$")
    public void onSqlServerDataBaseProfileIDeleteUserBygdrLogin(String gdrLogin) {
        User user = new User();
        user.setGdrLogin(gdrLogin);
        List<User> list = userDAO.getAll(user, null);
        // Delete all user on sql
        sql_deleteUser(list);
    }

    @Given("^On SqlServer, I delete user by displayName \"(.*?)\"$")
    public void sqlDeleteUserByName(String displayName) {
        if (displayName != null && !displayName.isEmpty()) {
            User user = new User();
            user.setDisplayName(displayName);

            List<User> list = userDAO.getAll(user, null);
            // Delete all user on sql
            sql_deleteUser(list);
        }
    }

    @Given("^On SqlServer, I delete user by email \"(.*?)\"$")
    public void onSqlServerDataBaseProfileIDeleteUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);

        List<User> list = userDAO.getAll(user, null);
        // Delete all user on sql
        sql_deleteUser(list);
    }


    @Given("^On SqlServer, I delete change log \"([^\"]*)\"$")
    public void onSqlServerIDeleteChangeLog(String name) {
        changelogDAO.deleteByName(name);
        RedisUtil.getInstant().clearCacheChanglog();
    }

    @Given("^On SqlServer, I delete change log of app version english$")
    public void OnSqlServerIdeletechangelogofappversionenglish() {
        changelogDAO.deleteAppVersion(Language.English.value());
        RedisUtil.getInstant().clearCacheChanglog();
    }


    @When("^I clear all cache$")
    public void iClearAllCache() {
        RedisUtil.getInstant().clearAllCache();
    }

    @When("^I test all cache$")
    public void itestAllCache() {
        RedisUtil.getInstant().clearAllCache();
    }

    private void updateUserWithBelowInfo(String where, Map<String, Object> dataTable) {
        userDAO.update(where, dataTable);

        List<User> users = userDAO.getAllByCondition(where);
        for (User u : users) {
            RedisUtil.getInstant().clearCache(u.getUserId());
        }
    }

    @Given("On SqlServer, I set username is {string} for phone {string} and country {string}")
    public void setUserName(String username, String gdrlogin, String country) {
        Map<String, Object> dataTable = new HashMap<>();
        dataTable.put("account", username);
        onSqlServerIUpdateBelowInfoToUsernameAndCountry(gdrlogin, country, dataTable);
    }


    @Given("^On SqlServer, I update below info to username \"([^\"]*)\" and country \"([^\"]*)\"$")
    public void onSqlServerIUpdateBelowInfoToUsernameAndCountry(String phone, String country, Map<String, Object> dataTable) {
        String where = String.format("gdrLogin like '%s'", Common.convertFullPhone(phone, country));
        updateUserWithBelowInfo(where, dataTable);
    }


    @Given("^On SqlServer, I update below info to username with displayName \"([^\"]*)\"$")
    public void onSqlServerIUpdateBelowInfoToUsernameWithDisplayname(String displayName, Map<String, Object> dataTable) {
        String where = String.format("displayName like N'%s'", displayName);
        updateUserWithBelowInfo(where, dataTable);
    }

    @Given("^On SqlServer, I delete voucher by name \"([^\"]*)\"$")
    public void onSqlServerIDeleteVoucherByName(String voucherName) {
        walletGiftCardDAO.deleteByVoucherName(voucherName);
        giftCardDAO.deleteByVoucherName(voucherName);
        giftCardLotDAO.deleteByVoucherName(voucherName);
        giftVoucherDAO.deleteByVoucherName(voucherName);
        orgVoucherDAO.deleteByVoucherName(voucherName);
    }


    @Given("^On SqlServer, I delete guide with appscreen is \"([^\"]*)\"$")
    public void onSqlServerIDeleteGuideWithAppscreenIs(Integer id) {
        appStepGuideDAO.deleteByAppScreen(id);
        appScreenGuideDAO.deleteByAppScreen(id);
    }

    @When("^On SqlServer, I update \"([^\"]*)\" account to Globedr Config$")
    public void onSqlServerIUpdateAccountToGlobedrConfig(String role) {
        AccountMS body = AccountMS.getExistedAccount(role);
        String gdrLogin = Common.convertFullPhone(body.getGdrLogin(), body.getCountryName());
        userDAO.setAdmin(gdrLogin);
        gdrConfigDAO.allowUserIdConfig(userDAO.getUserIds(gdrLogin));
        Assert.assertEquals(userDAO.getUserType(gdrLogin), "255", "Account SYSTEM musts have user type is 4");
    }

    @Given("^On Redis, I clear all cache$")
    public void onRedisIClearAllCache() {
        RedisUtil.getInstant().clearAllCache();
    }

//    @And("On SqlServer, I clear below info to username {string} and country {string}")
//    public void onSqlServerIClearBelowInfoToUsernameAndCountry(String phone, String country, List<String> fields) {
//        userDAO.clear(String.format("gdrLogin like '%s'", Common.convertFullPhone(phone, country)), fields);
//    }

    @Given("On SqlServer, I remove {string} account from role updated config list")
    public void onSqlServerIRemoveAccountFromRoleUpdatedConfigList(String role) {
        AccountMS body = AccountMS.getExistedAccount(role);
        String gdrLogin = Common.convertFullPhone(body.getGdrLogin(), body.getCountryName());
        gdrConfigDAO.unAllowUserIdConfig(userDAO.getUserIds(gdrLogin));

    }

    @And("I will check gdrLogin {string} in the database have below infor")
    public void iWillCheckInTheDatabase(String gdrLogin, Map<String, Object> dataTable) {
        List<User> list = userDAO.getAll(gdrLogin);
        for (User user : list) {

            int i = user.getUserId();

            Assert.assertEquals(mgtRatingReviewDAO.getRatingReviewByUserId(i).getReview(), dataTable.get("review"));
            Assert.assertEquals(mgtRatingSumDAO.getRatingScore(i).getScore(), dataTable.get("score").toString());
        }

    }


    @Given("On SqlServer, I delete point rules")
    public void onSqlServerIDeletePointRules(List<WalletTransPointRule> list) {
        list.forEach(walletTransPointRuleDAO::delete);
    }

    @Given("On SqlServer, I delete product name is {string}")
    public void onSqlServerIDeleteProductNameIs(String productName) {
        productDAO.deleteProductAndServicesByName(productName);
    }
}
