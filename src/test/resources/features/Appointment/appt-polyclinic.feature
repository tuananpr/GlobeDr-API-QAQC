@regression @appt @appt-polyclinic-1
Feature: Appointment - User make appointment to nguyentriphuong hospital

  As User
  I want book appointment to Polyclinic Type
  In order to regular check-ups

  Background:
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

  @appt-polyclinic-1
  Scenario Outline: make appointment to Polyclinic
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

     # Update price for appointment
    When I update to appointment service has name "<appointmentTypeTxtVN>"
      | name                   | NameEn               | description | orgProductType | fromPrice | currency | isInternal | isPublic | isVisible |
      | <appointmentTypeTxtVN> | <appointmentTypeTxt> | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      |
    And The request should be succeed

    Given I login as "user_1"
    When I switch <account> of logged user with below info
      | name      |
      | <patient> |

    # make appointment
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | timeType | appointmentType   | onDate   | contactPhone   |
      | 1        | <appointmentType> | <onDate> | <contactPhone> |

    #  Upload appointment doc
    And I add insurance card "09876554" and identity card "1234324" for appointment
      | attributes | description                 | medicalType | docType            | createdDate | file                                |
      | 1          | front side of identity card | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of identity card  | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 1          | front side of Insurance doc | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of Insurance doc  | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

    #  view appointment details
    And As user, I load appointment details
      | status |
      |        |
    Then The response should be
      | success                       | true                 |
      | data.appt.patient.displayName | <patient>            |
      | data.appt.patient.fullName    | <patient>            |
      | data.appt.patient.phone       | <contactPhone>       |
      | data.appt.toName              | BV_THAI_AN           |
      | data.appt.appointmentTypeTxt  | <appointmentTypeTxt> |
      | data.appt.contactPhone        | <contactPhone>       |
      | data.appt.idInsurance         | 09876554             |
      | data.appt.idCardNumber        | 1234324              |
      | data.appt.status              | 32                   |
      | data.appt.statusTxt           | Not Present          |

    # User load appointments
    And As user, I load appointments
      | status |
      |        |
    Then The response should be
      | success                           | true                 |
      | data.totalCount                   | 1                    |
      | data.list[0].status.status        | 32                   |
      | data.list[0].status.name          | not present          |
      | data.list[0].appointmentType.name | <appointmentTypeTxt> |
      | data.list[0].displayName          | user_1               |
      | data.list[0].patientName          | <patient>            |
      | data.list[0].doctorName           | DoctorAuto1          |
      | data.list[0].name                 | BV_THAI_AN           |

    # Manager load appointments
    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I load appointments
      | status |
      |        |
    Then The response should be
      | success                          | true                 |
      | data.totalCount                  | 1                    |
      | data.list[0].patient.displayName | <patient>            |
      | data.list[0].patient.fullName    | <patient>            |
      | data.list[0].patient.phone       | <contactPhone>       |
      | data.list[0].toName              | BV_THAI_AN           |
      | data.list[0].appointmentTypeTxt  | <appointmentTypeTxt> |
      | data.list[0].contactPhone        | <contactPhone>       |

    # Manager load appointments
    Given I login as "DoctorAuto1"
    And As doctor, I load appointment details
      | IsPastVisit |
      | false       |
    Then The response should be
      | success                       | true                 |
      | data.appt.patient.displayName | <patient>            |
      | data.appt.patient.fullName    | <patient>            |
      | data.appt.patient.phone       | <contactPhone>       |
      | data.appt.toName              | BV_THAI_AN           |
      | data.appt.appointmentTypeTxt  | <appointmentTypeTxt> |
      | data.appt.contactPhone        | <contactPhone>       |
      | data.appt.idInsurance         | 09876554             |
      | data.appt.idCardNumber        | 1234324              |
      | data.appt.status              | 32                   |
      | data.appt.statusTxt           | Not Present          |
    Examples:
      | account | patient | description               | appointmentType        | appointmentTypeTxt        | appointmentTypeTxtVN  | onDate   | contactPhone |
      | main    | user_1  | Visit at Small Polyclinic | VisitAtSmallPolyClinic | Visit at Small Polyclinic | Phòng khám đa dịch vụ | tomorrow | 0867891234   |

