@regression @product-service @product-gdr
Feature: Product Globedr
  As Globedr
  I want to show all my product to user
  In order to user can buy it


  Background:
    When I login as "user_1"

  @product-gdr-1
  Scenario Outline: user load products of globedr
    And As user, I load products
      | isGlobedr   | page | pageSize |
      | <isGlobedr> | 1    | 5        |
    And The response should be
      | success   | true |
    And List "data.list[*].orgName" of response should contains
      | GlobeDr |
    Examples:
      | orgName      | isGlobedr |
      | GlobeDr Test | true      |

