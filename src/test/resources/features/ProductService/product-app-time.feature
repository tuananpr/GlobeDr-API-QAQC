@regression @product-service @product @product-app-time @app-user-time
Feature: Product GlobeDr

  As GlobeDr manager,
  I create product type is usage app time


  Background:
    Given On SqlServer, I delete product name is "AppUsageTime_1"
    Given I re-signup "user_2" account and update profile
    Given I re-signup "manager_1" account
    And I login as "system_admin_1"
    And As sysAdmin, I add staff for org name "GlobeDr"
      | displayName | country | isAdmin |
      | manager_1   | VN      | true    |
    Then The request should be succeed

    And I set features for staff
      | featureAttributes | displayName |
      | ProductService    | manager_1   |
    Then The request should be succeed

    Given I login as "manager_1"
    And I accept join organization
    And I select org "GlobeDr" that I manage

  @product-app-time-1
  Scenario Outline: manager create product type is usage app time
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | currency   | isInternal | isPublic | isVisible | monthsUsedApp | points |
      | <name> | <description> | <orgProductType> | <fromPrice> | <currency> | true       | true     | true      | 1             | 1      |
    Then The request should be succeed

    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    And The response should be
      | success | true |
    And List "data.list[*].name" of response should contains
      | <name> |

    When I login as "user_2"
    And As user, I load products
      | isGlobedr |
      | true      |
    And The response should be
      | success | true |
    And List "data.list[*].name" of response should contains
      | <name> |

    Examples:
      | name           | description                    | orgProductType | fromPrice | currency |
      | AppUsageTime_1 | description for AppUsageTime_1 | AppUsageTime   | 150       | VND      |




























