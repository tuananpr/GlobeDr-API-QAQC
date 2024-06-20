@regression @org @user-trial
Feature: Organization : User trial

  As manager
  I want to add/remove user trial

  Background:
    When I re-signup "manager_1" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | owner     |
      | BV_HA_AN | manager_1 |
    When I login as "manager_1"
    And I accept join organization

  @user-trial-1
  Scenario: manager add user trial
    When I login as "manager_1"
    And I select org "BV_HA_AN" that I manage
    When On org, I add staff
      | displayName | country | isTrial |
      | user_4      | VN      | true    |
    And The request should be succeed

    When I load staffs of department
      | deptName | isTrial |
      |          | true    |
    And The response should be
      | success               | true   |
      | data.totalCount       | 1      |
      | data.list[0].userName | user_4 |


  @user-trial-2
  Scenario: manager remove user trial
    When I login as "manager_1"
    And I select org "BV_HA_AN" that I manage
    When On org, I add staff
      | displayName | country | isTrial |
      | user_4      | VN      | true    |
    And The request should be succeed

    When On org, I remove user trial
      | deptName | displayName |
      |          | user_4      |
    And The request should be succeed

    When I load staffs of department
      | deptName | isTrial |
      |          | true    |
    And The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |