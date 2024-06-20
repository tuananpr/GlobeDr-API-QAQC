@regression @connection
Feature: Connection

  Background:
    Given On SqlServer, I delete organization by name "PK_KIET"
    And I re-signup "user_1" account and update profile
    And I re-signup "user_2" account and update profile

  @connection-1
  Scenario: User can search and connect to other user
    When I login as "user_2"
    When I request connection to below user
      | name   |
      | user_1 |
    Then The request should be succeed
    When I login as "user_1"
    And I load my connection list
    Then I have '0' connections
    And I count my request connection
    And The response should be
      | success         | true |
      | data.totalCount | 1    |
    When I load request connection list
    And I have '1' connections request
    And I accept all request connection
    Then The request should be succeed
    When I login as "user_1"
    And I load my connection list
    And I have '1' connections
    Then My connection list should contain "user_2"
    When I search user "user_2" into my connection list
    Then My connection list should contain "user_2"


  @connection-2
  Scenario: User can search and decline a request connection from other user
    When I login as "user_2"
    And I load my connection list
    Then The request should be succeed
    When I request connection to below user
      | name   |
      | user_1 |
    Then The request should be succeed
    When I login as "user_1"
    And I decline request connection
      | name   |
      | user_2 |
    Then The request should be succeed
    When I load request connection list
    And I have '0' connections
    And I load my connection list
    Then I have '0' connections

  @connection-3
  Scenario: User can search and remove to other user after connected
    When I login as "user_2"
    And I load my connection list
    Then I have '0' connections
    When I request connection to below user
      | name   |
      | user_1 |
    Then The request should be succeed

    When I login as "user_1"
    And I accept request connection
      | name   |
      | user_2 |
    When I login as "user_1"
    And I load my connection list
    And I have '1' connections
    Then My connection list should contain "user_2"
    When I remove connection
      | name   |
      | user_2 |
    And The request should be succeed
    And I load my connection list
    Then I have '0' connections
