@regression @org @customer-care @customer-settings @video-call
Feature: Customer care : settings


  Background:
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_lap" account
    When I re-signup "cskh_lien" account
    When I re-signup "cskh_lanh" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     |
      | BV_MY_TAM | manager_1 |


  @customer-settings-1
  Scenario: config video call to customer cares into org
    And I login as "system_admin_1"
    And As sysAdmin, I set attributes for org name "BV_MY_TAM"
      | EnableVideoCall |
      | CallRoundRobin  |
      | JoinedGdr       |
    Then The request should be succeed

    And I login as "user_1"
    And I get information org
      | orgName   |
      | BV_MY_TAM |
    And The response should be
      | success                     | true |
      | data.orgInfo.allowVideoCall | true |
      | data.orgInfo.roundRobin     | true |
    And The request should be succeed

  @customer-settings-2
  Scenario: load customer care into org
    And As sysAdmin, I setting customer care
      | name      | maxCustomerCare |
      | BV_MY_TAM | 4               |
    Then The request should be succeed

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_lap    | VN      | CustomerCare    |
      | cskh_lien   | VN      | Reception       |
      | cskh_lanh   | VN      | Nurse           |
    Then The request should be succeed

    And I login as "cskh_lap"
    And I accept join organization
    And As customer care, I set online

    And I login as "cskh_lien"
    And I accept join organization
    And As customer care, I set online

    And I login as "cskh_lanh"
    And I accept join organization
    And As customer care, I set online

    And I login as "user_1"
    And I load customer care into org
      | orgName   |
      | BV_MY_TAM |
    And List "data.list[*].name" of response should contains
      | cskh_lap  |
      | cskh_lien |
      | cskh_lanh |




