@regression @voucher @voucher-approver
Feature: Voucher : approver active and deactive voucher

  As Approver
  I want to check information of voucher after it's displayed on Globedr system


  Background:
    Given I re-signup "manager_1" account and update profile
    When On SqlServer, I delete voucher category that have "nameVN" is "Dụng cụ nấu ăn"
    When On SqlServer, I delete voucher category that have "nameEN" is "Cooking tools"


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
      | AUTOMATION DEFAULT VOUCHER | mô tả       | không có | yesterday | tomorrow | US      |
    Then The request should be succeed

    And As manager, I add cards for voucher name "AUTOMATION DEFAULT VOUCHER"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 10    | 2029-12-31  |
    And The request should be succeed


  @voucher-approver-1
  Scenario: user just can see voucher after it's approved
    #User login and get voucher
    When I login as "user_1"
    And As user, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | US      |
    And The response should be
      | success         | true |
      | data.totalCount | 0    |

    #Active voucher
    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                       | isSendNoti |
      | AUTOMATION DEFAULT VOUCHER | true       |
    Then The request should be succeed

    #User login and get voucher
    When I login as "user_1"
    And As user, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | US      |
    And The response should be
      | success         | true |
      | data.totalCount | 1    |

    When I login as "user_1"
    And I load notifications
      | groupType  |
      | SystemNoti |
    Then List "data.list[*].message" of response should contains
      | New voucher: AUTOMATION DEFAULT VOUCHER |


  @voucher-approver-2
  Scenario: approver deActive voucher

    #User login and get voucher
    When I login as "user_1"
    And As user, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | US      |
    And The response should be
      | success         | true |
      | data.totalCount | 0    |

    #Active voucher
    And I login as "approver_1"
    And As approver, I deActive vouchers
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    Then The request should be succeed

    #User login and get voucher
    When I login as "user_1"
    And As user, I load vouchers
      | name                       | country |
      | AUTOMATION DEFAULT VOUCHER | US      |
    And The response should be
      | success         | true |
      | data.totalCount | 0    |
