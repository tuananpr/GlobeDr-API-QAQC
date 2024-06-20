@regression @health @health-after-visit-org
Feature: After Visit for OrgAdmin

  As org
  I want to send health record to patient

  Background: create org, patient, doctor
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "user_1" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | doctor     |
      | BV_MAT_BAO | Hospital | manager_1 | DOCTOR_TEO |

    # user join org
    And I login as "user_1"
    And I FOLLOW business
      | name       |
      | BV_MAT_BAO |

    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | user_1 | BV_MAT_BAO           | ShareOrgEdit |

    # admin add staffs
    Given I login as "DOCTOR_TEO"
    And I accept join organization

    Given I login as "manager_1"
    And I accept join organization
    And I select org "BV_MAT_BAO" that I manage


  @health-after-visit-org-1
  Scenario Outline: orgAdmin send After Visit to existed main account
    # orgAdmin send After visit
    And As org, I send After Visit to member name "<displayName>"
      | visitDate | doctorName   | issuesObservationDescription | visitSubclinicalDescription | medicationDescription | instructionDescription | followUpDescription | height   | weight   | bmi   | head   | bloodPressure   | pulse   | respiration   | physicalType   | chiefComplaintVisit |
      | today     | <doctorName> | <issuesObs>                  | <visitSub>                  | <medicationDes>       | <instructionDes>       | <followUpDes>       | <height> | <weight> | <bmi> | <head> | <bloodPressure> | <pulse> | <respiration> | <physicalType> | <chiefComplaint>    |
    And The request should be succeed

    And As manager, I load After Visit of member name "<displayName>"
    Then The response should be
      | success                                                              | true             |
      | data.list[0].visitData.visitVital.providers[0].name                  | <doctorName>     |
      | data.list[0].visitData.visitVital.chiefComplaint.chiefComplaintVisit | <chiefComplaint> |
      | data.list[0].visitData.visitVital.vital.height                       | <height>         |
      | data.list[0].visitData.visitVital.vital.weight                       | <weight>         |
      | data.list[0].visitData.visitVital.vital.bmi                          | <bmi>            |
      | data.list[0].visitData.visitVital.vital.head                         | <head>           |
      | data.list[0].visitData.visitVital.vital.bloodPressure                | <bloodPressure>  |
      | data.list[0].visitData.visitVital.vital.pulse                        | <pulse>          |
      | data.list[0].visitData.visitVital.vital.respiration                  | <respiration>    |
      | data.list[0].visitData.visitVital.vital.physicalType                 | <physicalType>   |
      | data.list[0].visitData.issuesObservation.description                 | <issuesObs>      |
      | data.list[0].visitData.visitSubclinical.description                  | <visitSub>       |
      | data.list[0].visitData.medication.description                        | <medicationDes>  |
      | data.list[0].visitData.instruction.description                       | <instructionDes> |
      | data.list[0].visitData.followUp.description                          | <followUpDes>    |

    Given I login as "user_1"
    When I switch main of family members with below info
      | name   |
      | user_1 |
    And I load visit medical
    Then The response should be
      | success           | true       |
      | data.totalCount   | 1          |
      | data.list[0].name | BV_MAT_BAO |
    When I load visit medical details
    Then The response should be
      | success                                      | true             |
      | data[1].issuesObservation[0].description     | <issuesObs>      |
      | data[2].visitSubclinical[0].description      | <visitSub>       |
      | data[4].medication[0].description            | <medicationDes>  |
      | data[3].instruction[0].description           | <instructionDes> |
      | data[5].followUp[0].description              | <followUpDes>    |
      | data[0].visitVital[2].vital[0].height        | <height>         |
      | data[0].visitVital[2].vital[1].weight        | <weight>         |
      | data[0].visitVital[2].vital[3].bmi           | <bmi>            |
      | data[0].visitVital[2].vital[2].head          | <head>           |
      | data[0].visitVital[2].vital[4].bloodPressure | <bloodPressure>  |
      | data[0].visitVital[2].vital[5].pulse         | <pulse>          |
      | data[0].visitVital[2].vital[6].respiration   | <respiration>    |
      | data[0].visitVital[2].vital[7].physicalType  | <physicalType>   |
      | data[0].visitVital[0].providers[0].name      | <doctorName>     |
      | data[0].visitVital[1].chiefComplaintVisit    | <chiefComplaint> |


    And I load notifications
      | groupType  |
      | HealthNoti |
    Then List "data.list[*].message" of response should contains
      | Update after visit summary |

    And I open noti after visit
      | groupType  | message                    |
      | HealthNoti | Update after visit summary |
    Then The response should be
      | success                                      | true             |
      | data[1].issuesObservation[0].description     | <issuesObs>      |
      | data[2].visitSubclinical[0].description      | <visitSub>       |
      | data[4].medication[0].description            | <medicationDes>  |
      | data[3].instruction[0].description           | <instructionDes> |
      | data[5].followUp[0].description              | <followUpDes>    |
      | data[0].visitVital[2].vital[0].height        | <height>         |
      | data[0].visitVital[2].vital[1].weight        | <weight>         |
      | data[0].visitVital[2].vital[3].bmi           | <bmi>            |
      | data[0].visitVital[2].vital[2].head          | <head>           |
      | data[0].visitVital[2].vital[4].bloodPressure | <bloodPressure>  |
      | data[0].visitVital[2].vital[5].pulse         | <pulse>          |
      | data[0].visitVital[2].vital[6].respiration   | <respiration>    |
      | data[0].visitVital[2].vital[7].physicalType  | <physicalType>   |
      | data[0].visitVital[0].providers[0].name      | <doctorName>     |
      | data[0].visitVital[1].chiefComplaintVisit    | <chiefComplaint> |

    Examples:
      | displayName | chiefComplaint | issuesObs      | visitSub           | medicationDes | instructionDes | followUpDes    | height | weight | bmi   | head     | bloodPressure | pulse | respiration | physicalType | doctorName |
      | user_1      | binh thuong    | mo ta quan sát | mo ta cận lâm sàng | mo ta thuốc   | mo ta chỉ dẫn  | mo ta theo sát | 165    | 65     | 23.88 | 38.85417 | 120           | 130   | 2           | 1            | DOCTOR_TEO |


  @health-after-visit-org-2
  Scenario Outline: orgAdmin send After Visit with docs
    And As org, I send After Visit to member name "<displayName>"
      | visitDate | height   | weight   | bmi   | head   |
      | today     | <height> | <weight> | <bmi> | <head> |
    And The request should be succeed

    And As org, I upload After Visit docs to member name "<displayName>"
      | createDate | description   | note   | file   | docType   | attributes | MedicalType |
      | today      | <description> | <note> | <file> | <docType> | 0          | ClinicVisit |
    And The request should be succeed

    And As manager, I load Visit Medical of member name "<displayName>"
    Then The response should be
      | success           | true          |
      | <key>.description | <description> |
    And The image into "<key>.docUrl" should be match "<file>"
    Examples:
      | displayName | height | weight | bmi   | head     | doctorName | note           | description | file                                | docType          | key                                  |
      | user_1      | 165    | 65     | 23.88 | 38.85417 | DOCTOR_TEO | chu thich them | mo ta them  | data/image/immunization/immu-01.jpg | VisitObservation | data[1].issuesObservation[1].docs[0] |
      | user_1      | 165    | 65     | 23.88 | 38.85417 | DOCTOR_TEO | chu thich them | mo ta them  | data/image/immunization/immu-01.jpg | LabResult        | data[2].visitSubclinical[1].docs[0]  |
      | user_1      | 165    | 65     | 23.88 | 38.85417 | DOCTOR_TEO | chu thich them | mo ta them  | data/image/immunization/immu-01.jpg | Prescription     | data[4].medication[1].docs[0]        |
      | user_1      | 165    | 65     | 23.88 | 38.85417 | DOCTOR_TEO | chu thich them | mo ta them  | data/image/immunization/immu-01.jpg | VisitInstruction | data[3].instruction[1].docs[0]       |


  @health-after-visit-org-3
  Scenario Outline: orgAdmin send After Visit to sub-account
    Given On SqlServer, I delete user by displayName "<displayName>"
    # user create sub account
    And I login as "user_1"
    When I add new sub-account
      | displayName   | dob                     | carerType |  gender |  country                                               | city                                  | district                        | ward                                |
      | <displayName> | 2010-09-30T00:00:00.000 | 2         |  1      |  {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed

    # user share sub account to org
    And I share my sub-account to org
      | name          | sharedConnectionName | sharedType   |
      | <displayName> | BV_MAT_BAO           | ShareOrgEdit |

    # orgAdmin send After visit to sub account
    Given I login as "manager_1"
    And I select org "BV_MAT_BAO" that I manage
        # orgAdmin send After visit
    And As org, I send After Visit to member name "<displayName>"
      | visitDate | doctorName   | issuesObservationDescription | visitSubclinicalDescription | medicationDescription | instructionDescription | followUpDescription | height   | weight   | bmi   | head   | bloodPressure   | pulse   | respiration   | physicalType   | chiefComplaintVisit |
      | today     | <doctorName> | <issuesObs>                  | <visitSub>                  | <medicationDes>       | <instructionDes>       | <followUpDes>       | <height> | <weight> | <bmi> | <head> | <bloodPressure> | <pulse> | <respiration> | <physicalType> | <chiefComplaint>    |
    And The request should be succeed


    # sub-account load visit medical
    Given I login as "user_1"
    When I switch sub-account of family members with below info
      | name          |
      | <displayName> |

    And I load visit medical
    Then The response should be
      | success           | true       |
      | data.totalCount   | 1          |
      | data.list[0].name | BV_MAT_BAO |
    When I load visit medical details
    Then The response should be
      | success                                      | true             |
      | data[1].issuesObservation[0].description     | <issuesObs>      |
      | data[2].visitSubclinical[0].description      | <visitSub>       |
      | data[4].medication[0].description            | <medicationDes>  |
      | data[3].instruction[0].description           | <instructionDes> |
      | data[5].followUp[0].description              | <followUpDes>    |
      | data[0].visitVital[2].vital[0].height        | <height>         |
      | data[0].visitVital[2].vital[1].weight        | <weight>         |
      | data[0].visitVital[2].vital[3].bmi           | <bmi>            |
      | data[0].visitVital[2].vital[2].head          | <head>           |
      | data[0].visitVital[2].vital[4].bloodPressure | <bloodPressure>  |
      | data[0].visitVital[2].vital[5].pulse         | <pulse>          |
      | data[0].visitVital[2].vital[6].respiration   | <respiration>    |
      | data[0].visitVital[2].vital[7].physicalType  | <physicalType>   |
      | data[0].visitVital[0].providers[0].name      | <doctorName>     |
      | data[0].visitVital[1].chiefComplaintVisit    | <chiefComplaint> |

    Examples:
      | displayName | chiefComplaint | issuesObs      | visitSub           | medicationDes | instructionDes | followUpDes    | height | weight | bmi   | head     | bloodPressure | pulse | respiration | physicalType | doctorName |
      | SUB_user_1  | binh thuong    | mo ta quan sát | mo ta cận lâm sàng | mo ta thuốc   | mo ta chỉ dẫn  | mo ta theo sát | 165    | 65     | 23.88 | 38.85417 | 120           | 130   | 2           | 1            | DOCTOR_TEO |


  @health-after-visit-org-4
  Scenario Outline: orgAdmin update After Visit
    # orgAdmin send After visit
    And As org, I send After Visit to member name "<displayName>"
      | visitDate | height   | weight   | bmi   | head   | bloodPressure   |
      | today     | <height> | <weight> | <bmi> | <head> | <bloodPressure> |
    And The request should be succeed


    And As org, I update After Visit to member name "<displayName>"
      | visitDate | doctorName   | issuesObservationDescription | visitSubclinicalDescription | medicationDescription | instructionDescription | followUpDescription | height   | weight   | bmi   | head   | bloodPressure   | pulse   | respiration   | physicalType   | chiefComplaintVisit |
      | today     | <doctorName> | <issuesObs>                  | <visitSub>                  | <medicationDes>       | <instructionDes>       | <followUpDes>       | <height> | <weight> | <bmi> | <head> | <bloodPressure> | <pulse> | <respiration> | <physicalType> | <chiefComplaint>    |
    And The request should be succeed

    And As manager, I load After Visit of member name "<displayName>"
    Then The response should be
      | success                                                              | true             |
      | data.list[0].visitData.visitVital.providers[0].name                  | <doctorName>     |
      | data.list[0].visitData.visitVital.chiefComplaint.chiefComplaintVisit | <chiefComplaint> |
      | data.list[0].visitData.visitVital.vital.height                       | <height>         |
      | data.list[0].visitData.visitVital.vital.weight                       | <weight>         |
      | data.list[0].visitData.visitVital.vital.bmi                          | <bmi>            |
      | data.list[0].visitData.visitVital.vital.head                         | <head>           |
      | data.list[0].visitData.visitVital.vital.bloodPressure                | <bloodPressure>  |
      | data.list[0].visitData.visitVital.vital.pulse                        | <pulse>          |
      | data.list[0].visitData.visitVital.vital.respiration                  | <respiration>    |
      | data.list[0].visitData.visitVital.vital.physicalType                 | <physicalType>   |
      | data.list[0].visitData.issuesObservation.description                 | <issuesObs>      |
      | data.list[0].visitData.visitSubclinical.description                  | <visitSub>       |
      | data.list[0].visitData.medication.description                        | <medicationDes>  |
      | data.list[0].visitData.instruction.description                       | <instructionDes> |
      | data.list[0].visitData.followUp.description                          | <followUpDes>    |

    Given I login as "user_1"
    When I switch main of family members with below info
      | name   |
      | user_1 |
    And I load visit medical
    Then The response should be
      | success           | true       |
      | data.totalCount   | 1          |
      | data.list[0].name | BV_MAT_BAO |
    When I load visit medical details
    Then The response should be
      | success                                      | true             |
      | data[1].issuesObservation[0].description     | <issuesObs>      |
      | data[2].visitSubclinical[0].description      | <visitSub>       |
      | data[4].medication[0].description            | <medicationDes>  |
      | data[3].instruction[0].description           | <instructionDes> |
      | data[5].followUp[0].description              | <followUpDes>    |
      | data[0].visitVital[2].vital[0].height        | <height>         |
      | data[0].visitVital[2].vital[1].weight        | <weight>         |
      | data[0].visitVital[2].vital[3].bmi           | <bmi>            |
      | data[0].visitVital[2].vital[2].head          | <head>           |
      | data[0].visitVital[2].vital[4].bloodPressure | <bloodPressure>  |
      | data[0].visitVital[2].vital[5].pulse         | <pulse>          |
      | data[0].visitVital[2].vital[6].respiration   | <respiration>    |
      | data[0].visitVital[2].vital[7].physicalType  | <physicalType>   |
      | data[0].visitVital[0].providers[0].name      | <doctorName>     |
      | data[0].visitVital[1].chiefComplaintVisit    | <chiefComplaint> |
    Examples:
      | displayName | chiefComplaint | issuesObs      | visitSub           | medicationDes | instructionDes | followUpDes    | height | weight | bmi   | head     | bloodPressure | pulse | respiration | physicalType | doctorName |
      | user_1      | binh thuong    | mo ta quan sát | mo ta cận lâm sàng | mo ta thuốc   | mo ta chỉ dẫn  | mo ta theo sát | 165    | 65     | 23.88 | 38.85417 | 120           | 130   | 2           | 1            | DOCTOR_TEO |
