@regression @noti-common @noti
Feature: All common notification

  @noti-common-1
  Scenario: web config
    Given I login as "user_1"
    And I get config
      | forWeb |
      | true   |
    Then The request should be succeed
