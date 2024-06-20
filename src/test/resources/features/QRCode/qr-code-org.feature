@regression @qr-code
Feature: Org Scan QR code

  Background:
    Given I re-signup "user_1" account and update profile
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_MINH_ANH | Hospital | manager_1 |


    Given I login as "manager_1"
    And I accept join organization
    When I select org "BV_MINH_ANH" that I manage

  @qr-code-org-01
  Scenario: User scan org QRCode then become member
    And I get QRCode on my org
    Then The request should be succeed

    And I login as "user_1"
    And As user, I scan above QRCode

    Given I login as "manager_1"
    When I select org "BV_MINH_ANH" that I manage
    And I load org members
      | displayName |
      | user_1      |
    And List "data.list[*].displayName" of response should contains
      | user_1 |

  @qr-code-org-02
  Scenario: Org scan user QRCode then user become member

    And I login as "user_1"
    And I get my QRCode
    Then The request should be succeed

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MINH_ANH" that I manage
    And I add member by scan QRcode
    And I load org members
      | displayName |
      | user_1      |
    And List "data.list[*].displayName" of response should contains
      | user_1 |


  @qr-code-org-03
  Scenario: load information org from qrcode
    And I get QRCode on my org
    Then The request should be succeed
    And As manager, I load info from above QRCode
    Then The response should contains
      | success              | true                    |
      | data.info.qrCodeType | 2                       |
      | data.info.detail     | "OrgName":"BV_MINH_ANH" |

    And I login as "user_1"
    And As user, I load info from above QRCode
    Then The response should contains
      | success              | true                    |
      | data.info.qrCodeType | 2                       |
      | data.info.detail     | "OrgName":"BV_MINH_ANH" |

    And As guest, I load info from above QRCode
    Then The response should contains
      | success              | true                    |
      | data.info.qrCodeType | 2                       |
      | data.info.detail     | "OrgName":"BV_MINH_ANH" |

