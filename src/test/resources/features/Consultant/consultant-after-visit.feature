@regression @consultant @consultant-after-visit
Feature: Consultant_After visit

  Background:
    Given I re-signup "user_2" account and update profile
    And On SqlServer, I delete user by displayName "TIEN_LUI"
    When I login as "user_2"


  @consultant-after-visit-01
  Scenario: User create question then question is moved to after visit.
    When I switch main of logged user with below info
      | name   |
      | user_2 |
    And I create a question to GlobeDr with below info
      | msg                                                                      | height | weight |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |
    And The request should be succeed
    And I load visit medical
    And The request should be succeed
    And I load visit medical details
    And The response should be
      | data[0].visitVital[1].chiefComplaintVisit | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | data[0].visitVital[2].vital[0].height     | 130                                                                      |
      | data[0].visitVital[2].vital[1].weight     | 30                                                                       |
    And The "data[*].link[*].linkSig" should "not null"
    And As user, I load comment of question
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    And The response should be
      | success          | true                                                                     |
      | data.list[1].msg | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @consultant-after-visit-02
  Scenario: Sub_account create question
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | TIEN_LUI    | 2019-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |

    And The request should be succeed
    When I switch sub-account of logged user with below info
      | name     |
      | TIEN_LUI |
    And I create a question to GlobeDr with below info
      | msg                                                                      | height | weight |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |
    And The request should be succeed

    And I load visit medical
    And I load visit medical details
    And The response should be
      | data[0].visitVital[1].chiefComplaintVisit | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
      | data[0].visitVital[2].vital[0].height     | 130                                                                      |
      | data[0].visitVital[2].vital[1].weight     | 30                                                                       |
    And The "data[*].link[*].linkSig" should "not null"
    And As user, I load comment of question
      | msg                                                                      |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |
    And The response should be
      | success          | true                                                                     |
      | data.list[1].msg | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? |

  @consultant-after-visit-03
  Scenario:User create question=> coordiartor asign=>Doctor accept=> User load medical history
    When I switch main of logged user with below info
      | name   |
      | user_2 |
    And I create a question to GlobeDr with below info
      | msg                                                                      | height | weight |
      | [Auto] Làm sao để tái hòa nhập với động đồng khi mà tao bị nhiễm corona? | 130    | 30     |
    And The request should be succeed
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
    # USER LOGIN AND CHECK DANH SÁCH BÁC SĨ ĐÃ KHÁM TRONG AFTER VISIT
    When I login as "user_2"
    And I load visit medical
    And I load visit medical details
    And The response should be
      | data[0].visitVital[0].providers[0].name | DOCTOR_TEO |

