package com.apis.globedr.constant;

import com.apis.globedr.services.config.Environment;

public class API {

    private static Environment environment = new Environment();
    public static Integer version;

    private static String getApiDefaultVersion() {
        return String.format("%s/%s/%s/", environment.get("api.uri"), environment.get("api.group"), environment.get("api.version"));
    }


    public static String getDomain() {
        return environment.get("api.uri");
    }

    public static String version() {
        if (version != null) {
            return String.format("%s/%s/%s/", environment.get("api.uri"), environment.get("api.group"), version);
        }
        return getApiDefaultVersion();
    }

    public static class TopDeal {
        private static String getController() {
            return version() + "TopDeal/";
        }


        public static String LOAD_TOP_DEALS() {
            return getController() + "LoadTopDeals";
        }

        public static String PROMOTION() {
            return getController() + "Promotion";
        }

        public static String LOAD_PROMOTIONS() {
            return getController() + "LoadPromotions";
        }

        public static String LOAD_ORG_TOP_DEALS() {
            return getController() + "LoadOrgTopDeals";
        }

        public static String TOP_DEAL() {
            return getController() + "TopDeal";
        }

        public static String DELETE_TOP_DEAL() {
            return getController() + "DeleteTopDeal";
        }

        public static String DELETE_PROMOTION() {
            return getController() + "DeletePromotion";
        }

        public static String UPLOAD_PROMOTION_ICON() {
            return getController() + "UploadPromotionIcon";
        }

        public static String UPLOAD_PROMOTION_FILE() {
            return getController() + "UploadPromotionFile";
        }
    }

    public static class Order {

        private static String getController() {
            return version() + "Order/";
        }


        public static String MEDICINE_ORDER() {
            return getController() + "MedicineOrder";
        }

        public static String ORDERS() {
            return getController() + "EOrders";
        }

        public static String ORDER_CANCEL() {
            return getController() + "OrderCancel";
        }

        public static String NEW_MEDICINE_TEST_ORDER() {
            return getController() + "ENewMedicineTestOrder";
        }

        public static String ORDER_DETAIL() {
            return getController() + "EOrderDetail";
        }

        public static String PRODUCT_SERVICE_ORGS() {
            return getController() + "EProductServiceOrgs";
        }

        public static String ORDER_ATTACH_FILE() {
            return getController() + "OrderAttachFile";
        }

        public static String ORDER_PROCESS() {
            return getController() + "EOrderProcess";
        }

        public static String COUNT_DOCTOR_INDICATED() {
            return getController() + "ECountDoctorIndicated";
        }

        public static String LIST_ORDER_PROCESS() {
            return getController() + "EListOrderProcess";
        }

        public static String DELIVERY_TYPES() {
            return getController() + "EDeliveryTypes ";
        }

        public static String NEW_PRODUCT_ORDER() {
            return getController() + "NewProductOrder ";
        }
        public static String ORDER_ASSIGN_TAKEN_SAMPLE() {
            return getController() + "OrderAssignTakenSample";
        }
    }

    public static class Noti {

        private static String getController() {
            return version() + "Noti/";
        }


        public static String COUNT_UNREAD() {
            return getController() + "ECountUnread";
        }

        public static String SEEN() {
            return getController() + "ESeen";
        }

        public static String SEEN_ALL() {
            return getController() + "ESeenAll";
        }

        public static String GET_CONFIG() {
            return getController() + "EConfigAzurePusher";
        }

        public static String NOTIFICATIONS() {
            return getController() + "ENotifications";
        }

        public static String REGISTER_DEVICE() {
            return getController() + "ERegisterDevice";
        }

        public static String GEN_INSTALLATION() {
            return getController() + "GenInstallation";
        }

        public static String DEVICE_ON_HUB() {
            return getController() + "EDeviceOnHub";
        }
    }

    public static class Appt {

        private static String getController() {
            return version() + "Appt/";
        }


        public static String DOCTORS() {
            return getController() + "EDoctors";
        }

        public static String APPOINTMENT() {
            return getController() + "Appointment";
        }

        public static String APPOINTMENTS() {
            return getController() + "EAppointments";
        }

        public static String DOCTOR_DECLINE() {
            return getController() + "DoctorDecline";
        }


        public static String APPOINTMENT_DETAIL() {
            return getController() + "AppointmentDetail";
        }

        public static String APPT() {
            return getController() + "EAppt";
        }

        public static String UI_CONFIG() {
            return getController() + "UIConfig";
        }

        public static String UI_CONFIG_UPDATE() {
            return getController() + "UIConfigUpdate";
        }


        public static String ORG_EXAMINATION_SCHEDULE() {
            return getController() + "EOrgExaminationSchedule";
        }

        public static String DOCTOR_SCHEDULES() {
            return getController() + "EDoctorSchedules";
        }

        public static String PATIENT() {
            return getController() + "EPatient";
        }

        public static String INFO() {
            return getController() + "EInfo";
        }

        public static String ORG_APPOINTMENT_TYPES() {
            return getController() + "OrgAppointmentTypes";
        }

        public static String APPT_UPDATE() {
            return getController() + "EApptUpdate";
        }

        public static String INFO_BY_QRCODE() {
            return getController() + "InfoByQRcode";
        }

    }

    public static class ApptManage {
        private static String getController() {
            return version() + "ApptManage/";
        }


        public static String ORG_DOCTOR_SCHEDULES() {
            return getController() + "EOrgDoctorSchedules";
        }

        public static String NEW_SCHEDULE_EXAMINATION() {
            return getController() + "ENewScheduleExamination";
        }

        public static String SCHEDULE_EXAMINATION() {
            return getController() + "EScheduleExamination";
        }

        public static String DOCTOR_SCHEDULE() {
            return getController() + "EDoctorSchedule";
        }

        public static String NEW_ORG_DOCTOR_SCHEDULE() {
            return getController() + "ENewOrgDoctorSchedule";
        }

        public static String EXISTED_DOCTOR_SCHEDULE() {
            return getController() + "ExistDoctorSchedule";
        }

        public static String ORG_SCHEDULE_EXAMINATIONS() {
            return getController() + "EOrgScheduleExaminations";
        }

        public static String APPTS() {
            return getController() + "EAppts";
        }

        public static String STATUS() {
            return getController() + "EStatus";
        }

        public static String DOCTOR_SCHEDULE_VISIT_VIEWS() {
            return getController() + "EDoctorScheduleVisitViews";
        }

        public static String DOCTOR_SCHEDULE_VISIT() {
            return getController() + "EDoctorScheduleVisit";
        }


        public static String APPOINTMENT_CONFIG() {
            return getController() + "EAppointmentConfig";
        }

