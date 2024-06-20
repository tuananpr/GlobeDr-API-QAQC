@regression @consultant-doctor @consultant
Feature: Consultant doctor


  Background:
    Given I re-signup "user_1" account and update profile
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed

  @consultant-doctor-1-a
  Scenario Outline: doctor get actions of consult that assigned
    When I login as "DOCTOR_TEO"
    And As doctor, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | true  |
      | data.actions.acceptQuestion      | true  |
      | data.actions.declineQuestion     | true  |
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
      | data.actions.sendPatientNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | true  |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | false |
      | data.actions.drTransferPoint     | false |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-doctor-1-b
  Scenario Outline: doctor get actions of consult that accepted
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As doctor, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | true  |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | true  |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.sendPatientNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | true  |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | true  |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | true  |
      | data.actions.drTransferPoint     | true  |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-doctor-1-c
  Scenario Outline: doctor get actions of consult that reviewed
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As doctor, I complete questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As doctor, I get actions into consult
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
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.sendPatientNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | true  |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | false |
      | data.actions.drTransferPoint     | true  |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-doctor-1-d
  Scenario Outline: doctor get actions of consult that completed
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

    When I login as "DOCTOR_TEO"
    And As doctor, I get actions into consult
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
      | data.actions.hideMsg             | false |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | false |
      | data.actions.spamQuestion        | false |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | false |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.sendPatientNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | true  |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | false |
      | data.actions.drTransferPoint     | true  |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-doctor-1-e
  Scenario Outline: doctor get actions of consult that closed
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    When I login as "DOCTOR_TEO"
    And As doctor, I get actions into consult
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
      | data.actions.sendPatientNoti     | false |
      | data.actions.viewActivityLog     | false |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |
      | data.actions.giveBackQuestion    | false |
      | data.actions.drTransferPoint     | true  |
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-doctor-3-a
  Scenario Outline: coordinator received notification after doctor accepted
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "coordinator_auto"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Agree to answer the question: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                  |
      | ConsultantNoti | Agree to answer the question: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |
    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |

  @consultant-doctor-3-b
  Scenario Outline: coordinator received notification after doctor complete
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
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Consultation is completed for the question: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                                |
      | ConsultantNoti | Consultation is completed for the question: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |
    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |

  @consultant-doctor-4
  Scenario Outline: doctor decline consult then coordinator receive noti and assign to other
    When I login as "DOCTOR_TEO"
    And As doctor, I decline questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As doctor, I load questions
      | textSearch |
      | <msg>      |
    Then The response should be
      | success   | true |
      | data.list | []   |


    When I login as "coordinator_auto"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Decline to answer the question: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                    |
      | ConsultantNoti | Decline to answer the question: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    And As coordinator, I assign question to doctor name "DoctorAuto1"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    When I login as "DoctorAuto1"
    # check members into consult
    And As doctor, I load questions
      | textSearch |
      | <msg>      |
    Then List "data.list[0].providers[*].userName" of response should contains
      | DoctorAuto1      |
      | COORDINATOR_AUTO |
      | user_1           |

    Then List "data.list[0].providers[*].userName" of response should not contains
      | DOCTOR_TEO |
    # accept consult
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |

  @consultant-doctor-5
  Scenario Outline: Doctor give back question to coordinator then coordinator receive notification and assign to other
    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As doctor, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                       | true |
      | data.actions.giveBackQuestion | true |
    And As doctor, I give back questions
      | textSearch | reason   |
      | <msg>      | <reason> |
    And The request should be succeed
    And As doctor, I load questions
      | textSearch |
      | <msg>      |
    Then The response should be
      | success   | true |
      | data.list | []   |

    When I login as "coordinator_auto"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Doctor give back the question: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                   |
      | ConsultantNoti | Doctor give back the question: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    And As coordinator, I assign question to doctor name "DoctorAuto1"
      | textSearch |
      | <msg>      |
    Then The request should be succeed

    When I login as "DoctorAuto1"
    # check members into consult
    And As doctor, I load questions
      | textSearch |
      | <msg>      |
    Then List "data.list[0].providers[*].userName" of response should contains
      | DoctorAuto1      |
      | COORDINATOR_AUTO |
      | user_1           |

    Then List "data.list[0].providers[*].userName" of response should not contains
      | DOCTOR_TEO |
    # accept consult
    And As doctor, I accept questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    Examples:
      | msg                                                            | shortMsg                                              | reason                                 |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... | câu hỏi này ko đúng chuyên môn của tôi |