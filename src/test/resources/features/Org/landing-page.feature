@regression @org
Feature: User: Manager Group.

  @landing-page
  Scenario: send contact org
    When On landing page, I send contact org with below info
      | businessName | orgType | name | phone      | email                |
      | BV_HAI_QUAN  | 1       | test | 0767891111 | BV_HAI_QUAN@test.com |
    And The request should be succeed

