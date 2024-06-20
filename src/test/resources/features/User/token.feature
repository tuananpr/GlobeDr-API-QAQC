@token
Feature: Token


  @token-expired
  Scenario: Token Expired (Status code 401 and header 'Token-Expired' is true)
    Given I getting personal information with token expired
    When I get status code
    And I get Token-Expired on Headers
    Then The status code should be '401'
    And Token should be expired


  @token-kicked
  Scenario: Account will be kicked after login 2 devices (Status code is 303)
    Given I login account on 2 devices
    When I getting personal information with token kicked
    And I get status code
    Then The status code should be '303'