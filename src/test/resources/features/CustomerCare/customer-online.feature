@regression @org @customer-care @customer-video-call @customer-online @video-call
Feature: Customer care : customer call user

  As customer care
  I want to online or offline
  In order user can see me or not

  Background:
    When I re-signup "manager_1" account
    When I re-signup "user_1" account
    When I re-signup "cskh_phi" account
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
      | cskh_phi    | VN      | CustomerCare    |
    Then The request should be succeed

    And I login as "cskh_phi"
    And I accept join organization
    And I select org "BV_MY_TAM" that I manage

  @customer-online-1
  Scenario: customer care set offline and online then view status
    And As customer care, I set online
    And As customer care, I load customer care status
    And The response should be
      | success                 | true |
      | data.customerCareStatus | true |

    And I login as "user_1"
    And As user, I load customer cares
      | name     |
      | cskh_phi |
    And The response should be
      | success           | true     |
      | data.list[0].name | cskh_phi |


    And I login as "cskh_phi"
    And I select org "BV_MY_TAM" that I manage
    And As customer care, I set offline
    And As customer care, I load customer care status
    And The response should be
      | success                 | true  |
      | data.customerCareStatus | false |

    And I login as "user_1"
    And As user, I load customer cares
      | name |
      |      |
    And The response should be
      | success   | true |
      | data.list | []   |

