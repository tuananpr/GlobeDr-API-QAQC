@regression @appt @appt-doctor-update-info
Feature: Appointment - Doctor update status for patient appointment

  As Doctor
  I want update appointment status after patient consultation
  In order to update status of process appointment

  Background:
    Given I re-signup "DoctorAuto1" account provider and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "manager_1" account and update profile

    # user update information
    And I login as "DoctorAuto1"
    And I want to add "ANESTHESIOLOGY" as my specialty
    Then The request should be succeed

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | specialties    | doctorTelemedicine |
      | BV_THAI_AN | Hospital | manager_1 | ANESTHESIOLOGY | DoctorAuto1        |

    Given I login as "DoctorAuto1"
    When I accept join organization
    And I update my work schedule
      | weekday   | timeType    |
      | Sunday    | OnlyMorning |
      | Monday    | OnlyMorning |
      | Tuesday   | OnlyMorning |
      | Wednesday | OnlyMorning |
      | Thursday  | OnlyMorning |
      | Friday    | OnlyMorning |
      | Saturday  | OnlyMorning |

    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType      |
      | FirstTimeExamination |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "FirstTimeExamination" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |


    # Update price for appointment
    When I update to appointment service has name "Khám lần đầu"
      | name         | NameEn                 | description | orgProductType | fromPrice | currency | isInternal | isPublic | isVisible |
      | Khám lần đầu | First Time Examination | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      |
    And The request should be succeed

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |

    # make appointment
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType      | onDate   | specialtyCode | contactPhone |
      | FirstTimeExamination | tomorrow | 01            | 0867891234   |


    #  Upload appointment doc
    And I add insurance card "09876554" and identity card "1234324" for appointment
      | attributes | description                 | medicalType | docType            | createdDate | file                                |
      | 1          | front side of identity card | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of identity card  | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 1          | front side of Insurance doc | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of Insurance doc  | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

  @appt-doctor-update-info-1
  Scenario Outline: As default, doctor can update appointment status to Finished examination
    # Manager load appointments
    Given I login as "DoctorAuto1"
    And As doctor, I load appointment details
      | status |
      |        |
    # verify that doctor can update appointment status to Finished examination
    Then The response should be
      | success                            | true  |
      | data.appt.actions.cancel           | false |
      | data.appt.actions.updateNotPresent | false |
      | data.appt.actions.updateVisited    | true  |
      | data.appt.actions.updateDone       | false |
    And As doctor, I update appointment status that has patient name "user_1"
      | status  | visitedDate   |
      | Visited | <visitedDate> |

    Then The response should be
      | success | true |
    Examples:
      | visitedDate |
      | tomorrow    |
      |             |

  @appt-doctor-update-info-2
  Scenario Outline: Manage not allow doctor can update appointment status to Finished examination
    Given I login as "manager_1"
    And I select org "BV_THAI_AN" that I manage
    When I set configuration appointment
      | visitedAppointmentNotiVi                           | visitedAppointmentNotiEN                              | unAllowDoctorUpdateStatus | attributes |
      | khong cho phép bác sĩ thay đổi trạng thái lịch hen | not allow doctor to perform update appointment status | true                      | None       |
    Then The request should be succeed

    # Manager load appointments
    Given I login as "DoctorAuto1"
    And As doctor, I load appointment details
      | status     |
      | NotPresent |
    Then The response should be
      | success                            | true  |
      | data.appt.actions.cancel           | false |
      | data.appt.actions.updateNotPresent | false |
      | data.appt.actions.updateVisited    | false |
      | data.appt.actions.updateDone       | false |
    Examples:
      | description          | onDate   |
      | FirstTimeExamination | tomorrow |


  @appt-doctor-update-info-3
  Scenario: Doctor can decline appointment
    # Manager load appointments
    Given I login as "DoctorAuto1"
    And As doctor, I load appointments
      | status     |
      | NotPresent |

    Then The response should be
      | success         | true |
      | data.totalCount | 1    |

    And As doctor, I decline appointment
      | patientName |
      | user_1      |
    Then The response should be
      | success | true |

    And As doctor, I load appointments
      | status     |
      | NotPresent |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |