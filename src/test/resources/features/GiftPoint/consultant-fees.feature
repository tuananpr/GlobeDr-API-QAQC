@regression @consultant @consultant-fees @fees @gift-points
Feature: Consultant Fees

  As system admin,
  I want user pay to use consultant

  Background:
    Given I re-signup "user_2" account and update profile
    When I login as "user_2"


  @prepare-consultant-fees-1
  Scenario: set consult fees
    Given I re-signup "manager_1" account
    And I login as "system_admin_1"
    And As sysAdmin, I add staff for org name "GlobeDr"
      | displayName | country | isAdmin |
      | manager_1   | VN      | true    |
    Then The request should be succeed

    And I set features for staff
      | featureAttributes | displayName |
      | ConsultConfig     | manager_1   |
    Then The request should be succeed

    Given I login as "manager_1"
    And I accept join organization


  @consultant-fees-1
  Scenario Outline: User load function fees when not enough point
    Given I login as "manager_1"
    And I select org "GlobeDr" that I manage
    And As manager, I setting consult
      | normalConsultantPntActFees | videoConsultantPntActFees | maxTimeVideoCallInSeconds | warningTimeVideoCallInSeconds | limitedTimeVideoCallInSeconds |
      | <point>                    | <point>                   | 300                       | 10                            | 5                             |
    Then The request should be succeed

    When I login as "user_2"
    And I load function fees
      | type                   |
      | NormalConsultantPntAct |
    And The response should be
      | success            | true                                                                                                                                                                                                     |
      | data.info.isNext   | false                                                                                                                                                                                                    |
      | data.info.pointFee | <point>                                                                                                                                                                                                  |
      | data.info.msg      | <p>It takes <font color='#36AF49;'>10,000 points</font> for a question of doctor consultation</p><p>You don't have enough points to make it</p><p>Click ACCEPT to learn more about how to get points<p/> |

    And I load function fees
      | type                  |
      | VideoConsultantPntAct |
    And The response should be
      | success            | true                                                                                                                                                                                                     |
      | data.info.isNext   | false                                                                                                                                                                                                    |
      | data.info.pointFee | <point>                                                                                                                                                                                                  |
      | data.info.msg      | <p>It takes <font color='#36AF49;'>10,000 points</font> for a doctor's consultation via Video</p><p>You don't have enough points to make it</p><p>Click ACCEPT to learn more about how to get points<p/> |
    Examples:
      | point |
      | 10000 |


  @consultant-fees-2
  Scenario Outline: User load function fees when enough point
    Given I login as "manager_1"
    And I select org "GlobeDr" that I manage
    And As manager, I setting consult
      | normalConsultantPntActFees | videoConsultantPntActFees | maxTimeVideoCallInSeconds | warningTimeVideoCallInSeconds | limitedTimeVideoCallInSeconds |
      | 1                          | 2                         | 300                       | 10                            | 5                             |
    Then The request should be succeed

    And I login as "system_admin_1"
    And As sysAdmin, I gift point to user
      | user   | amount  | pointActivity   |
      | user_2 | <point> | GiftPointPntAct |
    Then The request should be succeed

    When I login as "user_2"
    And I load function fees
      | type            |
      | <pointActivity> |
    And The response should be
      | success            | true    |
      | data.info.isNext   | true    |
      | data.info.pointFee | <point> |

    Examples:
      | pointActivity          | point |
      | NormalConsultantPntAct | 1     |
      | VideoConsultantPntAct  | 2     |


  @consultant-fees-3
  Scenario Outline: User pay fees to use ask doctor Globedr
    And I login as "system_admin_1"
    And As sysAdmin, I gift point to user
      | user   | amount  | pointActivity   |
      | user_2 | <point> | GiftPointPntAct |
    Then The request should be succeed

    When I login as "user_2"
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |

    And I load points history
      | type            | walletStatus    |
      | <pointActivity> | AllWalletStatus |
    And The response should be
      | success                   | true           |
      | data.list[0].point        | -1             |
      | data.list[0].pointTxt     | -1             |
      | data.list[0].activityName | <activityName> |
    And I load points
    Then The response should be
      | success              | true |
      | data.info.pointTotal | 1499 |

    Examples:
      | pointActivity          | point | activityName                      |
      | NormalConsultantPntAct | 100   | a question of doctor consultation |


  @consultant-fees-4
  Scenario Outline: Coordinator close and set this question must payment or not
    And I load points
    Then The response should be
      | success              | true         |
      | data.info.pointTotal | <pointTotal> |
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    And The request should be succeed

    # Coordinator close quesstion
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch | isPayment4Doctor   |
      | <msg>      | <isPayment4Doctor> |
    Then The request should be succeed
    And I login as "user_2"
    And I load points
    Then The response should be
      | success              | true                 |
      | data.info.pointTotal | <expectedPointTotal> |
    Examples:
      | msg                                                            | isPayment4Doctor | activityName                      | pointTotal | expectedPointTotal |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | true             | a question of doctor consultation | 1400       | 1399               |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | false            | a question of doctor consultation | 1400       | 1400               |