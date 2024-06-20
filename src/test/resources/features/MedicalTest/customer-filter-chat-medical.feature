@regression @medical-test @customer-care @medical-test-org @chat @medical-test-chat @customer-filter @customer-filter-chat-medical
Feature: Medicine Test - Customer care filter chat


  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "cskh_linh" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type       | owner     |
      | BV_MY_THANH | LabAndTest | manager_2 |

    And As sysAdmin, I setting customer care
      | name        | maxCustomerCare |
      | BV_MY_THANH | 2               |
    Then The request should be succeed

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

    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_linh   | VN      | CustomerCare    |
    Then The request should be succeed
    And I set features for staff
      | featureAttributes                                              | displayName |
      | Appointment,Order,ChatInbox,OrderMedicalTest,OrderPrescription | cskh_linh   |
    Then The request should be succeed

    When I login as "user_1"
    And As user, I register medical test
      | orgName     | deliveryType | address                   | phone     | productAndService                                                              |
      | BV_MY_THANH | 4            | 230 cong hoa tan binh hcm | 089999999 | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed


  @customer-filter-chat-medical-1
  Scenario Outline: Customer care filter chat from medical test
    And As user, I create conversation to medical test with content "<msg>"
      | orderType   |
      | MedicalTest |
    And The response should be
      | success | true |

    And I login as "cskh_linh"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage
    #Type is OrderMedicalTest
    And As customer care, I load conversations
      | type             |
      | OrderMedicalTest |
    Then The response should be
      | success                  | true    |
      | data.list[0].name        | <title> |
      | data.list[0].lastMsgText | <msg>   |
    #Type is all
    And As customer care, I load conversations
      | type |
      |      |
    Then The response should be
      | success                  | true    |
      | data.list[0].name        | <title> |
      | data.list[0].lastMsgText | <msg>   |

    Examples:
      | msg      | title                         |
      | chào bạn | user_1 - Order a medical test |
