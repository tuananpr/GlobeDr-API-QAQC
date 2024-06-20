@regression @org @customer-care @customer-video-call @customer-statistic @video-call
Feature: Customer care : statistic


  Background: user call to customer care
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_le" account
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
      | cskh_le     | VN      | CustomerCare    |
    Then The request should be succeed

    And I login as "cskh_le"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage
    And As customer care, I set online
    #customer care subscribe channels to receive information from server
    And On Pusher, "cskh_le" subscribe all channels

    And I login as "user_1"
    #user subscribe channels to receive information from server
    And On Pusher, "user_1" subscribe all channels
    And As user, I call video to customer care
      | orgName   | customerCareName |
      | BV_MY_TAM | cskh_le          |
    Then The request should be succeed


  @customer-statistic-1
  Scenario: user call customer care. Then customer care receive call and view statistic
    And I login as "cskh_le"
    And On Pusher, "cskh_le" get information
    And As customer care, I receive video call from chat
    Then The request should be succeed

    And I login as "user_1"
    And As user, I end video call
      | videoCallType |
      | Chat          |
    Then The request should be succeed

    And I login as "cskh_le"
    And I select org "BV_MY_TAM" that I manage
    And I view customer care statistic
      | videoCall |
      | false     |
    And The response should be
      | success                       | true    |
      | data.list[0].customerCareName | cskh_le |
