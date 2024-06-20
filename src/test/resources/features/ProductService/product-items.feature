@regression @product-items-service @product-items
Feature: Create service items into org product

  Background:
    Given I re-signup "manager_3" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_MY_THANH | manager_3 |

    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

    When I create new product
      | name      | description               | orgProductType | fromPrice | isInternal | isPublic | isVisible |
      | product 1 | description for product 1 | MedicalTest    | 150       | true       | true     | true      |
    Then The request should be succeed

  @product-items-1
  Scenario Outline: Create new product item successfully
    And I create new item for product name "<productName>"
      | currency | currencyName | description    | name    | price    |
      | 2        | VND          | <descriptionA> | <nameA> | <priceA> |
    Then The request should be succeed
    And I load all services into product name "<productName>"
      | name    |
      | <nameA> |
    And The response should be
      | success                  | true           |
      | data.list[0].name        | <nameA>        |
      | data.list[0].description | <descriptionA> |
      | data.list[0].price       | <priceA>       |
    Examples:
      | productName | descriptionA           | nameA          | priceA |
      | product 1   | tẩy trắng toàn bộ răng | tẩy trắng răng | 150000 |


  @product-items-2
  Scenario Outline: update product item successfully
    And I create new item for product name "<productName>"
      | currency | currencyName | description    | name    | price    |
      | 2        | VND          | <descriptionA> | <nameA> | <priceA> |
    Then The request should be succeed
    And I update item for product name "<productName>" with item name "<nameA>"
      | currency | currencyName | description    | name    | price    |
      | 2        | VND          | <descriptionB> | <nameB> | <priceB> |
    Then The request should be succeed
    And I load all services into product name "<productName>"
      | name    |
      | <nameB> |
    And The response should be
      | success                   | true           |
      | data.list[0].currency     | 2              |
      | data.list[0].currencyName | VNĐ            |
      | data.list[0].name         | <nameB>        |
      | data.list[0].description  | <descriptionB> |
      | data.list[0].price        | <priceB>       |

    Examples:
      | productName | descriptionA           | nameA          | priceA | descriptionB | nameB    | priceB |
      | product 1   | tẩy trắng toàn bộ răng | tẩy trắng răng | 150000 | nhổ răng     | nhổ răng | 500000 |


  @product-items-3
  Scenario Outline: delete product item successfully
    # create service item
    And I create new item for product name "<productName>"
      | currency | currencyName | description    | name    | price    |
      | 2        | VND          | <descriptionA> | <nameA> | <priceA> |
    Then The request should be succeed
    # delete service item
    And I delete item for above product
      | productName   | currency | currencyName | description    | name    | price    |
      | <productName> | 2        | VND          | <descriptionA> | <nameA> | <priceA> |

    And I load all services into product name "<productName>"
      | name    |
      | <nameB> |
    And The response should be
      | success   | true |
      | data.list | []   |

    Examples:
      | productName | descriptionA           | nameA          | priceA | descriptionB | nameB    | priceB |
      | product 1   | tẩy trắng toàn bộ răng | tẩy trắng răng | 150000 | nhổ răng     | nhổ răng | 500000 |

