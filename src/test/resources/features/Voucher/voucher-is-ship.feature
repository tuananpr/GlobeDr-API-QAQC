@regression @voucher @voucher-user @voucher-is-ship
Feature: Voucher deliver to home

  As User
  I want to buy product by voucher anh deliver to home


  Background:
    Given I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account
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
      | name                       | description | note     | fromDate  | toDate   | country | isShip |
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | tomorrow | VN      | true   |
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

  @voucher-is-ship-1
  Scenario Outline: user buy voucher type is ship. And approver view information of voucher

    ##User login and get voucher
    When I login as "<userName>"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember | shipAddress | country                                             | city                                  | district                                      | ward                                  |
      | true        | <address>   | {"country":"VN","name":"<country>","postCode":"84"} | {"code":"<cityCode>","name":"<city>"} | {"code":"<districtCode>","name":"<district>"} | {"code":"<wardCode>","name":"<ward>"} |
    Then The response should be
      | success | true |

    When I login as "approver_1"
    And As approver, I load cards into voucher
      | name                       | giftCardStatus |
      | AUTOMATION DEFAULT VOUCHER | <statusName>   |
    Then The response should be
      | success                   | true         |
      | data.total                | 1            |
      | data.list[0].statusName   | <statusName> |
      | data.list[0].userName     | <userName>   |
      | data.list[0].address      | <address>    |
      | data.list[0].country      | <country>    |
      | data.list[0].city         | <city>       |
      | data.list[0].districtName | <district>   |
      | data.list[0].wardName     | <ward>       |
    Examples:
      | userName | statusName | address              | country  | city        | cityCode | district | districtCode | ward            | wardCode |
      | user_1   | Used       | 29 Nguyễn Trung Ngạn | Việt Nam | Hồ Chí Minh | HCM      | Quận 1   | 1442         | Phường Bến Nghé | 20101    |