@regression @appt-invalid @appt-invalid @appt
Feature: Appointment - user make invalid appointment to org

  As User,
  I want to get error message when book appointment with invalid information
  So I can fix it

  Background:
    Given On SqlServer, I delete user by displayName "ABC_01"

    And I re-signup "manager_1" account and update profile
    And I re-signup "DoctorAuto1" account provider and update profile
    And I re-signup "user_1" account and update profile
    And I login as "DoctorAuto1"
    And I want to add "ANESTHESIOLOGY" as my specialty
    Then The request should be succeed

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | specialties    | doctorTelemedicine |
      | BV_THAI_AN | Hospital | manager_1 | ANESTHESIOLOGY | DoctorAuto1        |

    And I login as "DoctorAuto1"
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
    Then The request should be succeed
    And I login as "system_admin_1"


  @appt-invalid-2-a
  Scenario Outline: make invalid appointment for <description>
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    # make appointment
    And I make an invalid appointment to org name is "BV_THAI_AN"
      | appointmentType   |
      | <appointmentType> |
    Then The response should be
      | success                      | false   |
      | errors.onDate                | <error> |
      | errors.doctorSig             | <error> |
      | errors.patientSig            | <error> |
      | errors.contactPhone          | <error> |
      | errors.scheduleExaminationId | <error> |
      | errors.specialtyCode         | <error> |

    Examples:
      | appointmentType      | error                  |
      | FirstTimeExamination | This field is required |


  @appt-invalid-2-b
  Scenario Outline: make invalid appointment for <description>
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    # make appointment
    And I make an invalid appointment to org name is "BV_THAI_AN"
      | appointmentType   |
      | <appointmentType> |
    Then The response should be
      | success                      | false   |
      | errors.patientSig            | <error> |
      | errors.onDate                | <error> |
      | errors.contactPhone          | <error> |
      | errors.scheduleExaminationId | <error> |
    Examples:
      | appointmentType         | error                  |
      | OnlineConsultant        | This field is required |
      | PhysicalExamination     | This field is required |
      | ImmunizationExamination | This field is required |

  @appt-invalid-2-c
  Scenario Outline: make invalid appointment for <description>
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    # make appointment
    And I make an invalid appointment to org name is "BV_THAI_AN"
      | appointmentType   |
      | <appointmentType> |
    Then The response should be
      | success                      | false   |
      | errors.onDate                | <error> |
      | errors.contactPhone          | <error> |
      | errors.scheduleExaminationId | <error> |
      | errors.specialtyCode         | <error> |
    Examples:
      | appointmentType | error                  |
      | ReExamination   | This field is required |


  @appt-invalid-2-d
  Scenario Outline: make invalid appointment for <description>
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    # make appointment
    And I make an invalid appointment to org name is "BV_THAI_AN"
      | appointmentType   |
      | <appointmentType> |
    Then The response should be
      | success                      | false   |
      | errors.patientSig            | <error> |
      | errors.onDate                | <error> |
      | errors.contactPhone          | <error> |
      | errors.scheduleExaminationId | <error> |
    Examples:
      | appointmentType         | error                  |
      | TelemedicineExamination | This field is required |


  @appt-invalid-2-e
  Scenario Outline: make invalid appointment for <description>
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    # make appointment
    And I make an invalid appointment to org name is "BV_THAI_AN"
      | appointmentType   |
      | <appointmentType> |
    Then The response should be
      | success                      | false   |
      | errors.onDate                | <error> |
      | errors.contactPhone          | <error> |
      | errors.scheduleExaminationId | <error> |
      | errors.addressExamination    | <error> |

    Examples:
      | appointmentType    | error                  |
      | HomeMedicalService | This field is required |

  @appt-invalid-2-f
  Scenario Outline: update invalid insurance card and identity card to verify patient information for appointment
    And I login as "system_admin_1"
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "<appointmentType>" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |

    Given I login as "user_1"
    When I switch <account> of logged user with below info
      | name      |
      | <patient> |
    # make appointment
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType   | onDate   | specialtyCode   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <contactPhone> |

    And I add invalid insurance, identity card for appointment
    Then The response should be
      | success                  | false                  |
      | message                  | Please try again later |
      | errors.idCardNumber      | This field is required |
      | errors.idCardDocSigFront | This field is required |
      | errors.idCardDocSigBack  | This field is required |

    Examples:
      | account | patient | appointmentType      | onDate   | specialtyCode | contactPhone |
      | main    | user_1  | FirstTimeExamination | tomorrow | 01            | 0867891234   |