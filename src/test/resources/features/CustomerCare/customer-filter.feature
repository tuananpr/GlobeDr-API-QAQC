@regression @org @customer-care @customer-video-call @customer-filter @video-call
Feature: Customer care : filter chat


  Background:
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_do" account
    When I re-signup "user_3" account

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     | orgFeatureAttributes | orgAttribute              |
      | BV_MY_TAM | manager_1 | All                  | JoinedGdr,EnableVideoCall |

    And As sysAdmin, I setting customer care
      | name      | maxCustomerCare |
      | BV_MY_TAM | 4               |
    Then The request should be succeed
    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_do     | VN      | CustomerCare    |
    Then The request should be succeed

    And I set features for staff
      | featureAttributes                                              | displayName |
      | Appointment,Order,ChatInbox,OrderMedicalTest,OrderPrescription | cskh_do     |
    Then The request should be succeed

  @prepare-customer-filter-3
  Scenario: Customer care load conversation types
    And I login as "cskh_do"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And I load conversation types
    Then The response should be
      | success            | true                 |
      | data.list[0].text  | Appointment          |
      | data.list[0].value | 16                   |
      | data.list[0].tag   | 16                   |

      | data.list[1].text  | Order Prescription   |
      | data.list[1].value | 32                   |
      | data.list[1].tag   | 32                   |

      | data.list[2].text  | Order a medical test |
      | data.list[2].value | 64                   |
      | data.list[2].tag   | 64                   |

      | data.list[3].text  | Customer care        |
      | data.list[3].value | 128                  |
      | data.list[3].tag   | 128                  |

      | data.list[4].text  | Order                |
      | data.list[4].value | 256                  |
      | data.list[4].tag   | 256                  |


  @customer-filter-2
  Scenario: conversation that answering from customer care, it always on top when filter type "Customer care"
    And I login as "user_1"
    And As user, I create conversation with org
      | recipientName | msg           |
      | BV_MY_TAM     | Hi, Im user_1 |
    And The request should be succeed

    And I login as "cskh_do"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As org, I add message into conversation with content
      | conversationName | msg      |
      | user_1           | Chào bạn |
    And The request should be succeed

    And I login as "user_3"
    And As user, I create conversation with org
      | recipientName | msg            |
      | BV_MY_TAM     | Hi, I'm user_3 |
    And The request should be succeed

    And I login as "cskh_do"
    And I select org "BV_MY_TAM" that I manage
    #Type is customer care
    And As customer care, I load conversations
      | type         |
      | CustomerCare |
    Then The response should be
      | success                  | true           |
      | data.list[0].name        | user_1         |
      | data.list[0].lastMsgText | Chào bạn       |
      | data.list[1].name        | user_3         |
      | data.list[1].lastMsgText | Hi, I'm user_3 |

    #Type is all
    And As customer care, I load conversations
      | type |
      |      |
    Then The response should be
      | success                  | true           |
      | data.list[0].name        | user_3         |
      | data.list[0].lastMsgText | Hi, I'm user_3 |
      | data.list[1].name        | user_1         |
      | data.list[1].lastMsgText | Chào bạn       |
