@regression @buy-medicine @customer-care @customer-filter @customer-filter-buy-medicine
Feature: Buy Medicine : Customer care

  As Customer care
  I want load chat from order prescription

  Background: create user and org
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "cskh_lam" account
    And On SqlServer, I delete voucher category that have "nameVN" is "Do an Han Quoc"

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     |
      | BV_TRAN_SU | Hospital | manager_1 |

    And As sysAdmin, I setting customer care
      | name       | maxCustomerCare |
      | BV_TRAN_SU | 2               |
    Then The request should be succeed


    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_TRAN_SU" that I manage
    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_lam    | VN      | CustomerCare    |
    Then The request should be succeed
    And I set features for staff
      | featureAttributes                                              | displayName |
      | Appointment,Order,ChatInbox,OrderMedicalTest,OrderPrescription | cskh_lam    |
    Then The request should be succeed
    And I login as "user_1"

  @customer-filter-buy-medicine-1
  Scenario Outline: Customer care filter chat from order prescription
    And I order prescription to org name "BV_TRAN_SU"
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

    And I login as "cskh_lam"
    And I accept join organization
    And I select org "BV_TRAN_SU" that I manage
    #Type is OrderPrescription
    And As customer care, I load conversations
      | type              |
      | OrderPrescription |
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
      | msg           | title                       |
      | [Attach file] | user_1 - Order Prescription |

























