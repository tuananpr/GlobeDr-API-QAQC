@regression @medical-test @medical-test-org @chat @medical-test-chat @chat
Feature: Medicine Test - User Chat

  As user
  I want chat to org that I order medical test
  In order to get more information

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
    When I login as "user_1"
    And As user, I register medical test
      | orgName     | deliveryType | address                   | phone     | productAndService                                                              |
      | BV_MY_THANH | 4            | 230 cong hoa tan binh hcm | 089999999 | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed

  @medical-test-chat-1
  Scenario Outline: user chat to org from medical test
    And As user, I create conversation to medical test with content "<msg>"
      | orderType   |
      | MedicalTest |
    And The response should be
      | success | true |

    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage

    And As manager, I load conversation details
      | conversationName |
      | <title>          |
    And The response should be
      | success                       | true    |
      | data.conversation.name        | <title> |
      | data.conversation.lastMsgText | <msg>   |

    And As manager, I load msg of medical test
      | orderType   |
      | MedicalTest |
    And The response should be
      | success          | true  |
      | data.list[0].msg | <msg> |

    Examples:
      | msg      | title                         |
      | chào bạn | user_1 - Order a medical test |
