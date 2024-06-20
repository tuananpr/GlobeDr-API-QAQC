@regression @payment
Feature: Payment

  Background: add payoo payment for org
    Given I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | owner     |
      | BV_HOANG_GIA | manager_1 |
    And I add payoo payment for org
      | orgName      | pwd    |
      | BV_HOANG_GIA | 123456 |
    Then The request should be succeed

  @payment-1
  Scenario: load payment info
    And I get payment info
      | orgName      | pwd    |
      | BV_HOANG_GIA | 123456 |
    Then The response should be
      | success             | true                                                   |
      | data[0].checkoutUrl | https://newsandbox.payoo.com.vn/v2/paynow/order/create |


  @payment-2
  Scenario: get payment type
    And I get payment type
      | orgName      |
      | BV_HOANG_GIA |
    Then The response should be
      | success           | true                 |
      | data.list[0].text | Direct payment (COD) |
      | data.list[1].text | Online payment       |

  @payment-3
  Scenario: update payment
    And I update payoo payment for org
      | orgName      | pwd    | paymentText |
      | BV_HOANG_GIA | 123456 | Payoo       |
    Then The request should be succeed
