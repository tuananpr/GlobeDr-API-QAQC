@regression @user
Feature: Login

   @user-role
  Scenario Outline: create user with any role. ( except sysadmin )
    Given I re-signup "user_1" account <role> and update profile
    And I login as "user_1"
    Then User role should be "<role>"
    Then The request should be succeed
    Examples:
      | role        |
      | provider    |
      | patient     |
      | coordinator |
      | approver    |
      | editor      |
      | appConfig   |
      | auditor     |
