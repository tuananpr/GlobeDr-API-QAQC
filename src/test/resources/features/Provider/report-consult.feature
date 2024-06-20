@regression @consultant @report-consult @provider-report
Feature: Provider : report consultant


  Background: user ask question then doctor answer
    And I re-signup "user_1" account and update profile
    And I re-signup "DOCTOR_TEO" account provider and update profile
    And I login as "user_1"
    And I create a question to GlobeDr with below info
      | msg                                                            | height | weight |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | 130    | 30     |
    And The request should be succeed
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed

    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed

    When As doctor, I add comment "Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé" into consult
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed

    And As doctor, I complete questions
      | textSearch                                                     |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? |
    And The request should be succeed

    When I login as "coordinator_auto"

  @report-consult-1
  Scenario: doctor load report and log fees of consultant when coordinator close question with payment

    And As coordinator, I close questions
      | textSearch                                                     | isPayment4Doctor |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | true             |
    Then The request should be succeed

    And I login as "DOCTOR_TEO"
    And As doctor, I load report by org
    Then The response should be
      | success           | true    |
      | data.list[0].name | GlobeDr |
    And As doctor, I load report fees
      | fromDate  | toDate   | reportFeesType   |
      | yesterday | tomorrow | NormalConsultant |
    Then The response should be
      | success                            | true                                  |
      | data.list[0].sumFees               | 10000.00000 VNĐ                       |
      | data.list[0].reportFeesTypeNameTxt | The number of answered text questions |
      | data.totalFees                     | 10000.00000 VNĐ                       |

    And As doctor, I load log fees
      | fromDate  | toDate   | reportFeesType   |
      | yesterday | tomorrow | NormalConsultant |
    Then The response should be
      | success                         | true              |
      | data.list[0].fees               | + 10000.00000 VNĐ |
      | data.list[0].orgName            | GlobeDr           |
      | data.list[0].patientName        | user_1            |
      | data.list[0].reportFeesTypeName | TEXT CONSULTANT   |


  @report-consult-2
  Scenario: doctor load report and log fees of consultant when coordinator close question without payment

    And As coordinator, I close questions
      | textSearch                                                     | isPayment4Doctor |
      | [Auto]Làm sao để khắc phục tình trạng viêm dạ dày vay bac si ? | false            |
    Then The request should be succeed

    And I login as "DOCTOR_TEO"
    And As doctor, I load report by org
    Then The response should be
      | success   | true |
      | data.list | []   |
    And As doctor, I load report fees
      | fromDate  | toDate   | reportFeesType   |
      | yesterday | tomorrow | NormalConsultant |
    Then The response should be
      | success        | true       |
      | data.list      | []         |
      | data.totalFees | .00000 VNĐ |

    And As doctor, I load log fees
      | fromDate  | toDate   | reportFeesType   |
      | yesterday | tomorrow | NormalConsultant |
    Then The response should be
      | success   | true |
      | data.list | []   |

