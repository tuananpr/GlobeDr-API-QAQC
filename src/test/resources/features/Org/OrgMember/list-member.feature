@regression @org-member @list-member
Feature: Filter membership of org

  As org
  I want filter member
  In order to update information for member

  Background:
    Given On SqlServer, I delete organization by name "PK_LUONG_VAN"
    And I re-signup "manager_2" account
    And I re-signup "user_3" account

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | owner     |
      | PK_LUONG_VAN | manager_2 |

    # user join org
    Given I login as "user_3"
    And I FOLLOW business
      | name         |
      | PK_LUONG_VAN |
    Then The request should be succeed

  @list-member-1
  Scenario Outline: filter members into org
    # user update profile
    When I update profile of logged user with dob is '<age>' and below info
      | displayName   | email                  | address                                     | city                              | country   | gender   | phone   |
      | <displayName> | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | <country> | <gender> | <phone> |
    Then The request should be succeed
    # manager search member
    Given I login as "manager_2"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage
    And I load org members
      | displayName   | phone   | gender   | country   | fromAge   | toAge   | fromJoinDate | toJoinDate |
      | <displayName> | <phone> | <gender> | <country> | <fromAge> | <toAge> | yesterday    | today      |
    Then The response should be
      | success                  | true            |
      | data.total               | 1               |
      | data.list[0].displayName | <displayName>   |
      | data.list[0].phone       | <expectedPhone> |
      | data.list[0].gender      | <gender>        |
      | data.list[0].country     | <country>       |
    Examples:
      | displayName | phone      | expectedPhone | gender | country | age     | fromAge | toAge |
      | A li ba ma  | 0303305400 | 84303305400   | 2      | VN      | 16 yr   | 15      | 16    |
      | A li ba ma  | 0303305400 | 1303305400    | 1      | US      | 1 month | 0       | 1     |

  @list-member-2
  Scenario Outline: update patientId
    Given I login as "manager_2"
    And I accept join organization
    And I select org "PK_LUONG_VAN" that I manage
    And As org, I update patient id for member name "<displayName>"
      | patientId   |
      | <patientId> |
    Then The request should be succeed

    And I load org members
      | patientId   |
      | <patientId> |
    Then The response should be
      | success                  | true          |
      | data.total               | 1             |
      | data.list[0].displayName | <displayName> |
    Examples:
      | displayName | patientId |
      | user_3      | G123      |



