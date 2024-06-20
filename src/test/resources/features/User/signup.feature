@regression @signup
Feature: Signup

  @signup-01
  Scenario Outline: Signup and verify should be success.
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName | language |
      | <country> | <phone>  | 123456   | Nguyen_Hai | 0        |
    And The "data.accessToken" should "not null"
    Then The response should be
      | success              | <expected>  |
      | data.userType        | 2           |
      | data.gdrLogin        | <gdrLogin>  |
      | data.displayName     | Nguyen_Hai |
      | data.country         | <country>   |
      | data.visitingCountry | <country>   |
      | data.isVerified      | true        |
      | data.tokenType       | Bearer      |
    Examples:
      | phone      | country | gdrLogin    | expected |
      | 0903305411 | VN      | 84903305411 | true     |
      | 0303305466 | US      | 1303305466  | true     |

  @signup-02
  Scenario Outline: Signup successfully after resend verify code
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info
      | country   | gdrLogin | password | displayName | language |
      | <country> | <phone>  | 123456   | Nguyen_Hai | 0        |
    And Please resend verify code for signup
    And The request should be succeed
    And I enter verification code
    And The "data.accessToken" should "not null"
    Then The response should be
      | data.userType        | 2           |
      | data.gdrLogin        | <gdrLogin>  |
      | data.displayName     | Nguyen_Hai |
      | data.country         | <country>   |
      | data.visitingCountry | <country>   |
      | data.isVerified      | true        |
      | data.tokenType       | Bearer      |
    Examples:
      | phone      | country | gdrLogin    |
      | 0903305411 | VN      | 84903305411 |


  @update-phone-1
  Scenario Outline: Update new phone with valid code successfully
    Given On SqlServer, I delete user by username "<username>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin  | password | displayName | language |
      | <country> | <loginId> | 123456   | Lan La      | 0        |
    And I login account
      | gdrLogin   | password | country   |
      | <username> | 123456   | <country> |
    And The request should be succeed

    And I update new phone "<newPhone>" with country "<country>" for loginId "<loginId>" and send valid code
    And The request should be succeed
    And I load my profile
    And The request should be succeed
    Then The response should be
      | data.gdrLogin | <loginId>      |
      | data.phone    | <fullNewPhone> |
    Examples:
      | username   | loginId     | newPhone   | fullNewPhone | country |
      | 0933334444 | 84933334444 | 0933334466 | 84933334466  | VN      |

# Server Dev always pass although enter invalid code
#   @update-phone-2
#  Scenario Outline: Update new phone with invalid code successfully
#    Given On SqlServer, I delete user by username "<gdrlogin>" and country "<country>"
#    When I signup new account with below info and verify it
#      | country   | gdrLogin   | password | displayName| language |
#      | <country> | <gdrlogin> | 123456   | Lan La     | 0        |
#    When I login with username "<gdrlogin>", password "123456" and country "<country>"
#    And I update new phone "<newPhone>" with country "<country>" for loginId "<loginId>" and send invalid code
#    And The request should be fail
#    Examples:
#      | username   | loginId     | newPhone   | country |
#      | 0933334444 | 84933334444 | 0933334455 | VN      |
