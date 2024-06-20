@regression @health @health-after-visit-summary
Feature: After Visit for DHYD2

  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_7" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     |
      | BV_MAT_BAO | Hospital | manager_1 |

    Given I login as "manager_1"
    And I accept join organization
    And I select org "BV_MAT_BAO" that I manage
    And I refresh api key
    Then The request should be succeed
    And The "data.apiKey" should "not empty"


  @health-after-visit-summary-1
  Scenario Outline: DHYD send visit medical doc to existed main account
    Given I login as "user_7"
    # user update profile
    When I update my profile with below info
      | displayName   | dob   | email                             | address                                     | country   | gender   | phone    |
      | <displayName> | <dob> | changtraicute111@bacsitoancau.com | 10 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | <country> | <gender> | <vPhone> |
    Then The request should be succeed
    And I log out
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # user load visit medical
    Given I login as "user_7"
    When I switch main of family members with below info
      | name |
      |      |
    And I load visit medical
    Then The response should be
      | success           | true       |
      | data.totalCount   | 1          |
      | data.list[0].name | BV_MAT_BAO |
    When I load visit medical details
    Then The request should be succeed

    And I load notifications
      | groupType  |
      | HealthNoti |
    Then List "data.list[*].message" of response should contains
      | Update after visit summary |

    And I open noti after visit
      | groupType  | message                    |
      | HealthNoti | Update after visit summary |
    Then The response should be
      | success | true |

    # manager search member
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
    And I load org members
      | displayName   |
      | <displayName> |
    Then The response should be
      | data.list[0].displayName | <displayName> |
      | data.list[0].patientId   | <vPatientId>  |
    Examples:
      | displayName          | dob        | gender | country | vPatientId    | vDisplayName  | vDoB       | vGender | vPhone     |
      | Tom Hiddleston       | 1969-09-10 | 1      | VN      | pid0000000017 | Tom           | 1969       | nam     | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 2      | VN      | pid0000000017 | Ngô Văn Duy   | 1969-09-10 | nữ      | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 2      | VN      | pid0000000017 | Duy           | 1969-09-10 | nữ      | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 2      | VN      | pid0000000017 | Văn Duy       | 1969-09-10 | nữ      | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 2      | VN      | pid0000000017 | Duy           | 1969       | nữ      | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 2      | VN      | pid0000000017 | Duy           | 1969-09    | nữ      | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 1      | VN      | pid0000000017 | Duy           | 1969       | nam     | 0000000017 |
      | Ngô Văn Duy          | 1969-09-10 | 1      | VN      | pid0000000017 | Duy           | 1969       | nam     | 0000000017 |
      | Duy Ngo              | 1969-09-10 | 1      | VN      | pid0000000017 | Duy           | 1969       | nam     | 0000000017 |
      | Bác sĩ Trần Thanh An | 1969-09-10 | 1      | VN      | pid0000000017 | Trần Thanh An | 1969       | nam     | 0000000017 |
      | Duy ahihi            | 1969-09-10 | 2      | VN      | pid0000000017 | Duy           | 1969-09    | nữ      | 0000000017 |

  @health-after-visit-summary-2
  Scenario Outline: DHYD send visit medical doc to main account that is not existed
    Given On SqlServer, I delete user by username "<vPhone>" and country "<country>"
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # manager search member
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
    And I load org members
      | displayName   |
      | <displayName> |
    Then The response should be
      | data.list[0].displayName | <displayName> |
      | data.list[0].patientId   | <vPatientId>  |
    Examples:
      | displayName | country | vPatientId    | vDisplayName | vDoB       | vGender | vPhone     |
      | KO_TON_TAI  | VN      | pidKO_TON_TAI | KO_TON_TAI   | 1969-09-10 | nữ      | 0333111222 |

  @health-after-visit-summary-3
  Scenario Outline: DHYD send visit medical doc to existed sub-account
    Given I login as "user_7"
    # user update profile
    When I add new sub-account
      | displayName   | dob   | carerType | gender   | country                                               | city                                  | district                        | ward                                |
      | <displayName> | <dob> | 1         | <gender> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    And I log out
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # user load visit medical
    Given I login as "user_7"
    When I switch sub-account of logged user with below info
      | name          |
      | <displayName> |

    And I load visit medical
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    When I load visit medical details
    Then The request should be succeed
    # manager search member
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
    And I load org members
      | displayName   |
      | <displayName> |
    Then The response should be
      | data.list[0].displayName | <displayName> |
      | data.list[0].patientId   | <vPatientId>  |
    Examples:
      | displayName | dob        | gender | country | vPatientId    | vDisplayName | vDoB | vGender | vPhone     |
      | Ngô Văn An  | 1969-09-10 | 2      | VN      | pid0000000017 | An           | 1969 | nữ      | 0000000017 |
      | Ngô Văn An  | 1969-09-10 | 2      | VN      | pid0000000017 | Văn An       | 1969 | nữ      | 0000000017 |


  @health-after-visit-summary-4
  Scenario Outline: DHYD send visit medical doc to sub-account that is not existed. Then sub-account will created automatically
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # user load visit medical
    Given I login as "user_7"
    When I switch sub-account of logged user with below info
      | name          |
      | <displayName> |
    And I load visit medical
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    When I load visit medical details
    Then The request should be succeed
    # manager search member
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
    And I load org members
      | displayName   |
      | <displayName> |
    Then The response should be
      | data.list[0].displayName | <displayName> |
      | data.list[0].patientId   | <vPatientId>  |
    Examples:
      | displayName | vPatientId    | vDisplayName | vDoB       | vGender | vPhone     |
      | Ngô Văn An  | pid0000000017 | Ngô Văn An   | 1969-09-10 | nữ      | 0000000017 |

  @health-after-visit-summary-5
  Scenario Outline: DHYD send visit medical doc to member by patientId
    Given I login as "user_7"
    # user update profile
    And I FOLLOW business
      | name       |
      | BV_MAT_BAO |
    Then The request should be succeed
    # manager search member
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
    And As org, I update patient id for member name "<displayName>"
      | patientId    |
      | <vPatientId> |
    Then The request should be succeed

    Then The request should be succeed
    And I log out
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # user load visit medical
    Given I login as "user_7"
    When I load all account of logged user with below info
      | name |
      |      |
    Then The request should be succeed
    And List "data.list[*].displayName" of response should contains
      | <displayName> |

    When I switch main of logged user with below info
      | name          |
      | <displayName> |
    And I load visit medical
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    When I load visit medical details
    Then The request should be succeed

    Examples:
      | displayName | vPatientId    | vDisplayName        | vDoB | vGender | vPhone     |
      | Ngo Van Hai | pid0000000017 | KHONG_CAN_GIONG_TEN | 1969 | nữ      | 0000000017 |

  @health-after-visit-summary-6
  Scenario Outline: DHYD send visit medical doc to member that gender is Unspecified
    Given On SqlServer, I update below info to username "<vPhone>" and country "<country>"
      | dob    | <dob>    |
      | gender | <gender> |
    # send visit medical
    Given Org send visit medical to below user
      | patientInfo.1 | <vPatientId>   |
      | patientInfo.2 | <vDoB>         |
      | patientInfo.3 | <vGender>      |
      | patientInfo.4 | <vPhone>       |
      | patientInfo.6 | <vDisplayName> |
    Then The request should be succeed
    # user load visit medical
    Given I login as "user_7"
    When I switch main of logged user with below info
      | name |
      |      |
    And I load visit medical
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    When I load visit medical details
    Then The request should be succeed
    Examples:
      | dob        | gender | country | vPatientId    | vDisplayName | vDoB       | vGender | vPhone     |
      | 2002-03-02 | 4      | VN      | pid0000000017 | Ngo Van Hai  | 2002-03-02 | nữ      | 0000000017 |
      | 2002-03-02 | 4      | VN      | pid0000000017 | Hai          | 2002       | nam     | 0000000017 |