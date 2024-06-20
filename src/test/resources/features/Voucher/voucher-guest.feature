@regression @voucher @voucher-guest
Feature: Voucher guest

  As guest
  I want to view voucher into Globedr

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
      | name                       | description | note     | fromDate  | toDate   | country |
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | tomorrow | VN      |
    Then The request should be succeed

    And As manager, I add cards for voucher name "AUTOMATION DEFAULT VOUCHER"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 2     | 2029-12-31  |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    Then The request should be succeed


  @voucher-guest-1
  Scenario: guest load voucher
    When I log out
    And As guest, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | VN      |
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |



