@regression @change-password
Feature: Change Password


  @change-password-1
  Scenario Outline: change password successfully
    Given On SqlServer, I delete user by username "<gdrLogin>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin   | password  | displayName | gender | language |
      | <country> | <gdrLogin> | <oldPass> | <gdrLogin>  | 1      | 0        |


    And I login account
      | gdrLogin   | password  | country   |
      | <gdrLogin> | <oldPass> | <country> |
    And The request should be succeed
    When I change my password
      | oldPassword | newPassword |
      | <oldPass>   | <newPass>   |
    And The request should be succeed
    And I login account
      | gdrLogin   | password  | country   |
      | <gdrLogin> | <oldPass> | <country> |
    And The request should be fail
    And The "data" should "null"
    And I login account
      | gdrLogin   | password  | country   |
      | <gdrLogin> | <newPass> | <country> |
    And The request should be succeed
    Examples:
      | gdrLogin   | oldPass | newPass    | country |
      | 0303305466 | 123456  | 1234567890 | VN      |


  @change-password-2
  Scenario Outline: change password fail
    Given On SqlServer, I delete user by username "<gdrLogin>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin   | password  | displayName | gender | language |
      | <country> | <gdrLogin> | <oldPass> | <name>      | 1      | 0        |
    And I login account
      | gdrLogin   | password  | country   |
      | <gdrLogin> | <oldPass> | <country> |
    And The request should be succeed
    When I change my password
      | oldPassword | newPassword |
      |             |             |
    And The request should be fail
    When I change my password
      | oldPassword | newPassword |
      |             | <newPass>   |
    And The request should be fail
    When I change my password
      | oldPassword | newPassword |
      | <oldPass>   |             |
    And The request should be fail
    When I change my password
      | oldPassword | newPassword |
      | wrongPass   | wrongPass   |
    And The request should be fail

    Examples:
      | gdrLogin   | oldPass | newPass    | country |
      | 0303305466 | 123456  | 1234567890 | VN      |