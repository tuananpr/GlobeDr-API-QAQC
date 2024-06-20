@regression @blood-pressure @health
Feature: My health - Blood Pressure

  Background:
    When I re-signup "user_1" account
    Given I login as "user_1"


  @blood-pressure-1
  Scenario Outline: Add Blood Pressure for <description>
    And I update profile of logged user with dob is '<age>' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | 1      | 0903123455 |
    And The request should be succeed

    When I add Blood Pressure
      | systolic   | diastolic   | pulse   | fromUnit | onDate   |
      | <systolic> | <diastolic> | <pulse> | <unit>   | <onDate> |
    And The request should be succeed
    And I load my Blood Pressure
      | fromUnit |
      |          |
    Then The response should be
      | success                | true          |
      | data.totalCount        | 1             |
      | data.list[0].systolic  | <systolic>.0  |
      | data.list[0].diastolic | <diastolic>.0 |
    And I load chart Blood Pressure
      | toUnit |
      |        |
    Then The response should be
      | success                | true          |
      | data.list[0].systolic  | <systolic>.0  |
      | data.list[0].diastolic | <diastolic>.0 |

    And I load last Vitals
      | toUnit |
      | <unit> |
    Then The response should be
      | success                      | true          |
      | data.info.systolic           | <systolic>.0  |
      | data.info.diastolic          | <diastolic>.0 |
      | data.info.statusPressure.key | <status>      |
    Examples:
      | systolic | diastolic | pulse | unit   | onDate | status               | age   | description  |
      | 100      | 79        | 65    | Metric | today  | Normal               | 18 yr | 18 years old |
      | 125      | 79        | 60    | Metric | today  | Prehypertension      | 18 yr | 18 years old |
      | 125      | 85        | 66    | Metric | today  | Hypertension stage 1 | 18 yr | 18 years old |
      | 145      | 91        | 70    | Metric | today  | Hypertension stage 2 | 18 yr | 18 years old |
      | 181      | 120       | 76    | Metric | today  | Hypertensive crisis  | 18 yr | 18 years old |
      | 94       | 90        | 65    | Metric | today  | Prehypertension      | 2 yr  | 2 years old  |
      | 96       | 98        | 65    | Metric | today  | Hypertension stage 1 | 2 yr  | 2 years old  |
      | 100      | 100       | 65    | Metric | today  | Hypertension stage 2 | 2 yr  | 2 years old  |
      | 89       | 89        | 60    | Metric | today  | Normal               | 2 yr  | 2 years old  |

  @blood-pressure-2
  Scenario Outline: Add Blood pressure with special value will be fail
    When I add Blood Pressure
      | systolic   | diastolic   | pulse   | fromUnit | onDate   |
      | <systolic> | <diastolic> | <pulse> | <unit>   | <onDate> |
    Then The response should be
      | success | false |


    Examples:
      | systolic | diastolic | pulse | unit   | onDate | field     | errMsg                             |
      |          | 140       | 60    | Metric | today  | systolic  | This field is required             |
      | 140      |           | 60    | Metric | today  | diastolic | This field is required             |
      | 140      | 140       | abc   | Metric | today  | pulse     | You didn't give enough information |
      | 201      | 140       | 60    | Metric | today  | systolic  | No more than 200                   |
      | 100      | 141       | 60    | Metric | today  | diastolic | No more than 140                   |
      | 79       | 140       | 60    | Metric | today  | systolic  | No less than 80                    |
      | 100      | 49        | 60    | Metric | today  | diastolic | No less than 50                    |














































































