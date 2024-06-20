@regression @gift-points @gift-points-history
Feature: Gift points history

  As User
  I want to view points history


  Background:
    Given I re-signup "user_1" account
    And I login as "system_admin_1"

  @gift-points-history-1
  Scenario Outline: user load points history
    And As sysAdmin, I gift point to user
      | user   | amount   | pointActivity   | description   |
      | <user> | <amount> | <pointActivity> | <description> |
    Then The request should be succeed
    When I login as "user_1"
    And I load points history
      | page | fromDate   | toDate   | walletStatus    |
      | 1    | <fromDate> | <toDate> | AllWalletStatus |
    Then The response should be
      | success                   | true           |
      | data.list[0].activityName | <activityName> |
      | data.list[0].point        | +100           |

    Examples:
      | user   | amount | description        | fromDate  | toDate   | pointActivity   | activityName       |
      | user_1 | 100    | tặng điểm cho user | yesterday | tomorrow | BuyPointPntAct  | Exchangeable point |
      | user_1 | 100    | tặng điểm cho user | yesterday | tomorrow | GiftPointPntAct | Reward point       |

  @gift-points-history-2
  Scenario Outline: user load invalid points history
    And As sysAdmin, I gift point to user
      | user   | amount   | pointActivity   | description   |
      | <user> | <amount> | GiftPointPntAct | <description> |
    And I load points history
      | page | fromDate   | toDate   | walletStatus    |
      | 1    | <fromDate> | <toDate> | AllWalletStatus |
    Then The response should be
      | success   | true |
      | data.list | []   |
    Examples:
      | user   | amount | description        | fromDate | toDate   |
      | user_1 | 100    | tặng điểm cho user | tomorrow | tomorrow |