        public static String APPT_ASSIGN_DOCTOR() {
            return getController() + "EApptAssignDoctor";
        }


    }

    public static class UserManager {


        private static String getController() {
            return version() + "UserManager/";
        }



        public static String LOAD_USERS() {
            return getController() + "ELoadUsers";
        }

        public static String DELETE_ES_DATA() {
            return getController() + "DeleteESData";
        }

        public static String CHANGE_USER_TYPE() {
            return getController() + "ChangeUserType";
        }

        public static String SET_GLOBEDR_DOCTOR() {
            return getController() + "ESetGlobeDrDoctor";
        }

        public static String PWD_RESET() {
            return getController() + "PwdReset";
        }

        public static String USERS_EXCEL() {
            return getController() + "EUsersExcel";
        }

        public static String CHANGE_USER_STATUS() {
            return getController() + "EChangeUserStatus";
        }

        public static String VERIFY_USER() {
            return getController() + "EVerifyUser";
        }

        public static String REMOVE_USERS() {
            return getController() + "ERemoveUsers";
        }

        public static String ADD_TO_GROUP() {
            return getController() + "EAddToGroup";
        }

        public static String GIFT_POINT() {
            return getController() + "GiftPoint";
        }

        public static String POINT_RULES() {
            return getController() + "PointRules";
        }

        public static String NEW_POINT_RULE() {
            return getController() + "NewPointRule";
        }

        public static String CHANGE_USER_FEATURE_ATTRIBUTE() {
            return getController() + "EChangeUserFeatureAttribute";
        }

        public static String APP_USAGE_TIME() {
            return getController() + "AppUsageTime";
        }
        public static String USED_POINT_HISTORY() {
            return getController() + "UsedPointHistory";
        }

    }

    public static class Communication {

        private static String getController() {
            return version() + "Communication/";
        }


        public static String DHYD2() {
            return getController() + "DHYD2";
        }
    }

    public static class System {
        private static String getController() {
            return version() + "System/";
        }


        public static String GET_VAC_INFO() {
            return getController() + "GetVacInfo";
        }

        public static String GET_SYSTEM_POST() {
            return getController() + "GetSystemPost";
        }

        public static String USERS_GROWTH_CHART() {
            return getController() + "UsersGrowthChart";
        }

        public static String USERS_BY_COUNTRY() {
            return getController() + "UsersByCountry";
        }

        public static String USERS_BY_GENDER() {
            return getController() + "UsersByGender";
        }

        public static String ORGS_GROWTH_CHART() {
            return getController() + "OrgsGrowthChart";
        }

        public static String ORGS_BY_COUNTRY() {
            return getController() + "OrgsByCountry";
        }

        public static String ORGS_BY_TYPE() {
            return getController() + "OrgsByType";
        }

        public static String FEEDBACKS() {
            return getController() + "Feedbacks";
        }

        public static String FEEDBACKS_Excel() {
            return getController() + "FeedbacksExcel";
        }

        public static String SUB_SPECIALTIES() {
            return getController() + "SubSpecialties";
        }

        public static String MAIN_SPECIALTIES() {
            return getController() + "MainSpecialties";
        }

        public static String CONFIG_GET() {
            return getController() + "ConfigGet";
        }

    }

    public static class Other {
        public static String GET_HOSTS() {
            return getApiDefaultVersion() + getController() + "GetHosts"; // this api always use v1
        }

        private static String getController() {
            return version() + "Other/";
        }

        public static String LOAD_SPECIALTIES() {
            return getController() + "LoadSpecialties";
        }

        public static String GET_REGIONS() {
            return getController() + "GetRegions";
        }

        public static String META_DATA() {
            return getController() + "MetaData";
        }

        public static String GET_DOWNLOAD_APP() {
            return getController() + "GetDownloadApp";
        }

        public static String GET_LANDING_PAGE() {
            return getController() + "GetLandingPage";
        }

        public static String CHECK_PHONE() {
            return getController() + "CheckPhone";
        }

        public static String DETECT_LOCATION() {
            return getController() + "DetectLocation";
        }
        public static String GET_COUNTRIES() {
            return getController() + "GetCountries";
        }
        public static String GET_CITIES() {
            return getController() + "GetCities";
        }

        public static String GET_DISTRICTS() {
            return getController() + "GetDistricts";
        }

        public static String GET_WARDS() {
            return getController() + "GetWards";
        }
    }

    public static class Guide {


        private static String getController() {
            return version() + "Guide/";
        }



        public static String NEW_GUIDE() {
            return getController() + "NewGuide";
        }

        public static String Guide() {
            return getController() + "Guide";
        }

        public static String GUIDE_UPDT() {
            return getController() + "GuideUpdt";
        }

        public static String GUIDE_DEL() {
            return getController() + "GuideDel";
        }

        public static String APP_SCREENS() {
            return getController() + "AppScreens";
        }

        public static String APP_SCREEN_UPDT() {
            return getController() + "AppScreenUpdt";
        }

        public static String APP_SCREEN_DEL() {
            return getController() + "AppScreenDel";
        }

        public static String NEW_APP_SCREEN() {
            return getController() + "NewAppScreen";
        }

        public static String GET() {
            return getController() + "get";
        }

        public static String GUIDES() {
            return getController() + "Guides";
        }
    }

    public static class Sponsor {

        private static String getController() {
            return version() + "Sponsor/";
        }


        public static String APPROVE() {
            return getController() + "Approve";
        }

        public static String STATUS() {
            return getController() + "Status";
        }

        public static String DURATION() {
            return getController() + "Duration";
        }

        public static String REMOVE() {
            return getController() + "Remove";
        }

        public static String LIST() {
            return getController() + "List";
        }

        public static String SPONSORS() {
            return getController() + "Sponsors";
        }
    }

    public static class Forum {

        private static String getController() {
            return version() + "Forum/";
        }


        public static String LOAD_CATEGORY_WITH_POST() {
            return getController() + "LoadCategoryWithPost";
        }

        public static String LOAD_CATEGORY_WITH_POST2() {
            return getController() + "LoadCategoryWithPost2";
        }

        public static String LOAD_POST_FOR_CATEGORY() {
            return getController() + "LoadPostForCategory";
        }

        public static String LOAD_POST_FOR_CATEGORY2() {
            return getController() + "LoadPostForCategory2";
        }

        public static String LOAD_POST_DETAIL() {
            return getController() + "LoadPostDetail";
        }

        public static String LOAD_POST_DETAIL2() {
            return getController() + "LoadPostDetail2";
        }

        public static String LIKE_COMMENT() {
            return getController() + "LikeComment";
        }

        public static String COMMENT() {
            return getController() + "Comment";
        }

