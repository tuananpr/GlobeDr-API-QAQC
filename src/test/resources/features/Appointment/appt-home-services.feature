@regression @appt-home-services
Feature: Appointment - Home services

  As User
  I want book appointment "Home services" to org
  In order to regular check-ups

  Background:
    Given On SqlServer, I delete user by displayName "petun"
    And I re-signup "manager_1" account
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

  @appt-home-services-1
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
      | appointmentType   | onDate   | specialtyCode   | reasonExamination   | notes   | bloodVessel   | bodyTemperature   | systolic   | diastolic   | capillary   | immuGdr   | addressExamination   | homeMedicalServiceType   | contactPhone   | country                                            | city                                | district                        | ward                                |
      | <appointmentType> | <onDate> | <specialtyCode> | <reasonExamination> | <notes> | <bloodVessel> | <bodyTemperature> | <systolic> | <diastolic> | <capillary> | <immuGdr> | <addressExamination> | <homeMedicalServiceType> | <contactPhone> | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |

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

    #  view appointment details
    And As user, I load appointment details
      | status |
      |        |
    Then The response should be
      | success                          | true                     |
      | data.appt.patient.displayName    | <patient>                |
      | data.appt.patient.fullName       | <patient>                |
      | data.appt.patient.phone          | <contactPhone>           |
      | data.appt.toName                 | BV_THAI_AN               |
      | data.appt.appointmentTypeTxt     | <appointmentTypeTxt>     |
      | data.appt.scheduleExaminationTxt | Morning: 07h30 - 11h30   |
      | data.appt.specialtyCode          | <specialtyCode>          |
      | data.appt.reasonExamination      | <reasonExamination>      |
      | data.appt.bloodVessel            | <bloodVessel>            |
      | data.appt.bodyTemperature        | <bodyTemperature>        |
      | data.appt.systolic               | <systolic>               |
      | data.appt.diastolic              | <diastolic>              |
      | data.appt.immuGdr                | <immuGdr>                |
      | data.appt.addressExamination     | <addressExamination>     |
      | data.appt.homeMedicalServiceType | <homeMedicalServiceType> |
      | data.appt.contactPhone           | <contactPhone>           |
      | data.appt.idInsurance            | 09876554                 |
      | data.appt.idCardNumber           | 1234324                  |
      | data.appt.status                 | 32                       |
      | data.appt.patient.country.name   | Việt Nam                 |
      | data.appt.patient.city.name      | Hồ Chí Minh              |
      | data.appt.patient.district.name  | Quận 3                   |

    And The image into "data.appt.<presDocUrls>" should be match "<presDocUrlsValue>"
    And The image into "data.appt.<capillaryDocUrls>" should be match "<capillaryDocUrlsValue>"
    And The image into "data.appt.<immuDocUrls>" should be match "<immuDocUrlsValue>"
    And The image into "data.appt.<idCardDocUrls>" should be match "<idCardDocUrlsValue>"
    And The image into "data.appt.<insuDocUrls>" should be match "<insuDocUrlsValue>"

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
      | data.list[0].timeType.name        | Morning              |
      | data.list[0].displayName          | user_1               |
      | data.list[0].patientName          | <patient>            |
      | data.list[0].name                 | BV_THAI_AN           |

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
      | data.info.country.name          | Việt Nam           |
      | data.info.city.name             | Hồ Chí Minh        |
      | data.info.district.name         | Quận 3             |
      | data.info.ward.name             | Phường 11          |

    # Manager load appointments
    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I load appointments
      | patientName |
      | user_1      |
    Then The response should be
      | success                             | true                     |
      | data.totalCount                     | 1                        |
      | data.list[0].patient.displayName    | <patient>                |
      | data.list[0].patient.fullName       | <patient>                |
      | data.list[0].patient.phone          | <contactPhone>           |
      | data.list[0].toName                 | BV_THAI_AN               |
      | data.list[0].appointmentTypeTxt     | <appointmentTypeTxt>     |
      | data.list[0].scheduleExaminationTxt | Morning: 07h30 - 11h30   |
      | data.list[0].specialtyCode          | <specialtyCode>          |
      | data.list[0].reasonExamination      | <reasonExamination>      |
      | data.list[0].bloodVessel            | <bloodVessel>            |
      | data.list[0].bodyTemperature        | <bodyTemperature>        |
      | data.list[0].systolic               | <systolic>               |
      | data.list[0].diastolic              | <diastolic>              |
      | data.list[0].immuGdr                | <immuGdr>                |
      | data.list[0].addressExamination     | <addressExamination>     |
      | data.list[0].homeMedicalServiceType | <homeMedicalServiceType> |
      | data.list[0].contactPhone           | <contactPhone>           |

    And The image into "data.list[0].<presDocUrls>" should be match "<presDocUrlsValue>"
    And The image into "data.list[0].<capillaryDocUrls>" should be match "<capillaryDocUrlsValue>"
    And The image into "data.list[0].<immuDocUrls>" should be match "<immuDocUrlsValue>"
    And The image into "data.list[0].<idCardDocUrls>" should be match "<idCardDocUrlsValue>"
    And The image into "data.list[0].<insuDocUrls>" should be match "<insuDocUrlsValue>"


    Examples:
      | patient | description                         | appointmentType    | appointmentTypeTxt   | appointmentTypeTxtVN | onDate   | specialtyCode | reasonExamination | notes | bloodVessel | bodyTemperature | systolic | diastolic | capillary | immuGdr | addressExamination | homeMedicalServiceType | contactPhone | presDocUrls | presDocUrlsValue | capillaryDocUrls | capillaryDocUrlsValue | immuDocUrls | immuDocUrlsValue | idCardDocUrls    | idCardDocUrlsValue                  | insuDocUrls    | insuDocUrlsValue                    |
      | user_1  | HomeMedicalService Others           | HomeMedicalService | Home Medical Service | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 255                    | 0867891234   | presDocUrls | []               | capillaryDocUrls | []                    | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |
      | user_1  | HomeMedicalService VisitExamination | HomeMedicalService | Home Medical Service | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 1                      | 0867891234   | presDocUrls | []               | capillaryDocUrls | []                    | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |
      | user_1  | HomeMedicalService GetSampleTest    | HomeMedicalService | Home Medical Service | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 2                      | 0867891234   | presDocUrls | []               | capillaryDocUrls | []                    | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |
      | user_1  | HomeMedicalService WoundCare        | HomeMedicalService | Home Medical Service | Dịch vụ y tế tại nhà | tomorrow |               |                   |       |             |                 |          |           |           |         | nguyen chi thanh   | 3                      | 0867891234   | presDocUrls | []               | capillaryDocUrls | []                    | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |
