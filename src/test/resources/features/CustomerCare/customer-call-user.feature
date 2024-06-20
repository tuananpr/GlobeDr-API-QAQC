@regression @org @customer-care @customer-video-call @customer-call-user @video-call
Feature: Customer care : customer call user

  As customer care
  I want to call to user

  Background:
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_an" account
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
      | cskh_an     | VN      | CustomerCare    |
    Then The request should be succeed

    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I create conversation with org
      | recipientName | msg  |
      | BV_MY_TAM     | chào |
    And The request should be succeed

    And I login as "cskh_an"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    #customer care subscribe channels to receive information from server
    And On Pusher, "cskh_an" subscribe all channels
    And As customer care, I set online

  @customer-call-user-1
  Scenario: customer care call to user
    And As customer care, I call video to user into conversation
      | conversationName |
      | user_1           |
    And The request should be succeed

    And I login as "user_1"
    And On Pusher, "user_1" get information
    And As user, I receive video call from chat
    Then The request should be succeed

    And As user, I end video call
      | videoCallType |
      | Chat          |
    Then The request should be succeed

    And I load msg on conversation
      | isOrg | conversationName |
      | true  | BV_MY_TAM        |
    And The response should be
      | success          | true                                                                                                                                                                  |
      | data.list[0].msg | <b>cskh_an</b> ends support                                                                                                                                           |
      | data.list[1].msg | <p style='text-align: center'><font color='#2196F3'>End video call</font></p><p style='text-align: center'><font color='#2196F3'>Video chat duration 00:00</font></p> |
      | data.list[2].msg | <p style='margin:0'>SUPPORTING BY</p><p style='margin:0'>cskh_an</p>                                                                                                  |


  @customer-call-user-2
  Scenario: customer care call to user then user busy
    And As customer care, I call video to user into conversation
      | conversationName |
      | user_1           |
    And The request should be succeed

    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" get information
    And I busy video call from chat
    Then The request should be succeed
