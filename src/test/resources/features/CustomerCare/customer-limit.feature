@regression @org @customer-care @customer-limit @video-call
Feature: Customer care : limit customer care on org

  As sysAdmin
  I want to limit customer care on org

  Background:
    When I re-signup "manager_1" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     |
      | BV_MY_TAM | manager_1 |
    And As sysAdmin, I setting customer care
      | name      | maxCustomerCare |
      | BV_MY_TAM | 2               |
    Then The request should be succeed

  @customer-limit-1
  Scenario Outline: total customer care into org should less than max customer care into settings
    When I re-signup "cskh_loi" account
    When I re-signup "cskh_luyen" account
    When I re-signup "cskh_luy" account

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_loi    | VN      | <staffAttributes> |
      | cskh_luyen  | VN      | <staffAttributes> |
      | cskh_luy    | VN      | <staffAttributes> |
    And The response should be
      | success | false                                     |
      | message | Number of customer cares did not exceed 2 |

    Examples:
      | staffAttributes |
      | CustomerCare    |
      | Reception       |
      | Nurse           |

  @customer-limit-2
  Scenario Outline: total customer care into org should not be more than max customer care into settings
    When I re-signup "cskh_loi" account
    When I re-signup "cskh_luyen" account
    When I re-signup "cskh_luy" account

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_loi    | VN      | None            |
      | cskh_luyen  | VN      | None            |
      | cskh_luy    | VN      | None            |
    And The request should be succeed

    And I set features for staff
      | featureAttributes   | displayName |
      | <featureAttributes> | cskh_loi    |
      | <featureAttributes> | cskh_luyen  |
    And The request should be succeed

    And I set features for staff
      | featureAttributes   | displayName |
      | <featureAttributes> | cskh_luy    |
    And The response should be
      | success | false                                     |
      | message | Number of customer cares did not exceed 2 |
    Examples:
      | featureAttributes |
      | ChatInbox         |

  @customer-limit-3
  Scenario Outline: total customer care into org should not be more than max customer care into settings
    Given On SqlServer, I delete user by gdrLogin "teo1@test.com"
    Given On SqlServer, I delete user by gdrLogin "teo2@test.com"
    Given On SqlServer, I delete user by gdrLogin "teo3@test.com"
    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    When I add department on org
      | name         |
      | <department> |
    And The request should be succeed

    # manager create new doctor to deparment
    When On org, I create new account on department
      | email         | title   | displayName | password   | deptName | staffAttributes   |
      | teo1@test.com | <title> | teo1        | <password> | Khoa Tim | <staffAttributes> |
      | teo2@test.com | <title> | teo2        | <password> | Khoa Tim | <staffAttributes> |
      | teo3@test.com | <title> | teo3        | <password> | Khoa Tim | <staffAttributes> |
    Then The request should be fail
    Examples:
      | title    | password    | department | staffAttributes |
      | giám đốc | nguyenvanba | Khoa Tim   | CustomerCare    |
      | giám đốc | nguyenvanba | Khoa Tim   | Reception       |
      | giám đốc | nguyenvanba | Khoa Tim   | Nurse           |


  @customer-limit-4
  Scenario: get limit customer care into org
    And I login as "system_admin_1"
    And As sysAdmin, I get total customer care into org
      | name      |
      | BV_MY_TAM |
    And The response should be
      | success              | true |
      | data.maxCustomerCare | 2    |
