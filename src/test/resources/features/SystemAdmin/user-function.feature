@regression @system-admin @user-function
Feature: System Admin can set function for user

  As SysAdmin
  I want to allow or not allow user use function as health, chat, ...


  Background:
    Given I re-signup "user_1" account
    And I login as "system_admin_1"


  @user-function-1
  Scenario Outline: System admin set function for user
    When As sysAdmin, I set user function
      | name   | country | userFeatureAttributes   |
      | user_1 | VN      | <userFeatureAttributes> |
    And The request should be succeed
    When I login as "user_1"
    Then The response should be
      | success                   | true       |
      | data.userFeatureAttribute | <expected> |
    Examples:
      | userFeatureAttributes | expected |
      | ChatInbox             | 1        |
      | Consult               | 2        |
      | Appointment           | 4        |
      | Prescription          | 8        |
      | JoinedOrgs            | 16       |
      | BioProfile            | 32       |
      | AccountProfile        | 64       |
      | Health                | 128      |
      | ALL                   | 255      |