        public static String LOAD_COMMENTS() {
            return getController() + "LoadComments";
        }

        public static String LOAD_COMMENTS2() {
            return getController() + "LoadComments2";
        }

        public static String LOAD_POST() {
            return getController() + "LoadPost";
        }

        public static String RELATED_POST() {
            return getController() + "RelatedPosts";
        }

        public static String ARTICLE_POSTS() {
            return getController() + "ArticlePosts";
        }

        public static String UPLOAD_POST_MSG_DOCS() {
            return getController() + "UploadPostMsgDocs";
        }

    }

    public static class ForumManager {

        private static String getController() {
            return version() + "ForumManager/";
        }

        public static String ADD_CATEGORY() {
            return getController() + "AddCategory";
        }

        public static String LOAD_CATEGORIES() {
            return getController() + "LoadCategories";
        }

        public static String UPDATE_CATEGORY() {
            return getController() + "UpdateCategory";
        }

        public static String ADD_SYSTEM_POST_INIT() {
            return getController() + "AddSystemPostInit";
        }

        public static String ADD_SYSTEM_POST() {
            return getController() + "AddSystemPost";
        }

        public static String UPDATE_SYSTEM_POST() {
            return getController() + "UpdateSystemPost";
        }

        public static String LOAD_SYSTEM_POSTS() {
            return getController() + "LoadSystemPosts";
        }

        public static String ADD_POST() {
            return getController() + "AddPost";
        }

        public static String POSTS() {
            return getController() + "Posts";
        }

        public static String LOAD_CATEGORY_WITH_POST() {
            return getController() + "LoadCategoryWithPost";
        }

        public static String LOAD_POST_BY_CATEGORY() {
            return getController() + "LoadPostByCategory";
        }

        public static String APPROVE_POST() {
            return getController() + "ApprovePost";
        }

        public static String REJECT_POST() {
            return getController() + "RejectPost";
        }

        public static String HIDE_POST() {
            return getController() + "HidePost";
        }

        public static String UP_HOT_TITLE_POST() {
            return getController() + "UpHotTitlePost";
        }

        public static String DELETE_POST() {
            return getController() + "DeletePost";
        }

        public static String LOAD_NOTES() {
            return getController() + "LoadNotes";
        }

        public static String LOAD_REJECT_LOG() {
            return getController() + "LoadRejectLog";
        }

        public static String CLOSE_COMMENT() {
            return getController() + "CloseComment";
        }

        public static String LOAD_POST_DETAIL() {
            return getController() + "LoadPostDetail";
        }

        public static String UPLOAD_FORUM_ICON() {
            return getController() + "UploadForumIcon";
        }

        public static String UPLOAD_POST_DOC() {
            return getController() + "UploadPostDoc";
        }

        public static String ADD_POST_INIT() {
            return getController() + "AddPostInit";
        }

        public static String UPDATE_POST() {
            return getController() + "UpdatePost";
        }

        public static String SHOW_GLOBEDR_ARTICLE_CONFIG() {
            return getController() + "ShowGlobedrArticleConfig";
        }

        public static String SHOW_GLOBEDR_ARTICLE_VALUE() {
            return getController() + "ShowGlobedrArticleValue";
        }


        public static String SUBMIT_POST() {
            return getController() + "SubmitPost";
        }


    }

    public static class Vaccination {

        private static String getController() {
            return version() + "Vaccination/";
        }



        public static String GET_IMMUNIZATION() {
            return getController() + "GetImmunization";
        }

        public static String UPDATE_VACCINE_RECORD() {
            return getController() + "EUpdateVaccineRecord";
        }

        public static String GET_NEXT_VACCINE() {
            return getController() + "EGetNextVaccine";
        }

        public static String GET_IMMUNIZATION_BY_AGE() {
            return getController() + "EGetImmunizationByAge";
        }

        public static String GET_IMMUNIZATION_BY_VACCINE() {
            return getController() + "EGetImmunizationByVaccine";
        }

        public static String LOAD_MEDICINES() {
            return getController() + "ELoadMedicines";
        }

        public static String LOAD_VACCINE_BY_MED() {
            return getController() + "ELoadVaccineByMed";
        }

        public static String REMOVE_VACCINE_RECORD() {
            return getController() + "ERemoveVaccineRecord";
        }

        public static String GET_VAC_INFO() {
            return getController() + "GetVacInfo";
        }

        public static String GET_SYSTEM_POST() {
            return getController() + "GetSystemPost";
        }

        public static String BUILD_REPORT() {
            return getController() + "EBuildReport";
        }


    }

    public static class ProductService {

        private static String getController() {
            return version() + "ProductService/";
        }


        //Product
        public static String NEW_CATEGORY() {
            return getController() + "NewCategory";
        }

        public static String CATEGORY() {
            return getController() + "Category";
        }

        public static String PRODUCT_CATEGORIES() {
            return getController() + "Categories";
        }

        public static String NEW_PRODUCT() {
            return getController() + "NewProduct";
        }

        public static String PRODUCT() {
            return getController() + "Product";
        }

        public static String NEW_PRODUCT_DOC() {
            return getController() + "NewProductDoc";
        }

        public static String PRODUCT_DOC() {
            return getController() + "ProductDoc";
        }

        public static String EXIST_PRODUCT_DOC() {
            return getController() + "ExistProductDoc";
        }

        public static String PRODUCT_SERVICES() {
            return getController() + "ProductServices";
        }

        public static String NEW_SERVICE_ITEM() {
            return getController() + "NewServiceItem";
        }

        public static String SERVICE_ITEMS() {
            return getController() + "ServiceItems";
        }

        public static String SERVICE_ITEM() {
            return getController() + "ServiceItem";
        }

        public static String EXIST_SERVICE_ITEM() {
            return getController() + "ExistServiceItem";
        }

    }

    public static class Org {

        private static String getController() {
            return version() + "Org/";
        }


        //Product
        public static String PRODUCTS() {
            return getController() + "Products";
        }

        // Staffs
        public static String MOVE_STAFFS() {
            return getController() + "MoveStaffs";
        }

        public static String ADD_STAFFS() {
            return getController() + "AddStaffs";
        }

        public static String REMOVE_STAFFS() {
            return getController() + "RemoveStaffs";
        }

        public static String LOAD_STAFFS() {
            return getController() + "LoadStaffs";
        }

        public static String SET_ORG_MANAGER() {
            return getController() + "SetOrgManager";
        }

        public static String VERIFY_STAFFS() {
            return getController() + "VerifyStaffs";
        }

        public static String SET_ORG_ADMIN() {
            return getController() + "SetOrgAdmin";
        }

