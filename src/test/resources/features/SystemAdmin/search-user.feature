@regression @system-admin
Feature: System Admin can manage all GlobeDr Users in System.

  Background:
    Given I login as "system_admin_1"


  @system-admin-1
  Scenario Outline: System admin can searching for user in system by their country and phone
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName | language |
      | <country> | <phone>  | 123456   | <phone>     | 0        |
    And The request should be succeed
    Given I login as "system_admin_1"

    When As sysAdmin, I load user
      | gdrLogin   | country   |
      | <gdrLogin> | <country> |
    And The request should be succeed
    And List "data.list[*].gdrLogin" of response should contains
      | <gdrLogin> |
    Examples:
      | country | phone      | country | gdrLogin    | expected |
      | VN      | 0903305411 | VN      | 84903305411 | true     |
      | US      | 0303305466 | US      | 1303305466  | true     |


  @system-admin-2
  Scenario: System admin can searching for user in system by their city
    When As sysAdmin, I load user
      | address                             | country |
      | 100 Điện Biên Phủ, Ho Chi Minh City | VN      |
    And The request should be succeed
    And List "data.list[*].address" of response should contains
      | 100 Điện Biên Phủ, Ho Chi Minh City |

  @system-admin-3
  Scenario: System admin can searching for user in system by their city
    When As sysAdmin, I load user
      | city | country |
      | HCM  | VN      |
    And The request should be succeed
    And List "data.list[*].city" of response should be "HCM"


