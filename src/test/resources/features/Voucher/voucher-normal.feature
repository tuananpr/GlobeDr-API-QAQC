@regression @voucher @voucher-normal
Feature: Voucher normal

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


  @voucher-normal-1
  Scenario: user will be member of organization automatically after using voucher
   #    User login and get voucher
    When I login as "user_1"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The request should be succeed       


    #    User A is members in member group
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And I load org members
      | displayName |
      | user_1      |
    Then The response should be
      | success    | true |
      | data.total | 1    |

  @voucher-normal-2
  Scenario: User cannot get voucher which is out of card.
    When I login as "user_1"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The request should be succeed
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The request should be succeed

    And As user, I load vouchers
      | name                       | country | language |
      | AUTOMATION DEFAULT VOUCHER | VN      | 0        |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |


  @voucher-normal-3
  Scenario:Org only can QRCode from auto card
    When I login as "user_1"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The request should be succeed
    And I save voucher qrcode

    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I scan above QRCode
    #And I scan QR Code voucher
    Then The request should be fail
