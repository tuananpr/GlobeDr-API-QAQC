@regression @buy-medicine
Feature: Buy Medicine when user don't have use voucher.

  As User
  I want buy medicine from org

  Background: create user and org
    Given I re-signup "manager_1" account
    And I re-signup "user_1" account
    And On SqlServer, I delete voucher category that have "nameVN" is "Do an Han Quoc"

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     |
      | BV_TRAN_SU | Hospital | manager_1 |
    And As sysAdmin, I set attributes for org name "BV_TRAN_SU"
      | EnableShipTo |
      | Confirmed    |
      | JoinedGdr    |
    Then The request should be succeed
    And I login as "user_1"

  @buy-medicine-connected-1-a
  Scenario: user order prescription to followed org (delivery type is StorePickup)
    And I FOLLOW business
      | name       |
      | BV_TRAN_SU |
    And The request should be succeed
    And I order prescription to followed org name "BV_TRAN_SU"
      | phoneNumber | address                              | additionalItems            | files                               | deliveryType | isAttached |
      | 0933338822  | 74 Ong Ich Khiem, phuong 14, quan 11 | mua them 2 goi thuoc chu P | data/image/prescription/pres-01.jpg | StorePickup  | true       |
    And The request should be succeed
    And As user, I load conversation details
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success                       | true                            |
      | data.conversation.name        | BV_TRAN_SU - Order Prescription |
      | data.conversation.lastMsgText | [Attach file]                   |
    And I load msg on conversation
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success              | true                                                                                                                                                                                                                                                                |
      | data.list[0].msgText | [Attach file]                                                                                                                                                                                                                                                       |
      | data.list[1].msg     | <p><b>Order is requested by</b></p><p>user_1</p><p>0933338822</p><p><b>Prescription</b></p><p>Attached</p><p>Additional items</p><p>mua them 2 goi thuoc chu P</p><p><b>Method of delivery</b></p><p>Pickup in store</p><p>74 Ong Ich Khiem, phuong 14, quan 11</p> |


  @buy-medicine-connected-1-b
  Scenario: user order prescription to org (delivery type is Ship to address)
    And I order prescription to org name "BV_TRAN_SU"
      | phoneNumber | address                              | country                                            | city                                | district                        | ward                                | additionalItems            | files                               | deliveryType | isAttached |
      | 0933338822  | 74 Ong Ich Khiem, phuong 14, quan 11 | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | mua them 2 goi thuoc chu P | data/image/prescription/pres-01.jpg | ShipTo       | true       |
    And The request should be succeed
    And As user, I load conversation details
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success                       | true                            |
      | data.conversation.name        | BV_TRAN_SU - Order Prescription |
      | data.conversation.lastMsgText | [Attach file]                   |
    And I load msg on conversation
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success              | true                                                                                                                                                                                                                                                                                                                                                                                                     |
      | data.list[0].msgText | [Attach file]                                                                                                                                                                                                                                                                                                                                                                                            |
      | data.list[1].msg     | <p><b>Order is requested by</b></p><p>user_1</p><p>0933338822</p><p><b>Prescription</b></p><p>Attached</p><p>Additional items</p><p>mua them 2 goi thuoc chu P</p><p><b>Method of delivery</b></p><p>Ship to</p><p>74 Ong Ich Khiem, phuong 14, quan 11</p><p><b>Ward</b></p><p>Phường 11</p><p><b>District</b></p><p>Quận 3</p><p><b>City</b></p><p>Hồ Chí Minh</p><p><b>Country</b></p><p>Việt Nam</p> |

  @buy-medicine-connected-1-c
  Scenario: User order medicine from exited prescription
    And I login as "user_1"
    When I switch main of family members with below info
      | name   |
      | user_1 |
    When As user, I upload health document
      | attributes | description                       | medicalType | docType | createdDate | file                                |
      | 1          | Don thuoc di kham ngay 25.12.2019 | 1           | 1       | today       | data/image/prescription/pres-03.jpg |
    Then The request should be succeed
    And I order Prescription with description "Don thuoc di kham ngay 25.12.2019" to org name "BV_TRAN_SU"
      | phoneNumber | address                              | deliveryType | isAttached |
      | 0933338822  | 74 Ong Ich Khiem, phuong 14, quan 11 | StorePickup  | true       |
    And The request should be succeed
    And As user, I load conversation details
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success                       | true                            |
      | data.conversation.name        | BV_TRAN_SU - Order Prescription |
      | data.conversation.lastMsgText | [Attach file]                   |

  @buy-medicine-connected-1-d
  Scenario: User order medicine by description
    And I order prescription to org name "BV_TRAN_SU"
      | phoneNumber | address                              | deliveryType | additionalItems    | isAttached |
      | 0933338822  | 74 Ong Ich Khiem, phuong 14, quan 11 | StorePickup  | mua 2 chai dầu gió | false      |
    And The request should be succeed
    And As user, I load conversation details
      | isOrg | conversationName                |
      | true  | BV_TRAN_SU - Order Prescription |
    Then The response should be
      | success                       | true                            |
      | data.conversation.name        | BV_TRAN_SU - Order Prescription |
      | data.conversation.lastMsgText | [Order]                         |



























