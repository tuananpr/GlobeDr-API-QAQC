@regression @health @health-doc
Feature: Update information for health as labresult, prescription, others

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"
    When I switch main of family members with below info
      | name   |
      | user_1 |
    Then The request should be succeed

  @health-doc-1
  Scenario Outline: Add,Update, Remove health document successfully
    # Add health docs
    When As user, I upload health document
      | attributes | description   | medicalType   | docType   | createdDate | file    |
      | 1          | <description> | <medicalType> | <docType> | today       | <image> |
    Then The request should be succeed

    And I load health document
      | medicalType |
      | <type>      |
    Then The response should be
      | success            | true          |
      | <path>.attributes  | 1             |
      | <path>.description | <description> |

    # update description of health docs
    And I update health document that has type "<type>" and  description "<description>"
      | description      |
      | <newDescription> |
    Then The request should be succeed

    And I load health document
      | medicalType |
      | <type>      |
    Then The response should be
      | success            | true             |
      | <path>.attributes  | 1                |
      | <path>.description | <newDescription> |

    And I remove health document
      | medicalType | description      |
      | <type>      | <newDescription> |
    Then The request should be succeed

    # check remove successully
    And I load health document
      | medicalType |
      | <type>      |
    Then The response should be
      | success        | true            |
      | <expectedPath> | <expectedValue> |


    Examples:
      | type               | medicalType | docType | image                               | path                       | expectedPath                      | expectedValue | description                                | newDescription                                                  |
      | Prescription       | 1           | 1       | data/image/immunization/immu-01.jpg | data.list[0]               | data.list                         | []            | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |
      | LabResult          | 2           | 2       | data/image/immunization/immu-01.jpg | data.list[0]               | data.list                         | []            | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |
      | ImmunizationRecord | 3           | 3       | data/image/immunization/immu-01.jpg | data.list[0]               | data.list                         | []            | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |
      | Insurance          | 4           | 4       | data/image/immunization/immu-01.jpg | data.list[0].healthDocs[0] | data.list[0].healthDocs[0].docUrl | null          | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |
      | IdCard             | 5           | 11      | data/image/immunization/immu-01.jpg | data.list[0].healthDocs[0] | data.list[0].healthDocs[0].docUrl | null          | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |
      | Others             | 6           | 12      | data/image/immunization/immu-01.jpg | data.list[0]               | data.list                         | []            | Ket qua tiem chung vaccine phong chong HIV | Hình ảnh 1 bác sĩ đang sử dụng chức năng tiêm chủng của GlobeDr |


  @health-doc-2
  Scenario Outline: Move health document successfully

    # Add health docs
    When As user, I upload health document
      | attributes | description   | medicalType   | docType   | createdDate | file    |
      | 1          | <description> | <medicalType> | <docType> | today       | <image> |
    Then The request should be succeed

     # check move successully
    And I move health document to "<toMedicalType>"
      | medicalType | description   |
      | <type>      | <description> |
    Then The request should be succeed

    And I load health document
      | medicalType |
      | <type>      |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |

    And I load health document
      | medicalType     |
      | <toMedicalType> |
    Then The response should be
      | success                    | true          |
      | <expectedPath>.attributes  | 1             |
      | <expectedPath>.description | <description> |
    Examples:
      | type               | toMedicalType | medicalType | docType | path         | image                               | expectedPath | description                                |
      | Prescription       | LabResult     | 1           | 1       | data.list[0] | data/image/immunization/immu-01.jpg | data.list[0] | Ket qua tiem chung vaccine phong chong HIV |
      | LabResult          | Prescription  | 2           | 2       | data.list[0] | data/image/immunization/immu-01.jpg | data.list[0] | Ket qua tiem chung vaccine phong chong HIV |
      | ImmunizationRecord | LabResult     | 3           | 3       | data.list[0] | data/image/immunization/immu-01.jpg | data.list[0] | Ket qua tiem chung vaccine phong chong HIV |
      | Others             | LabResult     | 6           | 12      | data.list[0] | data/image/immunization/immu-01.jpg | data.list[0] | Ket qua tiem chung vaccine phong chong HIV |
