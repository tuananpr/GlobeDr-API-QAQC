@regression @appt @appt-private-clinic
Feature: Appointment - User make appointment to nguyentriphuong hospital

  As User
  I want book appointment to private clinic Type
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


  @appt-private-clinic-1
  Scenario Outline: make appointment to private clinic
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
    When I switch main of logged user with below info
      | name      |
      | <patient> |

    # make appointment
    And I make an appointment to org name is "BV_THAI_AN"
      | appointmentType   | onDate   | reason   | contactPhone   |
      | <appointmentType> | <onDate> | <reason> | <contactPhone> |

    #  view appointment details
    And As user, I load appointment details
      | status |
      |        |
    Then The response should be
      | success                       | true           |
      | data.appt.patient.displayName | <patient>      |
      | data.appt.patient.fullName    | <patient>      |
      | data.appt.patient.phone       | <contactPhone> |
      | data.appt.toName              | BV_THAI_AN     |
      | data.appt.reason              | <reason>       |
      | data.appt.contactPhone        | <contactPhone> |
      | data.appt.status              | 32             |
      | data.appt.statusTxt           | Not Present    |


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
      | data.list[0].name                 | BV_THAI_AN           |

    # Manager load appointments
    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I load appointments
      | status |
      |        |
    Then The response should be
      | success                          | true           |
      | data.totalCount                  | 1              |
      | data.list[0].patient.displayName | <patient>      |
      | data.list[0].patient.fullName    | <patient>      |
      | data.list[0].patient.phone       | <contactPhone> |
      | data.list[0].toName              | BV_THAI_AN     |
      | data.list[0].reason              | <reason>       |
      | data.list[0].contactPhone        | <contactPhone> |

    Examples:
      | patient | description       | appointmentType        | appointmentTypeTxt | appointmentTypeTxtVN | onDate   | reason     | contactPhone |
      | user_1  | One Doctor Clinic | VisitAtOneDoctorClinic | Private clinics    | Phòng mạch tư        | tomorrow | Đau bao tử | 0867891234   |

