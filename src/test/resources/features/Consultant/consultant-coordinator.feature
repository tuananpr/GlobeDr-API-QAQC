@regression @consultant-coordinator @coordinator
Feature: Consultant Coordinator

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    And The request should be succeed

  @consultant-coordinator-0
  Scenario Outline: Coordinator get actions into consult
    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | true  |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | true  |
      | data.actions.assignQuestion      | true  |
      | data.actions.closeQuestion       | true  |
      | data.actions.spamQuestion        | true  |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.sendPatientNoti     | true  |
      | data.actions.viewActivityLog     | true  |
      | data.actions.viewPatientHealth   | true  |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | false |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-1
  Scenario Outline: Coordinator can create question
    When I login as "coordinator_auto"
    And I create a question to GlobeDr with below info
      | msg   | height | weight |
      | <msg> | 130    | 30     |
    And The request should be succeed
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-2-a
  Scenario Outline: Coordinator can close question

    # Coordinator close quesstion
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch |
      | <msg>      |
    Then The request should be succeed
    And As coordinator, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.viewActivityLog     | true  |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
    And As coordinator, I get activity log
      | textSearch |
      | <msg>      |
    Then List "data.list[*].notes" of response should contains
      | The question was closed by <font color='#39B44A'>COORDINATOR_AUTO, </font> |
    When I login as "user_1"
    And As user, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |

    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |



  @consultant-coordinator-3
  Scenario Outline: Coordinator assign or re_assign question to doctors

    # Coordinator login and assign question to doctor 1
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <msg>      |
    Then The request should be succeed
    And As coordinator, I assign question to doctor name "DOCTOR_Duc_Dien"
      | textSearch |
      | <msg>      |
    Then The request should be succeed
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-4
  Scenario Outline: Coordinator can send notification to assigned Doctor

    # Coordinator login and assign question to doctor
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

    #COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I send notification to assigned doctor into consult
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As coordinator, I get activity log
      | textSearch |
      | <msg>      |
    And The request should be succeed
    Then List "data.list[*].notes" of response should contains
      | <font color='#39B44A'>COORDINATOR_AUTO, </font> reminded <font color='#39B44A'>DOCTOR_TEO, </font> to answer this question |

    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Please consult for question |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-5
  Scenario Outline: Coordinator can send notification to assigned Auditor
 # Coordinator login and assign question to doctor
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
    And As doctor, I add comment "Chào em, em hãy đi khám đi nhé" into consult
      | textSearch |
      | <msg>      |
    Then The request should be succeed

  #COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    And As coordinator, I send notification to assigned auditor into consult
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As coordinator, I get activity log
      | textSearch |
      | <msg>      |
    And The request should be succeed

    Then List "data.list[*].notes" of response should contains
      | <font color='#39B44A'>COORDINATOR_AUTO, </font> reminded <font color='#39B44A'>Audit_Trung, </font> to answer this question |
    When I login as "Audit_Trung"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Please review this consultation |

    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-6
  Scenario Outline: Send notification to patient

    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                      | true |
      | data.actions.sendPatientNoti | true |

    And As coordinator, I send notification to patient into consult
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As coordinator, I get activity log
      | textSearch |
      | <msg>      |
    And The request should be succeed

    Then List "data.list[*].notes" of response should contains
      | <font color='#39B44A'>COORDINATOR_AUTO, </font> reminded <font color='#39B44A'>user_1, </font> to additional information this question |

    When I login as "user_1"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Your doctor asks for additional information for your question |

    And I open noti from consult
      | groupType      | message                                                       |
      | ConsultantNoti | Your doctor asks for additional information for your question |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-coordinator-7
  Scenario Outline: Coordinator set questions are spam then user can't comment into consult
    When I login as "coordinator_auto"
    And As coordinator, I set questions are spam
      | textSearch |
      | <msg>      |
    Then The request should be succeed
    And As coordinator, I load questions
      | textSearch | postStatus |
      | <msg>      | Spam       |
    Then The response should be
      | success                  | true  |
      | data.list[0].rootMsg.msg | <msg> |

    And As coordinator, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.viewActivityLog     | true  |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
    And As coordinator, I get activity log
      | textSearch |
      | <msg>      |
    Then List "data.list[*].notes" of response should contains
      | The question was reported spam by <font color='#39B44A'>COORDINATOR_AUTO, </font> |
    When I login as "user_1"
    And As user, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |

    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Spam question: <shortMsg> |

    And I open noti from consult
      | groupType      | message                   |
      | ConsultantNoti | Spam question: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |




