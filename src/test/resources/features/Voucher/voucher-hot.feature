@regression @voucher @voucher-hot
Feature: Voucher: Hot voucher

  As manager
  I want to create hot voucher
  In order to user and guest can view it

  Background:
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "user_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | owner     |
      | BV_Y_HOC | Hospital | manager_1 |
    Then The request should be succeed

      #    Approver Create category
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |
    Then The request should be succeed

    And I login as "manager_1"
    And I accept join organization

  @voucher-hot-1
  Scenario Outline: View hot voucher before login and after login

    #    Create voucher and cards
    And I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I create voucher info category name "<categoryName>"
      | name   | description   | note   | fromDate   | toDate   | country   |
      | <name> | <description> | <note> | <fromDate> | <toDate> | <country> |
    Then The request should be succeed

    And As manager, I add cards for voucher name "<name>"
      | receiptCode   | description   | total    | expiredDate   |
      | <receiptCode> | <description> | <totals> | <expiredDate> |
    And The request should be succeed
    And As manager, I set voucher is hot
      | fromDateHot | toDateHot | weightHot |
      | yesterday   | tomorrow  | 1000      |
    Then The request should be succeed
    #    approver approve new voucher

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name   |
      | <name> |
    Then The request should be succeed
    # View hot voucher Without login
    And I log out
    And As guest, I load vouchers
      | country   | language   | isHot |
      | <country> | <language> | true  |
    Then The request should be succeed
    And All vouchers should be hot voucher
    Then The list should contain voucher
      | <name> |
    # View hotvoucher after login
    When I login as "user_1"
    And As user, I load vouchers
      | country   | language   | isHot |
      | <country> | <language> | true  |
    And All vouchers should be hot voucher
    Then The list should contain voucher
      | <name> |
    Examples:
      | country | language | categoryName               | name                                                             | description                    | note                 | fromDate                | toDate                  | status | statusName | weight | city | supportPhone | isHot | link | type | receiptCode | description                                    | totals | expiredDate |
      | VN      | 0        | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |
      | VN      | 1        | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |
      | US      | 0        | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |
      | US      | 1        | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |









