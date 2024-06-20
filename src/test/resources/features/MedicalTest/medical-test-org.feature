@regression @medical-test @medical-test-org
Feature: Medicine Test - Org

  As manager
  I want update and send result of medical test to patient

  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "nv_ly_thong" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type       | owner     | staff       |
      | BV_MY_THANH | LabAndTest | manager_2 | nv_ly_thong |

    And I set features for staff
      | featureAttributes      | displayName |
      | MedicalTestTakenSample | nv_ly_thong |

    When I login as "nv_ly_thong"
    And I accept join organization

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
    When I login as "user_1"
    And As user, I register medical test
      | orgName     | deliveryType | address                   | phone     | productAndService                                                              |
      | BV_MY_THANH | 4            | 230 cong hoa tan binh hcm | 089999999 | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed

  @medical-test-org-1
  Scenario Outline: org receive new medical test from patient
   #org load medical test then upload result tests
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    When As manager, I load orders
      | orderStatus | orderType   |
      | New         | MedicalTest |
    And The response should be
      | success                         | true                                        |
      | data.total                      | 1                                           |
      | data.list[0].type.name          | Medical Test                                |
      | data.list[0].status.name        | New                                         |
      | data.list[0].deliveryType       | <deliveryType>                              |
      | data.list[0].deliveryTypeName   | <deliveryTypeName>                          |
    # address of profile user
      | data.list[0].address            | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận |
      | data.list[0].contactPhone       | <contactPhone>                              |
      | data.list[0].price              | 4500000.00                                  |
      | data.list[0].receiverName       | user_1                                      |
      | data.list[0].productServiceName | Vật lý trị liệu, Dịch vụ khám răng          |
      | data.list[0].personOrgInfo.name | <orgName>                                   |
    # org view order process
    And As org, I view orders process
      | displayName | orderType   |
      | user_1      | MedicalTest |
    And The response should contains
      | success                           | true         |
      | data.list[0].orderProcessTypeName | Ordered test |
      | data.list[0].personOrgInfo.name   | user_1       |
    Examples:
      | orgName     | address                   | contactPhone | deliveryType | deliveryTypeName |
      | BV_MY_THANH | 230 cong hoa tan binh hcm | 089999999    | 4            | At lab           |

  @medical-test-org-2
  Scenario Outline: Org cancel or take sample of medical test
    # user create medical test

    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    When As org, I update medical test for name "user_1"
      | orderProcessType   | notes   |
      | <orderProcessType> | <notes> |
    And The request should be succeed

    # org view order process
    And As org, I view orders process
      | displayName | orderType   |
      | user_1      | MedicalTest |
    And The response should contains
      | success                           | true                   |
      | data.list[1].orderProcessTypeName | <orderProcessTypeName> |
      | data.list[1].personOrgInfo.name   | <personOrgInfo>        |
    # user view suggest medical test into notification
    When I login as "user_1"
    When As user, I load orders
      | orderType   |
      | MedicalTest |
    And The response should be
      | success                  | true         |
      | data.list[0].type.name   | Medical Test |
      | data.list[0].status.name | <statusName> |

    And I load notifications
      | groupType  |
      | SystemNoti |
    And The response should be
      | success               | true      |
      | data.list[0].userName | <orgName> |
      | data.list[0].message  | <message> |

    And I open noti from medical test
      | groupType  | message   |
      | SystemNoti | <message> |
    And The response should be
      | success                           | true                                        |
      | data.info.patientInfo.patientName | user_1                                      |
      | data.info.type.name               | Medical Test                                |
      | data.info.status.name             | <statusName>                                |
      | data.info.deliveryType            | <deliveryType>                              |
      | data.info.deliveryTypeName        | <deliveryTypeName>                          |
      # address of profile user
      | data.info.address                 | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận |
      | data.info.contactPhone            | <contactPhone>                              |
      | data.info.price                   | 4500000.00                                  |
      | data.info.productServiceName      | Vật lý trị liệu, Dịch vụ khám răng          |
      | data.info.personOrgInfo.name      | <orgName>                                   |
    Examples:
      | orgName     | personOrgInfo | notes | orderProcessType | orderProcessTypeName | message          | statusName | contactPhone | deliveryType | deliveryTypeName |
      | BV_MY_THANH | BV_MY_THANH   | abcd  | CancelOrder      | Cancelled test       | Cancelled tests  | Cancel     | 089999999    | 4            | At lab           |
      | BV_MY_THANH | manager_2     | abcd  | TakenSample      | Got test samples     | Got test samples | Sampled    | 089999999    | 4            | At lab           |

  @medical-test-org-3
  Scenario Outline: Org send result of medical test
    # user create medical test

    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    When As org, I update medical test for name "user_1"
      | orderProcessType | notes |
      | TakenSample      |       |
    And The request should be succeed

    When As org, I upload medical test for name "user_1"
      | file                                | isResult |
      | data/image/avatar/phan-van-tien.jpg | true     |
      | data/pdf-test.pdf                   | true     |
    And The request should be succeed

    # org view order process
    And As org, I view orders process
      | displayName | orderType   |
      | user_1      | MedicalTest |
    And The response should contains
      | success                           | true                 |
      | data.list[0].orderProcessTypeName | Ordered test         |
      | data.list[1].orderProcessTypeName | Got test samples     |
      | data.list[2].orderProcessTypeName | Updated test results |
      | data.list[3].orderProcessTypeName | Updated test results |

    And As manager, I load order detail
      | displayName |
      | user_1      |
    And The response should contains
      | success                           | true                                        |
      | data.info.userInfo.displayName    | user_1                                      |
      | data.info.patientInfo.patientName | user_1                                      |
      | data.info.personOrgInfo.name      | <orgName>                                   |
      | data.info.type.type               | 4                                           |
      | data.info.type.name               | Medical Test                                |
      | data.info.status.status           | 32                                          |
      | data.info.status.name             | Result                                      |
      | data.info.deliveryType            | <deliveryType>                              |
      # address of user profile
      | data.info.address                 | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận |
      | data.info.contactPhone            | <contactPhone>                              |
      | data.info.docs[0].description     | phan-van-tien                               |
      | data.info.docs[1].description     | pdf-test                                    |
    And The image into "data.info.docs[0].url" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.info.docs[1].url" should be match "data/pdf-test.pdf"

     # user view test results into notification
    When I login as "user_1"
    When As user, I load orders
      | orderType   |
      | MedicalTest |
    And The response should be
      | success                  | true         |
      | data.list[0].type.name   | Medical Test |
      | data.list[0].status.name | <statusName> |

    And As user, I load order detail
      | displayName |
      | user_1      |
    And The response should contains
      | success                           | true                                        |
      | data.info.userInfo.displayName    | user_1                                      |
      | data.info.patientInfo.patientName | user_1                                      |
      | data.info.personOrgInfo.name      | <orgName>                                   |
      | data.info.type.type               | 4                                           |
      | data.info.type.name               | Medical Test                                |
      | data.info.status.status           | 32                                          |
      | data.info.status.name             | Result                                      |
      | data.info.deliveryType            | <deliveryType>                              |
      # address of user profile
      | data.info.address                 | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận |
      | data.info.contactPhone            | <contactPhone>                              |
      | data.info.docs[0].description     | phan-van-tien                               |
      | data.info.docs[1].description     | pdf-test                                    |
    And The image into "data.info.docs[0].url" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.info.docs[1].url" should be match "data/pdf-test.pdf"

    And I load notifications
      | groupType  |
      | SystemNoti |
    And The response should be
      | success               | true      |
      | data.list[0].userName | <orgName> |
      | data.list[0].message  | <message> |

    And I open noti from medical test
      | groupType  | message   |
      | SystemNoti | <message> |
    And The response should be
      | success                           | true                                        |
      | data.info.patientInfo.patientName | user_1                                      |
      | data.info.type.name               | Medical Test                                |
      | data.info.status.name             | <statusName>                                |
      | data.info.deliveryType            | <deliveryType>                              |
      | data.info.deliveryTypeName        | <deliveryTypeName>                          |
      # address of user profile
      | data.info.address                 | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận |
      | data.info.contactPhone            | <contactPhone>                              |
      | data.info.price                   | 4500000.00                                  |
      | data.info.productServiceName      | Vật lý trị liệu, Dịch vụ khám răng          |
      | data.info.personOrgInfo.name      | <orgName>                                   |

    # result tests are added into health doc
    When I switch main of family members with below info
      | name   |
      | user_1 |
    Then The request should be succeed
    And I load health document
      | medicalType |
      | LabResult   |
    Then List "data.list[*].description" of response should contains
      | phan-van-tien |
      | pdf-test      |
    And The image into "data.list[0].docUrl" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.list[1].docUrl" should be match "data/pdf-test.pdf"
    Examples:
      | orgName     | address                   | message              | statusName | contactPhone | deliveryType | deliveryTypeName |
      | BV_MY_THANH | 230 cong hoa tan binh hcm | Updated test results | Result     | 089999999    | 4            | At lab           |
