@regression @consultant @medical-test-doctor @medical-test
Feature: Medicine Test - Doctor

  As Doctor
  I want suggest medical test to patient when consulting


  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "coordinator_auto" account coordinator and update profile
    And I re-signup "DOCTOR_TEO" account provider and update profile

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
      | currency | currencyName | description             | name              | price   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 |
      | 2        | VND          | mô tả điều trị giãn cơ  | Điều trị giãn cơ  | 1500000 |
    Then The request should be succeed

    And I create new item for product name "Dịch vụ khám răng"
      | currency | currencyName | description             | name      | price   |
      | 2        | VND          | mô tả dịch vụ trám răng | Trám răng | 1500000 |
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
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    Then The request should be succeed
    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
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


  @medical-test-doctor-1
  Scenario Outline:  Doctor suggest medical test
    And As doctor, I suggest medical test for question "<msg>"
      | orgName   | deliveryType   | address   | phone          | productAndService                                                              |
      | <orgName> | <deliveryType> | <address> | <contactPhone> | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    And The response should be
      | success                           | true             |
      | data.info.userInfo.displayName    | user_1           |
      | data.info.patientInfo.patientName | user_1           |
      | data.info.doctorInfo.doctorName   | DOCTOR_TEO       |
      | data.info.personOrgInfo.name      | <orgName>        |
      | data.info.type.type               | <type>           |
      | data.info.type.name               | <typeName>       |
      | data.info.status.status           | 2048             |
      | data.info.status.name             | Doctor indicated |

    When I login as "user_1"
    When I get totals doctor indicated
    And The response should be
      | success    | true |
      | data.total | 1    |

    # user view suggest medical test
    And As user, I load comment of question
      | msg   |
      | <msg> |
    And The response should be
      | success          | true                                                                                                                                                                                                                                |
      | data.list[4].msg | <font color='#39B44A'>DOCTOR_TEO</font> has sent <font color='#39B44A'>user_1</font> a test request                                                                                                                                 |
      | data.list[5].msg | <p>&nbsp;1.&nbsp;Vật lý trị liệu</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Điều trị cột sống</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Điều trị giãn cơ</p><p>&nbsp;2.&nbsp;Dịch vụ khám răng</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trám răng</p> |
    And As user, I load order detail
      | orderType   | isDoctorIndicated |
      | MedicalTest | true              |
    And The response should be
      | success                           | true             |
      | data.info.userInfo.displayName    | user_1           |
      | data.info.patientInfo.patientName | user_1           |
      | data.info.doctorInfo.doctorName   | DOCTOR_TEO       |
      | data.info.personOrgInfo.name      | <orgName>        |
      | data.info.type.type               | <type>           |
      | data.info.type.name               | <typeName>       |
      | data.info.status.name             | Doctor indicated |
    Examples:
      | orgName     | notes                    | address                   | contactPhone | phone      | deliveryType | type | typeName     | msg                                                                      |
      | BV_MY_THANH | lấy mẫu thử với giá 200đ | 230 cong hoa tan binh hcm | 089999999    | 0347001005 | 4            | 4    | Medical Test | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @medical-test-doctor-2
  Scenario Outline:  patient update suggested medical test -> orgAdmin upload result tests to patient

    And As doctor, I suggest medical test for question "<msg>"
      | orgName   | deliveryType   | address   | phone          | productAndService                                                              |
      | <orgName> | <deliveryType> | <address> | <contactPhone> | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed

    # user update suggest medical test
    When I login as "user_1"
    And As user, I update suggested medical test
      | orgName   | deliveryType   | address   | country                                            | city                                | district                        | ward                                | phone          |
      | <orgName> | <deliveryType> | <address> | {"country":"VN","name":"Việt Nam","postCode":"84"} | {"code":"HCM","name":"Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | <contactPhone> |
    And The response should be
      | success                            | true                    |
      | data.info.userInfo.displayName     | user_1                  |
      | data.info.userInfo.userPhone       | <phone>                 |
      | data.info.patientInfo.patientName  | user_1                  |
      | data.info.patientInfo.patientDOB   | 1969-09-11t00:00:00.000 |
      | data.info.patientInfo.patientPhone | <phone>                 |
      | data.info.doctorInfo.doctorName    | DOCTOR_TEO              |
      | data.info.personOrgInfo.name       | <orgName>               |
      | data.info.type.type                | 4                       |
      | data.info.type.name                | Medical Test            |
      | data.info.status.status            | 2                       |
      | data.info.status.name              | New                     |
      | data.info.deliveryType             | <deliveryType>          |
      | data.info.address                  | <address>               |
      | data.info.country.name             | Việt Nam                |
      | data.info.city.name                | Hồ Chí Minh             |
      | data.info.district.name            | Quận 3                  |
      | data.info.ward.name                | Phường 11               |

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

    # user view result of medical test
    When I login as "user_1"
    And As user, I load comment of question
      | msg   |
      | <msg> |
    And The response should contains
      | success                                | true                                                                                                                                               |
      | data.list[5].order.lableText           | Ordered a medical test                                                                                                                             |
      | data.list[6].order.lableText           | View a result medical test                                                                                                                         |
      | data.list[6].msg                       | Test results have been <font color='#39B44A'><orgName></font> update to conversation and in Health Document of <font color='#39B44A'>user_1</font> |
      | data.list[6].order.docs[0].description | phan-van-tien                                                                                                                                      |
      | data.list[6].order.docs[1].description | pdf-test                                                                                                                                           |
    And The image into "data.list[6].order.docs[0].url" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.list[6].order.docs[1].url" should be match "data/pdf-test.pdf"

    And As user, I load order detail
      | orderType   |
      | MedicalTest |
    And The response should be
      | success                            | true           |
      | data.info.userInfo.displayName     | user_1         |
      | data.info.patientInfo.patientName  | user_1         |
      | data.info.doctorInfo.doctorName    | DOCTOR_TEO     |
      | data.info.personOrgInfo.name       | <orgName>      |
      | data.info.type.name                | Medical Test   |
      | data.info.status.name              | Result         |
      | data.info.userInfo.userPhone       | <phone>        |
      | data.info.patientInfo.patientPhone | <phone>        |
      | data.info.deliveryType             | <deliveryType> |
      | data.info.address                  | <address>      |
      | data.info.country.name             | Việt Nam       |
      | data.info.city.name                | Hồ Chí Minh    |
      | data.info.district.name            | Quận 3         |
      | data.info.ward.name                | Phường 11      |
      | data.info.contactPhone             | <contactPhone> |
      | data.info.docs[0].description      | phan-van-tien  |
      | data.info.docs[1].description      | pdf-test       |
    And The image into "data.info.docs[0].url" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.info.docs[1].url" should be match "data/pdf-test.pdf"

    Examples:
      | orgName     | notes                    | address                   | contactPhone | phone      | deliveryType | type | typeName     | msg                                                                      |
      | BV_MY_THANH | lấy mẫu thử với giá 200đ | 230 cong hoa tan binh hcm | 089999999    | 0347001005 | 4            | 4    | Medical Test | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |


  @medical-test-doctor-3
  Scenario Outline:  patient cancel suggested medical test
    And As doctor, I suggest medical test for question "<msg>"
      | orgName   | deliveryType   | address   | phone          | productAndService                                                              |
      | <orgName> | <deliveryType> | <address> | <contactPhone> | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    And The request should be succeed
    When I login as "user_1"
    And As user, I update medical test for name "user_1"
      | orderProcessType |
      | CancelIndicate   |
    And The request should be succeed

    When As user, I load orders
      | orderType   | isDoctorIndicated |
      | MedicalTest | true              |
    And The response should be
      | success   | true |
      | data.list | []   |
    Examples:
      | orgName     | notes                    | address                   | contactPhone | phone      | deliveryType | type | typeName     | msg                                                                      |
      | BV_MY_THANH | lấy mẫu thử với giá 200đ | 230 cong hoa tan binh hcm | 089999999    | 0347001005 | 4            | 4    | Medical Test | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |


  @medical-test-doctor-4
  Scenario:  Coordinator and Audit can't suggest medical test for patient
    #COORDINATOR assign question to audit
    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    And The response should be
      | success                          | true  |
      | data.actions.indicateMedicalTest | false |
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

    #audit suggest medical test
    When I login as "Audit_Trung"
    And As auditor, I get actions into consult
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    And The response should be
      | success                          | true  |
      | data.actions.indicateMedicalTest | false |
