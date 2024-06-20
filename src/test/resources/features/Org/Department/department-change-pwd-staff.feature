@regression @org @department @staff @department-change-pwd
Feature: Depatment : manage change password for staff account

  As org owner or Any staff that manage department
  I want to change password of staff that was created from org
  So I can set password for staff when they don't remember it

  Background:
    Given On SqlServer, I delete user by gdrLogin "nguyenvanba@test.com"
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_HAI_QUAN | manager_1 |

    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage
    When I add department on org
      | name     |
      | Khoa Tim |
    And The request should be succeed
    # manager create new doctor to deparment
    When On org, I create new account on department
      | email                | title    | displayName   | password | deptName |
      | nguyenvanba@test.com | giám đốc | nguyen van ba | 1234565  | Khoa Tim |
    Then The request should be succeed

  @department-change-pwd-1
  Scenario Outline: org owner change password doctor

    And On org, I change password staff
      | displayName  | password  |
      | <doctorName> | <newPass> |
    And I login account
      | gdrLogin | password  | country | language |
      | <email>  | <oldPass> | VN      | 0        |

    Then The request should be fail
    And I login account
      | gdrLogin | password  | country | language |
      | <email>  | <newPass> | VN      | 0        |
    Then The request should be succeed
    Examples:
      | user      | email                | doctorName    | oldPass     | department | newPass | title    |
      | manager_1 | nguyenvanba@test.com | nguyen van ba | nguyenvanba | Khoa Tim   | 123456  | giám đốc |

