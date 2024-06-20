@regression @voucher @voucher-wallet
Feature: Voucher wallet

  As user
  I want to save voucher into wallet
  In order to use it when necessary

  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_2" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | owner     |
      | BV_Y_HOC | Hospital | manager_1 |

    And I login as "manager_1"
    And I accept join organization
    # Create voucher category
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |
    Then The request should be succeed
    # Create voucher
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I create voucher info category name "Dụng cụ sinh hoạt tình bạn"
      | name                                                        | description | note     | fromDate  | toDate   |
      | Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | mô tả       | không có | yesterday | tomorrow |
    Then The request should be succeed

    And As manager, I add cards for voucher name "Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 10    | 2029-12-31  |
    And The request should be succeed


    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                                                        |
      | Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 |
    Then The request should be succeed


  @voucher-wallet-1
  Scenario: User save voucher to wallet
    When I login as "user_2"
    And As user, I get voucher
      | name                                                        | country | language |
      | Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | VN      | 1        |
    And The request should be succeed
    And As user, I buy voucher
      | name                                                        | country | language |
      | Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | VN      | 1        |
    And The request should be succeed

#    Verify voucher will be stored in user wallet
    When I login as "user_2"
    And I count my voucher
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    And As user, I load my wallet
    Then The response should be
      | success                   | true                                                        |
      | data.totalCount           | 1                                                           |
      | data.list[0].voucher.name | Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 |

