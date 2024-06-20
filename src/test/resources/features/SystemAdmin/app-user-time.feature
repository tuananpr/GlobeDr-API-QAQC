@regression @system-admin @app-user-time
Feature: System Admin can set function for user

  As system admin
  I want to set time to use app for doctor


  Background:


  @app-user-time-1
  Scenario: System admin can't set time use app for user
    Given I re-signup "user_1" account
    And I login as "system_admin_1"
    When As sysAdmin, I set time use app for user
      | name   | days |
      | user_1 | 2    |
    And The response should be
      | success        | false                                         |
      | errors.userSig | Only applies to doctors of the GlobeDr system |

  @app-user-time-2
  Scenario: System admin can set time use app for doctor of GlobeDr
    Given I re-signup "DoctorAuto" account provider and update profile
    And I login as "system_admin_1"
    When As sysAdmin, I set time use app for user
      | name       | days |
      | DoctorAuto | 2    |
    And The request should be succeed
    And The response "data.appUsageTime" has time equal 2 days
    When As sysAdmin, I set time use app for user
      | name       | days |
      | DoctorAuto | -1   |
    And The request should be succeed
    And The response "data.appUsageTime" has time equal 1 days










