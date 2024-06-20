@regression @recovery
Feature: User: Recovery Password

  @recovery-password-1
  Scenario Outline: System admin set default password for user
    Given On SqlServer, I delete user by username "<userName>" and country "<country>"

    When I signup new account with below info and verify it
      | country   | gdrLogin   | password      | displayName | gender | language   |
      | <country> | <userName> | <oldPassword> | TRAN_THANH  | 2      | <language> |

    Given I login as "system_admin_1"
    And I set default password for the below username
      | gdrLogin    |
      | 84347240011 |
    And The request should be succeed
    And I login account
      | gdrLogin   | password      | country   |
      | <userName> | <oldPassword> | <country> |
    And The request should be fail
    And The "data" should "null"
    And I login account
      | gdrLogin   | password          | country   |
      | <userName> | <defaultPassword> | <country> |
    And The request should be succeed
    And The "data.accessToken" should "not null"
    Examples:
      | oldPassword | defaultPassword | country | userName   | language |
      | 12345678    | 123456          | VN      | 0347240011 | 0        |

  @recovery-password-2
  Scenario Outline: User recovery password with valid password and <country>.
    Given On SqlServer, I delete user by username "<userName>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin   | password | displayName | gender | language   |
      | <country> | <userName> | 123456   | TRAN_THANH  | 2      | <language> |

    When I recovery password
      | gdrLogin   | country   | language   |
      | <userName> | <country> | <language> |

    And The request should be succeed
    And I confirm recovery password for user "<userName>" and country "<country>" with VALID code
    And The request should be succeed
    And I update "<newPassword>"
    And The request should be succeed
    And I login account
      | gdrLogin   | password | country   |
      | <userName> | 123456   | <country> |
    And The request should be fail
    Then The "data" should "null"
    And I login account
      | gdrLogin   | password      | country   |
      | <userName> | <newPassword> | <country> |

    And The request should be succeed
    Then The "data" should "not null"
    Examples:
      | newPassword | country | userName   | language |
      | 12345678    | VN      | 0347240011 | 0        |
      | aaaaaaa     | VN      | 0347240012 | 0        |
      | aa1234556   | VN      | 0347240013 | 0        |
      | 12345678    | US      | 0347240014 | 0        |
      | aaaaaaa     | US      | 0347240015 | 0        |
      | aa1234556   | US      | 0347240016 | 0        |

  #Server Dev always pass although enter invalid code

#  @recovery-password-3
#  Scenario Outline: User recovery password with invalid code.
#    Given On SqlServer, I delete user by username "<userName>" and country "<country>"
#    When I signup new account with below info and verify it
#      | country   | gdrLogin   | password | displayName    | gender | language   |
#      | <country> | <userName> | 123456   | TRAN_THANH | 2      | <language> |
#    When I recovery password
#      | gdrLogin   | country   | language   |
#      | <userName> | <country> | <language> |
#    And The request should be succeed
#    And I confirm recovery password for user "<userName>" and country "<country>" with INVALID code
#    And The request should be fail
#    And I login with username "<userName>", password "123456" and country "<country>"
#    And The request should be succeed
#    Then The "data.accessToken" should "not null"
#    Examples:
#      | userName   | country | language |
#      | 0347240022 | VN      | 0        |
#      | 0347240024 | US      | 0        |

  @recovery-password-4
  Scenario Outline: User recovery password with invalid account.
    Given On SqlServer, I delete user by username "<userName>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin   | password | displayName | gender | language   |
      | <country> | <userName> | 123456   | TRAN_THANH  | 2      | <language> |
    When I recovery password
      | gdrLogin      | country   | language   |
      | wrong_account | <country> | <language> |
    Then The response should be
      | success | false                                             |
      | message | This number or email does not exist in our system |
    Examples:
      | country | userName   | language |
      | VN      | 0347240031 | 0        |

