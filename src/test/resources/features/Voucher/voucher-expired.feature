@regression @voucher @voucher-user @voucher-expired

Feature: Voucher expired

  As system
  I want to user can't voucher that expired


  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | owner     |
      | BV_Y_HOC | Hospital | manager_1 |
    Then The request should be succeed

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
      | name                       | description | note     | fromDate  | toDate    | country |
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | yesterday | VN      |
    Then The request should be succeed

    And As manager, I add cards for voucher name "AUTOMATION DEFAULT VOUCHER"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 10    | yesterday   |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    Then The request should be succeed

  @voucher-expired-1
  Scenario: User cannot use expired voucher (voucher will be disappeared in user wallet)
    When I login as "user_1"
#    Wait for voucher to expired and check in wallet
    And I wait for '130' seconds
    And As user, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | VN      |
    And The response should be
      | success         | true |
      | data.totalCount | 0    |


