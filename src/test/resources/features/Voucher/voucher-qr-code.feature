@regression @qr-code @voucher-qr-code
Feature: Voucher QRcode

  As Manage
  I want to user can use voucher by scan QRCode

  Background:
    Given I re-signup "user_1" account and update profile
    Given I re-signup "manager_1" account and update profile

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

    And As manager, I add auto cards for voucher name "AUTOMATION DEFAULT VOUCHER"
      | receiptCode | description   | total | expiredDate |
      | AutoCard    | mã khuyến mãi | 10    | 2029-12-31  |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    Then The request should be succeed


    # User login and get voucher
    When I login as "user_1"
    And As user, I use voucher name "AUTOMATION DEFAULT VOUCHER" at country "VN"
      | isAddMember |
      | true        |
    Then The request should be succeed
    And I save voucher qrcode

  @voucher-qr-code-01-a
  Scenario:Org scan QR Code voucher of user
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I scan above QRCode
    #And I scan QR Code voucher
    Then The request should be succeed

    When I login as "approver_1"
    And As approver, I load cards into voucher
      | name                       |
      | AUTOMATION DEFAULT VOUCHER |
    And List "data.list[*].statusName" of response should be contains "Used"


  @voucher-qr-code-02
  Scenario:Org scan QR Code voucher already used
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I scan above QRCode
    #And I scan QR Code voucher
    Then The request should be succeed

    And As manager, I scan above QRCode
    Then The request should be fail


  @voucher-qr-code-03
  Scenario: User scan QR Code voucher of other user
    And I login as "user_2"
    And As user, I scan above QRCode
    Then The request should be fail


  @voucher-qr-code-04
  Scenario: manager scan code that is existed into system
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And I scan QR Code is not on the system
    And The request should be fail

  @voucher-qr-code-05
  Scenario: manager can't scan voucher from org
    And I re-signup "manager_2" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     |
      | ORG_QC_WIN | Hospital | manager_2 |

    And I login as "manager_2"
    And I accept join organization
    And I select org "ORG_QC_WIN" that I manage
    And As manager, I scan above QRCode
    And The request should be fail


  @voucher-qr-code-06
  Scenario: load information voucher from qrcode
    When I login as "manager_1"
    And I select org "BV_Y_HOC" that I manage
    And As manager, I load info from above QRCode
    Then The response should contains
      | success              | true                                |
      | data.info.qrCodeType | 4                                   |
      | data.info.detail     | "Name":"AUTOMATION DEFAULT VOUCHER" |

    And I login as "user_1"
    And As user, I load info from above QRCode
    Then The response should contains
      | success              | true                                |
      | data.info.qrCodeType | 4                                   |
      | data.info.detail     | "Name":"AUTOMATION DEFAULT VOUCHER" |

    And As guest, I load info from above QRCode
    Then The response should contains
      | success              | true                                |
      | data.info.qrCodeType | 4                                   |
      | data.info.detail     | "Name":"AUTOMATION DEFAULT VOUCHER" |










