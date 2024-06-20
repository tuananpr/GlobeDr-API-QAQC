@regression @voucher @voucher-manager
Feature: Voucher: Manage Voucher and Category - Part 2: Add edit remove voucher

  As manager
  I want to create voucher
  In order to discount product when buy

  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | owner     |
      | BV_Y_HOC | Hospital | manager_1 |


    And I login as "manager_1"
    And I accept join organization
    # Approver create category
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |
    Then The request should be succeed
    # orgAdmin create voucher
    And I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage

  @voucher-manager-1
  Scenario Outline: Organization create Voucher successfully
    And As manager, I create voucher info category name "<categoryName>"
      | name   | description   | note   | fromDate   | toDate   | weight   | country   | city   | supportPhone   | type   | link   |
      | <name> | <description> | <note> | <fromDate> | <toDate> | <weight> | <country> | <city> | <supportPhone> | <type> | <link> |
    Then The request should be succeed

    And As manager, I add cards for voucher name "<name>"
      | receiptCode   | description   | total    | expiredDate   |
      | <receiptCode> | <description> | <totals> | <expiredDate> |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name   | isSendNoti |
      | <name> | true       |
    And The request should be succeed

    Then The request should be succeed
       # orgAdmin create voucher
    And I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I load vouchers
      | name |
      |      |
    Then The response should be
      | data.list[0].name        | <name>        |
      | data.list[0].description | <description> |
      | data.list[0].note        | <note>        |
      | data.list[0].fromDate    | <fromDate>    |
      | data.list[0].toDate      | <toDate>      |
      | data.list[0].status      | <status>      |
      | data.list[0].statusName  | <statusName>  |
      | data.list[0].weight      | <weight>      |
      | data.list[0].isHot       | <isHot>       |
      | data.list[0].link        | <link>        |
    Examples:
      | categoryName               | name                                                             | description                     | note                 | fromDate                | toDate                  | status | statusName | weight | country | city | supportPhone | isHot | link                   | type | receiptCode | description                                    | totals | expiredDate |
      | Dụng cụ sinh hoạt tình bạn | Auto Voucher gắn link                                            | Công bố dịch Covid-19 toàn quốc | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | VN      | HCM  | 28282828     | false | https://vnexpress.net/ | 1    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |
      | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9  | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | VN      | HCM  | 28282828     | false | ""                     | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |

  @voucher-manager-2
  Scenario Outline: Organization update avatar for voucher successfully
    And As manager, I create voucher info category name "<categoryName>"
      | name   | description   | note   | fromDate   | toDate   | weight   | country   | city   | supportPhone   | type   | link   |
      | <name> | <description> | <note> | <fromDate> | <toDate> | <weight> | <country> | <city> | <supportPhone> | <type> | <link> |
    Then The request should be succeed

    And I update hot avatar for voucher
      | name   | image                        |
      | <name> | data/image/voucher/mp3_1.jpg |
    And The request should be succeed
    And I update avatar for voucher
      | nameVN | image                        |
      | <name> | data/image/voucher/mp3_2.png |
    And The request should be succeed
    And As manager, I load vouchers
      | name   |
      | <name> |
    Then The response should be
      | success | true |
    And The image into "data.list[0].iconUrl" should be match "data/image/voucher/mp3_2.png"
    And The image into "data.list[0].iconHotUrl" should be match "data/image/voucher/mp3_1.jpg"
    Examples:
      | categoryName               | name                                                             | description                    | note                 | fromDate                | toDate                  | status | statusName | weight | country | city | supportPhone | isHot | link | type | receiptCode | description                                    | totals | expiredDate |
      | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | VN      | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |


  @voucher-manager-3
  Scenario Outline: Organization edit voucher successfully
    And As manager, I create voucher info category name "<categoryName>"
      | name   | description   | note   | fromDate   | toDate   | weight   | country   | city   | supportPhone   | type   | link   |
      | <name> | <description> | <note> | <fromDate> | <toDate> | <weight> | <country> | <city> | <supportPhone> | <type> | <link> |
    Then The request should be succeed

    When I update below info for voucher name "<name>" on category "<categoryName>"
      | name          | description          | note          | fromDate   | toDate   | weight   | country   | city   | supportPhone   | type   | link   |
      | update <name> | update <description> | update <note> | <fromDate> | <toDate> | <weight> | <country> | <city> | <supportPhone> | <type> | <link> |
    Then The request should be succeed
    And As manager, I load vouchers
      | name          |
      | update <name> |
    Then The response should be
      | success                   | true                 |
      | data.list[0].name         | update <name>        |
      | data.list[0].description  | update <description> |
      | data.list[0].note         | update <note>        |
      | data.list[0].fromDate     | <fromDate>           |
      | data.list[0].toDate       | <toDate>             |
      | data.list[0].status       | 1                    |
      | data.list[0].statusName   | New                  |
      | data.list[0].weight       | <weight>             |
      | data.list[0].isHot        | false                |
      | data.list[0].country      | <country>            |
      | data.list[0].city         | <city>               |
      | data.list[0].supportPhone | <supportPhone>       |
    Examples:
      | categoryName               | name                                                             | description                    | note                 | fromDate                | toDate                  | status | statusName | weight | country | city | supportPhone | isHot | link | type | receiptCode | description                                    | totals | expiredDate |
      | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | VN      | HCM  | 28282828     | false | ""   | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |

  @voucher-manager-4
  Scenario Outline: Organization add cards for voucher successfully
    And As manager, I create voucher info category name "<categoryName>"
      | name   | description   | note   | fromDate   | toDate   | weight   | country   | city   | supportPhone   | type   | link   |
      | <name> | <description> | <note> | <fromDate> | <toDate> | <weight> | <country> | <city> | <supportPhone> | <type> | <link> |
    Then The request should be succeed

    And As manager, I add cards for voucher name "<name>"
      | receiptCode   | description   | total    | expiredDate   |
      | <receiptCode> | <description> | <totals> | <expiredDate> |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I load cards into voucher
      | name   | giftCardStatus |
      | <name> | New            |
    Then The response should be
      | success    | true |
      | data.total | 10   |

    Examples:
      | categoryName               | name                                                             | description                    | note                 | fromDate                | toDate                  | status | statusName | weight | country | city | supportPhone | isHot | link | type | receiptCode | description                                    | totals | expiredDate |
      | Dụng cụ sinh hoạt tình bạn | Auto Voucher giảm giá 99% khi mua máy nghe nhạc MP3 thế hệ thứ 9 | Máy nghe nhạc MP3 thế hệ thứ 9 | Không có note gì hết | 2019-01-01T00:00:00.000 | 2029-12-31T00:00:00.000 | 2      | Active     | 10     | VN      | HCM  | 28282828     | false |      | 2    | AUTOMATION  | description? what is description, I don't know | 10     | 2029-12-31  |

