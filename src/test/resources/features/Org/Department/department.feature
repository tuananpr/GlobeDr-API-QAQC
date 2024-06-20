@regression @org @department @staff
Feature: Department and staff of org.

  Background:
    Given I re-signup "manager_1" account
    Given I re-signup "manager_2" account
    Given I re-signup "user_4" account

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     | admin     |
      | BV_HAI_QUAN | manager_1 | manager_2 |

    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage

    And I set features for staff
      | featureAttributes | displayName |
      | Deparment         | manager_2   |
    When I login as "manager_2"
    And I accept join organization

  @department-1
  Scenario Outline: Owner or Admin add department for org successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When I add department on org
      | name      |
      | nhan vien |
    And The request should be succeed
    And I load all department of org above
    Then The response should be
      | data.list[0].name | nhan vien |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |


  @department-2
  Scenario Outline: Owner or Admin remove department for org successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When I add department on org
      | name      |
      | nhan vien |
    And The request should be succeed
    And I load all department of org above
    Then The response should be
      | data.list[0].name | nhan vien |

    And I remove department on org
      | name      |
      | nhan vien |
    And The request should be succeed
    And I load all department of org above
    Then The response should be
      | data.list | [] |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |


