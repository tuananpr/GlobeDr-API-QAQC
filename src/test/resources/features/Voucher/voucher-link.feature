@regression @voucher @voucher-user @voucher-link
Feature: Voucher that link from other web

  As org
  I want to voucher that link from my web
  In order to user can buy it on my web

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
      | name                       | description | note     | fromDate  | toDate   | country | type | link                   |
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | tomorrow | VN      | 1    | https://vnexpress.net/ |
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


  @voucher-link-1
  Scenario Outline: User use voucher has link


    When I login as "user_1"
    And As user, I open link voucher
      | name                       | country | link                   |
      | AUTOMATION DEFAULT VOUCHER | VN      | https://vnexpress.net/ |
    Then The request should be succeed

    # orgAdmin create voucher
    And I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And I view user that click on voucher
      | fromDate | toDate |
      |          |        |
    Then The response should be
      | success               | true   |
      | data.list[0].userName | user_1 |
      | data.total            | 1      |
    And I export user that click on voucher to file
      | fromDate | toDate |
      |          |        |
    Then The response should be
      | success       | true         |
      | data.fileName | cardList.csv |
    And The "data.base64Str" should "not empty"
    And CSV file should contains
      | user_1 |
    Examples:
      | name                  | description                     | note                 | fromDate                | toDate                  | weight | link                   | type |
      | Auto Voucher gắn link | Công bố dịch Covid-19 toàn quốc | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 10     | https://vnexpress.net/ | 1    |
