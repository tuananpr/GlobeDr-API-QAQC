@regression @product-service @product @product-trial @trial
Feature: Product trial

  As manager
  I want to create products trial
  In order to specified user can view it and normal user can't view it

  Background:
    Given I re-signup "manager_3" account and update profile
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     | admin     |
      | BV_MY_THANH | manager_3 | manager_1 |
    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

    When On org, I add staff
      | displayName | country | isTrial |
      | user_4      | VN      | true    |
    And The request should be succeed


  @product-trial-1
  Scenario Outline: Only user trial or owner org can view it
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible | isTrial |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      | true    |
    Then The request should be succeed

    And As user, I load products of org name "BV_MY_THANH"
      | productServiceType |
      | <orgProductType>   |
    Then The response should be
      | success                  | true          |
      | data.list[0].description | <description> |

    When I login as "user_4"
    And As user, I load products of org name "BV_MY_THANH"
      | productServiceType |
      | <orgProductType>   |
    Then The response should be
      | success                  | true          |
      | data.list[0].description | <description> |

    When I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage
    And As user, I load products of org name "BV_MY_THANH"
      | productServiceType |
      | <orgProductType>   |
    Then The response should be
      | success   | true |
      | data.list | []   |

    When I login as "user_1"
    And As user, I load products of org name "BV_MY_THANH"
      | productServiceType |
      | <orgProductType>   |
    Then The response should be
      | success   | true |
      | data.list | []   |

    Examples:
      | name            | description                     | fromPrice | currency | orgProductType | currencyCode | orgProductTypeCode |
      | product trial 1 | description for product trial 1 | 150       | VND      | Product        | 2            | 1                  |

