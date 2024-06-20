@regression @appt @order @appt-order
Feature: Appointment - Order

  As User
  I want view order after booking appointment
  In order to payment it

  Background:
    Given On SqlServer, I delete user by displayName "petun"
    And I re-signup "manager_1" account and update profile
    And I re-signup "DoctorAuto1" account provider and update profile
    And I re-signup "user_1" account and update profile
    And I login as "DoctorAuto1"
    And I want to add "ANESTHESIOLOGY" as my specialty
    Then The request should be succeed

    And I login as "user_1"
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | petun       | 2019-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed

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

  @appt-order-1
  Scenario Outline: make appointment for <description>
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
      | appointmentType   | onDate   | specialtyCode   | reasonExamination   | notes   | bloodVessel   | bodyTemperature   | systolic   | diastolic   | capillary   | immuGdr   | addressExamination   | homeMedicalServiceType   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <reasonExamination> | <notes> | <bloodVessel> | <bodyTemperature> | <systolic> | <diastolic> | <capillary> | <immuGdr> | <addressExamination> | <homeMedicalServiceType> | <contactPhone> |

    #  Upload appointment doc
    And As user, I upload health document appointment
      | attributes | description                       | medicalType        | docType            | createdDate | file                                |
      | 1          | <description> Immunization Record | ImmunizationRecord | ImmunizationRecord | today       | data/image/immunization/immu-01.jpg |
      | 1          | <description> Prescription doc    | Prescription       | Prescription       | today       | data/image/immunization/immu-01.jpg |
      | 1          | <description> CapillaryAppt doc   | Others             | Others             | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed


  #  insurance card and identity card to verify patient information for appointment
    And I add insurance card "09876554" and identity card "1234324" for appointment
      | attributes | description                               | medicalType | docType            | createdDate | file                                |
      | 1          | <description> front side of identity card | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 2          | <description> back side of identity card  | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 1          | <description> front side of Insurance doc | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
      | 2          | <description> back side of Insurance doc  | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

    And As user, I load orders
      | orderStatus |
      | New         |
    Then The response should be
      | success    | true |
      | data.total | 1    |

    And As user, I load order detail
      | orderStatus |
      | New         |
    And The response should contains
      | success                         | true               |
      | data.info.sourceInfo.sourceType | 8                  |
      | data.info.sourceInfo.lableTxt   | Appointment Detail |
    Examples:
      | patient | description             | appointmentType         | appointmentTypeTxt       | appointmentTypeTxtVN | onDate   | specialtyCode | reasonExamination | notes | bloodVessel | bodyTemperature | systolic | diastolic | capillary | immuGdr | addressExamination | homeMedicalServiceType | contactPhone | presDocUrls    | presDocUrlsValue                    | capillaryDocUrls    | capillaryDocUrlsValue               | immuDocUrls | immuDocUrlsValue | idCardDocUrls    | idCardDocUrlsValue                  | insuDocUrls    | insuDocUrlsValue                    |
      | user_1  | TelemedicineExamination | TelemedicineExamination | Telemedicine Examination | Khám bệnh từ xa      | tomorrow |               |                   |       | 100         | 37              | 120      | 80        |           |         |                    |                        | 0867891234   | presDocUrls[0] | data/image/immunization/immu-01.jpg | capillaryDocUrls[0] | data/image/immunization/immu-01.jpg | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |