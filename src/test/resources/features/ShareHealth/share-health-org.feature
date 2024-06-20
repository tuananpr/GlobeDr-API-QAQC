@regression @share-health
Feature: Share health for org connected.

  As user
  I want share my sub-account to hospital
  In order to doctor can view it

  Background:
    Given On SqlServer, I delete user by displayName "ABC_01"
    And I re-signup "user_2" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_HOI_PHUC | Hospital | manager_1 |
      | BV_HOI_SUC  | Pharmacy | manager_1 |

    And I login as "user_2"
    And I FOLLOW business
      | name        |
      | BV_HOI_PHUC |
      | BV_HOI_SUC  |

    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | ABC_01      | 2010-09-30T00:00:00.000 | 2         | 1      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    When I get sub-account of family members with below info
      | name   |
      | ABC_01 |
    Then The request should be succeed

  @share-health-org-01
  Scenario: Only just share sub-account to org that was followed
    And I load list org that i can share a account name "ABC_01" to it
      | notLoadShared |
      | true          |
    And List "data.list[*].orgName" of response should be
      | BV_HOI_PHUC |
      | BV_HOI_SUC  |

  @share-health-org-02
  Scenario: Share sub-account to org with permission "View Only"
    And I login as "user_2"
    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | ABC_01 | BV_HOI_PHUC          | ShareOrgView |
    And The request should be succeed

    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | success                                     | true        |
      | data.list[1].sharedPersons[0].name          | BV_HOI_PHUC |
      | data.list[1].sharedPersons[0].carerType     | 512         |
      | data.list[1].sharedPersons[0].carerTypeName | View only   |


    And I load list org that i can share a account name "ABC_01" to it
      | notLoadShared |
      | false         |
    And List "data.list[*].orgName" of response should be
      | BV_HOI_PHUC |
      | BV_HOI_SUC  |

    And I load list org that i can share a account name "ABC_01" to it
      | notLoadShared |
      | true          |
    And List "data.list[*].orgName" of response should be
      | BV_HOI_SUC |
    And List "data.list[*].orgName" of response should not has
      | BV_HOI_PHUC |

  @share-health-org-03
  Scenario: Share sub-account to org with permission "Edit & View"
    And I login as "user_2"
    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | ABC_01 | BV_HOI_PHUC          | ShareOrgEdit |
    And The request should be succeed

    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | success                                     | true        |
      | data.list[1].sharedPersons[0].name          | BV_HOI_PHUC |
      | data.list[1].sharedPersons[0].carerType     | 256         |
      | data.list[1].sharedPersons[0].carerTypeName | view & edit |


  @share-health-org-04
  Scenario: Share main account to org with permission "Edit & View"
    And I login as "user_2"
    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | user_2 | BV_HOI_PHUC          | ShareOrgEdit |
    And The request should be succeed

    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | success                                     | true        |
      | data.list[0].sharedPersons[0].name          | BV_HOI_PHUC |
      | data.list[0].sharedPersons[0].carerType     | 256         |
      | data.list[0].sharedPersons[0].carerTypeName | view & edit |


  @share-health-org-05
  Scenario: unshare sub-account to org
    And I login as "user_2"
    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | ABC_01 | BV_HOI_PHUC          | ShareOrgEdit |
    And The request should be succeed

    And I unshare my sub-account to org
      | name   | sharedConnectionName | sharedType |
      | ABC_01 | BV_HOI_PHUC          | 0          |
    And The request should be succeed

    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | success                    | true |
      | data.list[1].sharedPersons | []   |


  @share-health-org-06
  Scenario:Load member of org dc shared
    And I login as "user_2"
    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | ABC_01 | BV_HOI_PHUC          | ShareOrgEdit |
    And The request should be succeed

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_HOI_PHUC" that I manage
    And I load org members
      | isSharedHealthRecord |
      | true                 |
    And The response should be
      | success                  | true   |
      | data.list[0].displayName | ABC_01 |
      | data.list[0].isShareEdit | true   |