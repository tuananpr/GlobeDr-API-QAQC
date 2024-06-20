@regression @consultant @report-consult @provider-report
Feature: Provider : report medical test

  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "coordinator_auto" account coordinator and update profile
    And I re-signup "DoctorAuto1" account provider and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type       | owner     |
      | BV_MY_THANH | LabAndTest | manager_2 |

    When I login as "manager_2"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

    And I create product category with below info
      | status | nameVI   | nameEN |
      | Active | Suc Khoe | Health |
    Then The request should be succeed

    # Create product services
    When I create new product
      | name              | description             | orgProductType | link                  | isInternal | isPublic | isVisible |
      | Vật lý trị liệu   | mô tả vật lý trị liệu   | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
      | Dịch vụ khám răng | mô tả Dịch vụ khám răng | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
    Then The request should be succeed

    And I create new item for product name "Vật lý trị liệu"
      | currency | currencyName | description             | name              | price   | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 | 10                | 10                 | 10          |
      | 2        | VND          | mô tả điều trị giãn cơ  | Điều trị giãn cơ  | 1500000 | 10                | 10                 | 10          |
    Then The request should be succeed

    And I create new item for product name "Dịch vụ khám răng"
      | currency | currencyName | description             | name      | price   | feesDoctorConsult | feesGlobeDrConsult | feesGlobeDr |
      | 2        | VND          | mô tả dịch vụ trám răng | Trám răng | 1500000 | 10                | 10                 | 10          |
    Then The request should be succeed

    # User create question
    When I login as "user_1"
    When I switch main of logged user with below info
      | name   |
      | user_1 |
    Then The request should be succeed

    And I create a question to GlobeDr with below info
      | msg                                                                      | height | weight |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |
    Then The request should be succeed
    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DoctorAuto1"
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    Then The request should be succeed
    #DOCTOR LOGIN
    When I login as "DoctorAuto1"
    And As doctor, I accept questions
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    Then The request should be succeed
    And As doctor, I get actions into consult
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    And The response should be
      | success                          | true |
      | data.actions.indicateMedicalTest | true |


  @report-medical-test-1
  Scenario Outline:  doctor load report and log fees of medical test

    And As doctor, I suggest medical test for question "<msg>"
      | orgName   | deliveryType   | address   | phone          | productAndService                                                              |
      | <orgName> | <deliveryType> | <address> | <contactPhone> | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed

    # user update suggest medical test
    When I login as "user_1"
    And As user, I update suggested medical test
      | orgName   | deliveryType   | address   | country                                            | city                                | district                        | ward                                | phone          |
      | <orgName> | <deliveryType> | <address> | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | <contactPhone> |
    Then The request should be succeed

    #org load medical test then upload result tests
    When I login as "manager_2"
    And I select org "<orgName>" that I manage
    # org taken sample from patient
    And As org, I update medical test for name "user_1"
      | orderProcessType |
      | TakenSample      |
    And The request should be succeed

    When As org, I upload medical test for name "user_1"
      | file                                | isResult |
      | data/image/avatar/phan-van-tien.jpg | true     |
      | data/pdf-test.pdf                   | true     |
    And The request should be succeed

    And I login as "DoctorAuto1"
    And As doctor, I load report by org
    Then The response should be
      | success           | true        |
      | data.list[0].name | BV_MY_THANH |
    And As doctor, I load report fees
      | fromDate  | toDate   | reportFeesType      |
      | yesterday | tomorrow | IndicateMedicalTest |
    Then The response should be
      | success                            | true                                 |
      | data.list[0].sumFees               | 450000.00000 VNĐ                     |
      | data.list[0].reportFeesTypeNameTxt | The number of indicated medical test |
      | data.totalFees                     | 450000.00000 VNĐ                     |

    And As doctor, I load log fees
      | fromDate  | toDate   | reportFeesType      |
      | yesterday | tomorrow | IndicateMedicalTest |
    Then The response should be
      | success                         | true               |
      | data.list[0].fees               | + 450000.00000 VNĐ |
      | data.list[0].orgName            | BV_MY_THANH        |
      | data.list[0].patientName        | user_1             |
      | data.list[0].reportFeesTypeName | MEDICAL TEST       |

    Examples:
      | orgName     | notes                    | address                   | contactPhone | phone      | deliveryType | type | typeName     | msg                                                                      |
      | BV_MY_THANH | lấy mẫu thử với giá 200đ | 230 cong hoa tan binh hcm | 089999999    | 0347001005 | 4            | 4    | Medical Test | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |



