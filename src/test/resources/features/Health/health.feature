@regression @health
Feature: Health common

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"
    When I switch main of family members with below info
      | name   |
      | user_1 |
    And The request should be succeed

  @health-1
  Scenario: I view health dashboard
    When I view health dashboard
    Then The response should be
      | success           | true           |
      | data.list[0].text | Immunization   |
      | data.list[1].text | Pregnant zone  |
      | data.list[2].text | BMI            |
      | data.list[3].text | Blood pressure |
      | data.list[4].text | Blood glucose  |
    And The image into "data.list[0].iconUrl" should be match "data/image/health/immunizationFrV59.png"
    And The image into "data.list[1].iconUrl" should be match "data/image/health/pregnantzoneFrV59.png"
    And The image into "data.list[2].iconUrl" should be match "data/image/health/bmiFrV59.png"
    And The image into "data.list[3].iconUrl" should be match "data/image/health/bloodpressureFrV59.png"
    And The image into "data.list[4].iconUrl" should be match "data/image/health/bloodglucoseFrV59.png"


  @health-2
  Scenario Outline: I load health status
    And I load health status
      | healthType |
      | <type>     |
    Then The response path "data" should be matched with "<filepath>"
    Examples:
      | type                  | filepath                                               |
      | BloodPresure          | data/expected_result/health/BloodPressure.json         |
      | HeightAndWeigth       | data/expected_result/health/BMI.json                   |
      | CholesterolAndGlucose | data/expected_result/health/CholesterolAndGlucose.json |




