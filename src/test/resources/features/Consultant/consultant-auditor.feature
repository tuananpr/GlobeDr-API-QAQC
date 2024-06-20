@regression @consultant-auditor @consultant
Feature: Consultant auditor


  Background:
    Given I re-signup "user_1" account and update profile

    And I login as "user_1"
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed
    And As doctor, I add comment "uống nước nhiều vào nha" into consult
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed
    And As doctor, I complete questions
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed


  @consultant-auditor-1
  Scenario Outline: auditor get actions of consult that reviewed
    When I login as "Audit_Trung"
    And As auditor, I get actions into consult
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | true  |
      | data.actions.rejectQuestion      | true  |
      | data.actions.hideMsg             | true  |
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
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-auditor-2
  Scenario Outline: auditor get actions of consult that approved
    When I login as "Audit_Trung"
    And As auditor, I approve this questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As auditor, I get actions into consult
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
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-auditor-3
  Scenario Outline: auditor get actions of consult that closed
    When I login as "Audit_Trung"
    And As auditor, I approve this questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed
    When I login as "Audit_Trung"
    And As auditor, I get actions into consult
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
    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |

  @consultant-auditor-4
  Scenario Outline: auditor not agree with answer of doctor then doctor receive noti
    When I login as "Audit_Trung"
    And As auditor, I reject this questions
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As auditor, I load questions
      | textSearch |
      | <msg>      |
    Then The response should be
      | success                  | true  |
      | data.list[0].status.name | Added |
      | data.list[0].rootMsg.msg | <msg> |


    When I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Will provide more suggestions: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                   |
      | ConsultantNoti | Will provide more suggestions: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |

  @consultant-auditor-5
  Scenario Outline: auditor agree with answer of doctor then doctor and user receive noti
    When I login as "Audit_Trung"
    And As auditor, I approve this questions
      | textSearch |
      | <msg>      |
    And The request should be succeed

    When I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Has agreed with the consulted doctor: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                          |
      | ConsultantNoti | Has agreed with the consulted doctor: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    When I login as "user_1"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Has agreed with the consulted doctor: <shortMsg> |

    And I open noti from consult
      | groupType      | message                                          |
      | ConsultantNoti | Has agreed with the consulted doctor: <shortMsg> |
    And The response should be
      | success          | true  |
      | data.list[1].msg | <msg> |

    Examples:
      | msg                                                            | shortMsg                                              |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày ... |