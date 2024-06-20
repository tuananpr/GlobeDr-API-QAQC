@regression @org @chat @customer-care @customer-chat @video-call
Feature: Customer care : answer questions from customer

  As orgAdmin
  I want to add customer care to answer questions from customer

  Background:
    When I re-signup "manager_1" account
    When I re-signup "cskh_lieu" account
    When I re-signup "user_2" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     |
      | BV_MY_TAM | manager_1 |
    And As sysAdmin, I setting customer care
      | name      | maxCustomerCare |
      | BV_MY_TAM | 2               |
    Then The request should be succeed
    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage


  @customer-chat-1
  Scenario Outline:  customer care receive conversation after user chat to org
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_lieu   | VN      | <staffAttributes> |
    Then The request should be succeed

    And I login as "user_2"
    And As user, I create conversation with org
      | recipientName | msg   |
      | BV_MY_TAM     | <msg> |
    And The request should be succeed

    And I login as "cskh_lieu"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As manager, I load conversations
      | type |
      |      |
    And The response should be
      | success                  | true  |
      | data.list[0].lastMsgText | <msg> |
    Examples:
      | msg                  | staffAttributes |
      | [Auto]Hi!!!! Orrg :) | CustomerCare    |
      | [Auto]Hi!!!! Orrg :) | Reception       |
      | [Auto]Hi!!!! Orrg :) | Nurse           |


  @customer-chat-2
  Scenario Outline: when customer care answer this conversation then other can't join conversation
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_lieu   | VN      | <staffAttributes> |
      | user_3      | VN      | <staffAttributes> |
    Then The request should be succeed

    And I login as "user_2"
    And As user, I create conversation with org
      | recipientName | msg   |
      | BV_MY_TAM     | <msg> |
    And The request should be succeed

    And I login as "cskh_lieu"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    And As org, I add message into conversation with content
      | conversationName | msg                      |
      | user_2           | Chào bạn. m là cskh_lieu |
    And The request should be succeed

    And As manager, I load msg on conversation
      | conversationName |
      | user_2           |
    And The response should be
      | success          | true                                                                   |
      | data.list[0].msg | Chào bạn. m là cskh_lieu                                               |
      | data.list[1].msg | <p style='margin:0'>SUPPORTING BY</p><p style='margin:0'>cskh_lieu</p> |
      | data.list[2].msg | <msg>                                                                  |

    And I login as "user_3"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As org, I add message into conversation with content
      | conversationName | msg                   |
      | user_2           | Chào bạn. m là user_3 |
    And The response should be
      | success                | false                      |
      | errors.conversationSig | Supported by another staff |
    Examples:
      | msg                  | staffAttributes |
      | [Auto]Hi!!!! Orrg :) | CustomerCare    |
      | [Auto]Hi!!!! Orrg :) | Reception       |
      | [Auto]Hi!!!! Orrg :) | Nurse           |


  @customer-chat-3
  Scenario Outline: when sysAdmin answer this conversation then other can join conversation, except sysAdmin
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_lieu   | VN      | <staffAttributes> |
      | user_3      | VN      | <staffAttributes> |
    Then The request should be succeed

    And I login as "user_2"
    And As user, I create conversation with org
      | recipientName | msg   |
      | BV_MY_TAM     | <msg> |
    And The request should be succeed

    And I login as "system_admin_1"
    And As sysAdmin, I search org
      | name      |
      | BV_MY_TAM |
    And As org, I add message into conversation with content
      | conversationName | msg                           |
      | user_2           | Chào bạn. m là system_admin_1 |
    And The request should be succeed

    And I login as "cskh_lieu"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    And As org, I add message into conversation with content
      | conversationName | msg                      |
      | user_2           | Chào bạn. m là cskh_lieu |
    And The request should be succeed

    Examples:
      | msg                  | staffAttributes |
      | [Auto]Hi!!!! Orrg :) | CustomerCare    |
      | [Auto]Hi!!!! Orrg :) | Reception       |
      | [Auto]Hi!!!! Orrg :) | Nurse           |


  @customer-chat-4
  Scenario Outline: Others just can only join conversation, after this customer care done pick up conversation
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_lieu   | VN      | <staffAttributes> |
      | user_3      | VN      | <staffAttributes> |
    Then The request should be succeed

    And I login as "user_2"
    And As user, I create conversation with org
      | recipientName | msg   |
      | BV_MY_TAM     | <msg> |
    And The request should be succeed

    And I login as "cskh_lieu"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    And As org, I add message into conversation with content
      | conversationName | msg                      |
      | user_2           | Chào bạn. m là cskh_lieu |
    And The request should be succeed

    And As org, I get actions into conversation
      | conversationName |
      | user_2           |
    And The response should be
      | success                 | true |
      | data.actions.donePickUp | true |

    And As org, I done conversation
      | conversationName |
      | user_2           |
    And The request should be succeed

    And I login as "user_3"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As org, I add message into conversation with content
      | conversationName | msg                   |
      | user_2           | Chào bạn. m là user_3 |
    And The response should be
      | success      | true                  |
      | data.msg.msg | Chào bạn. m là user_3 |
    Examples:
      | msg                  | staffAttributes |
      | [Auto]Hi!!!! Orrg :) | CustomerCare    |
      | [Auto]Hi!!!! Orrg :) | Reception       |
      | [Auto]Hi!!!! Orrg :) | Nurse           |


  @customer-chat-5
  Scenario Outline: The manager checks if the customer care is answering conversation or not
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | cskh_lieu   | VN      | <staffAttributes> |
    Then The request should be succeed

    And I login as "user_2"
    And As user, I create conversation with org
      | recipientName | msg   |
      | BV_MY_TAM     | <msg> |
    And The request should be succeed

    And I login as "cskh_lieu"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    And As org, I add message into conversation with content
      | conversationName | msg                      |
      | user_2           | Chào bạn. m là cskh_lieu |
    And The request should be succeed

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As manager, I checks if the customer care answering conversation or not
      | displayName |
      | cskh_lieu   |
    And The response should be
      | success       | true |
      | data.isPickUp | true |
    Examples:
      | msg                  | staffAttributes |
      | [Auto]Hi!!!! Orrg :) | CustomerCare    |
      | [Auto]Hi!!!! Orrg :) | Reception       |
      | [Auto]Hi!!!! Orrg :) | Nurse           |
