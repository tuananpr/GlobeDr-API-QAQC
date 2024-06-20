@regression @telemedicine @telemedicine-user @consultant @video-call @provider-report @report-video-call
Feature: Provider : report video call

  Background: User call to doctor then doctor receive and end call
    Given I re-signup "user_2" account and update profile
    Given I re-signup "DOCTOR_TEO" account provider and update profile
    And I login as "user_2"
    # user subscribe channels to receive information from server
    And On Pusher, "user_2" subscribe all channels

    # set on telemedicine for doctor
    And I login as "DOCTOR_TEO"
    And I want to add "ORTHOPEDICS" as my specialty
    And I turn on telemedicine mode
    Then The request should be succeed
    # doctor subscribe channels to receive information from server
    And On Pusher, "DOCTOR_TEO" subscribe all channels

      # user call to doctor
    And I login as "user_2"

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


  @report-video-call-1
  Scenario: doctor load report and log fees of video call
    And I login as "DOCTOR_TEO"
    And As doctor, I load report by org
    Then The response should be
      | success           | true    |
      | data.list[0].name | GlobeDr |
    And As doctor, I load report fees
      | fromDate  | toDate   | reportFeesType  |
      | yesterday | tomorrow | VideoConsultant |
    Then The response should be
      | success                            | true                                   |
      | data.list[0].sumFees               | 20000.00000 VNĐ                        |
      | data.list[0].reportFeesTypeNameTxt | The number of answered video questions |
      | data.totalFees                     | 20000.00000 VNĐ                        |

    And As doctor, I load log fees
      | fromDate  | toDate   | reportFeesType  |
      | yesterday | tomorrow | VideoConsultant |
    Then The response should be
      | success                         | true              |
      | data.list[0].fees               | + 20000.00000 VNĐ |
      | data.list[0].orgName            | GlobeDr           |
      | data.list[0].patientName        | user_2            |
      | data.list[0].reportFeesTypeName | VIDEO CALL        |


