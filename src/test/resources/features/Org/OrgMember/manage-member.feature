@regression @org-member @manage-member
Feature: Manager member into group of org

  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "user_2" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | type     | owner     |
      | PK_LUONG_VAN | Hospital | manager_1 |
    When I login as "manager_1"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage
    And I create group member
      | name  |
      | lop 1 |
    And The request should be succeed

    Given I login as "user_1"
    And I FOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed
    # user2 follow org
    Given I login as "user_2"
    And I FOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed

    When I login as "manager_1"
    And I select org "PK_LUONG_VAN" that I manage

  @manage-member-1
  Scenario: Add member to group
    And I add below members to group member name "lop 1"
      | displayName | country |
      | user_1      | VN      |
    Then The request should be succeed

    And I load members into group member
      | groupName |
      | lop 1     |
    And List "data.list[*].displayName" of response should contains
      | user_1 |


  @manage-member-2
  Scenario: Remove member from group
    And I add below members to group member name "lop 1"
      | displayName | country |
      | user_1      | VN      |
    Then The request should be succeed

    And I delete below members from group member name "lop 1"
      | displayName | country |
      | user_1      | VN      |
    Then The request should be succeed

    And I load members into group member
      | groupName |
      | lop 1     |
    And List "data.list[*].displayName" of response should not contains
      | user_1 |
