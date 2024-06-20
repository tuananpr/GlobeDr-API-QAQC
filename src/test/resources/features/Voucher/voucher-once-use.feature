@regression @voucher @voucher-user @voucher-once-use
Feature: Voucher once use

  As system
  I want to user only use voucher once


  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | owner     |
      | BV_Y_HOC | Hospital | manager_1 |


    #Create category for voucher
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |
    Then The request should be succeed

    #Create voucher
    When I login as "manager_1"
    And I accept join organization
    And I select org "BV_Y_HOC" that I manage
    And As manager, I create voucher info category name "Dụng cụ sinh hoạt tình bạn"
      | name                       | description | note     | fromDate  | toDate   | country | isOneUse |
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | tomorrow | VN      | true     |
    Then The request should be succeed

    And As manager, I add cards for voucher name "AUTOMATION DEFAULT VOUCHER"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 10    | 2029-12-31  |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    Then The request should be succeed

  @voucher-once-use-1
  Scenario: user A isn't able to use voucher again

    #User login and get voucher
    When I login as "user_1"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The response should be
      | success | true |

    And As user, I buy voucher
      | name                       | country | isAddMember |
      | AUTOMATION DEFAULT VOUCHER | VN      | true        |
    Then The response should be
      | success | false                                |
      | message | Voucher is only for non-member users |
