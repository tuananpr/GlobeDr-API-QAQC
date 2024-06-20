@regression @gift-points @gift-points-user
Feature: Gift points user

  As User
  I want to get reward points
  In order to use Globedr features

  Background:
    Given I re-signup "user_1" account
    When I login as "user_1"

  @gift-point-user-1
  Scenario: user load list reward points instructions
    And I load reward points instructions
      | page |
      | 1    |
    Then List "data.list[*].name" of response should contains
      | GlobeDr member |
      | Update profile |
      | Daily active   |
      | Upload avatar  |
