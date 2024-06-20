@regression @consultant @consultant-view-health
Feature: Create question for sub_account

  As coordinator or doctor
  I want to view health record of patient when consult
  In order to get more information to consult

  Background:
    Given I re-signup "user_2" account and update profile
    And On SqlServer, I delete user by displayName "TIEN_LUI"
    And I login as "user_2"
    When I add new sub-account
      | displayName | dob        | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | TIEN_LUI    | 2010-09-30 | 2         | 1      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed


  @consultant-view-health-1
  Scenario Outline: Work-flow: User create question  -> Coordinator view health information when question status is received
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |
    And The request should be succeed
    # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The response should be
      | success                        | true |
      | data.actions.commentQuestion   | true |
      | data.actions.hideMsg           | true |
      | data.actions.assignQuestion    | true |
      | data.actions.closeQuestion     | true |
      | data.actions.viewActivityLog   | true |
      | data.actions.viewPatientHealth | true |
    And I get shared account information
    And The response should be
      | success               | true   |
      | data.info.displayName | <name> |
    And The request should be succeed
    When I view health dashboard
    And The request should be succeed
    And As user, I load last BMI
      | toUnit |
      | Metric |
    And The request should be succeed
    When I print health history
    And The request should be succeed
    Examples:
      | account     | name     | userMsg                                                                  |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @consultant-view-health-2
  Scenario Outline: Work-flow: User(create question)->Coordinator assign question to Doctor ->Doctor view patient health information when question is accepted
  ->Auditor view patient health information when question is completed
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And The request should be succeed
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
    And As doctor, I add comment "Chào em, em hãy ăn nhiều quả có màu đỏ, nhiều rau và hải sản nhé" into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed

    And As doctor, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And I get shared account information
    And The response should be
      | success               | true   |
      | data.info.displayName | <name> |
    And The request should be succeed
    When I view health dashboard
    And The request should be succeed
    And As user, I load last BMI
      | toUnit |
      | Metric |
    And The request should be succeed
    When I print health history
    And The request should be succeed
    And As doctor, I complete questions
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
    And As auditor, I get actions into consult
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And I get shared account information
    And The response should be
      | success               | true   |
      | data.info.displayName | <name> |
    And The request should be succeed
    When I view health dashboard
    And The request should be succeed
    And As user, I load last BMI
      | toUnit |
      | Metric |
    And The request should be succeed
    When I print health history
    And The request should be succeed
    Examples:
      | account     | name     | userMsg                                                                  |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @consultant-view-health-3
  Scenario Outline: Work-flow: User create question ->Coordinator can't view health information when question is closed
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And The request should be succeed
    And I create a question to GlobeDr with below info
      | msg       | height | weight |
      | <userMsg> | 130    | 30     |
    And The request should be succeed

  # COORDINATOR LOGIN
    When I login as "coordinator_auto"
    Then As coordinator, I close questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As coordinator, I get actions into consult
      | textSearch |
      | <userMsg>  |
    And The response should be
      | success                        | true  |
      | data.actions.viewPatientHealth | false |
    Examples:
      | account     | name     | userMsg                                                                  |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @consultant-view-health-4
  Scenario Outline: Doctor cant' view health after decline question
    When I switch <account> of logged user with below info
      | name   |
      | <name> |
    And The request should be succeed
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
    And As doctor, I decline questions
      | textSearch |
      | <userMsg>  |
    Then The request should be succeed
    And As doctor, I load questions
      | textSearch |
      | <userMsg>  |
    And List "data.list[*].rootMsg[*].msg" of response should not has
      | <userMsg> |

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
    And The response should be
      | success                        | true |
      | data.actions.viewPatientHealth | true |

    When I view health dashboard
    Then The request should be succeed

    Examples:
      | account     | name     | userMsg                                                                  |
      | main        | user_2   | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | sub-account | TIEN_LUI | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