        public static String SET_HIDE_STAFFS() {
            return getController() + "SetHideStaffs";
        }

        // Department
        public static String ADD_DEPARTMENT() {
            return getController() + "AddDepartment";
        }

        public static String UPDATE_DEPARTMENT() {
            return getController() + "UpdateDepartment";
        }

        public static String REMOVE_DEPARTMENT() {
            return getController() + "RemoveDepartment";
        }

        public static String LOAD_DEPARTMENTS() {
            return getController() + "LoadDepartments";
        }

        //Org

        public static String CURRENCY() {
            return getController() + "Currency";
        }

        public static String ORG_FEATURE_ATTRIBUTE_FOR_STAFF() {
            return getController() + "OrgFeatureAttributes4Staff";
        }

        public static String CONSULT_CONFIG() {
            return getController() + "ConsultConfig";
        }

        public static String CONSULT_CONFIG_VALUE() {
            return getController() + "ConsultConfigValue";
        }


        public static String GET_ORGS_MANAGE() {
            return getController() + "GetOrgsManage";
        }

        public static String ADD_SPECIALTIES() {
            return getController() + "AddSpecialties";
        }

        public static String REMOVE_SPECIALTIES() {
            return getController() + "RemoveSpecialties";
        }

        public static String CHECK_MANAGE_ORG() {
            return getController() + "CheckManageOrg";
        }

        public static String CHECK_ADMIN_ORG() {
            return getController() + "CheckAdminOrg";
        }

        public static String QR_CODE() {
            return getController() + "QRCode";
        }

        public static String GET_ORG_INFO() {
            return getController() + "GetOrgInfo";
        }

        public static String GET_SPECIALTIES() {
            return getController() + "GetSpecialties";
        }

        public static String PROVIDER() {
            return getController() + "Provider";
        }

        public static String GET_ORG_ATTRIBUTES() {
            return getController() + "GetOrgAttributes";
        }

        public static String FEATURE_ATTRIBUTES() {
            return getController() + "FeatureAttributes";
        }

        public static String UPDATE_INTRO() {
            return getController() + "UpdateIntro";
        }

        public static String UPDATE_ORG_INFO() {
            return getController() + "UpdateOrgInfo";
        }

        public static String GET_ORG_ATTRIBUTE() {
            return getController() + "GetOrgAttributes";
        }

        public static String REFRESH_API_KEY() {
            return getController() + "RefreshApiKey";
        }

        public static String NEW_DOCTOR() {
            return getController() + "NewDoctor";
        }

        public static String PWD_DOCTOR() {
            return getController() + "PwdDoctor";
        }

        public static String UI_TYPE() {
            return getController() + "UIType";
        }

        public static String TELEMEDICINE_DOCTOR() {
            return getController() + "TelemedicineDoctor";
        }


        //Profile
        public static String DEFAULT_COVER() {
            return getController() + "DefaultCover";
        }

        public static String UPDATE_ORG_ATTRIBUTE() {
            return getController() + "UpdateOrgAttribute";
        }

        public static String UPDATE_ORG_TYPE() {
            return getController() + "UpdateOrgType";
        }

        public static String COVER() {
            return getController() + "Cover";
        }

        //Rating
        public static String LOAD_RATING() {
            return getController() + "LoadRating";
        }

        public static String LOAD_USERS_TO_ADD_STAFFS() {
            return getController() + "LoadUsersToAddStaffs";
        }

        public static String ORG_STAFF_ATTRIBUTES() {
            return getController() + "orgStaffAttributes";
        }

        public static String STAFF_FEATURE_ATTRIBUTES() {
            return getController() + "StaffFeatureAttributes";
        }

        public static String CHECK_PICK_UP_CHAT() {
            return getController() + "CheckPickUpChat";
        }

        //Branch
        public static String CREATE_BRANCH() {
            return getController() + "ECreateBranch";
        }

        public static String UPDATE_BRANCH() {
            return getController() + "EUpdateBranch";
        }

        public static String LOAD_BRANCHS() {
            return getController() + "LoadBranches";
        }

        public static String REMOVE_BRANCH() {
            return getController() + "RemoveBranch";
        }


        public static String ACCESS_STAFF() {
            return getController() + "AccessStaff";
        }

        public static String PAYMENT_TYPES() {
            return getController() + "paymentTypes";
        }
        public static String USER_TRIAL_ADD() {
            return getController() + "UserTrialAdd ";
        }

        public static String USER_TRIAL_REMOVE() {
            return getController() + "UserTrialRemove ";
        }


    }

    public static class OrgManager {
        private static String getController() {
            return version() + "OrgManager/";
        }


        public static String CREATE_ORG() {
            return getController() + "CreateOrg";
        }

        public static String LOAD_ORGS() {
            return getController() + "LoadOrgs";
        }

        public static String CONTACT_ORG() {
            return getController() + "ContactOrg";
        }

        public static String CHANGE_ORG_ATTRIBUTE() {
            return getController() + "ChangeOrgAttribute";
        }

        public static String DELETE_ES_DATA() {
            return getController() + "DeleteESData";
        }

        public static String ORG_LOAD_HEALTH_DOCS() {
            return getController() + "OrgLoadHealthDocs";
        }

        public static String ORG_LOAD_SUB_ACCOUNTS() {
            return getController() + "OrgLoadSubAccounts";
        }

        public static String CHANGE_ORG_STATUS() {
            return getController() + "ChangeOrgStatus";
        }

        public static String ORG_APPOINTMENT_TYPE() {
            return getController() + "OrgAppointmentType";
        }

        public static String CHANGE_ORG_APPOINTMENT_TYPE() {
            return getController() + "ChangeOrgAppointmentType";
        }

        public static String CHANGE_ORG_TYPE() {
            return getController() + "ChangeOrgType";
        }

        public static String VERIFY_ORG() {
            return getController() + "VerifyOrg";
        }

        public static String ORG_FEATURE_ATTRIBUTE() {
            return getController() + "OrgFeatureAttribute";
        }

        public static String CUSTOMER_CARE_SETTING() {
            return getController() + "CustomerCareSetting";
        }

        public static String CUSTOMER_CARE() {
            return getController() + "CustomerCare";
        }

        public static String ORG_PMS() {
            return getController() + "OrgPMs";
        }

        public static String ORG_PM_UPDATE() {
            return getController() + "OrgPMUpdate";
        }


        public static String NEW_ORG_PM() {
            return getController() + "NewOrgPM";
        }

    }

    public static class OrgMember {
        private static String getController() {
            return version() + "OrgMember/";
        }


        public static String LOAD_GROUPS() {
            return getController() + "LoadGroups";
        }

