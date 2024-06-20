@regression @consultant @consultant-and-health-doc
Feature: Send Health Records into consult
  As a user
  I want send health records into consult
  In order to doctor diagnosis online

  Rule:
  - User, Sub-account can send Health Records

    Background:
      Given I re-signup "user_2" account and update profile
      When I login as "user_2"
      When I add new sub-account
        | displayName | dob        | carerType | gender | country                                               | city                                  | district                        | ward                                |
        | TIEN_LUI    | 2010-09-30 | 2         | 1      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
      And The request should be succeed


    @consultant-and-health-doc-1
    Scenario Outline: Send Health Records into consult
      When I switch <account> of logged user with below info
        | name   |
        | <name> |
      Then The request should be succeed
    # Add health docs
      When As user, I upload health document
        | attributes | description        | medicalType   | docType   | createdDate | file    |
        | 1          | Ket qua xet nghiem | <medicalType> | <docType> | today       | <image> |
      Then The request should be succeed
      And I create a question to GlobeDr with below info
        | msg       | height | weight |
        | <userMsg> | 130    | 30     |
      And The request should be succeed
      And I send document with below information into above consult
        | medicalType   | docType   | description   |
        | <medicalType> | <docType> | <description> |
      And The request should be succeed

      And As user, I load comment of question
        | msg       |
        | <userMsg> |
      And The response should contains
        | success                         | true                                                                        |
        | data.list[3].msgExtension.value | "Description":"Ket qua xet nghiem","FileType":"4","Title":"Health document" |
      And The document into consult should be match with "<image>"
      Examples:

        | account     | name     | medicalType | docType | image                               | userMsg                                                   | description        |
        | main        | user_2   | 3           | 3       | data/image/immunization/immu-01.jpg | [Auto]Bác sĩ xem qua dùm kết quả xét nghiệm này giúp em ? | Ket qua xet nghiem |
        | main        | user_2   | 6           | 12      | data/image/immunization/immu-01.jpg | [Auto]Bác sĩ xem qua dùm kết quả xét nghiệm này giúp em ? | Ket qua xet nghiem |
        | main        | user_2   | 1           | 1       | data/image/immunization/immu-01.jpg | [Auto]Bác sĩ xem qua dùm kết quả xét nghiệm này giúp em ? | Ket qua xet nghiem |
        | sub-account | TIEN_LUI | 1           | 1       | data/image/immunization/immu-01.jpg | [Auto]Bác sĩ xem qua dùm kết quả xét nghiệm này giúp em ? | Ket qua xet nghiem |
