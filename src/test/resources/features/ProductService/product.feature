@regression @product-service @product
Feature: Create product with category

  As manager
  I want to create products for my org as products, services, medical tests, ...
  In order to sell it for user

  Background:
    Given I re-signup "manager_3" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_MY_THANH | manager_3 |
    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

  @product-0
  Scenario Outline: I create new product and view product
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      |
    Then The request should be succeed
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | data.list[0].name           | <name>               |
      | data.list[0].description    | <description>        |
      | data.list[0].fromPrice      | <fromPrice>          |
      | data.list[0].currency       | <currencyCode>       |
      | data.list[0].orgProductType | <orgProductTypeCode> |
    And I log out
    And As user, I load products of org name "BV_MY_THANH"
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | data.list[0].name           | <name>               |
      | data.list[0].description    | <description>        |
      | data.list[0].fromPrice      | <fromPrice>          |
      | data.list[0].currency       | <currencyCode>       |
      | data.list[0].orgProductType | <orgProductTypeCode> |
    Examples:
      | name          | description                   | fromPrice | currency | orgProductType | currencyCode | orgProductTypeCode |
      | product 1     | description for product 1     | 150       | VND      | Product        | 2            | 1                  |
      | Service 1     | description for Service 1     | 150       | VND      | Service        | 2            | 2                  |
      | MedicalTest 1 | description for MedicalTest 1 | 150       | VND      | MedicalTest    | 2            | 4                  |


  @product-1
  Scenario Outline: Create new product with link
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | success                  | true          |
      | data.list[0].name        | <name>        |
      | data.list[0].description | <description> |

    And I log out
    And As user, I load products of org name "BV_MY_THANH"
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | data.list[0].name        | <name>        |
      | data.list[0].description | <description> |
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ |

  @product-2-a
  Scenario Outline: Create new product with image
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed
    And I add image "<image>" to product name "<name>"
    And The request should be succeed
    And The image into "data.imageURL" should be match "<image>"
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    And The image into "data.list[0].productDocs[0].url" should be match "<image>"
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg |

  @product-2-b
  Scenario Outline: I re-update product doc succeed
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed
    And I add image "<image>" to product name "<name>"
    And The request should be succeed
    When I update new image "<newImage>" to product name "<name>"
    Then The request should be succeed
    And The image into "data.imageURL" should be match "<newImage>"
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    And The image into "data.list[0].productDocs[0].url" should be match "<newImage>"
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       | newImage                            |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg | data/image/avatar/phan-van-tien.jpg |

  @product-3
  Scenario Outline: I remove doc into product
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed
    And I add image "<image>" to product name "<name>"
    And The request should be succeed

    When I remove above product doc
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    And The request should be succeed
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |

    Then The response should be
      | data.list[0].productDocs | [] |
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg |

  @product-4
  Scenario Outline: I update product successfully
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed
    When I update to product name "<name>"
      | name          | description          | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | update <name> | update <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    And The request should be succeed
    And As manager, I load products
      | description          | productServiceType |
      | update <description> | <orgProductType>   |
    Then The response should be
      | success                  | true                 |
      | data.list[0].name        | update <name>        |
      | data.list[0].description | update <description> |
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg |


  @product-5
  Scenario Outline: I hide product
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed

    When I update to product name "<name>"
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | false      | false    | false     |
    And The request should be succeed
    And As manager, I load products
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    And I log out
    And As user, I load products of org name "BV_MY_THANH"
      | description   | productServiceType |
      | <description> | <orgProductType>   |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg |

  @product-6
  Scenario: Create new product fail
    When I create new product
      | name |
      |      |
    Then The response should be
      | success            | false                  |
      | errors.link        | This field is required |
      | errors.name        | This field is required |
      | errors.description | This field is required |


  @product-7
  Scenario Outline: I update product fail
    When I create new product
      | name   | description   | orgProductType   | fromPrice   | toPrice   | currency   | link   | isInternal | isPublic | isVisible |
      | <name> | <description> | <orgProductType> | <fromPrice> | <toPrice> | <currency> | <link> | true       | true     | true      |
    Then The request should be succeed

    When I update to product name "<name>"
      | name |
      |      |
    Then The response should be
      | success            | false                  |
      | errors.link        | This field is required |
      | errors.name        | This field is required |
      | errors.description | This field is required |
    Examples:
      | name                 | description                             | orgProductType | fromPrice | toPrice | currency | link                  | image                       |
      | Physical examination | description for Physical examination  1 | Product        | 0         | 130     | 1        | https://jsonpath.com/ | data/image/avatar/org-1.jpg |


  @product-8
  Scenario Outline: I load org that has product
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal | isPublic | isVisible |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | true       | true     | true      |
    Then The request should be succeed
    And I load org that has product
      | productServiceType   |
      | <orgProductTypeCode> |
    Then The request should be succeed
    And List "data.list[*].name" of response should contains
      | BV_MY_THANH |
    Examples:
      | name          | description                   | fromPrice | currency | orgProductType | currencyCode | orgProductTypeCode |
      | product 1     | description for product 1     | 150       | VND      | Product        | 2            | 1                  |
      | Service 1     | description for Service 1     | 150       | VND      | Service        | 2            | 2                  |
      | MedicalTest 1 | description for MedicalTest 1 | 150       | VND      | MedicalTest    | 2            | 4                  |

  @product-9
  Scenario Outline: Require link if product isn't isInternal
    When I create new product
      | name   | description   | fromPrice   | currency   | orgProductType   | isInternal |
      | <name> | <description> | <fromPrice> | <currency> | <orgProductType> | false      |
    Then The response should be
      | success     | false                  |
      | errors.link | This field is required |
    Examples:
      | name      | description               | fromPrice | currency | orgProductType | currencyCode | orgProductTypeCode |
      | product 1 | description for product 1 | 150       | VND      | Product        | 2            | 1                  |