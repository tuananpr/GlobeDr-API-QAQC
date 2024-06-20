@regression @org @department @staff @department-access
Feature: Department manage access to staff account

  As org owner or Any staff that manage department
  I want to access to staff that was created from org
  So I can access and config for it

  Background:
    Given On SqlServer, I delete user by gdrLogin "teo1234324@test.com"
    Given I re-signup "manager_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_HAI_QUAN | manager_1 |

    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage

  @department-access-1
  Scenario Outline: manage access to staff
    When I add department on org
      | name         |
      | <department> |
    And The request should be succeed

      # manager create new doctor to deparment
    When On org, I create new account on department
      | email   | title   | displayName | password   | deptName |
      | <email> | <title> | <staffName> | <password> | Khoa Tim |

    Then The request should be succeed
      # I access to staff and view personal information
    When <appType> access to staff with name is "<staffName>" into department is "<department>"
    And I get personal information
    Then The response should be
      | data.displayName | <staffName> |
      | data.title       | <title>     |

    Examples:
      | appType   | email               | title    | staffName  | password    | department |
      | WebManage | teo1234324@test.com | giám đốc | teo1234324 | nguyenvanba | Khoa Tim   |
      | User      | teo1234324@test.com | giám đốc | teo1234324 | nguyenvanba | Khoa Tim   |
      | Manage    | teo1234324@test.com | giám đốc | teo1234324 | nguyenvanba | Khoa Tim   |
