@regression @share-health
Feature: User created sub_account and share with other users.

  As user
  I want to share sub-account to others
  In order to support me to health monitoring of  my family

  Background:
    Given On SqlServer, I delete user by displayName "ABC_01"
    And I re-signup "user_1" account
    And I re-signup "user_2" account
    And I re-signup "user_6" account
    And I login as "user_1"
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | ABC_01      | 2010-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    # user_1 send request to user 2 and user 6
    When I request connection to below user
      | name   |
      | user_2 |
      | user_6 |
    Then The request should be succeed
    # user_2 accept request from user 1
    And I login as "user_2"
    And I accept request connection
      | name   |
      | user_1 |
    Then The request should be succeed
   # user_6 accept request from user 1
    And I login as "user_6"
    And I accept request connection
      | name   |
      | user_1 |
    Then The request should be succeed
   # user_1 share sub-account to user 2, user 6
    When I login as "user_1"
    And I share my sub-account to friends
      | name   | sharedConnectionName | sharedType |
      | ABC_01 | user_2               | ShareView  |
      | ABC_01 | user_6               | ShareEdit  |
    Then The request should be succeed


  @share-health-0
  Scenario: Share sub-account to connection
    #User 1 da share sub_account cho user_2 va user_6
    When I login as "user_1"
    And I load all account of family members with below info
      | name |
      |      |
    Then The request should be succeed
    And The response should be
      | data.list[1].sharedPersons[0].carerTypeName | view only   |
      | data.list[1].sharedPersons[0].name          | user_2      |
      | data.list[1].sharedPersons[1].name          | user_6      |
      | data.list[1].sharedPersons[1].carerTypeName | view & edit |

    #User 2 được user 1 share sub_account
    When I login as "user_2"
    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | data.list[1].owner.name          | user_1    |
      | data.list[1].owner.carerTypeName | view only |
    #User 6 được user 1 share sub_account
    When I login as "user_6"
    And I load all account of family members with below info
      | name |
      |      |
    And The response should be
      | data.list[1].owner.name          | user_1      |
      | data.list[1].owner.carerTypeName | view & edit |

  @share-health-1
  Scenario: Share main account to connection
    When I login as "user_1"
    And I share my sub-account to friends
      | name   | sharedConnectionName | sharedType |
      | user_1 | user_2               | ShareView  |
      | user_1 | user_6               | ShareEdit  |
    Then The request should be succeed

  @share-health-2
  Scenario: view total connection that this sub-account are shared to it
    When I login as "user_1"
    And I load all account of family members with below info
      | name |
      |      |
    And I have '2' count shared
    And The response should be
      | data.list[1].isShared | true |


  @share-health-3a
  Scenario: Connection that are received sub-account with permission "view only" can't edit health records of it
    When I login as "user_2"
    When I switch sub-account of family members with below info
      | name   |
      | ABC_01 |

    And As user, I load last BMI
      | toUnit |
      | Metric |
    Then The request should be succeed
    When I add BMI
      | heightMajor | heightMinor | weight | fromUnit | onDate |
      | 1           | 65          | 55     | Metric   | today  |

    And The request should be fail

  @share-health-3b
  Scenario: Connection that are received sub-account with permission "view & edit" can edit health records as health history, BMI,...
    When I login as "user_6"
    When I switch sub-account of family members with below info
      | name   |
      | ABC_01 |
    When I print health history
    And The request should be succeed

    And As user, I load last BMI
      | toUnit |
      | Metric |
    Then The request should be succeed
    When I add BMI
      | heightMajor | heightMinor | weight | fromUnit | onDate |
      | 1           | 65          | 55     | Metric   | today  |
    And The request should be succeed


  @share-health-5
  Scenario: Owner unshare sub-account to connections
    When I login as "user_1"
    And I unshare my sub-account to friends
      | name   | sharedConnectionName | sharedType |
      | ABC_01 | user_2               | 0          |
      | ABC_01 | user_6               | 0          |
    And The request should be succeed

    When I login as "user_6"
    And I load all account of family members with below info
      | name |
      |      |
    And List "data.list[*].displayName" of response should be not contains "ABC_01"

    When I login as "user_2"
    And I load all account of family members with below info
      | name |
      |      |
    And List "data.list[*].displayName" of response should be not contains "ABC_01"

  @share-health-6
  Scenario: Remove sub_account after share to connections
    When I login as "user_2"
    And I load list shared account
      | name   |
      | ABC_01 |
    Then The response should be
      | success                      | true      |
      | data.list[0].country.country | VN        |
      | data.list[0].city.code       | HCM       |
      | data.list[0].district.name   | Quận 3    |
      | data.list[0].ward.name       | Phường 11 |
    Then List "data.list[*].displayName" of response should be contains "ABC_01"

    When I login as "user_1"
    And I remove a above sub-account with name "ABC_01"
    When I load all account of family members with below info
      | name |
      |      |
    Then List "data.list[*].displayName" of response should be not contains "ABC_01"

  @share-health-7
  Scenario: Connection refuses to receive sub-account from other
    When I login as "user_2"
    And I refuses to receive sub-account "ABC_01"
    Then The request should be succeed
    When I load all account of family members with below info
      | name |
      |      |
    And List "data.list[*].displayName" of response should be not contains "ABC_01"

  @share-health-9
  Scenario: Friends aren't displayed into list after share sub-account successfully
    And I load friends to share my account
      | notLoadShared | name   |
      | true          | ABC_01 |
    And The response should be
      | success | true |
    And List "data.list[*].userName" of response should not has
      | user_2 |
      | user_6 |

    And I load friends to share my account
      | notLoadShared | name   |
      | false         | ABC_01 |
    And The response should be
      | success | true |
    And List "data.list[*].userName" of response should be
      | user_2 |
      | user_6 |