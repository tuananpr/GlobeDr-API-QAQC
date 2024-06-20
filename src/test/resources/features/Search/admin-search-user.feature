@regression @system-admin @system-admin-user
Feature: System admin control user

  As system admin
  I want to control all account into GlobeDr as set permissions, roles, attributes, lock, ...

  Background:


  @system-admin-user-1
  Scenario Outline: admin verify user
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info
      | country   | gdrLogin | password | displayName   | language |
      | <country> | <phone>  | <pass>   | <displayName> | 0        |

    Given I login as "system_admin_1"
    When As sysAdmin, I verify below username
      | gdrLogin | country   | isVerify |
      | <phone>  | <country> | true     |
    Then The request should be succeed
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |

    Then The response should be
      | data.displayName | <displayName> |
      | data.country     | <country>     |
      | data.isVerified  | true          |
    Examples:
      | phone      | country | pass   | displayName |
      | 0903305555 | VN      | 123456 | Tran nam    |

  @system-admin-user-2
  Scenario Outline: admin change status user
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName   | language |
      | <country> | <phone>  | <pass>   | <displayName> | 0        |
    Given I login as "system_admin_1"
    When As sysAdmin, I change status of below user
      | name          | country   | changeUserStatus |
      | <displayName> | <country> | <status>         |
    Then The request should be succeed
    When As sysAdmin, I load user
      | gdrLogin | country   |
      | <phone>  | <country> |
    Then The response should be
      | success                  | true          |
      | data.list[0].displayName | <displayName> |
      | data.list[0].userStatus  | <statusCode>  |
    Examples:
      | phone      | country | pass   | displayName | status  | statusCode |
      | 0903305555 | VN      | 123456 | Tran nam    | New     | 1          |
      | 0903305555 | VN      | 123456 | Tran nam    | Active  | 2          |
      | 0903305555 | VN      | 123456 | Tran nam    | Locked  | 4          |
      | 0903305555 | VN      | 123456 | Tran nam    | Pending | 8          |
      | 0903305555 | VN      | 123456 | Tran nam    | Merged  | 16         |
      | 0903305555 | VN      | 123456 | Tran nam    | Deleted | 32         |

  @system-admin-user-3
  Scenario Outline: admin remove user
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info
      | country   | gdrLogin | password | displayName   | language |
      | <country> | <phone>  | <pass>   | <displayName> | 1        |

    Given I login as "system_admin_1"
    When As sysAdmin, I remove below username
      | gdrLogin | country   |
      | <phone>  | <country> |
    Then The request should be succeed
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |

    Then The response should be
      | success | false     |
      | message | <message> |
    Examples:
      | phone      | country | pass   | displayName | message                                                                      |
      | 0903305555 | VN      | 123456 | Tran nam    | Tài khoản của bạn bị khóa! Vui lòng liên hệ số +8428 73006880 để được hỗ trợ |



  @system-admin-user-4
  Scenario: System admin view users growth chart
    When As sysAdmin, I view users growth chart
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed

  @system-admin-user-5
  Scenario: System admin view users by country
    When As sysAdmin, I view users by country
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed

  @system-admin-user-6
  Scenario: System admin view users by gender
    When As sysAdmin, I view users by gender
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed
