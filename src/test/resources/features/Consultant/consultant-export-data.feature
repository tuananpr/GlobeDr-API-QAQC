@regression @consultant @export-data @consultant-coordinator
Feature: Export Data

  As coordinator
  I want view and export statistic about consultant

  Background:
    Given I re-signup "user_1" account and update profile
    When I login as "user_1"
    And I create a question to GlobeDr with below info
      | msg                                      | height | weight |
      | [Auto]Câu hỏi của user_1 a b c d e f g h | 130    | 30     |

  @export-data-1
  Scenario Outline: Coordinator load and export question
    When I login as "coordinator_auto"
    And I load question statistic
      | fromDate  | toDate | postStatus   | userMode    |
      | yesterday | today  | <postStatus> | Coordinator |
    And The request should be succeed
    And I export question statistic
      | fromDate  | toDate | postStatus   | userMode    |
      | yesterday | today  | <postStatus> | Coordinator |

    And The response should be
      | success       | true                     |
      | data.fileType | application/octet-stream |
      | data.fileName | QUESTION_STATISTIC.csv   |
    And The "data.base64Str" should "not empty"
    Examples:
      | postStatus |
      | 280        |
      | 16         |
      | 511        |

  @export-data-2
  Scenario Outline: Coordinator load and export question that GlobeDr must be payment
    And I create a question to GlobeDr with below info
      | msg                             | height | weight |
      | [Auto]Câu hỏi 1 a b c d e f g h | 130    | 30     |
      | [Auto]Câu hỏi 2 a b c d e f g h | 130    | 30     |
      | [Auto]Câu hỏi 3 a b c d e f g h | 130    | 30     |
      | [Auto]Câu hỏi 4 a b c d e f g h | 130    | 30     |
      | [Auto]Câu hỏi 5 a b c d e f g h | 130    | 30     |
    And The request should be succeed

    # Coordinator close quesstion
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch                      | isPayment4Doctor |
      | [Auto]Câu hỏi 1 a b c d e f g h | true             |
      | [Auto]Câu hỏi 2 a b c d e f g h | true             |
      | [Auto]Câu hỏi 3 a b c d e f g h | true             |
      | [Auto]Câu hỏi 4 a b c d e f g h | true             |
      | [Auto]Câu hỏi 5 a b c d e f g h | false            |
    Then The request should be succeed
    And I load question statistic
      | fromDate    | toDate | postStatus   | userMode    | isPayment4Doctor |
      | prev 1 mins | today  | <postStatus> | Coordinator | true             |
    And The request should be succeed
    And The "data.totalCount" equal "4"

    And I export question statistic
      | fromDate  | toDate | postStatus   | userMode    | isPayment4Doctor |
      | prev 1 mins | today  | <postStatus> | Coordinator | true             |

    And The response should be
      | success       | true                     |
      | data.fileType | application/octet-stream |
      | data.fileName | QUESTION_STATISTIC.csv   |
    And The "data.base64Str" should "not empty"
    And CSV file should contains
      | [Auto]Câu hỏi 1 a b c d e f g h |
      | [Auto]Câu hỏi 2 a b c d e f g h |
      | [Auto]Câu hỏi 3 a b c d e f g h |
      | [Auto]Câu hỏi 4 a b c d e f g h |
    And CSV file should not contains
      | [Auto]Câu hỏi 5 a b c d e f g h |
    Examples:
      | postStatus |
      | Closed     |

  @export-data-3-a
  Scenario Outline: Coordinator statistic questions are <postStatus>
    When I login as "coordinator_auto"
    And As coordinator, I set questions are spam
      | textSearch |
      | <msg>      |
    Then The request should be succeed
    And I load question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And The response should be
      | success                  | true  |
      | data.list[0].rootMsg.msg | <msg> |
    And I export question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And CSV file should contains
      | <msg> |
    Examples:
      | postStatus | msg                                      |
      | Spam       | [Auto]Câu hỏi của user_1 a b c d e f g h |


  @export-data-3-b
  Scenario Outline: Coordinator statistic questions are <postStatus>
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "coordinator_auto"
    And I load question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And The response should be
      | success                  | true  |
      | data.list[0].rootMsg.msg | <msg> |
    And I export question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And CSV file should contains
      | <msg> |
    Examples:
      | postStatus | msg                                      |
      | Accepted   | [Auto]Câu hỏi của user_1 a b c d e f g h |


  @export-data-3-c
  Scenario Outline: Coordinator statistic questions are <postStatus>
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    And As doctor, I complete questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "coordinator_auto"
    And I load question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And The response should be
      | success                  | true  |
      | data.list[0].rootMsg.msg | <msg> |
    And I export question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And CSV file should contains
      | <msg> |
    Examples:
      | postStatus | msg                                      |
      | Completed  | [Auto]Câu hỏi của user_1 a b c d e f g h |

  @export-data-3-d
  Scenario Outline: Coordinator statistic questions are <postStatus>
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    And As doctor, I complete questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "Audit_Trung"
    And As auditor, I approve this questions
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    When I login as "coordinator_auto"
    And I load question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And The response should be
      | success                  | true  |
      | data.list[0].rootMsg.msg | <msg> |
    And I export question statistic
      | fromDate    | toDate | postStatus   | userMode    |
      | prev 1 mins | today  | <postStatus> | Coordinator |
    And CSV file should contains
      | <msg> |
    Examples:
      | postStatus | msg                                      |
      | Reviewed   | [Auto]Câu hỏi của user_1 a b c d e f g h |

