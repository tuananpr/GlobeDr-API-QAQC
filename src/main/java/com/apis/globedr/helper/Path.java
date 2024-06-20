package com.apis.globedr.helper;

public class Path {


    public static final String PROJECT = System.getProperty("user.dir") + "/";
    public static final String CONFIG = PROJECT + "src/main/resources/";

    public static final String CONFIG_TEST = PROJECT + "src/test/"
            + "resources/";

    public static final String MAIN_RESOURCE = PROJECT + "src/main/resources/";

    public static final String TEST_RESOURCE = PROJECT + "src/test/resources/";

    public static final String DEFAULT_API = "data/default_api/";


    public static final String ENVIRONMENT = CONFIG + "env-test-api.properties";
    public static final String EXPECTED_RESULT = PROJECT + "data" + System.getProperty("file.separator") + "expected_result/";

    public static final String TARGET = PROJECT + "target/";
    public static final String TEXT_TEMP = TARGET + "text.txt";


    public static final String USER = DEFAULT_API + "user/";
    public static final String NOTI = DEFAULT_API + "noti/";
    public static final String COMMUNICATION = DEFAULT_API + "communication/";
    public static final String CONNECTION = DEFAULT_API + "connection/";
    public static final String CHAT = DEFAULT_API + "chat/";
    public static final String SYSTEM = DEFAULT_API + "system/";
    public static final String CHANGELOG = DEFAULT_API + "changeLog/";
    public static final String CONSULT = DEFAULT_API + "consult/";
    public static final String VOUCHER = DEFAULT_API + "voucher/";
    public static final String VOUCHER_MANAGER = DEFAULT_API + "voucherManager/";
    public static final String FORUM_MANAGER = DEFAULT_API + "forumManager/";
    public static final String FORUM = DEFAULT_API + "forum/";
    public static final String ACCOUNT = DEFAULT_API + "account/";
    public static final String SUB_ACCOUNT = DEFAULT_API + "subAccount/";
    public static final String ORG_MANAGER = DEFAULT_API + "orgManager/";
    public static final String HEALTH = DEFAULT_API + "health/";
    public static final String TOP_DEAL = DEFAULT_API + "topdeal/";
    public static final String USER_MANAGER = DEFAULT_API + "userManager/";
    public static final String APPOINTMENT = DEFAULT_API + "appointment/";
    public static final String APPOINTMENT_MANAGE = DEFAULT_API + "appointmentManage/";
    public static final String SEARCH = DEFAULT_API + "search/";
    public static final String SPONSOR = DEFAULT_API + "sponsor/";
    public static final String GUIDE = DEFAULT_API + "guide/";
    public static final String OTHER = DEFAULT_API + "other/";

}
