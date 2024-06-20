@regression @blood-glucose @health
Feature: Blood glucose

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"

  # See http://www.diabetes.co.uk/diabetes_care/blood-sugar-level-ranges.html
  # Glucose Levels	Category (before meal)
  # For people age 59 and younger.
  #     Normal: 80-120
  #     Low sugar level: be 80
  #     High sugar level: lon 120
  # For people age 60 and older
  #     Normal: 100 - 140
  #     Low sugar level: be 100
  #     High sugar level: lon 140
  # See also : http://www.diabetes.co.uk/diabetes_care/blood-sugar-level-ranges.html

  @blood-glucose-1
  Scenario Outline: Add Blood Glucose by mg/DL unit
    When I add Blood Glucose with info
      | glucoseMilestone   | glucose   | fromUnit   | onDate   |
      | <glucoseMilestone> | <glucose> | <fromUnit> | <onDate> |
    And The request should be succeed

    And I load Blood Glucose
      | toUnit |
      | Metric |
    Then The response should be
      | data.list[0].glucose              | <glucose>.00000        |
      | data.list[0].glucoseMilestoneName | <glucoseMilestoneName> |
      | data.list[0].status.key           | <status>               |
    And I load Blood Glucose Chart
      | toUnit |
      | Metric |
    Then The response should be
      | data.list.<response>[0].glucose | <glucose>.00000 |
    Examples:
      | glucoseMilestone | glucoseMilestoneName | glucose | fromUnit | onDate | status      | response               |
      | Fasting          | Fasting              | 60      | Metric   | today  | N/A         | fastingGlucoseCharts   |
      | Fasting          | Fasting              | 100     | Metric   | today  | Normal      | fastingGlucoseCharts   |
      | Fasting          | Fasting              | 120     | Metric   | today  | Prediabetic | fastingGlucoseCharts   |
      | Fasting          | Fasting              | 130     | Metric   | today  | Diabetic    | fastingGlucoseCharts   |
      | Fasting          | Fasting              | 400     | Metric   | today  | Diabetic    | fastingGlucoseCharts   |
      | AfterEating      | After meal           | 60      | Metric   | today  | N/A         | afterMealGlucoseCharts |
      | AfterEating      | After meal           | 180     | Metric   | today  | Normal      | afterMealGlucoseCharts |
      | AfterEating      | After meal           | 220     | Metric   | today  | Prediabetic | afterMealGlucoseCharts |
      | AfterEating      | After meal           | 240     | Metric   | today  | Diabetic    | afterMealGlucoseCharts |
      | AfterEating      | After meal           | 400     | Metric   | today  | Diabetic    | afterMealGlucoseCharts |


  @blood-glucose-2
  Scenario Outline: Add Blood Glucose by A1C unit
    When I add Blood Glucose with info
      | a1C   | fromUnit   | onDate   |
      | <a1C> | <fromUnit> | <onDate> |
    And The request should be succeed

    And I load Blood Glucose
      | toUnit |
      | Metric |
    Then The response should be
      | data.list[0].a1C        | <a1C>.0  |
      | data.list[0].status.key | <status> |
    And I load Blood Glucose Chart
      | toUnit |
      | Metric |
    Then The response should be
      | data.list.a1cGlucoseCharts[0].a1C | <a1C>.0 |
    Examples:
      | a1C | fromUnit | onDate | status      | description                                  |
      | 4   | Metric   | today  | Normal      | 4-6 normal, 7-9 Prediabetic, 10-13  Diabetic |
      | 6   | Metric   | today  | Normal      |                                              |
      | 7   | Metric   | today  | Prediabetic |                                              |
      | 9   | Metric   | today  | Prediabetic |                                              |
      | 10  | Metric   | today  | Diabetic    |                                              |
      | 13  | Metric   | today  | Diabetic    |                                              |


  @blood-glucose-3
  Scenario Outline: Add invalid Blood Glucose
    When I add Blood Glucose with info
      | glucoseMilestone   | glucose   | a1C   | fromUnit   | onDate   |
      | <glucoseMilestone> | <glucose> | <a1C> | <fromUnit> | <onDate> |
    Then The response should be
      | success       | false     |
      | message       | <message> |
      | errors<field> | <errMsg>  |

    Examples:
      | glucoseMilestone | glucose | a1C | fromUnit | onDate | message                | field    | errMsg                 |
      |                  |         | 15  | Metric   | today  | Please try again later | .a1C     | A1C value from 4 to 13 |
      |                  |         | 3   | Metric   | today  | Please try again later | .a1C     | A1C value from 4 to 13 |
      |                  |         | -1  | Metric   | today  | Please try again later | .a1C     | A1C value from 4 to 13 |
      | Fasting          |         |     | Metric   | today  | Please try again later | .glucose | This field is required |
      | Fasting          | 59      |     | Metric   | today  | Please try again later | .glucose | < 60                   |
      | Fasting          | 601     |     | Metric   | today  | Please try again later | .glucose | > 600                  |
















