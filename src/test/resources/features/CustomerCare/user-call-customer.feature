@regression @org @customer-care @customer-video-call @user-call-customer @video-call
Feature: Customer care : user call customer


  Background: create customer care
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_lan" account
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
      | cskh_lan    | VN      | CustomerCare    |
    Then The request should be succeed

    And I login as "cskh_lan"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    #customer care subscribe channels to receive information from server
    And On Pusher, "cskh_lan" subscribe all channels


  @user-call-customer-1
  Scenario: user call to customer care
    And As customer care, I set online
    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I call video to customer care
      | orgName   | customerCareName |
      | BV_MY_TAM | cskh_lan         |
    Then The request should be succeed

    And I login as "cskh_lan"
    And On Pusher, "cskh_lan" get information
    And As customer care, I receive video call from chat
    Then The request should be succeed

    And I login as "user_1"
    And As user, I end video call
      | videoCallType |
      | Chat          |
    Then The request should be succeed
    And I load msg on conversation
      | isOrg | conversationName |
      | true  | BV_MY_TAM        |
    And The response should be
      | success          | true                                                                                                                                                                  |
      | data.list[0].msg | <b>cskh_lan</b> ends support                                                                                                                                          |
      | data.list[1].msg | <p style='text-align: center'><font color='#2196F3'>End video call</font></p><p style='text-align: center'><font color='#2196F3'>Video chat duration 00:00</font></p> |
      | data.list[2].msg | <p style='margin:0'>SUPPORTING BY</p><p style='margin:0'>cskh_lan</p>                                                                                                 |


  @user-call-customer-2
  Scenario: user call to customer care then customer care busy
    And As customer care, I set online
    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I call video to customer care
      | orgName   | customerCareName |
      | BV_MY_TAM | cskh_lan         |
    Then The request should be succeed

    And I login as "cskh_lan"
    And On Pusher, "cskh_lan" get information
    And I busy video call from chat
    Then The request should be succeed

  @user-call-customer-3
  Scenario: user call to customer care then press miss call
    And As customer care, I set online
    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I call video to customer care
      | orgName   | customerCareName |
      | BV_MY_TAM | cskh_lan         |
    Then The request should be succeed

    And As user, I miss call from customer care
    Then The request should be succeed

  @user-call-customer-4
  Scenario: user can't call to customer care that is offline
    And As customer care, I set offline
    And I login as "user_1"
    And As user, I load customer cares
      | name |
      |      |
    And The response should be
      | success   | true |
      | data.list | []   |