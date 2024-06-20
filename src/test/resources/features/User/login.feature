@regression @login
Feature: Login

  @login-prepare-data
  Scenario Outline: Prepare account for login.
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName   | gender | language |
      | <country> | <phone>  | 123456   | <displayName> | 1      | 0        |
    Examples:
      | phone      | country | displayName |
      | 0303305467 | VN      | Nguyen      |
      | 0303305417 | US      | Nguyen_Hai  |

  @login-prepare-data-2
  Scenario Outline: Prepare account for login.
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    Examples:
      | phone      | country |
      | 0303301234 | VN      |


  @login-1
  Scenario Outline: Login success and get information of current account
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |
    And The response should be
      | success       | <status>   |
      | data.gdrLogin | <gdrLogin> |
    Then The "<field>" should "<token>"
    And I get information of signed in account
    And The response should be
      | success       | <status>   |
      | data.gdrLogin | <gdrLogin> |
    Then The "<field>" should "<token>"
    Examples:
      | gdrLogin    | phone       | country | pass   | field            | token    | status |
      | 84303305467 | 0303305467  | VN      | 123456 | data.accessToken | not null | true   |
      | 84303305467 | 0303305467  | VN      | 123456 | data.accessToken | not null | true   |
      | 84303305467 | 84303305467 | VN      | 123456 | data.accessToken | not null | true   |
      | 1303305417  | 0303305417  | US      | 123456 | data.accessToken | not null | true   |
      | 1303305417  | 0303305417  | US      | 123456 | data.accessToken | not null | true   |
      | 1303305417  | 1303305417  | US      | 123456 | data.accessToken | not null | true   |

  @login-2
  Scenario Outline: Login fail
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |
    And The response should be
      | success | false     |
      | message | <message> |
    Then The "data" should "null"
    Then The "<errorField>" should "<errorValue>"
    Examples:
      | phone      | country | pass      | message                                                          | errorField      | errorValue             |
      | 0303305467 | VN      | 123456789 | Vui lòng kiểm tra lại số điện thoại, email hoặc mật khẩu của bạn | errors          | {}                     |
      | 0303305467 | SG      | 123456    | Vui lòng kiểm tra lại số điện thoại, email hoặc mật khẩu của bạn | errors          | {}                     |
      | 0303305467 |         | 123456    | Vui lòng thử lại sau                                             | errors.country  | Trường này là bắt buộc |
      | 0303305467 | VN      |           | Vui lòng thử lại sau                                             | errors.password | Trường này là bắt buộc |
      | 0303301234 | VN      | 123456    | Vui lòng kiểm tra lại số điện thoại, email hoặc mật khẩu của bạn | errors          | {}                     |


  @config-1
  Scenario Outline: load config for user
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |
    And The response should be
      | success | <status> |
    When I load config
    And The response should be
      | success | <status> |
    When I log out
    And The request should be succeed
    Examples:
      | phone      | country | pass   | status |
      | 0303305467 | VN      | 123456 | true   |

