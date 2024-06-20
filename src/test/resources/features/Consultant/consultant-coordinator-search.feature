@regression @consultant @consultant-search @coordinator @consultant-coordinator
Feature: Coordinator search questions.

  As coordinator
  I want to search question

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"
    And I create a question to GlobeDr with below info
      | msg                                                         | height | weight |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. | 130    | 30     |
    And The request should be succeed
    When I login as "coordinator_auto"

  @consultant-coordinator-search-1
  Scenario: Coordinator search question by ID, date, name, phone, content
    And I search question with ID
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

    And As coordinator, I load questions
      | toDate | fromDate    |
      | today  | prev 5 mins |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

    And As coordinator, I load questions
      | createdByName |
      | user_1        |
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."
    And As coordinator, I load questions
      | emailOrphone |
      | 84000000011  |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

    And As coordinator, I load questions
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

    And As coordinator, I load questions
      | postStatus |
      | 1          |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."


  @consultant-coordinator-search-2
  Scenario: Coordinator search question by doctor assigned.
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    And The request should be succeed
    And As coordinator, I load questions
      | doctorName |
      | DOCTOR_TEO |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

  @consultant-coordinator-search-3
  Scenario: Coordinator search question by auditor.
    And As coordinator, I assign question to doctor name "DOCTOR_TEO"
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    And The request should be succeed

    When I login as "DOCTOR_TEO"
    And As doctor, I accept questions
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    And The request should be succeed
    And As doctor, I add comment "Chào em, em hãy đi khám đi nhé" into consult
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    And The request should be succeed

  #COORDINATOR LOGIN
    When I login as "coordinator_auto"
    And As coordinator, I submit question to auditor name "Audit_Trung"
      | msg                                                         |
      | [Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ. |
    Then The request should be succeed
    And As coordinator, I load questions
      | auditorName |
      | Audit_Trung |
    And The request should be succeed
    Then I check msg return should be "[Auto]Chào bác sĩ, em muốn hỏi về bệnh ho lao, viêm phổi ạ."

