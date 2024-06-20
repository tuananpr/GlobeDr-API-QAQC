@regression @org-member @manage-member

Feature: Manager group member of org

  As org
  I want create group
  In order to move membership to group

  Background:
    Given I re-signup "manager_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | type     | owner     |
      | PK_LUONG_VAN | Hospital | manager_1 |

    When I login as "manager_1"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage

  @org-member-group-1
  Scenario: Add group
    And I create group member
      | name |
      | AAA  |
      | DDD  |
    And The request should be succeed
    And I load group member
      | name |
      |      |
    And List "data.list[*].name" of response should contains
      | AAA |
      | DDD |

  @org-member-group-2
  Scenario: delete group
    And I create group member
      | name  |
      | lop 1 |
    And The request should be succeed

    And I delete group member
      | name  |
      | lop 1 |
    And The request should be succeed

    And I load group member
      | name  |
      | lop 1 |
    And The response should be
      | success    | true |
      | data.total | 0    |
      | data.list  | []   |

  @org-member-group-3
  Scenario: Update group
    And I create group member
      | name  |
      | lop 1 |
    And The request should be succeed

    And I update below info for group member name "lop 1"
      | name  |
      | lop 2 |
    And The request should be succeed
    And I load group member
      | name |
      |      |
    And List "data.list[*].name" of response should contains
      | lop 2 |
    And List "data.list[*].name" of response should not contains
      | lop 1 |


  @org-member-group-4
  Scenario: load default group
    And I load group member
      | name |
      |      |
    And The response should be
      | success                | true       |
      | data.total             | 1          |
      | data.list[0].name      | All member |
      | data.list[0].isDefault | true       |


  @org-member-group-5
  Scenario: can't delete default group
    And I delete group member
      | name       |
      | All member |
    And The request should be fail