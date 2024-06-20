@regression @qr-code @qr-code-user
Feature: User Scan QR Code

  As user
  I want to make friend by scan QRcode

  Background:
    Given I re-signup "user_1" account
    And I re-signup "user_2" account

  @qr-code-user-01
  Scenario: User make friend by scan QRcode

    And I login as "user_1"
    And I get my QRCode
    Then The request should be succeed

    And I login as "user_2"
    # user2 send request connection to user1 by scanning QRCode and view profile
    And As user, I scan above QRCode
    Then The request should be succeed

    When I login as "user_1"
    And I load request connection list
    And The request should be succeed
    Then List "data.list[*].userName" of response should contains
      | user_2 |

  @qr-code-user-02
  Scenario: User make friend to itself
    And I login as "user_1"
    And I get my QRCode

    And The request should be succeed
    And As user, I scan above QRCode
    Then The request should be fail

  @qr-code-user-03
  Scenario:  User make friend to who isn't existed
    When I login as "user_1"
    And I scan QR Code is not on the system
    And The request should be fail

  @qr-code-user-04
  Scenario:  load information user from qrcode
    And I login as "user_1"
    And I get my QRCode
    Then The request should be succeed

    And I login as "user_2"
    And As user, I load info from above QRCode
    Then The response should contains
      | success              | true                   |
      | data.info.qrCodeType | 1                      |
      | data.info.detail     | "DisplayName":"user_1" |
    And As guest, I load info from above QRCode
    Then The response should contains
      | success              | true                   |
      | data.info.qrCodeType | 1                      |
      | data.info.detail     | "DisplayName":"user_1" |
