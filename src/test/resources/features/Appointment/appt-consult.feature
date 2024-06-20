@regression @appt @appt-consult
Feature: Appointment - Consultant Online

  As User
  I want book "Consultant Online" appointment
  In order to regular check-ups

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
      | name              | NameEn            | description | orgProductType | fromPrice | currency | isInternal | isPublic | isVisible |
      | Tư vấn trực tuyến | Online Consultant | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      |
    And The request should be succeed

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    Then The request should be succeed
    # make appointment
    And I make an appointment to org name is "BV_THAI_AN"
      | appointmentType  | onDate   | bloodVessel | bodyTemperature | systolic | diastolic | capillary | contactPhone |
      | OnlineConsultant | tomorrow | 120         | 37              | 140      | 100       | 90        | 0867891234   |

    #  Upload appointment doc
    And As user, I upload health document appointment
      | attributes | description       | medicalType  | docType      | createdDate | file                                |
      | 1          | Prescription doc  | Prescription | Prescription | today       | data/image/immunization/immu-01.jpg |
      | 1          | CapillaryAppt doc | Others       | Others       | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

    And I add insurance card "09876554" and identity card "1234324" for appointment
      | attributes | description                 | medicalType | docType            | createdDate | file                                |
      | 1          | front side of identity card | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of identity card  | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 1          | front side of Insurance doc | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of Insurance doc  | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed


    # Manager load appointments
    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I choose a below appointment and assign to doctor "DoctorAuto1"
      | patientName |
      | user_1      |
    Then The response should be
      | success | true |


  @appt-consult-1 @upload-file
  Scenario: Patient book Consult Online Appointment

    When I login as "user_1"
    And As user, I choose a first appointment then comment "chào bác sĩ" into consultation
      | name       |
      | BV_THAI_AN |
    Then The response should be
      | success | true |
    When I login as "DoctorAuto1"
    And As doctor, I choose a first appointment then view comments into consultation
      | patientName |
      | user_1      |
    Then The response should be
      | success          | true        |
      | data.list[1].msg | chào bác sĩ |
    And As doctor, I choose a first appointment then view actions into consultation
      | patientName |
      | user_1      |
    Then The response should be
      | success                          | true |
      | data.actions.recallTelemedicine  | true |
      | data.actions.commentQuestion     | true |
      | data.actions.indicateMedicalTest | true |

  @appt-consult-2
  Scenario: disable Consult Online into Appointment after finish
    And As manager, I update appointment status that has patient name "user_1"
      | status | visitedDate |
      | Done   | tomorrow    |
    Then The request should be succeed

    When I login as "user_1"
    And As user, I choose a first appointment then view actions into consultation
      | name       |
      | BV_THAI_AN |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.indicateMedicalTest | false |

    When I login as "DoctorAuto1"
    And As doctor, I choose a first appointment then view actions into consultation
      | patientName |
      | user_1      |
    Then The response should be
      | success                          | true  |
      | data.actions.recallTelemedicine  | false |
      | data.actions.commentQuestion     | false |
      | data.actions.indicateMedicalTest | false |


  @appt-consult-3 @upload-file
  Scenario Outline: total in-progress appointment should displayed on list doctors that to assign
    # total in-progress appointment
    When As manager, I load doctors to assign appointment
      | isLoadAssign | name        |
      | true         | DoctorAuto1 |
    Then The response should be
      | success                 | true |
      | data.list[0].countAppts | 1    |

     # set appointment is done
    And As manager, I update appointment status that has patient name "user_1"
      | status   | visitedDate |
      | <status> | tomorrow    |
    Then The request should be succeed

    # total in-progress appointment
    When As manager, I load doctors to assign appointment
      | isLoadAssign | name        |
      | true         | DoctorAuto1 |
    Then The response should be
      | success                 | true         |
      | data.list[0].countAppts | <countAppts> |
    Examples:
      | status  | countAppts |
      | Done    | 0          |
      | Cancel  | 0          |
      | Visited | 0          |