        public static String LOAD_MEMBERS() {
            return getController() + "LoadMembers";
        }

        public static String ADD_GROUP() {
            return getController() + "AddGroup";
        }

        public static String UPDATE_GROUP() {
            return getController() + "UpdateGroup";
        }

        public static String ADD_MEMBER_TO_GROUP() {
            return getController() + "AddMemberToGroup";
        }

        public static String DELETE_GROUP() {
            return getController() + "DeleteGroup";
        }

        public static String LOAD_MEMBERS_BY_GROUP() {
            return getController() + "LoadMembersByGroup";
        }

        public static String REMOVE_MEMBER_OUT_GROUP() {
            return getController() + "RemoveMemberOutGroup";
        }

        public static String ORG_ADD_HEALTH_DOC() {
            return getController() + "OrgAddHealthDoc";
        }

        public static String ADD_MEMBER() {
            return getController() + "AddMember";
        }

        public static String SEND_SMS() {
            return getController() + "SendSMS";
        }

        public static String LOAD_LOG_SMS() {
            return getController() + "LoadLogSMS";
        }

        //Dashboard
        public static String STATISTIC_BY_GENDER() {
            return getController() + "StatisticByGender";
        }

        public static String STATISTIC_BY_COUNTRY() {
            return getController() + "StatisticByCountry";
        }

        public static String STATISTIC_BY_CITY() {
            return getController() + "StatisticByCity";
        }

        public static String STATISTIC_GROWTH_CHART() {
            return getController() + "StatisticGrowthChart";
        }

        public static String PATIENT_ID_MEMBER() {
            return getController() + "PatientIdMember";
        }

    }

    public static class Provider {


        private static String getController() {
            return version() + "Provider/";
        }

        public static String ACCEPT_QUESTION() {
            return getController() + "EAcceptQuestion";
        }

        public static String GIVE_BACK_QUESTION() {
            return getController() + "GiveBackQuestion";
        }

        public static String COMPLETE_QUESTION() {
            return getController() + "ECompleteQuestion";
        }

        public static String DECLINE_QUESTION() {
            return getController() + "EDeclineQuestion";
        }

        public static String SEARCH_USER() {
            return getController() + "SearchUser";
        }

        public static String GIFT_POINT() {
            return getController() + "GiftPoint";
        }
        public static String REPORT_ORGS() {
            return getController() + "ReportOrgs";
        }
        public static String REPORT_FEES() {
            return getController() + "ReportFees";
        }

        public static String LOG_RECEIVER_FEES() {
            return getController() + "LogReceiverFees";
        }

    }

    public static class Search {
        private static String getController() {
            return version() + "Search/";
        }


        public static String MEDICAL_SERVICES() {
            return getController() + "MedicalServices";
        }

        public static String MEDICAL_WORKERS() {
            return getController() + "MedicalWorkers";
        }

        public static String MEDICAL_ORGS() {
            return getController() + "MedicalOrgs";
        }
    }

    public static class SubAccount {

        private static String getController() {
            return version() + "SubAccount/";
        }


        public static String CREATE_SUB_ACCOUNT() {
            return getController() + "ECreateSubAccount";
        }

        public static String LOAD_SUB_ACCOUNT() {
            return getController() + "ELoadSubAccounts";
        }

        public static String UPDATE_SUB_ACCOUNT() {
            return getController() + "EUpdateSubAccount";
        }

        public static String CONNECTIONS_TO_SHARE() {
            return getController() + "EConnectionsToShare";
        }

        public static String LIST_SHARED_ACCOUNT() {
            return getController() + "EListSharedAccount";
        }

        public static String ACCOUNT_SHARE() {
            return getController() + "EAccountShare";
        }

        public static String SHARED_ACCOUNT() {
            return getController() + "ESharedAccount";
        }

        public static String SUB_ACCOUNT_SHARING() {
            return getController() + "ESubAccountSharing";
        }

        public static String REMOVE_SUB_ACCOUNT() {
            return getController() + "ERemoveSubAccount";
        }

        public static String SUB_ACCOUNTS() {
            return getController() + "ESubAccount";
        }

        public static String SHARED_ACCOUNT_INFORMATION() {
            return getController() + "ESharedAccountInformation";
        }

        public static String FAMILY_MEMBERS() {
            return getController() + "EFamilyMembers";
        }
    }

    public static class User {
        private static String getController() {
            return version() + "User/";
        }

        public static String ADD_SPECIALTIES() {
            return getController() + "EAddSpecialties";
        }

        public static String GET_SPECIALTIES() {
            return getController() + "EGetSpecialties";
        }

        public static String REMOVE_SPECIALTIES() {
            return getController() + "ERemoveSpecialties";
        }

        public static String QR_CODE() {
            return getController() + "EQRCode";
        }

        public static String QR_CODE_SCAN() {
            return getController() + "EQRCodeScan";
        }

        public static String CONFIG() {
            return getController() + "Econfig";
        }

        public static String REQUEST_JOIN_ORGS() {
            return getController() + "ERequestJoinOrgs";
        }

        public static String LOAD_JOINED_ORGS() {
            return getController() + "ELoadJoinedOrgs";
        }

        public static String REMOVE_JOINED_ORGS() {
            return getController() + "ERemoveJoinedOrg";
        }

        public static String LOAD_ORGS() {
            return getController() + "ELoadOrgs";
        }

        public static String ACCEPT_JOIND_ORG() {
            return getController() + "EAcceptJoinOrg";
        }

        public static String DECLINE_JOIND_ORG() {
            return getController() + "EDeclineJoinOrg";
        }

        public static String GET_USER_INFO() {
            return getController() + "EGetUserInfo";
        }

        public static String USER_INFO() {
            return getController() + "EUserInfo";
        }

        public static String FEEDBACK() {
            return getController() + "EFeedBack";
        }

        public static String LOAD_USER_BIO() {
            return getController() + "ELoadUserBio";
        }

        public static String ADD_USER_BIO() {
            return getController() + "EAddUserBio";
        }

        public static String UPDATE_USER_BIO() {
            return getController() + "EUpdateUserBio";
        }

        public static String REMOVE_USER_BIO() {
            return getController() + "ERemoveUserBio";
        }

        public static String UPLOAD_CERTIFICATE() {
            return getController() + "UploadCertificate";
        }

        public static String REMOVE_CERTIFICATE() {
            return getController() + "ERemoveCertificate";
        }

        public static String UPDATE_REQUIRE_INFO() {
            return getController() + "EUpdateRequireInfo";
        }

        public static String REQUIRE_INFO() {
            return getController() + "ERequireInfo";
        }

        public static String PAGE_DASH_BOARDS() {
            return getController() + "EPageDashBoards";
        }

