@regression @telemedicine @telemedicine-mode  @consultant @video-call
Feature: Telemedicine : mode

  Background:
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


  @telemedicine-mode-1
  Scenario: User just only call if doctor turn on telemedicine
    # user call to doctor
    And I login as "user_2"
    And I load doctors to call by specialty "ORTHOPEDICS"
    And List "data.other[*].name" of response should contains
      | DOCTOR_TEO |
    # doctor call off telemedicine mode
    And I login as "DOCTOR_TEO"
    And I turn off telemedicine mode
#    # user cant find above doctor
    And I login as "user_2"
    And I load doctors to call by specialty "ORTHOPEDICS"
    And List "data.other[*].name" of response should not contains
      | DOCTOR_TEO |