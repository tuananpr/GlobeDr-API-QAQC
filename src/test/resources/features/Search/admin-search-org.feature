@regression @system-admin @system-admin-org
Feature: System Admin can manage all GlobeDr Organization in System.

  Background:
    Given I login as "system_admin_1"

  @system-admin-org-1
  Scenario: System admin can searching for Organization in Viet Nam
    When As sysAdmin, I search org
      | name        |
      | BV_MY_THANH |
    And The request should be succeed

  @system-admin-org-2
  Scenario: System admin can searching for Organization in US
    When As sysAdmin, I search org
      | name    |
      | PK_LUAN |
    And The request should be succeed


  @system-admin-org-3
  Scenario: System admin view orgs growth chart
    When As sysAdmin, I view orgs growth chart
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed

  @system-admin-org-4
  Scenario: System admin view orgs by country
    When As sysAdmin, I view orgs by country
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed

  @system-admin-org-5
  Scenario: System admin view orgs by type
    When As sysAdmin, I view orgs by type
      | fromDate  | toDate |
      | yesterday | today  |
    And The request should be succeed

  @system-admin-org-6
  Scenario Outline: System admin search org by name
    Given On SqlServer, I delete organization by name "<orgName>"
    And I create a org
      | name      | type      | status | address   | latitude   | longitude   | country   | city   | district   | ward   |
      | <orgName> | <orgType> | Active | <address> | <latitude> | <longitude> | <country> | <city> | <district> | <ward> |
    Then The request should be succeed
    When As sysAdmin, I search org
      | name      |
      | <orgName> |
    And List "<path>" of response should contains
      | <orgName> |
    When As sysAdmin, I search org
      | name      |
      | THIEN PHU |
    And List "<path>" of response should contains
      | <orgName> |

    When As sysAdmin, I search org
      | name  |
      | THIEN |
    And List "<path>" of response should contains
      | <orgName> |
    When As sysAdmin, I search org
      | name   |
      | PHUONG |
    And List "<path>" of response should contains
      | <orgName> |
    When As sysAdmin, I search org
      | name |
      | PHU  |
    And List "<path>" of response should contains
      | <orgName> |
    Examples:
      | path                 | orgName         | address                  | latitude           | longitude          | country                                               | city                                  | district                        | ward                                | orgType       |
      | data.list[*].orgName | NK THIEN PHUONG | 520 CMT8, Quan 3, Tp.HCM | 10.785729039069945 | 106.66766245536387 | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | ClinicOrgType |
