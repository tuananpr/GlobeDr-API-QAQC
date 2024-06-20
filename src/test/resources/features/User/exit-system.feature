@regression @exit-system
Feature: Exit system

  As User
  I want to remove account from system

  @exit-system-1
  Scenario Outline: I remove my account from system then I can't login to system
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName   | gender | language |
      | <country> | <phone>  | 123456   | <displayName> | 1      | 0        |
    Then The request should be succeed

    And I login account
      | gdrLogin | password | country   |
      | <phone>  | 123456   | <country> |
    Then The request should be succeed

    And I remove my account from system
    Then The request should be succeed

    And I login account
      | gdrLogin | password | country   |
      | <phone>  | 123456   | <country> |
    Then The response should be
      | success | false |
    Examples:
      | phone      | country | displayName |
      | 0303305467 | VN      | Nguyen      |

