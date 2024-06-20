@regression @appt @appt-payment
Feature: Appointment - Payment


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

    And As sysAdmin, I set attributes for org name "BV_THAI_AN"
      | JoinedGdr     |
      | EnablePayment |
    And The request should be succeed

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
      | Khám lần đầu | First time examination | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      |
    And The request should be succeed

  @appt-payment-1
  Scenario Outline: user should see payment into apt details
    Given I login as "user_1"
    When I switch <account> of logged user with below info
      | name      |
      | <patient> |

    # make appointment
    And I make an appointment to org name is "BV_THAI_AN" and doctor name is "DoctorAuto1"
      | appointmentType   | onDate   | specialtyCode   | contactPhone   |
      | <appointmentType> | <onDate> | <specialtyCode> | <contactPhone> |

    #  Upload appointment doc
    And As user, I upload health document appointment
      | attributes | description         | medicalType        | docType            | createdDate | file                                |
      | 1          | Immunization Record | ImmunizationRecord | ImmunizationRecord | today       | data/image/immunization/immu-01.jpg |
      | 1          | Prescription doc    | Prescription       | Prescription       | today       | data/image/immunization/immu-01.jpg |
      | 1          | CapillaryAppt doc   | Others             | Others             | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

    And I add insurance card "09876554" and identity card "1234324" for appointment
      | attributes | description                 | medicalType | docType            | createdDate | file                                |
      | 1          | front side of identity card | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of identity card  | IdCard      | PassPort           | today       | data/image/immunization/immu-01.jpg |
      | 1          | front side of Insurance doc | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
      | 2          | back side of Insurance doc  | Insurance   | MedicalSupplement1 | today       | data/image/immunization/immu-01.jpg |
    Then The request should be succeed

    #  user should see payment into apt details
    And As user, I load appointment details
      | status |
      |        |
    Then The response should be
      | success                | true |
      | data.appt.allowPayment | true |

    Examples:
      | account | patient | description          | appointmentType      | appointmentTypeTxt     | onDate   | specialtyCode | contactPhone | presDocUrls | presDocUrlsValue | capillaryDocUrls | capillaryDocUrlsValue | immuDocUrls | immuDocUrlsValue | idCardDocUrls    | idCardDocUrlsValue                  | insuDocUrls    | insuDocUrlsValue                    |
      | main    | user_1  | FirstTimeExamination | FirstTimeExamination | First Time Examination | tomorrow | 01            | 0867891234   | presDocUrls | []               | capillaryDocUrls | []                    | immuDocUrls | []               | idCardDocUrls[0] | data/image/immunization/immu-01.jpg | insuDocUrls[0] | data/image/immunization/immu-01.jpg |