        public static String FEATURES_OF_PAGE() {
            return getController() + "EFeaturesOfPage";
        }

        public static String QR_CODE_GET_DETAIL() {
            return getController() + "EQRCodeGetDetail";
        }

    }

    public static class Voucher {

        private static String getController() {
            return version() + "Voucher/";
        }
        
        public static String GET_CATEGORIES() {
            return getController() + "GetCategories";
        }

        public static String LOAD_VOUCHERS() {
            return getController() + "LoadVouchers";
        }

        public static String GET_VOUCHER() {
            return getController() + "GetVoucher";
        }

        public static String BUY_VOUCHER() {
            return getController() + "BuyVoucher";
        }

        public static String GET_MY_VOUCHERS() {
            return getController() + "GetMyVouchers";
        }

        public static String USE_VOUCHER() {
            return getController() + "UseVoucher";
        }

        public static String LOAD_VOUCHERS_ALLOW_ANONYMOUS() {
            return getController() + "LoadVouchersAllowAnonymous";
        }

        public static String COUNT_MY_VOUCHER() {
            return getController() + "CountMyVoucher";
        }

        public static String GET_REVIEW() {
            return getController() + "GetReview";
        }

        public static String REVIEW() {
            return getController() + "Review";
        }

        public static String CLICK_VOUCHER_LINK() {
            return getController() + "ClickVoucherLink";
        }

    }

    public static class VoucherManager {
        private static String getController() {
            return version() + "VoucherManager/";
        }



        public static String LOAD_CATEGORIES() {
            return getController() + "LoadCategories";
        }

        public static String CREATE_CATEGORY() {
            return getController() + "CreateCategory";
        }

        public static String UPDATE_CATEGORIES() {
            return getController() + "UpdateCategory";
        }

        public static String CREATE_VOUCHER() {
            return getController() + "CreateVoucher";
        }

        public static String UPDATE_VOUCHER() {
            return getController() + "UpdateVoucher";
        }

        public static String LOAD_VOUCHER() {
            return getController() + "LoadVouchers";
        }

        public static String UPDATE_VOUCHER_ICON() {
            return getController() + "UploadVoucherIcon";
        }

        public static String ADD_CARDS() {
            return getController() + "AddCards";
        }

        public static String LOAD_GIFT_CARDS() {
            return getController() + "LoadGiftCards";
        }

        public static String UPDATE_VOUCHER_HOT_ICON() {
            return getController() + "UploadVoucherHotIcon";
        }

        public static String APPROVE_VOUCHER() {
            return getController() + "ApproveVoucher";
        }

        public static String DEACTIVE_VOUCHER() {
            return getController() + "DeActiveVoucher";
        }

        public static String SET_HOT_VOUCHER() {
            return getController() + "SetHotVoucher";
        }

        public static String SET_POPULAR_VOUCHER() {
            return getController() + "SetPopularVoucher";
        }

        public static String ADD_CARD_AUTO() {
            return getController() + "AddCardAuto";
        }

        public static String LOAD_VOUCHERS() {
            return getController() + "LoadVouchers";
        }

        public static String DEACTIVE_CATEGORY() {
            return getController() + "DeActiveCategory";
        }

        public static String UPDATE_CATEGORY() {
            return getController() + "UpdateCategory";
        }

        public static String VOUCHER_CLICK_USERS() {
            return getController() + "VoucherClickUsers";
        }

        public static String EXPORT_CLICK_USERS() {
            return getController() + "ExportClickUsers";
        }
    }

    public static class Account {

        private static String getController() {
            return version() + "Account/";
        }


        public static String REFRESH_TOKEN() {
            return getController() + "ERefreshToken";
        }

        public static String SIGN_IN() {
            return getController() + "ESignIn";
        }

        public static String VERIFY_SIGN_UP() {
            return getController() + "EVerifySignUp";
        }

        public static String SEND_VERIFY_CODE() {
            return getController() + "ESendVerifyCode";
        }

        public static String SIGN_UP() {
            return getController() + "ESignUp";
        }

        public static String CHANGE_PASSWORD() {
            return getController() + "EChangePassword";
        }

        public static String LOG_OUT() {
            return getController() + "ELogout";
        }

        public static String GET_PERSONAL_INFO() {
            return getController() + "GetPersonalInfo";
        }

        public static String RECOVERY_PASSWORD() {
            return getController() + "ERecoveryPassword";
        }

        public static String CONFIRM_RECOVERY_PASSWORD() {
            return getController() + "EConfirmRecoveryPassword";
        }

        public static String UPDATE_NEW_PASSWORD() {
            return getController() + "EUpdateNewPassword";
        }

        public static String VERIFY_PHONE() {
            return getController() + "VerifyPhone";
        }

        public static String UPDATE_PHONE() {
            return getController() + "UpdatePhone";
        }

        public static String UPDATE_PERSONAL_INFO() {
            return getController() + "EUpdatePersonalInfo";
        }

        public static String UPDATE_LANGUAGE() {
            return getController() + "UpdateLanguage";
        }

        public static String UPLOAD_AVATAR() {
            return getController() + "UploadAvatar";
        }

        public static String SIGN_IN_INFO() {
            return getController() + "SignInInfo";
        }

        public static String EXIT_SYSTEM() {
            return getController() + "ExitSystem";
        }

    }

    public static class Auditor {

        private static String getController() {
            return version() + "Auditor/";
        }

        public static String APPROVE_QUESTION() {
            return getController() + "EApproveQuestion";
        }

        public static String REJECT_QUESTION() {
            return getController() + "ERejectQuestion";
        }
    }

    public static class Chat {


        private static String getController() {
            return version() + "Chat/";
        }


        public static String CREATE_CONVERSATION() {
            return getController() + "ECreateConversation";
        }

        public static String LOAD_CONVERSATION() {
            return getController() + "ELoadConversation";
        }

        public static String LOAD_CONVERSATIONS() {
            return getController() + "ELoadConversations";
        }

        public static String LOAD_RECIPIENTS() {
            return getController() + "ELoadRecipients";
        }

        public static String COUNT_RECIPIENTS() {
            return getController() + "ECountRecipients";
        }

        public static String LOAD_MSGS() {
            return getController() + "ELoadMsgs";
        }

        public static String SEND_MESSAGE() {
            return getController() + "ESendMessage";
        }

        public static String FIND_CONVERSATION() {
            return getController() + "EFindConversation";
        }

        public static String USER_GROUPS() {
            return getController() + "UserGroups";
        }

        public static String ACTIONS() {
            return getController() + "actions";
        }

        public static String LOAD_CAMPAIGNS() {
            return getController() + "ELoadCampaigns";
        }

