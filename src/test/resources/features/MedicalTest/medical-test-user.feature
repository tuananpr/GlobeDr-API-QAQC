@regression @medical-test @medical-test-user
Feature: Medicine Test - User

  As user
  I want to register medical test

  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile

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

    # Create product services
    When I create new product
      | name              | description             | orgProductType | link                  | isInternal | isPublic | isVisible |
      | Vật lý trị liệu   | mô tả vật lý trị liệu   | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
      | Dịch vụ khám răng | mô tả Dịch vụ khám răng | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
    Then The request should be succeed

    And I create new item for product name "Vật lý trị liệu"
      | currency | currencyName | description             | name              | price   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 |
      | 2        | VND          | mô tả điều trị giãn cơ  | Điều trị giãn cơ  | 1500000 |
    Then The request should be succeed

    And I create new item for product name "Dịch vụ khám răng"
      | currency | currencyName | description             | name      | price   |
      | 2        | VND          | mô tả dịch vụ trám răng | Trám răng | 1500000 |
    Then The request should be succeed


  @medical-test-user-1
  Scenario Outline: Patient order new medical test
    # user create medical test
    When I login as "user_1"
    And As user, I register medical test
      | orgName   | deliveryType   | address   | country                                            | city                                | district                        | ward                                | phone          | productAndService                                                              |
      | <orgName> | <deliveryType> | <address> | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | <contactPhone> | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    And The response should be
      | success                           | true                               |
      | data.info.patientInfo.patientName | user_1                             |
      | data.info.type.name               | Medical Test                       |
      | data.info.status.name             | New                                |
      | data.info.deliveryType            | <deliveryType>                     |
      | data.info.deliveryTypeName        | <deliveryTypeName>                 |
      | data.info.address                 | <address>                          |
      | data.info.country.name            | Việt Nam                           |
      | data.info.city.name               | Hồ Chí Minh                        |
      | data.info.district.name           | Quận 3                             |
      | data.info.ward.name               | Phường 11                          |
      | data.info.contactPhone            | <contactPhone>                     |
      | data.info.price                   | 4500000.00                         |
      | data.info.productServiceName      | Vật lý trị liệu, Dịch vụ khám răng |
      | data.info.personOrgInfo.name      | <orgName>                          |
    When As user, I load orders
      | orderStatus | orderType   |
      | New         | MedicalTest |
    And The response should be
      | success                         | true                               |
      | data.total                      | 1                                  |
      | data.list[0].type.name          | Medical Test                       |
      | data.list[0].status.name        | New                                |
      | data.list[0].deliveryType       | <deliveryType>                     |
      | data.list[0].deliveryTypeName   | <deliveryTypeName>                 |
      | data.list[0].address            | <address>                          |
      | data.list[0].country.name       | Việt Nam                           |
      | data.list[0].city.name          | Hồ Chí Minh                        |
      | data.list[0].district.name      | Quận 3                             |
      | data.list[0].ward.name          | Phường 11                          |
      | data.list[0].contactPhone       | <contactPhone>                     |
      | data.list[0].price              | 4500000.00                         |
      | data.list[0].receiverName       | user_1                             |
      | data.list[0].productServiceName | Vật lý trị liệu, Dịch vụ khám răng |
      | data.list[0].personOrgInfo.name | <orgName>                          |
    When As user, I load order detail
      | orderStatus | orderType   |
      | New         | MedicalTest |
    And The response should be
      | success                           | true                               |
      | data.info.patientInfo.patientName | user_1                             |
      | data.info.type.name               | Medical Test                       |
      | data.info.status.name             | New                                |
      | data.info.deliveryType            | <deliveryType>                     |
      | data.info.deliveryTypeName        | <deliveryTypeName>                 |
      | data.info.address                 | <address>                          |
      | data.info.country.name            | Việt Nam                           |
      | data.info.city.name               | Hồ Chí Minh                        |
      | data.info.district.name           | Quận 3                             |
      | data.info.ward.name               | Phường 11                          |
      | data.info.contactPhone            | <contactPhone>                     |
      | data.info.price                   | 4500000.00                         |
      | data.info.productServiceName      | Vật lý trị liệu, Dịch vụ khám răng |
      | data.info.personOrgInfo.name      | <orgName>                          |
    Examples:
      | orgName     | address                   | contactPhone | deliveryType | deliveryTypeName |
      | BV_MY_THANH | 230 cong hoa tan binh hcm | 089999999    | 4            | At lab           |
      | BV_MY_THANH | 230 cong hoa tan binh hcm | 089999999    | 8            | At home          |

  @medical-test-user-2
  Scenario Outline:  Patient order invalid medical test
    When I login as "user_1"
        # user order medical test
    And As user, I register medical test
      | orgName   | deliveryType   | address   | phone          | productAndService   |
      | <orgName> | <deliveryType> | <address> | <contactPhone> | <productAndService> |

    And The response should be
      | success    | false     |
      | message    | <message> |
      | <errField> | <errMsg>  |
    Examples:
      | orgName     | address                   | contactPhone | deliveryType | deliveryTypeName | productAndService                                                              | message                                | errField               | errMsg                 |
      | BV_MY_THANH | 230 cong hoa tan binh hcm | 089999999    | 4            | At lab           |                                                                                | List of tests : This field is required | errors.productServices | This field is required |
      | BV_MY_THANH | 230 cong hoa tan binh hcm |              | 4            | At lab           | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng | Phone : This field is required         | errors.phone           | This field is required |


