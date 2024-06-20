@regression @gift-points
Feature: Gift points

  As System admin
  I want to gift points to user

  Background:
    Given I re-signup "user_2" account
    And I login as "system_admin_1"

  @gift-point-1
  Scenario Outline: sysAdmin gift point to user
    And As sysAdmin, I gift point to user
      | user   | amount   | pointActivity   | description   |
      | <user> | <amount> | <pointActivity> | <description> |
    Then The request should be succeed
    When I login as "user_2"

    And I load points history
      | page | walletStatus    |
      | 1    | AllWalletStatus |
    Then The response should be
      | success                   | true           |
      | data.list[0].activityName | <activityName> |
      | data.list[0].point        | +100           |

    And I load points
    Then The response should be
      | success                 | true            |
      | data.info.pointTotalTxt | <pointTotalTxt> |
    Then The "data.info.<pointType>" greater than "100"

    And I load notifications
      | groupType  |
      | SystemNoti |
    Then List "data.list[*].message" of response should contains
      | <message> |

    Examples:
      | user   | amount | description        | pointActivity   | activityName       | pointType      | message                   | pointTotalTxt |
      | user_2 | 100    | tặng điểm cho user | GiftPointPntAct | Reward point       | pointPromotion | You have added 100 points | 1,100         |
      | user_2 | 100    | tặng điểm cho user | BuyPointPntAct  | Exchangeable point | point          | You have added 100 points | 1,100         |


  @gift-point-2
  Scenario Outline: sysAdmin can gift point twice in a day
    And As sysAdmin, I gift point to user
      | user   | amount   | pointActivity   | description   |
      | <user> | <amount> | <pointActivity> | <description> |
      | <user> | <amount> | <pointActivity> | <description> |
    Then The response should be
      | success | true |
    Examples:
      | user   | amount | description        | pointActivity   | activityName | pointType      | message                   | pointTotalTxt |
      | user_2 | 100    | tặng điểm cho user | GiftPointPntAct | Reward point | pointPromotion | You have added 100 points | 1,200         |