        public static String CREATE_TEMPMSG() {
            return getController() + "ECreateTempMsg";
        }

        public static String ADD_MESSAGE() {
            return getController() + "EAddMessage";
        }

        public static String OPEN_MSG() {
            return getController() + "EOpenMsg";
        }

        public static String CONVERSATION_NAME() {
            return getController() + "EConversationName";
        }

        public static String CONVERSATION_RECIPIENT() {
            return getController() + "EConversationRecipient";
        }

        public static String GET_ACTIONS() {
            return getController() + "EGetActions";
        }

        public static String CONVERSATION_AVATAR() {
            return getController() + "ConversationAvatar";
        }

        public static String STATISTIC_CAMPAIGNS() {
            return getController() + "EStatisticCampaigns";
        }

        public static String USER_UNREAD() {
            return getController() + "EUserUnRead";
        }

        public static String USER_UNREAD_TO_GROUP() {
            return getController() + "EUserUnReadToGroup";
        }

        public static String USER_UNCLICKED_TO_GROUP() {
            return getController() + "EUserUnClickedToGroup";
        }

        public static String USER_UNCLICKED() {
            return getController() + "EUserUnClicked";
        }

        public static String USER_CLICKED_TO_GROUP() {
            return getController() + "EUserClickedToGroup";
        }

        public static String USER_CLICKED() {
            return getController() + "EUserClicked";
        }

        public static String CLICK_MSG() {
            return getController() + "EClickMsg";
        }

        public static String APPOINTMENT_CANCEL() {
            return getController() + "AppointmentCancel";
        }

        public static String APPOINTMENT_SET_TIME() {
            return getController() + "AppointmentSetTime";
        }

        public static String GET_SEEN() {
            return getController() + "EGetSeen";
        }

        public static String UPLOAD_MSG_DOCS() {
            return getController() + "UploadMsgDocs";
        }

        public static String DONE_PICK_UP() {
            return getController() + "DonePickUp ";
        }

        public static String CUSTOMER_CARES() {
            return getController() + "customerCares";
        }

        public static String SUB_CONVERSATIONS() {
            return getController() + "SubConversations";
        }

        public static String CONVERSATION_TYPES() {
            return getController() + "conversationTypes";
        }

        public static String TURN_ON_CUSTOMER_CARE() {
            return getController() + "TurnOnCustomerCare";
        }

        public static String CUSTOMER_CARE_STATUS() {
            return getController() + "CustomerCareStatus";
        }

        public static String CALL() {
            return getController() + "Call";
        }

        public static String RECEIVE() {
            return getController() + "Receive";
        }

        public static String RECEIVER_BUSY() {
            return getController() + "Busy";
        }

        public static String MISS() {
            return getController() + "Miss";
        }

        public static String END() {
            return getController() + "End";
        }


    }

    public static class Connection {

        private static String getController() {
            return version() + "Connection/";
        }

        public static String LOAD_CONNECTIONS() {
            return getController() + "LoadConnections";
        }

        public static String REQUEST_CONNECTION() {
            return getController() + "RequestConnection";
        }

        public static String LOAD_REQUEST_CONNECTION() {
            return getController() + "LoadRequestConnection";
        }

        public static String ACCEPT_CONNECTION() {
            return getController() + "AcceptConnection";
        }

        public static String DECLINE_CONNECTION() {
            return getController() + "DeclineConnection";
        }

        public static String SEARCH_USER() {
            return getController() + "SearchUser";
        }

        public static String REMOVE_CONNECTION_USER() {
            return getController() + "RemoveConnectionUser";
        }

        public static String SEARCH_ORGS() {
            return getController() + "SearchOrgs";
        }

        public static String FOLLOW_ORG() {
            return getController() + "FollowOrg";
        }

        public static String LOAD_FOLLOW_ORGS() {
            return getController() + "LoadFollowedOrgs";
        }

        public static String PHONE_CONTACT() {
            return getController() + "PhoneContact";
        }

        public static String INVITATION() {
            return getController() + "Invitation";
        }

        public static String LOAD_GROUPS() {
            return getController() + "LoadGroups";
        }

        public static String CREATE_GROUP() {
            return getController() + "CreateGroup";
        }

        public static String RENAME_GROUP() {
            return getController() + "RenameGroup";
        }

        public static String REMOVE_GROUP() {
            return getController() + "RemoveGroup";
        }

        public static String ADD_USER_TO_GROUP() {
            return getController() + "AddUserToGroup";
        }

        public static String ADD_GROUP_FOR_USER() {
            return getController() + "AddGroupForUser";
        }

        public static String REMOVE_USER_IN_GROUP() {
            return getController() + "RemoveUserInGroup";
        }

        public static String COUNT_REQUEST_CONNECTION() {
            return getController() + "CountRequestConnection";
        }
    }

    public static class Consult {

        private static String getController() {
            return version() + "Consult/";
        }

        public static String ADD_COMMENT() {
            return getController() + "EAddComment";
        }

        public static String GET_COMMENTS() {
            return getController() + "EGetComments";
        }

        public static String GET_ACTIONS() {
            return getController() + "EGetActions";
        }

        public static String GET_QUESTION() {
            return getController() + "EGetQuestion";
        }

        public static String LOAD_QUESTIONS() {
            return getController() + "ELoadQuestions";
        }

        public static String ADD_FILE() {
            return getController() + "AddFiles";
        }

        public static String APPOINTMENT_CANCEL() {
            return getController() + "AppointmentCancel";
        }

        public static String APPOINTMENT_SET_TIME() {
            return getController() + "AppointmentSetTime";
        }

        public static String REVIEW_QUESTION() {
            return getController() + "EReviewQuestion";
        }

        public static String QUESTION_REVIEWS() {
            return getController() + "EQuestionReviews";
        }

        public static String ADD_HEALTH_DOC() {
            return getController() + "AddHealthDoc";
        }

        public static String MODE() {
            return getController() + "Mode";
        }

        public static String DOCTORS() {
            return getController() + "Doctors";
        }


    }

    public static class Coordinator {

        private static String getController() {
            return version() + "Coordinator/";
        }

        public static String ASSIGN_QUESTION() {
            return getController() + "EAssignQuestion";
        }

        public static String LOAD_DOCTOR_ASSIGN() {
            return getController() + "ELoadDoctorAssign";
        }

        public static String LOAD_DOCTOR_AUDIT() {
            return getController() + "ELoadDoctorAudit";
        }

        public static String SUBMIT_AUDIT() {
            return getController() + "ESubmitAudit";
        }

        public static String CLOSE_QUESTION() {
            return getController() + "ECloseQuestion";
        }

