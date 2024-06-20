@regression @telemedicine @telemedi-coor @consultant @export-data @coordinator @video-call
Feature: Export Data of telemedicine

  As coordinator
  I want view and export statistic all telemedicine

  Background: User call to doctor then doctor receive and end call
    Given I re-signup "user_2" account and update profile
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

  @telemedi-export-data-1
  Scenario: Coordinator load and export telemedicine that GlobeDr must be payment
    And I login as "coordinator_auto"
    And As coordinator, I close questions
      | toDate | fromDate  | postTypes              | isPayment4Doctor |
      | today  | yesterday | TelemedicineConsultant | true             |
    And The response should be
      | success | true |

    And I load question statistic
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Closed     | Coordinator | true             | TelemedicineConsultant |
    And The request should be succeed
    And The "data.totalCount" equal "1"

    And I export question statistic
      | fromDate    | toDate | postStatus | userMode    | isPayment4Doctor | postTypes              |
      | prev 1 mins | today  | Closed     | Coordinator | true             | TelemedicineConsultant |

    And The response should be
      | success       | true                     |
      | data.fileType | application/octet-stream |
      | data.fileName | QUESTION_STATISTIC.csv   |
    And The "data.base64Str" should "not empty"
    And CSV file should contains
      | Telemedicine via video call |

