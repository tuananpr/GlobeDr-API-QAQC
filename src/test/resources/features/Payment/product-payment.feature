@regression @product-service @product @payment @product-payment @order
Feature: Product payment


  Background:
    Given I re-signup "manager_3" account
    Given I re-signup "user_1" account
    Given I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     | orgAttribute            |
      | BV_MY_THANH | manager_3 | JoinedGdr,EnablePayment |
    And I add payoo payment for org
      | orgName     | pwd    |
      | BV_MY_THANH | 123456 |
    Then The request should be succeed

    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage


  @product-payment-1
  Scenario Outline: I create new product with payment type is <paymentTypes>
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible | paymentTypes   |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      | <paymentTypes> |
    Then The request should be succeed
    Examples:
      | name      | description               | fromPrice | currency | orgProductType | paymentTypes |
      | product 1 | description for product 1 | 150       | VND      | Product        | COD          |
      | product 1 | description for product 1 | 150       | VND      | Product        | Payoo        |

  @product-payment-2
  Scenario Outline: user order product has payment type is <paymentTypes>
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible | paymentTypes   |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      | <paymentTypes> |
    Then The request should be succeed

    When I login as "user_1"
    And As user, I order products
      | orgName     | description   | productServiceType | deliveryType | deliveryAddress   | country                                            | city                                | district                        | ward                                |
      | BV_MY_THANH | <description> | <orgProductType>   | ShipTo       | <deliveryAddress> | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    Then The response should be
      | success                      | true              |
      | data.info.productServiceName | <name>            |
      | data.info.address            | <deliveryAddress> |
      | data.info.country.name       | <countryName>     |
      | data.info.city.name          | <cityName>        |
      | data.info.district.name      | <districtName>    |
      | data.info.ward.name          | <wardName>        |

    And As user, I load orders
      | orderStatus |
      | New         |
    Then The response should be
      | success                    | true              |
      | data.total                 | 1                 |
      | data.list[0].address       | <deliveryAddress> |
      | data.list[0].country.name  | <countryName>     |
      | data.list[0].city.name     | <cityName>        |
      | data.list[0].district.name | <districtName>    |
      | data.list[0].ward.name     | <wardName>        |
    And As user, I load order detail
      | orderStatus |
      | New         |
    And The response should contains
      | success                      | true           |
      | data.info.productServiceName | <name>         |
      | data.info.country.name       | <countryName>  |
      | data.info.city.name          | <cityName>     |
      | data.info.district.name      | <districtName> |
      | data.info.ward.name          | <wardName>     |
    Examples:
      | name      | description               | fromPrice | currency | orgProductType | paymentTypes | countryName | cityName    | districtName | wardName  | deliveryAddress |
      | product 1 | description for product 1 | 150       | VND      | Product        | COD          | Việt Nam    | Hồ Chí Minh | Quận 3       | Phường 11 | 102 cmt8        |
      | product 1 | description for product 1 | 150       | VND      | Product        | Payoo        | Việt Nam    | Hồ Chí Minh | Quận 3       | Phường 11 | 102 cmt8        |

  @product-payment-3
  Scenario Outline: user payment for product and system admin view payoo order
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible | paymentTypes   |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      | <paymentTypes> |
    Then The request should be succeed

    When I login as "user_1"
    And As user, I order products
      | orgName     | description   | productServiceType | deliveryType | deliveryAddress | country                                            | city                                | district                        | ward                                |
      | BV_MY_THANH | <description> | <orgProductType>   | ShipTo       | 102 cmt8        | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    Then The response should be
      | success | true |

    And As user, I payment products
      | orderStatus |
      | New         |
    Then The response should be
      | success | true |
    And The "data.paymentUrl" should "not null"

    When I login as "system_admin_1"
    And As sysAdmin, I load above payoo order
    Then The response should be
      | success       | true        |
      | data.orgName  | BV_MY_THANH |
      | data.userName | user_1      |

    And As sysAdmin, I load payoo order that isn't existed
    Then The response should be
      | success | false                     |
      | message | This order is not existed |

    Examples:
      | name      | description               | fromPrice | currency | orgProductType | paymentTypes |
      | product 1 | description for product 1 | 150       | VND      | Product        | Payoo        |