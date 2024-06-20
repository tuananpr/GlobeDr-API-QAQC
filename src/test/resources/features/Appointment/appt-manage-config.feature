@regression @appt @appt-manage-config
Feature: Appointment - Doctor update status for patient appointment

  As Manager
  I want create config for appointment as hide/show ID/Insurance card when patient book
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

  @appt-manage-config-1
  Scenario Outline: As Manage, i want hide or show ID card and Insurance card when patient book

    When I set configuration appointment
      | isShowConfirmPatientInfo   | attributes |
      | <isShowConfirmPatientInfo> | None       |
    Then The request should be succeed

    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed



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
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType   | onDate   | specialtyCode   | reasonExamination   | notes   | bloodVessel   | bodyTemperature   | systolic   | diastolic   | capillary   | immuGdr   | addressExamination   | homeMedicalServiceType   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <reasonExamination> | <notes> | <bloodVessel> | <bodyTemperature> | <systolic> | <diastolic> | <capillary> | <immuGdr> | <addressExamination> | <homeMedicalServiceType> | <contactPhone> |

    Then The response should be
      | success              | true              |
      | data.isVerifyPatient | <isVerifyPatient> |

    Examples:
      | isShowConfirmPatientInfo | isVerifyPatient | patient | description                         | appointmentType         | appointmentTypeTxt       | appointmentTypeTxtVN | onDate   | specialtyCode | reasonExamination | notes | bloodVessel | bodyTemperature | systolic | diastolic | capillary | immuGdr | addressExamination | homeMedicalServiceType | contactPhone |
      | true                     | true            | user_1  | FirstTimeExamination                | FirstTimeExamination    | First Time Examination   | Khám lần đầu         | tomorrow | 01            |                   |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | false                    | false           | user_1  | FirstTimeExamination                | FirstTimeExamination    | First Time Examination   | Khám lần đầu         | tomorrow | 01            |                   |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | true                     | true            | user_1  | OnlineConsultant                    | OnlineConsultant        | Online consultant        | Tư vấn trực tuyến    | tomorrow |               |                   |       |             | 37              | 140      | 100       | 90        |         |                    |                        | 0867891234   |
      | false                    | false           | user_1  | OnlineConsultant                    | OnlineConsultant        | Online consultant        | Tư vấn trực tuyến    | tomorrow |               |                   |       |             | 37              | 140      | 100       | 90        |         |                    |                        | 0867891234   |
      | true                     | true            | user_1  | TelemedicineExamination             | TelemedicineExamination | Telemedicine Examination | Khám bệnh từ xa      | tomorrow |               |                   |       | 100         | 37              | 120      | 80        |           |         |                    |                        | 0867891234   |
      | false                    | false           | user_1  | TelemedicineExamination             | TelemedicineExamination | Telemedicine Examination | Khám bệnh từ xa      | tomorrow |               |                   |       | 100         | 37              | 120      | 80        |           |         |                    |                        | 0867891234   |
      | true                     | true            | user_1  | Immunization Examination            | ImmunizationExamination | Immunization             | Chủng ngừa           | tomorrow |               |                   |       |             |                 |          |           |           | false   |                    |                        | 0867891234   |
      | false                    | false           | user_1  | Immunization Examination            | ImmunizationExamination | Immunization             | Chủng ngừa           | tomorrow |               |                   |       |             |                 |          |           |           | false   |                    |                        | 0867891234   |
      | true                     | true            | user_1  | ReExamination                       | ReExamination           | ReExamination            | Tái khám             | tomorrow | 01            |                   |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | false                    | false           | user_1  | ReExamination                       | ReExamination           | ReExamination            | Tái khám             | tomorrow | 01            |                   |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | true                     | true            | user_1  | Physical Examination of Others      | PhysicalExamination     | Physical Examination     | Khám sức khỏe        | tomorrow |               | 255               |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | false                    | false           | user_1  | Physical Examination of Others      | PhysicalExamination     | Physical Examination     | Khám sức khỏe        | tomorrow |               | 255               |       |             |                 |          |           |           |         |                    |                        | 0867891234   |
      | true                     | true            | user_1  | HomeMedicalService Others           | HomeMedicalService      | Home Medical Service     | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 255                    | 0867891234   |
      | false                    | false           | user_1  | HomeMedicalService VisitExamination | HomeMedicalService      | Home Medical Service     | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 1                      | 0867891234   |


  @appt-manage-config-2
  Scenario Outline: Appointment "One Doctor Clinic" never require ID card and Insurance card when patient book
    When I set configuration appointment
      | isShowConfirmPatientInfo   | attributes |
      | <isShowConfirmPatientInfo> | None       |
    Then The request should be succeed

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
    When I update to appointment service has name ""
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
    Then The response should be
      | success              | true              |
      | data.isVerifyPatient | <isVerifyPatient> |

    Examples:
      | isShowConfirmPatientInfo | isVerifyPatient | patient | status | appointmentType        | appointmentTypeTxt         | appointmentTypeTxtVN | onDate   | reason     | contactPhone |
      | true                     | true            | user_1  |        | VisitAtOneDoctorClinic | Visit At One Doctor Clinic | Khám lần đầu         | tomorrow | Đau bao tử | 0867891234   |
      | false                    | false           | user_1  |        | VisitAtOneDoctorClinic | Visit At One Doctor Clinic | Khám lần đầu         | tomorrow | Đau bao tử | 0867891234   |


  @appt-manage-config-3
  Scenario Outline: Turn on/off require patient enter the information of Pulse, Blood Pressure, Temperature for <appointmentType>
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
      | appointmentType   | onDate   | contactPhone   |
      | <appointmentType> | <onDate> | <contactPhone> |
    Then The response should be
      | success | true |

    Given I login as "manager_1"
    And I select org "BV_THAI_AN" that I manage
    When I set configuration appointment
      | attributes          |
      | IsRequireHealthInfo |
    Then The request should be succeed

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name      |
      | <patient> |

    # make appointment
    And I make an appointment to org name is "BV_THAI_AN"
      | appointmentType   | onDate   | contactPhone   |
      | <appointmentType> | <onDate> | <contactPhone> |
    Then The response should be
      | success                | false      |
      | errors.systolic        | <errorTxt> |
      | errors.diastolic       | <errorTxt> |
      | errors.bloodVessel     | <errorTxt> |
      | errors.bodyTemperature | <errorTxt> |

    Examples:
      | patient | appointmentType         | appointmentTypeTxt       | appointmentTypeTxtVN | onDate   | contactPhone | errorTxt               |
      | user_1  | TelemedicineExamination | Telemedicine Examination | Khám bệnh từ xa      | tomorrow | 0867891234   | This field is required |
