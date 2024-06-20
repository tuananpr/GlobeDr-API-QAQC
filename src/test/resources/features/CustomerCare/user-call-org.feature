@regression @org @customer-care @customer-video-call @user-call-org @video-call
Feature: Customer care : user call org


  Background: create customer care and set call round robin for org
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_pham_van" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     | orgFeatureAttributes | orgAttribute                             |
      | BV_MY_TAM | manager_1 | All                  | JoinedGdr,EnableVideoCall,CallRoundRobin |

    And As sysAdmin, I setting customer care
      | name      | maxCustomerCare |
      | BV_MY_TAM | 4               |
    Then The request should be succeed

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

    When On org, I add staff
      | displayName   | country | staffAttributes |
      | cskh_pham_van | VN      | CustomerCare    |
    Then The request should be succeed

    And I login as "cskh_pham_van"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    #customer care subscribe channels to receive information from server
    And On Pusher, "cskh_pham_van" subscribe all channels


  @user-call-org-1
  Scenario: customer care (online) receive call when user call to org
    And As customer care, I set online
    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I call video to org
      | orgName   |
      | BV_MY_TAM |
    Then The request should be succeed

    And I login as "cskh_pham_van"
    And On Pusher, "cskh_pham_van" get information
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
      | data.list[0].msg | <b>cskh_pham_van</b> ends support                                                                                                                                     |
      | data.list[1].msg | <p style='text-align: center'><font color='#2196F3'>End video call</font></p><p style='text-align: center'><font color='#2196F3'>Video chat duration 00:00</font></p> |
      | data.list[2].msg | <p style='margin:0'>SUPPORTING BY</p><p style='margin:0'>cskh_pham_van</p>                                                                                            |

  @user-call-org-2
  Scenario: user receive message when call to org that all customer care is offline. And customer care can't receive pusher information from call
    And As customer care, I set offline
    And I login as "user_1"
    And As user, I call video to org
      | orgName   |
      | BV_MY_TAM |
    And The response should be
      | success | true                                 |
      | message | All customer care is offline for now |

    And I login as "cskh_pham_van"
    And On Pusher, I not found information from "cskh_pham_van"