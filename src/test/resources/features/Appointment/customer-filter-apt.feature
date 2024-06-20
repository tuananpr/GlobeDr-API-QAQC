@regression @appt @appt-chat @customer-care @customer-filter @customer-filter-apt @chat
Feature: Appointment - Customer care filter chat

  Background:
    And I re-signup "manager_1" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "cskh_ly" account
    And I re-signup "DoctorAuto1" account provider and update profile
    And I login as "DoctorAuto1"
    And I want to add "ANESTHESIOLOGY" as my specialty
    Then The request should be succeed

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | specialties    | doctorTelemedicine |
      | BV_THAI_AN | Hospital | manager_1 | ANESTHESIOLOGY | DoctorAuto1        |

    And As sysAdmin, I setting customer care
      | name       | maxCustomerCare |
      | BV_THAI_AN | 2               |
    Then The request should be succeed

    When I change appointment type of org
      | appointmentType  |
      | OnlineConsultant |
    And The request should be succeed

    And I login as "DoctorAuto1"
    When I accept join organization

    Given I login as "manager_1"
    When I accept join organization
    And I select org "BV_THAI_AN" that I manage

    And As manager, I create appointment schedule with type "OnlineConsultant" for doctor "DoctorAuto1"
      | specialtyCode | roomId | roomName | fromDate | toDate      |
      | AN            | A      | B        | today    | next 7 days |
    And The request should be succeed

     # Update price for appointment
    When I update to appointment service has name "Tư vấn trực tuyến"
      | name              | NameEn            | description | orgProductType | fromPrice | currency | isInternal | isPublic | isVisible |
      | Tư vấn trực tuyến | Online Consultant | mô tả       | AppointmentOrg | 130       | VND      | true       | true     | true      |
    And The request should be succeed

    When On org, I add staff
      | displayName | country | staffAttributes |
      | cskh_ly     | VN      | CustomerCare    |
    Then The request should be succeed
    And I set features for staff
      | featureAttributes                                              | displayName |
      | Appointment,Order,ChatInbox,OrderMedicalTest,OrderPrescription | cskh_ly     |
    Then The request should be succeed

    Given I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    Then The request should be succeed
    # make appointment
    And I make an appointment to org name is "BV_THAI_AN"
      | appointmentType  | onDate   | bloodVessel | bodyTemperature | systolic | diastolic | capillary | contactPhone |
      | OnlineConsultant | tomorrow | 120         | 37              | 140      | 100       | 90        | 0867891234   |

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


  @customer-filter-apt-1
  Scenario Outline: Customer care filter chat from appointment
    Given I login as "user_1"
    And As user, I create conversation to appointment with content "<msg>"
      | name       |
      | BV_THAI_AN |
    And The response should be
      | success | true |

    And I login as "cskh_ly"
    And I accept join organization
    And I select org "BV_THAI_AN" that I manage
    #Type is Appointment
    And As customer care, I load conversations
      | type        |
      | Appointment |
    Then The response should be
      | success                  | true    |
      | data.list[0].name        | <title> |
      | data.list[0].lastMsgText | <msg>   |
    #Type is all
    And As customer care, I load conversations
      | type |
      |      |
    Then The response should be
      | success                  | true    |
      | data.list[0].name        | <title> |
      | data.list[0].lastMsgText | <msg>   |
    Examples:
      | msg      | title                |
      | chào bạn | user_1 - Appointment |