@regression @gift-points @gift-points-user @doctor-gift-points-user
Feature: Doctor gift points to user

  As GlobeDr Doctor
  I want to gift my point to user

  Background: create doctor and user then check default points
    Given I re-signup "DOCTOR_TEO" account provider and update profile
    Given I re-signup "user_2" account

    When I login as "user_2"
    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,000 |

    When I login as "DOCTOR_TEO"
    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,400 |


  @doctor-gift-points-user-1
  Scenario: doctor search and gift point to user then Doctor lost point and user receive point
    And As doctor, I gift point to user
      | name   | phone       | points |
      | user_2 | 84000000012 | 100    |
    Then The request should be succeed

    And I load points history
      | page | walletStatus    |
      | 1    | AllWalletStatus |
    Then The response should be
      | success                   | true           |
      | data.list[0].activityName | Transfer point |
      | data.list[0].point        | -100           |

    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,300 |

    When I login as "user_2"

    And I load points history
      | page | walletStatus    |
      | 1    | AllWalletStatus |
    Then The response should be
      | success                   | true                           |
      | data.list[0].activityName | Received point from DOCTOR_TEO |
      | data.list[0].point        | +100                           |

    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,100 |

    And I load notifications
      | groupType  |
      | SystemNoti |
    Then List "data.list[*].message" of response should contains
      | You are awarded DOCTOR_TEO points by 100 |


  @doctor-gift-points-user-2
  Scenario: doctor search and gift point to user when doctor don't have enough point
    When I login as "DOCTOR_TEO"
    And As doctor, I gift point to user
      | name   | phone       | points |
      | user_2 | 84000000012 | 5000   |
    Then The request should be fail


  @doctor-gift-points-user-3
  Scenario Outline: doctor gift point to user into consultant
    And I login as "user_2"
    And I create a question to GlobeDr with below info
      | msg   | height | weight |
      | <msg> | 130    | 30     |
    And The request should be succeed

    And I load points
    Then The response should be
      | success                 | true |
      | data.info.pointTotalTxt | 999  |

    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "DOCTOR_TEO"
    And As doctor, I load question content "<msg>" and gift point to user
      | name   | phone       | points |
      | user_2 | 84000000012 | 100    |
    And The request should be succeed

    And I load points history
      | page | walletStatus    |
      | 1    | AllWalletStatus |
    Then The response should be
      | success                   | true           |
      | data.list[0].activityName | Transfer point |
      | data.list[0].point        | -100           |

    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,300 |

    When I login as "user_2"

    And I load points history
      | page | walletStatus    |
      | 1    | AllWalletStatus |
    Then The response should be
      | success                   | true                           |
      | data.list[0].activityName | Received point from DOCTOR_TEO |
      | data.list[0].point        | +100                           |

    And I load points
    Then The response should be
      | success                 | true  |
      | data.info.pointTotalTxt | 1,099 |

    And I load notifications
      | groupType  |
      | SystemNoti |
    Then List "data.list[*].message" of response should contains
      | You are awarded DOCTOR_TEO points by 100 |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @doctor-gift-points-user-4
  Scenario: doctor can't gift point to myself
    When I login as "DOCTOR_TEO"
    And As doctor, I search user to gift point
      | name       | phone       |
      | DOCTOR_TEO | 84444446666 |
    Then The response should be
      | success   | true |
      | data.list | []   |


