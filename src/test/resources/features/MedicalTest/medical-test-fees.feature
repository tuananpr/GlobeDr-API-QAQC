@regression @consultant @medical-test-doctor @medical-test @fees @medical-test-fees
Feature: Medicine Test - Fees

  As org manager
  I want add bonus for doctor and GlobeDr after user use medical test


  Background:
    Given I re-signup "manager_2" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type       | owner     |
      | BV_MY_THANH | LabAndTest | manager_2 |

    When I login as "manager_2"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

    And I create product category with below info
      | status | nameVI   | nameEN |
      | Active | Suc Khoe | Health |
    Then The request should be succeed


  @medical-test-fees-1
  Scenario Outline: add fees for "medical test" product
    When I create new product
      | name            | description           | orgProductType | link                  | isInternal | isPublic | isVisible | feesDoctorConsult   | feesGlobeDrConsult   | feesGlobeDr   |
      | Vật lý trị liệu | mô tả vật lý trị liệu | MedicalTest    | https://jsonpath.com/ | true       | true     | true      | <feesDoctorConsult> | <feesGlobeDrConsult> | <feesGlobeDr> |
    And The response should be
      | success                      | true                 |
      | data.info.feesDoctorConsult  | <feesDoctorConsult>  |
      | data.info.feesGlobeDrConsult | <feesGlobeDrConsult> |
      | data.info.feesGlobeDr        | <feesGlobeDr>        |
    And As manager, I load products
      | description           | productServiceType |
      | mô tả vật lý trị liệu | MedicalTest        |
    And The response should be
      | success                         | true                  |
      | data.list[0].description        | mô tả vật lý trị liệu |
      | data.list[0].feesDoctorConsult  | <feesDoctorConsult>   |
      | data.list[0].feesGlobeDrConsult | <feesGlobeDrConsult>  |
      | data.list[0].feesGlobeDr        | <feesGlobeDr>         |
    Examples:
      | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | 10                | 10                 | 10          |


  @medical-test-fees-2
  Scenario Outline: update fees for "medical test" product
    When I create new product
      | name   | description           | orgProductType | fromPrice | toPrice | currency | link                  | isInternal | isPublic | isVisible |
      | <name> | mô tả vật lý trị liệu | MedicalTest    | 100       | 100     | VND      | https://jsonpath.com/ | true       | true     | true      |
    And The response should be
      | success | true |
    When I update to product name "<name>"
      | name          | description                  | orgProductType | fromPrice | toPrice | currency | link                  | isInternal | isPublic | isVisible | feesDoctorConsult   | feesGlobeDrConsult   | feesGlobeDr   |
      | update <name> | update mô tả vật lý trị liệu | MedicalTest    | 100       | 100     | VND      | https://jsonpath.com/ | true       | true     | true      | <feesDoctorConsult> | <feesGlobeDrConsult> | <feesGlobeDr> |
    And The request should be succeed

    And The response should be
      | success                      | true                 |
      | data.info.feesDoctorConsult  | <feesDoctorConsult>  |
      | data.info.feesGlobeDrConsult | <feesGlobeDrConsult> |
      | data.info.feesGlobeDr        | <feesGlobeDr>        |
    And As manager, I load products
      | description                  | productServiceType |
      | update mô tả vật lý trị liệu | MedicalTest        |
    And The response should be
      | success                         | true                         |
      | data.list[0].description        | update mô tả vật lý trị liệu |
      | data.list[0].feesDoctorConsult  | <feesDoctorConsult>          |
      | data.list[0].feesGlobeDrConsult | <feesGlobeDrConsult>         |
      | data.list[0].feesGlobeDr        | <feesGlobeDr>                |
    Examples:
      | name            | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | Vật lý trị liệu | 10                | 10                 | 10          |

  @medical-test-fees-3
  Scenario Outline: add fees for services of "medical test" product
    When I create new product
      | name            | description           | orgProductType | link                  | isInternal | isPublic | isVisible |
      | Vật lý trị liệu | mô tả vật lý trị liệu | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
    Then The request should be succeed

    And I create new item for product name "Vật lý trị liệu"
      | currency | currencyName | description             | name              | price   | feesDoctorConsult   | feesGlobeDrConsult   | feesGlobeDr   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 | <feesDoctorConsult> | <feesGlobeDrConsult> | <feesGlobeDr> |
    And The response should be
      | success                      | true                 |
      | data.info.feesDoctorConsult  | <feesDoctorConsult>  |
      | data.info.feesGlobeDrConsult | <feesGlobeDrConsult> |
      | data.info.feesGlobeDr        | <feesGlobeDr>        |
    And I load all services into product name "Vật lý trị liệu"
      | name              |
      | Điều trị cột sống |
    And The response should be
      | success                         | true                    |
      | data.list[0].name               | Điều trị cột sống       |
      | data.list[0].description        | mô tả điều trị cột sống |
      | data.list[0].feesDoctorConsult  | <feesDoctorConsult>     |
      | data.list[0].feesGlobeDrConsult | <feesGlobeDrConsult>    |
      | data.list[0].feesGlobeDr        | <feesGlobeDr>           |
    Examples:
      | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | 10                | 10                 | 10          |


  @medical-test-fees-4
  Scenario Outline: update fees for services of "medical test" product
    When I create new product
      | name            | description           | orgProductType | link                  | isInternal | isPublic | isVisible |
      | Vật lý trị liệu | mô tả vật lý trị liệu | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
    Then The request should be succeed

    And I create new item for product name "Vật lý trị liệu"
      | currency | currencyName | description             | name              | price   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 |
    Then The request should be succeed

    And I update item for product name "Vật lý trị liệu" with item name "Điều trị cột sống"
      | currency | currencyName | description             | name              | price   | feesDoctorConsult   | feesGlobeDrConsult   | feesGlobeDr   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 | <feesDoctorConsult> | <feesGlobeDrConsult> | <feesGlobeDr> |

    And The response should be
      | success                      | true                 |
      | data.info.feesDoctorConsult  | <feesDoctorConsult>  |
      | data.info.feesGlobeDrConsult | <feesGlobeDrConsult> |
      | data.info.feesGlobeDr        | <feesGlobeDr>        |
    And I load all services into product name "Vật lý trị liệu"
      | name              |
      | Điều trị cột sống |
    And The response should be
      | success                         | true                    |
      | data.list[0].name               | Điều trị cột sống       |
      | data.list[0].description        | mô tả điều trị cột sống |
      | data.list[0].feesDoctorConsult  | <feesDoctorConsult>     |
      | data.list[0].feesGlobeDrConsult | <feesGlobeDrConsult>    |
      | data.list[0].feesGlobeDr        | <feesGlobeDr>           |
    Examples:
      | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | 10                | 10                 | 10          |

