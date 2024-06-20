@regression @telemedicine @telemedi-coor @consultant @coordinator @video-call
Feature: All actions of coordinator into telemedicine


  Background: User call to doctor
    Given I re-signup "user_1" account and update profile
    # set on telemedicine for doctor
    And I login as "DOCTOR_TEO"
    And I want to add "ORTHOPEDICS" as my specialty
    And I turn on telemedicine mode
    Then The request should be succeed
    # doctor subscribe channels to receive information from server
    And On Pusher, "DOCTOR_TEO" subscribe all channels

      # user call to doctor
    And I login as "user_1"

    And As user, I call video to doctor
      | doctorName | specialtyName |
      | DOCTOR_TEO | ORTHOPEDICS   |
    Then The request should be succeed

    # doctor receive call from patient
    And I login as "DOCTOR_TEO"
    And On Pusher, "DOCTOR_TEO" get information
    And As doctor, I receive video call from consult
    Then The request should be succeed

    # doctor end call after few seconds
    And As doctor, I end video call
      | videoCallType |
      | Telemedicine  |
    Then The request should be succeed


  @telemedi-coor-1
  Scenario: Coordinator get actions into telemedicine
    And I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | true  |
      | data.actions.commentQuestion     | true  |
      | data.actions.acceptQuestion      | false |
      | data.actions.declineQuestion     | false |
      | data.actions.completeQuestion    | false |
      | data.actions.approveQuestion     | false |
      | data.actions.rejectQuestion      | false |
      | data.actions.hideMsg             | true  |
      | data.actions.assignQuestion      | false |
      | data.actions.closeQuestion       | true  |
      | data.actions.spamQuestion        | true  |
      | data.actions.submitAudit         | false |
      | data.actions.sendDoctorNoti      | true  |
      | data.actions.sendAuditorNoti     | false |
      | data.actions.sendPatientNoti     | true  |
      | data.actions.viewActivityLog     | true  |
      | data.actions.viewPatientHealth   | false |
      | data.actions.setTime             | false |
      | data.actions.cancelAppointment   | false |
      | data.actions.indicateMedicalTest | false |
      | data.actions.review              | false |
      | data.actions.sendHealthDoc       | false |

  @telemedi-coor-2
  Scenario: Coordinator can close telemedicine
    And I login as "coordinator_auto"
    And As coordinator, I close questions
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    And As coordinator, I get actions into consult
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Closed     | Coordinator | true             | TelemedicineConsultant |
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
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Closed     | Coordinator | true             | TelemedicineConsultant |
    Then List "data.list[*].notes" of response should contains
      | The question was closed by <font color='#39B44A'>COORDINATOR_AUTO, </font> |
    And I login as "user_1"
    And As user, I get actions into consult
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Closed     | Coordinator | true             | TelemedicineConsultant |
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

  @telemedi-coor-3
  Scenario: Send notification to patient into telemedicine

    # Coordinator login and assign question to doctor
    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    Then The response should be
      | success                      | true |
      | data.actions.sendPatientNoti | true |

    And As coordinator, I send notification to patient into consult
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    And The request should be succeed

    And As coordinator, I get activity log
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
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
      | success | true |

  @telemedi-coor-4
  Scenario: Send notification to doctor into telemedicine

    # Coordinator login and assign question to doctor
    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    Then The response should be
      | success                     | true |
      | data.actions.sendDoctorNoti | true |

    And As coordinator, I send notification to assigned doctor into consult
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    And The request should be succeed

    And As coordinator, I get activity log
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    Then List "data.list[*].notes" of response should contains
      | <font color='#39B44A'>COORDINATOR_AUTO, </font> reminded <font color='#39B44A'>DOCTOR_TEO, </font> to answer this question |

    When I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConsultantNoti |
    Then List "data.list[*].message" of response should contains
      | Please consult for question |

  @telemedi-coor-5
  Scenario Outline: Coordinator set questions are spam then user can't comment into telemedicine
    When I login as "coordinator_auto"
    And As coordinator, I set questions are spam
      | fromDate    | toDate | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Coordinator | true             | TelemedicineConsultant |
    Then The request should be succeed

    And As coordinator, I get actions into consult
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Spam       | Coordinator | true             | TelemedicineConsultant |
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
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Spam       | Coordinator | true             | TelemedicineConsultant |
    Then List "data.list[*].notes" of response should contains
      | The question was reported spam by <font color='#39B44A'>COORDINATOR_AUTO, </font> |
    When I login as "user_1"
    And As user, I get actions into consult
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Spam       | Coordinator | true             | TelemedicineConsultant |
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
      | Spam question |

    And I open noti from consult
      | groupType      | message       |
      | ConsultantNoti | Spam question |
    And The response should be
      | success               | true                                                                                             |
      | data.list[0].msg      | <p style='text-align: center'><font color='#2196F3'>Start Telemedicine via video call</font></p> |
      | data.list[0].userName | user_1                                                                                           |

    Examples:
      | msg                                                            |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |