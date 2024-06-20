@regression @appt @appt-manage-update-info
Feature: Appointment - update information of existed appointment

  As manage
  I want update information of "Missed" appointment as Appointment date, Doctor, status ...
  In order to change information when necessary

  Background:
    Given I re-signup "DoctorAuto1" account provider and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "manager_1" account and update profile

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

  @appt-manage-update-info-1
  Scenario Outline: manager update appointment status

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
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType   | onDate   | specialtyCode   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <contactPhone> |

    #  Upload appointment doc
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
    And As manager, I update appointment status that has patient name "user_1"
      | status   | visitedDate |
      | <status> | tomorrow    |

    Then The request should be succeed
    Examples:
      | status   | patient | description          | appointmentType      | appointmentTypeTxt     | appointmentTypeTxtVN | onDate   | specialtyCode | contactPhone |
      | Visited  | user_1  | FirstTimeExamination | FirstTimeExamination | First Time Examination | Khám lần đầu         | tomorrow | 01            | 0867891234   |
      | MissAppt | user_1  | FirstTimeExamination | FirstTimeExamination | First Time Examination | Khám lần đầu         | tomorrow | 01            | 0867891234   |
      | Done     | user_1  | FirstTimeExamination | FirstTimeExamination | First Time Examination | Khám lần đầu         | tomorrow | 01            | 0867891234   |


  @appt-manage-update-info-2
  Scenario Outline: manager update appointment date and doctor
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
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType   | onDate   | specialtyCode   | reasonExamination   | bloodVessel   | bodyTemperature   | systolic   | diastolic   | capillary   | immuGdr   | addressExamination   | homeMedicalServiceType   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <reasonExamination> | <bloodVessel> | <bodyTemperature> | <systolic> | <diastolic> | <capillary> | <immuGdr> | <addressExamination> | <homeMedicalServiceType> | <contactPhone> |

    #  Upload appointment doc
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
    And As manager,  I update appointment that has patient name "user_1"
      | doctorName  | onDate   | shift    |
      | DoctorAuto1 | tomorrow | overTime |
    Then The response should be
      | success | true |

    Examples:
      | patient | description                         | appointmentType         | appointmentTypeTxt       | appointmentTypeTxtVN | onDate   | specialtyCode | reasonExamination | bloodVessel | bodyTemperature | systolic | diastolic | capillary | immuGdr | addressExamination | homeMedicalServiceType | contactPhone |
      | user_1  | FirstTimeExamination                | FirstTimeExamination    | First Time Examination   | Khám lần đầu         | tomorrow | 01            |                   |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | user_1  | OnlineConsultant                    | OnlineConsultant        | Online consultant        | Tư vấn trực tuyến    | tomorrow |               |                   |             | 37              | 140      | 100       | 90        |         |                    |                        | 0867891234   |
      | user_1  | TelemedicineExamination             | TelemedicineExamination | Telemedicine Examination | Khám bệnh từ xa      | tomorrow |               |                   | 100         | 37              | 120      | 80        |           |         |                    |                        | 0867891234   |
      | user_1  | Immunization Examination            | ImmunizationExamination | Immunization             | Chủng ngừa           | tomorrow |               |                   |             |                 |          |           |           | false   |                    |                        | 0867891234   |
      | user_1  | ReExamination                       | ReExamination           | ReExamination            | Tái khám             | tomorrow | 01            |                   |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | user_1  | Physical Examination of Others      | PhysicalExamination     | Physical Examination     | Khám sức khỏe        | tomorrow |               | 255               |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | user_1  | HomeMedicalService Others           | HomeMedicalService      | Home Medical Service     | Dịch vụ y tế tại nhà | tomorrow |               |                   |             |                 |          |           |           |         | nguyen chi thanh   | 255                    | 0867891234   |
      | user_1  | HomeMedicalService VisitExamination | HomeMedicalService      | Home Medical Service     | Dịch vụ y tế tại nhà | tomorrow |               |                   |             |                 |          |           |           |         | nguyen chi thanh   | 1                      | 0867891234   |



