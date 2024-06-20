@regression @consultant @gift-points @used-point-history
Feature: Points History

  As system admin,
  I view history of using points

  Background:
    Given I re-signup "user_2" account and update profile
    And I login as "system_admin_1"

    And As sysAdmin, I gift point to user
      | user   | amount | pointActivity   |
      | user_2 | 100    | GiftPointPntAct |
    Then The request should be succeed

    When I login as "user_2"
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    Then The request should be succeed
    And I login as "system_admin_1"

  @used-point-history-1
  Scenario Outline: sysAdmin load point history of user
    And As sysAdmin, I load point history of user
      | name   |
      | user_2 |
    And The response should be
      | success                   | true                              |
      | data.list[0].activityName | a question of doctor consultation |
      | data.list[0].point        | -1                                |
      | data.list[0].pointTxt     | -1                                |
    Examples:
      | pointActivity          | point | activityName                      |
      | NormalConsultantPntAct | 100   | a question of doctor consultation |


  @used-point-history-2
  Scenario Outline: sysAdmin load point history of all user
    And As sysAdmin, I load point history of all user
      | fromDate    | toDate   |
      | prev 5 mins | tomorrow |
    And The response should be
      | success                     | true   |
      | data.list[0].userName       | user_2 |
      | data.list[0].usedPoint      | 1      |
      | data.list[0].remainingPoint | 1499   |

    Examples:
      | pointActivity          | point | activityName                      |
      | NormalConsultantPntAct | 100   | a question of doctor consultation |
