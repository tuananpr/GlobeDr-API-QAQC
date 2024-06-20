@regression @telemedicine @telemedicine-user  @consultant @video-call
Feature: Telemedicine : user call doctor

  Background: user call doctor
    Given I re-signup "user_2" account and update profile
    And I login as "user_2"
     #user subscribe channels to receive information from server
    And On Pusher, "user_2" subscribe all channels

    # set on telemedicine for doctor
    And I login as "DOCTOR_TEO"
    And I want to add "ORTHOPEDICS" as my specialty
    And I turn on telemedicine mode
    Then The request should be succeed
     #doctor subscribe channels to receive information from server
    And On Pusher, "DOCTOR_TEO" subscribe all channels
    # user call to doctor
    And I login as "user_2"
    And As user, I call video to doctor
      | doctorName | specialtyName |
      | DOCTOR_TEO | ORTHOPEDICS   |
    Then The request should be succeed


  @user-call-doctor-2
  Scenario: User miss a call > recall to doctor
    # user miss a call
    And As user, I miss call from consult
    Then The request should be succeed
    # post status is Missed
    And As user, I load comment of question
      | postTypes              |
      | TelemedicineConsultant |
    And The response should contains
      | data.totalCount  | 2                                 |
      | data.list[0].msg | Start Telemedicine via video call |
      | data.list[1].msg | Missed                            |
    And I recall video to doctor
      | videoCallType |
      | Telemedicine  |
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


  @user-call-doctor-4
  Scenario: user call and end call
    # doctor receive call from patient
    And I login as "DOCTOR_TEO"
    And On Pusher, "DOCTOR_TEO" get information
    And As doctor, I receive video call from consult
    Then The request should be succeed

    # user start video call
    And I login as "user_2"
    And On Pusher, "user_2" get information

    # user end call after few seconds
    And As user, I end video call
      | videoCallType |
      | Telemedicine  |
    Then The request should be succeed

  @user-call-doctor-5
  Scenario: user call to doctor then doctor and coordinator end question
    # doctor receive call from patient
    And I login as "DOCTOR_TEO"
    And On Pusher, "DOCTOR_TEO" get information
    And As doctor, I receive video call from consult
    Then The request should be succeed

    # user start video call
    And I login as "user_2"
    # post status change from draft to accepted
    And As user, I load questions
      | postTypes              |
      | TelemedicineConsultant |
    And The response should be
      | data.totalCount                    | 1          |
      | data.list[0].providers[0].userName | DOCTOR_TEO |
      | data.list[0].providers[1].userName | user_2     |
      | data.list[0].status.name           | Accepted   |


    # doctor end call after few seconds
    And I login as "DOCTOR_TEO"
    And As doctor, I end video call
      | videoCallType |
      | Telemedicine  |
    Then The request should be succeed
    # post status change from draft to accepted
    And As doctor, I load questions
      | postTypes              |
      | TelemedicineConsultant |
    And The response should be
      | data.list[0].status.name | Answered |
    And As doctor, I load comment of question
      | userMode | postTypes              |
      | Patient  | TelemedicineConsultant |
    And The response should contains
      | data.totalCount  | 2                                                                                                                                                                                      |
      | data.list[0].msg | Start Telemedicine via video call                                                                                                                                                      |
      | data.list[1].msg | <p style='text-align: center'><font color='#2196F3'>End Telemedicine via video call</font></p><p style='text-align: center'><font color='#2196F3'>Video chat duration 00:00</font></p> |
    And As doctor, I get actions into consult
      | userMode | postTypes              |
      | Patient  | TelemedicineConsultant |
    And The response should be
      | success                         | true  |
      | data.actions.recallTelemedicine | true  |
      | data.actions.commentQuestion    | true  |
      | data.actions.acceptQuestion     | false |
      | data.actions.declineQuestion    | false |
      | data.actions.completeQuestion   | false |
      | data.actions.approveQuestion    | false |
      | data.actions.rejectQuestion     | false |
      | data.actions.hideMsg            | false |
      | data.actions.assignQuestion     | false |
      | data.actions.closeQuestion      | false |
      | data.actions.submitAudit        | false |
      | data.actions.sendDoctorNoti     | false |
      | data.actions.sendAuditorNoti    | false |
      | data.actions.viewActivityLog    | false |
      | data.actions.viewPatientHealth  | false |
      | data.actions.setTime            | false |
      | data.actions.cancelAppointment  | false |
    # Pusher to A, B

    # coordinator load and close question
    And I login as "coordinator_auto"
    And As coordinator, I load questions
      | toDate | fromDate    | postTypes              |
      | today  | prev 1 mins | TelemedicineConsultant |
    And The response should be
      | success | true |
    And As coordinator, I close questions
      | toDate | fromDate    | postTypes              | isPayment4Doctor |
      | today  | prev 1 mins | TelemedicineConsultant | true             |
    And The response should be
      | success | true |

  @user-call-doctor-6
  Scenario: user call doctor > doctor busy
    # doctor receive call from patient
    And I login as "DOCTOR_TEO"
    And On Pusher, "DOCTOR_TEO" get information
    And I busy video call from consult
    Then The request should be succeed
    # doctor load list question

    And As doctor, I load comment of question
      | postTypes              |
      | TelemedicineConsultant |
    And The response should contains
      | data.totalCount  | 1                                 |
      | data.list[0].msg | Start Telemedicine via video call |

  @user-call-doctor-7
  Scenario: Coordinator or patient can view total duration of call between patient and doctor
    And I login as "DOCTOR_TEO"
    And On Pusher, "DOCTOR_TEO" get information
    And As doctor, I receive video call from consult
    Then The request should be succeed

    And As doctor, I end video call
      | videoCallType |
      | Telemedicine  |
    Then The request should be succeed

    And I login as "coordinator_auto"
    And As coordinator, I load questions
      | toDate | fromDate    | postTypes              |
      | today  | prev 5 mins | TelemedicineConsultant |
    And The response should be
      | success                       | true  |
      | data.list[0].callDurationText | 00:00 |

    And I login as "user_2"
    And As user, I load questions
      | toDate | fromDate    | postTypes              |
      | today  | prev 5 mins | TelemedicineConsultant |
    And The response should be
      | success                       | true  |
      | data.list[0].callDurationText | 00:00 |