@regression @product-service
Feature: Created Product category
  As manager
  I want create product category
  In order to store product

  Background:
    Given I re-signup "manager_3" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_MY_THANH | manager_3 |
    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

  @category-product-01
  Scenario: Create new product category
    And I create product category with below info
      | status | nameVI   | nameEN |
      | New    | Suc Khoe | Health |
    Then The request should be succeed
    And The response should be
      | data.info.nameEN | Health   |
      | data.info.nameVI | Suc Khoe |
      | data.info.status | 1        |
    And I load product categories
      | orgName     |
      | BV_MY_THANH |
    Then The request should be succeed
    And The response should be
      | data.totalCount     | 1        |
      | data.list[0].nameVI | Suc Khoe |
      | data.list[0].nameEN | Health   |
      | data.list[0].status | 1        |

  @category-product-02
  Scenario Outline: Create product CATEGORY and update it
    And I create product category with below info
      | status | nameVI   | nameEN |
      | New    | Suc Khoe | Health |
    Then The request should be succeed
    And I update product category name "Health"
      | nameVI      | nameEN      | status      |
      | <newNameVI> | <newNameEN> | <newStatus> |
    Then The request should be succeed
    And I load product categories
      | nameVI      | nameEN      | status      |
      | <newNameVI> | <newNameEN> | <newStatus> |
    And The response should be
      | data.list[0].nameVI | <newNameVI>     |
      | data.list[0].nameEN | <newNameEN>     |
      | data.list[0].status | <newStatusCode> |
    Examples:
      | newNameVI | newNameEN | newStatus | newStatusCode |
      | The Thao  | sport     | Active    | 2             |
      | The Thao  | sport     | InActive  | 4             |