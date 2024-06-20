@regression @connection
Feature: User follow org and unfollow org

  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "user_2" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | type     | owner     |
      | PK_LUONG_VAN | Hospital | manager_1 |


  @follow-1
  Scenario: User follow org and then become to member
    Given I login as "user_1"
    And I FOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed

    And I load the followed org
    And List "data.list[*].orgName" of response should contains
      | PK_LUONG_VAN |

    When I login as "manager_1"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage

    Then I load org members
      | name |
      |      |
    And List "data.list[*].displayName" of response should contains
      | user_1 |

    And I load org members
      | name       |
      | All member |
    And List "data.list[*].displayName" of response should contains
      | user_1 |


  @follow-2
  Scenario: User unfollow org
    Given I login as "user_1"
    And I FOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed

    And I UNFOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed

    And I load the followed org
    And List "data.list[*].orgName" of response should not contains
      | PK_LUONG_VAN |

    When I login as "manager_1"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage

    Then I load org members
      | name |
      |      |
    And List "data.list[*].displayName" of response should not contains
      | user_1 |

    And I load org members
      | name       |
      | All member |
    And List "data.list[*].displayName" of response should not contains
      | user_1 |