@regression @appt @appt-qr-code @qr-code
Feature: Appointment - QR code

  As User
  I want to QRCode displayed on my appointment
  In order to Manager/doctor view appointment details by scanning

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
    And I set features for staff
      | featureAttributes | displayName |
      | QRCode            | DoctorAuto1 |

    And I login as "DoctorAuto1"
    When I accept join organization
    Then The request should be succeed

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
      | appointmentType  | onDate   | bloodVessel | bodyTemperature | systolic | diastolic | capillary | contactPhone | companyName | companyAddress | companyTax |
      | OnlineConsultant | tomorrow | 120         | 37              | 140      | 100       | 90        | 0867891234   | GlobeDr     | 5B Phổ Quang   | 75315980   |

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
    And I save appointment qrcode

  @appt-qr-code-1-a
  Scenario: User scan appointment QRCode
    And As user, I scan above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |

  @appt-qr-code-1-b
  Scenario: Other can't scan appointment QRCode
    Given I login as "user_2"
    And As user, I scan above QRCode
    Then The response should contains
      | success | false |


  @appt-qr-code-2-a
  Scenario: Manage scan appointment QRCode
    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I scan above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |

  @appt-qr-code-2-b
  Scenario: Other manager org  can't scan appointment QRCode
    And I re-signup "manager_2" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name   | type     | owner     |
      | DAI_LY | Hospital | manager_2 |

    When I login as "manager_2"
    And I accept join organization
    And I select org "DAI_LY" that I manage
    And As manager, I scan above QRCode
    Then The response should contains
      | success              | false                                       |
      | message              | You are not authorized to use this function |
      | data.info.qrCodeType | 5                                           |
      | data.info.msg        | You are not authorized to use this function |

  @appt-qr-code-3
  Scenario: doctor only scan QRCode from assiged appointment

    Given I login as "manager_1"
    When I select org "BV_THAI_AN" that I manage
    And As manager, I choose a below appointment and assign to doctor "DoctorAuto1"
      | patientName |
      | user_1      |
    Then The response should be
      | success | true |

    Given I login as "DoctorAuto1"
    When I select org "BV_THAI_AN" that I manage
    And As doctor, I scan above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |


  @appt-qr-code-4
  Scenario: load information appointment from above qrcode
    When I login as "manager_1"
    And I select org "BV_THAI_AN" that I manage
    And As manager, I load info from above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |

    And I login as "user_2"
    And As user, I load info from above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |

    And As guest, I load info from above QRCode
    Then The response should contains
      | success              | true |
      | data.info.qrCodeType | 5    |
    And Appointment information into QRcode should be
      | Patient.DisplayName  | user_1       |
      | Order.CompanyName    | GlobeDr      |
      | Order.CompanyAddress | 5B Phổ Quang |
      | Order.CompanyTax     | 75315980     |