        public static String SPAM_QUESTION() {
            return getController() + "SpamQuestion";
        }

        public static String SEND_AUDITOR_NOTI() {
            return getController() + "ESendAuditorNoti";
        }

        public static String SEND_DOCTOR_NOTI() {
            return getController() + "ESendDoctorNoti";
        }

        public static String SEND_PATIENT_NOTI() {
            return getController() + "SendPatientNoti";
        }


        public static String GET_ACTIVITY_LOG() {
            return getController() + "EGetActivityLog";
        }

        public static String EXPORT_QUESTION_STATICTIS() {
            return getController() + "EExportQuestionStatictis";
        }

        public static String LOAD_QUESTION_STATICTIS() {
            return getController() + "ELoadQuestionStatictis";
        }


    }

    public static class Changelog {
        private static String getController() {
            return version() + "ChangeLog/";
        }

        public static String NEW() {
            return getController() + "New";
        }

        public static String LIST() {
            return getController() + "List";
        }

        public static String LOGS() {
            return getController() + "Logs";
        }

        public static String UPDATE() {
            return getController() + "Update";
        }

        public static String REMOVE() {
            return getController() + "Remove";
        }
    }

    public static class Health {

        private static String getController() {
            return version() + "Health/";
        }

        public static String DELETE_HEALTH_DATA() {
            return getController() + "EDeleteHealthData";
        }

        public static String LOAD_LAST_VITALS() {
            return getController() + "ELoadLastVitals";
        }

        public static String UPDATE_MEASUREMENT_UNIT() {
            return getController() + "EUpdateMeasurementUnit";
        }

        public static String LOAD_STATUS() {
            return getController() + "ELoadStatus";
        }

        public static String UPDATE_DESCRIPTION() {
            return getController() + "EUpdateDescription";
        }

        public static String ADD_INFORMATION() {
            return getController() + "EAddInformation";
        }

        public static String HEALTH_DASHBOARD() {
            return getController() + "HealthDashboard";
        }

        // --> AFTER VISIT *** //
        public static String VISIT_MEDICAL_HISTORY() {
            return getController() + "EVisitMedicalHistory";
        }

        public static String VISIT_MEDICAL_DATA() {
            return getController() + "VisitMedicalData";
        }

        public static String NEW_VISIT_DATA() {
            return getController() + "ENewVisitData";
        }

        public static String VISIT_DATA() {
            return getController() + "EVisitData";
        }

        public static String NEW_VISIT_DOC() {
            return getController() + "NewVisitDoc";
        }

        public static String VISIT_DATAS() {
            return getController() + "EVisitDatas";
        }

        public static String VISITED_ACCESS() {
            return getController() + "EVisitedAccess";
        }
        // --> AFTER VISIT *** //


        // --> HISTORY *** //
        public static String SAVE_BLOOD_TYPE() {
            return getController() + "ESaveBloodType";
        }

        public static String HEALTH_HISTORY() {
            return getController() + "EHealthHistory";
        }

        public static String HEALTH_HISTORY_INFO() {
            return getController() + "EHealthHistoryInfo";
        }

        public static String PDF831() {
            return getController() + "PDF831";
        }

        // *** HISTORY <-- //

        // --> BMI *** //
        public static String ADD_BMI() {
            return getController() + "EAddBMI";
        }

        public static String LOAD_LAST_BMI() {
            return getController() + "ELoadLastBMI";
        }

        public static String LOAD_DATA_BMI() {
            return getController() + "ELoadDataBMI";
        }

        public static String LOAD_CHART_BMI() {
            return getController() + "ELoadChartBMI";
        }
        // *** BMI <-- //


        // --> GLUCOSE *** //
        public static String ADD_BLOOD_GLUCOSE() {
            return getController() + "EAddBloodGlucose";
        }

        public static String LOAD_BLOOD_GLUCOSE() {
            return getController() + "ELoadBloodGlucose";
        }

        public static String LOAD_CHART_BLOOD_GLUCOSE() {
            return getController() + "ELoadChartBloodGlucose";
        }

        // *** GLUCOSE <-- //

        // --> BLOOD PRESSURE *** //
        public static String ADD_BLOOD_PRESSURE() {
            return getController() + "EAddBloodPressure";
        }

        public static String LOAD_BLOOD_PRESSURE() {
            return getController() + "ELoadBloodPressure";
        }

        public static String LOAD_CHART_BLOOD_PRESSURE() {
            return getController() + "ELoadChartBloodPressure";
        }
        // *** BLOOD PRESSURE <-- //


        // --> Growth Target *** //
        public static String UPDATE_GROWTH_TARGET() {
            return getController() + "EUpdateGrowthTarget";
        }

        public static String LOAD_GROWTH_TARGET() {
            return getController() + "ELoadGrowthTarget";
        }

        public static String LOAD_GROWTH_TARGET_FULL() {
            return getController() + "ELoadGrowthTargetFull";
        }

        public static String LOAD_GROWTH_CHART() {
            return getController() + "ELoadGrowthChart";
        }

        // --> Growth Target *** //

        // --> Health Docs *** //
        public static String LOAD_HEALTH_DOCS() {
            return getController() + "ELoadHealthDocs";
        }

        public static String ADD_HEALTH_DOCS() {
            return getController() + "AddHealthDocs";
        }

        public static String MOVE_HEALTH_DOCS() {
            return getController() + "EMoveHealthDocs";
        }

        public static String REMOVE_HEALTH_DOCS() {
            return getController() + "ERemoveHealthDocs";
        }
        // --> Health Docs *** //

        // --> Health Prescription *** //
        public static String PRESCRIPTIONS() {
            return getController() + "EPrescriptions";
        }
        // --> Health Prescription *** //


    }


    public static class HL7 {
        private static String getController() {
            return version() + "HL7s/";
        }

        public static String INFO_INSURANCE_CARD() {
            return getController() + "InfoInsuranceCard";
        }
    }

    public static class Wallet {
        private static String getController() {
            return version() + "Wallet/";
        }

        public static String POINTS() {
            return getController() + "points";
        }

        public static String GET_POINT_INTRUCT() {
            return getController() + "GetPointIntruct";
        }

        public static String TRANS_POINT() {
            return getController() + "TransPoint";
        }

        public static String USE_POINT_NOTI() {
            return getController() + "UsePointNoti";
        }
        public static String USED_POINT_HISTORY_DETAIL() {
            return getController() + "UsedPointHistoryDetail";
        }

    }

    public static class Payment {
        private static String getController() {
            return version() + "PM/";
        }

        public static String PRODUCT() {
            return getController() + "Product";
        }
        public static String PAYOO_ORDER() {
            return getController() + "PayooOrder";
        }
    }

}
