@regression @consultant @consultant-user-normal
Feature: Consultant

  As user
  I want ask doctor about my health


  Background:
    Given I re-signup "user_2" account and update profile
    When I login as "user_2"
    When I add new sub-account
      | displayName | dob        | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | TIEN_LUI    | 2010-09-30 | 2         | 1      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed


  @consultant-user-normal-1
  Scenario Outline: User create question
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And The request should be succeed
    And I create a question to GlobeDr with below info
      | msg   | height   | weight   |
      | <msg> | <height> | <weight> |
    And The request should be succeed
    And As user, I load questions
      | userMode |
      |          |
    Then The response should be
      | success                            | true     |
      | data.list[0].status.name           | Received |
      | data.list[0].providers[0].userName | user_2   |
      | data.list[0].rootMsg.msg           | <msg>    |
    When I login as "coordinator_auto"
    And As coordinator, I load questions
      | userMode |
      |          |
    Then The response should be
      | success                            | true     |
      | data.list[0].status.name           | Received |
      | data.list[0].providers[0].userName | user_2   |
      | data.list[0].rootMsg.msg           | <msg>    |
    Examples:
      | account     | name     | msg                                                                      | height | weight |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |
 #     | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |


  @consultant-user-normal-2 @upload-file
  Scenario Outline: User create question with image
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And The request should be succeed
    And I create a question to GlobeDr with below info
      | msg   | height   | weight   |
      | <msg> | <height> | <weight> |
    And The request should be succeed
    And As user, I add file "<image>" into below consult
      | textSearch |
      | <msg>      |
    And The request should be succeed
    And As user, I load comment of question
      | textSearch |
      | <msg>      |
    Then The response should be
      | success | true |
    And The image into "data.list[3].docs[0].docUrl" should be match "<image>"
    Examples:
      | account     | name     | msg                                                                      | height | weight | image                       |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     | data/image/avatar/org-1.jpg |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     | data/image/avatar/org-1.jpg |


  @consultant-user-normal-3
  Scenario Outline: Work-flow: User(create question)->Coordinator assign question to Doctor ->Doctor answer question ->Coordinator assign question to Auditor
  ->Auditor accept and comment question  ->Coordinator close question
    When I switch <account> of logged user with below info
      | name   |
      | <name> |

    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |
    And The request should be succeed

    # COORDINATOR assign question to doctor
    When I login as "coordinator_auto"
    And As coordinator, I load questions
      | userMode |
      |          |
    Then The response should be
      | success                  | true     |
      | data.list[0].status.name | Received |
    Then List "data.list[0].providers[*].userName" of response should be
      | user_2 |

    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    #DOCTOR accept and answer question
    When I login as "DOCTOR_TEO"
    And As doctor, I load questions
      | userMode |
      |          |
    Then The response should be
      | success                  | true     |
      | data.list[0].status.name | Assigned |
    Then List "data.list[0].providers[*].userName" of response should be
      | COORDINATOR_AUTO |
      | DOCTOR_TEO       |
      | user_2           |
    And As doctor, I accept questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    When As doctor, I add comment "<doctorMsg>" into consult
      | textSearch |
      | <userMsg>  |
    And As doctor, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                       | true |
      | data.actions.completeQuestion | true |
    And As doctor, I complete questions
      | textSearch |
      | <userMsg>  |
    And The request should be succeed

    #PATIENT view answer from doctor
    When I login as "user_2"
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And As user, I load questions
      | userMode |
      |          |
    Then The response should be
      | success                  | true     |
      | data.list[0].status.name | Answered |
    Then List "data.list[*].providers[*].userName" of response should be
      | COORDINATOR_AUTO |
      | DOCTOR_TEO       |
      | user_2           |
    And As user, I load comment of question
      | textSearch |
      | <userMsg>  |
    Then The response should contains
      | success               | true                                                                                                                                                                                                                                                                                                          |
      | data.list[0].userName | user_2                                                                                                                                                                                                                                                                                                        |
      | data.list[0].msg      | Male</p><p>Height: 130 cm</p><p>Weight: 30.0 kg</p>                                                                                                                                                                                                                                                           |
      | data.list[1].userName | user_2                                                                                                                                                                                                                                                                                                        |
      | data.list[1].msg      | <userMsg>                                                                                                                                                                                                                                                                                                     |
      | data.list[2].userName | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[2].msg      | <p style='text-align: center'>Your question has been received by globedr and will be answered by our doctors as soon as possible.</p> <p style='text-align: center'>You may send us <strong><u>photo</u></strong> of your condition. It may help the doctor to answer your question faster & more precise</p> |

      | data.list[3].userName | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[3].msg      | Your question has been received by <font color = '# 39B44A'>  DOCTOR_TEO </font>. Depending on the complexity of the question, the doctor will need time to research more. Please wait a moment!                                                                                                              |
      | data.list[4].userName | DOCTOR_TEO                                                                                                                                                                                                                                                                                                    |
      | data.list[4].msg      | <doctorMsg>                                                                                                                                                                                                                                                                                                   |


    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed


    #AUDITOR LOGIN
    When I login as "Audit_Trung"
    And As auditor, I load questions
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                  | true              |
      | data.list[0].status.name | Submit for review |
    Then List "data.list[*].providers[*].userName" of response should be
      | COORDINATOR_AUTO |
      | DOCTOR_TEO       |
      | Audit_Trung      |
      | user_2           |
    And As auditor, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                      | true |
      | data.actions.approveQuestion | true |
    And As auditor, I approve this questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As auditor, I add comment "<auditMsg>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    # COORDINATOR ask patient
    When I login as "coordinator_auto"
    And As coordinator, I add comment "<coorMsg>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    #PATIENT answer coordinator
    When I login as "user_2"
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    Then The request should be succeed
    And As user, I add comment "không" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    # COORDINATOR close question
    When I login as "coordinator_auto"
    And As coordinator, I close questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    # view all comment into consult
    And As coordinator, I load questions
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                  | true   |
      | data.list[0].status.name | Closed |
    Then List "data.list[*].providers[*].userName" of response should be
      | COORDINATOR_AUTO |
      | DOCTOR_TEO       |
      | Audit_Trung      |
      | user_2           |
    And As coordinator, I load comment of question
      | textSearch |
      | <userMsg>  |
    Then The response should contains
      | success                | true                                                                                                                                                                                                                                                                                                          |
      | data.list[0].userName  | user_2                                                                                                                                                                                                                                                                                                        |
      | data.list[0].msg       | Male</p><p>Height: 130 cm</p><p>Weight: 30.0 kg</p>                                                                                                                                                                                                                                                           |
      | data.list[1].userName  | user_2                                                                                                                                                                                                                                                                                                        |
      | data.list[1].msg       | <userMsg>                                                                                                                                                                                                                                                                                                     |
      | data.list[2].userName  | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[2].msg       | <p style='text-align: center'>Your question has been received by globedr and will be answered by our doctors as soon as possible.</p> <p style='text-align: center'>You may send us <strong><u>photo</u></strong> of your condition. It may help the doctor to answer your question faster & more precise</p> |
      | data.list[3].userName  | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[3].msg       | The question is being redirected to <font color = '# 39B44A;'>  DOCTOR_TEO </font>                                                                                                                                                                                                                            |
      | data.list[4].userName  | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[4].msg       | Your question has been received by <font color = '# 39B44A'>  DOCTOR_TEO </font>. Depending on the complexity of the question, the doctor will need time to research more. Please wait a moment!                                                                                                              |
      | data.list[5].userName  | DOCTOR_TEO                                                                                                                                                                                                                                                                                                    |
      | data.list[5].msg       | <doctorMsg>                                                                                                                                                                                                                                                                                                   |
      | data.list[6].userName  | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[6].msg       | <font color='#39B44A'>Audit_Trung, </font> has joined this consultation                                                                                                                                                                                                                                       |
      | data.list[7].userName  | Unknown                                                                                                                                                                                                                                                                                                       |
      | data.list[7].msg       | <font color='#39B44A'>Audit_Trung, </font> has agreed with the consulted doctor.                                                                                                                                                                                                                              |
      | data.list[8].userName  | Audit_Trung                                                                                                                                                                                                                                                                                                   |
      | data.list[8].msg       | <auditMsg>                                                                                                                                                                                                                                                                                                    |
      | data.list[9].userName  | COORDINATOR_AUTO                                                                                                                                                                                                                                                                                              |
      | data.list[9].msg       | <coorMsg>                                                                                                                                                                                                                                                                                                     |
      | data.list[10].userName | user_2                                                                                                                                                                                                                                                                                                        |
      | data.list[10].msg      | không                                                                                                                                                                                                                                                                                                         |
    Examples:
      | account     | name     | userMsg                                                                  | doctorMsg                                                        | auditMsg             | coorMsg                   |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé | Tôi hoàn toàn đồng ý | Bạn còn gì muốn hỏi không |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé | Tôi hoàn toàn đồng ý | Bạn còn gì muốn hỏi không |

  @consultant-user-normal-4
  Scenario Outline: Coordinator assign to 2nd doctor then 1st doctor decline question
    When I switch <account> of logged user with below info
      | name   |
      | <name> |

    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |

    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

   #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I decline questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_Duc_Dien"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    #DOCTOR LOGIN
    When I login as "doctor_duc_dien"
    And As doctor, I accept questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As doctor, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                        | true |
      | data.actions.viewPatientHealth | true |

    When I view health dashboard
    Then The request should be succeed
    And As doctor, I add comment "<doctorMsg>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As doctor, I complete questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    Examples:
      | account     | name     | userMsg                                                                  | doctorMsg                                                        |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |


  @consultant-user-normal-5
  Scenario Outline: Auditor reject question

    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |
    And The request should be succeed

    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As doctor, I add comment "<doctorMsg>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

     #AUDITOR LOGIN
    When I login as "Audit_Trung"
    And As auditor, I reject this questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As auditor, I add comment "tôi k đồng ý>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    Examples:
      | account     | name     | userMsg                                                                  | doctorMsg                                                        |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |


  @consultant-user-normal-7
  Scenario Outline: User review doctor and coordinator view list review doctors
    When I switch <account> of logged user with below info
      | name   |
      | <name> |

    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |
    And The request should be succeed

    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    #DOCTOR LOGIN
    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As doctor, I add comment "<doctorMsg>" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    # USER review doctor
    When I login as "user_2"
    And As user, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success             | true |
      | data.actions.review | true |
    And I rate 5 score with comment "good" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    # COORDINATOR view list review doctor
    When I login as "coordinator_auto"
    And I load list review doctor
    Then The request should be succeed
    Examples:
      | account     | name     | userMsg                                                                  | doctorMsg                                                        |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé |


  @consultant-user-normal-8
  Scenario Outline: Create invalid question
    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <content> | 130    | 30     |
    Then The request should be fail
    Examples:
      | content                   |
      |                           |
      | [Auto] TAO KO BIẾT HỎI GÌ |


  @consultant-user-normal-9
  Scenario: user ask many question
    And I create a question to GlobeDr with below info
      | msg                                                                                                   | height | weight |
      | [Auto]Câu hỏi 1: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu | 130    | 30     |
      | [Auto]Câu hỏi 2: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu | 130    | 30     |
      | [Auto]Câu hỏi 3: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu | 130    | 30     |
      | [Auto]Câu hỏi 4: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu | 130    | 30     |
    Then The request should be succeed
    And As user, I load questions
      | msg |
      |     |
    Then Post must be sorted like following order
      | [Auto]Câu hỏi 1: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu |
      | [Auto]Câu hỏi 2: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu |
      | [Auto]Câu hỏi 3: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu |
      | [Auto]Câu hỏi 4: Xin chào bác sĩ, bác sĩ cho em hỏi: Thực phẩm nào giúp mắt không bị khô khi nhìn lâu |

