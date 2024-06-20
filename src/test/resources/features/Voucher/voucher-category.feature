@regression @voucher @voucher-category
Feature: Voucher category

  As Approver
  I want to create voucher category
  In order to org create voucher into it

  Background:
    And I login as "approver_1"

  @voucher-category-1
  Scenario Outline: create Voucher category successfully
    # Approver create category

    And As approver, I re-create voucher category
      | nameVN   | nameEN   | weight   |
      | <nameVN> | <nameEN> | <weight> |
    Then The request should be succeed
    And As manager, I load voucher categories
      | nameVN   |
      | <nameVN> |
    Then The response should be
      | success             | true     |
      | data.list[0].name   | <nameVN> |
      | data.list[0].nameEN | <nameEN> |
      | data.list[0].weight | <weight> |
    Examples:
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |


  @voucher-category-2
  Scenario Outline: edit Voucher category successfully
    # Approver create category

    And As approver, I re-create voucher category
      | nameVN   | nameEN   | weight   |
      | <nameVN> | <nameEN> | <weight> |
    Then The request should be succeed

    And As manager, I update voucher category name "<nameVN>" with below info
      | nameVN          | nameEN          | weight   |
      | update <nameVN> | update <nameEN> | <weight> |

    And As manager, I load voucher categories
      | nameVN          |
      | update <nameVN> |
    Then The response should be
      | success             | true            |
      | data.list[0].name   | update <nameVN> |
      | data.list[0].nameEN | update <nameEN> |
      | data.list[0].weight | <weight>        |
    And On SqlServer, I delete voucher category that have "nameVN" is "update <nameVN>"
    And On SqlServer, I delete voucher category that have "nameEN" is "update <nameEN>"
    Examples:
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |


  @voucher-category-3
  Scenario Outline: delete category successfully
    # Approver create category

    And As approver, I re-create voucher category
      | nameVN   | nameEN   | weight   |
      | <nameVN> | <nameEN> | <weight> |
    Then The request should be succeed
    And As approver, I delete voucher categories
      | nameVN   |
      | <nameVN> |
    Then The request should be succeed
    And As manager, I load voucher categories
      | nameVN   |
      | <nameVN> |
    Then The response should be
      | success    | true |
      | data.total | 0    |
    Examples:
      | nameVN                     | nameEN           | weight |
      | Dụng cụ sinh hoạt tình bạn | Friendship tools | 10     |
