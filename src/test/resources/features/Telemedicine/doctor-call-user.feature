@regression @telemedicine @telemedicine-user  @consultant @video-call
Feature: Telemedicine : doctor call user

  Background: user miss call then doctor call to user
    Given I re-signup "user_2" account and update profile

    And I login as "DOCTOR_TEO"
    And I want to add "ORTHOPEDICS" as my specialty
    And I turn on telemedicine mode
    Then The request should be succeed

    And I login as "user_2"
    And On Pusher, "user_2" subscribe all channels

    And As user, I call video to doctor
      | doctorName | specialtyName |
      | DOCTOR_TEO | ORTHOPEDICS   |
    Then The request should be succeed

    And As user, I miss call from consult
    Then The request should be succeed

    # set on telemedicine for doctor
    And I login as "DOCTOR_TEO"
     #doctor subscribe channels to receive information from server
    And On Pusher, "DOCTOR_TEO" subscribe all channels
    And As doctor, I call video to user into consult
      | userName | postTypes              |
      | user_2   | TelemedicineConsultant |
    Then The request should be succeed

  @doctor-call-user-1
  Scenario: doctor call to user
    And I login as "user_2"
    And On Pusher, "user_2" get information
    And As user, I receive video call from consult
    Then The request should be succeed

    And I login as "DOCTOR_TEO"
    And As doctor, I end video call
      | videoCallType |
      | Telemedicine  |
    Then The request should be succeed

  @doctor-call-user-2
  Scenario: doctor call to user then pressing miss
    And I login as "DOCTOR_TEO"
    And As doctor, I miss call from consult
    Then The request should be succeed

  @doctor-call-user-3
  Scenario: doctor call to user then user busy
    And I login as "user_2"
    And On Pusher, "user_2" get information
    And I busy video call from consult
    Then The request should be succeed