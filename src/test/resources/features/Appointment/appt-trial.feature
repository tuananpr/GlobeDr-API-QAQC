@regression @appt @appt-trial @trial
Feature: Appointment trial

  As manager
  I want to create appointment trial
  In order to specified user can view it and normal user can't view it

  Background:
    And I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "DoctorAuto1" account provider and update profile
    And I login as "DoctorAuto1"
    And I want to add "ANESTHESIOLOGY" as my specialty
    Then The request should be succeed

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | specialties    | doctorTelemedicine |
      | BV_THAI_AN | Hospital | manager_1 | ANESTHESIOLOGY | DoctorAuto1        |
    When I change appointment type of org
      | appointmentType  |
      | OnlineConsultant |
    And The request should be succeed

    And I login as "DoctorAuto1"
    When I accept join organization

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage
    And As manager, I create appointment schedule with type "OnlineConsultant" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |


    # Update price for appointment
    When I update to appointment service has name "Tư vấn trực tuyến"
      | name              | NameEn            | description | orgProductType | fromPrice | currency | isInternal | isPublic | isVisible | allowVideoCall | isTrial |
      | Tư vấn trực tuyến | Online Consultant | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      | true           | true    |
    And The request should be succeed

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    Then The request should be succeed
    # make appointment



  @appt-trial-1
  Scenario: Patient book Consult Online Appointment
    And As user, I load products of org name "BV_THAI_AN"
      | description | productServiceType |
      | mô tả       | AppointmentOrg     |
    Then The response should be
      | success   | true |
      | data.list | []   |