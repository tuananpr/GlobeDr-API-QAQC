@regression @growth-target @health
Feature: My Health - Growth Target

  Background:
    Given I re-signup "user_1" account
    Given I login as "user_1"


  @growth-target-1
  Scenario: Add growth target
    And I update profile of logged user with dob is '1 month' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | 1      | 0903123455 |
    And The request should be succeed

    And I switch main of family members with below info
      | name |
      |      |
    And The request should be succeed

    When I add growth target
      | heightBirth | weightBirth | fromUnit |
      | 40          | 2           | Metric   |
    And The request should be succeed

    And As user, I load last BMI
      | toUnit |
      | Metric |
    Then The growth target should be "To 4 month"

    And I load growth target chart
      | toUnit |
      | Metric |
    Then The response should be
      | success               | true  |
      | data.info.heightBirth | 40.00 |
      | data.info.weightBirth | 2.00  |

    And I load growth target chart full
      | toUnit |
      | Metric |
    Then The response should be
      | success                                     | true       |
      | data.info.heightBirth                       | 40.00      |
      | data.info.weightBirth                       | 2.00       |
      | data.info.listTarget[0].heightTarget        | 54.00      |
      | data.info.listTarget[0].weightTarget        | 4.00       |
      | data.info.listTarget[0].growthTargetAge     | 1          |
      | data.info.listTarget[0].growthTargetAgeName | To 4 month |

      | data.info.listTarget[1].heightTarget        | 65.00      |
      | data.info.listTarget[1].weightTarget        | 6.0        |
      | data.info.listTarget[1].growthTargetAge     | 2          |
      | data.info.listTarget[1].growthTargetAgeName | To 1 year  |

      | data.info.listTarget[2].heightTarget        | 106.00     |
      | data.info.listTarget[2].weightTarget        | 12.0       |
      | data.info.listTarget[2].growthTargetAge     | 4          |
      | data.info.listTarget[2].growthTargetAgeName | To 6 year  |


      | data.info.listTarget[3].heightTarget        | 140.00     |
      | data.info.listTarget[3].weightTarget        | 24.0       |
      | data.info.listTarget[3].growthTargetAge     | 8          |
      | data.info.listTarget[3].growthTargetAgeName | To 12 year |

      | data.info.listTarget[4].heightTarget        |            |
      | data.info.listTarget[4].weightTarget        |            |
      | data.info.listTarget[4].growthTargetAge     | 16         |
      | data.info.listTarget[4].growthTargetAgeName | Maturity   |

  @growth-target-2
  Scenario Outline: Add growth target for user greater 12 years old
    And I update profile of logged user with dob is '<time>' and below info
      | displayName | email                  | address                                     | city                              | country | gender   | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | <gender> | 0903123455 |
    And The request should be succeed
    And I switch main of family members with below info
      | name |
      |      |
    And The request should be succeed
    When I add growth target
      | heightFather | heightMother | fromUnit |
      | 167          | 152          | Metric   |
    And The request should be succeed
    And As user, I load last BMI
      | toUnit |
      | Metric |
    Then Check result with gender "<result>"
    Examples:
      | time  | gender | result |
      | 16 yr | 1      | BOY    |
      | 16 yr | 2      | GIRL   |

  @growth-target-3
  Scenario Outline: Add growth target for user less than 12 years old
    And I update profile of logged user with dob is '<time>'
    And The request should be succeed
    And I switch main of family members with below info
      | name |
      |      |
    And The request should be succeed
    When I add growth target
      | heightBirth | weightBirth | fromUnit |
      | 40          | 10          | 2        |
    And The request should be succeed
    And As user, I load last BMI
      | toUnit |
      | Metric |
    Then The growth target should be "<expected>"
    Examples:
      | time     | expected   |
      | 1 month  | To 4 month |
      | 12 month | To 1 year  |
      | 4 yr     | To 6 year  |
      | 11 yr    | To 12 year |

  @growth-target-4
  Scenario Outline: Add growth target with invalid value
    And I update profile of logged user with dob is '1 month'
    And The request should be succeed
    And I switch main of family members with below info
      | name |
      |      |
    And The request should be succeed
    When I add growth target
      | heightBirth   | weightBirth   | heightFather   | heightMother   | fromUnit |
      | <heightBirth> | <weightBirth> | <heightFather> | <heightMother> | 2        |
    Then The response should contains
      | <key> | <value> |
    Examples:
      | heightBirth | weightBirth | heightFather | heightMother | key                 | value                  |
      | 0           | 9           | 170          | 150          | errors.heightBirth  | No less than 30 cm     |
      | 85          | 9           | 170          | 150          | errors.heightBirth  | No more than 80 cm     |
      | 35          | 0           | 170          | 150          | errors.weightBirth  | No less than 0.5 kg    |
      | 35          | 19          | 170          | 150          | errors.weightBirth  | No more than 18 kg     |
      | 35          | 9           | 300          | 150          | errors.heightFather | No more than 250 cm    |
      | 35          | 9           | 25           | 150          | errors.heightFather | No less than 30 cm     |
      | 35          | 9           | 170          | 300          | errors.heightMother | No more than 250 cm    |
      | 35          | 9           | 170          | 25           | errors.heightMother | No less than 30 cm     |
      |             | 9           | 170          | 150          | errors.heightBirth  | This field is required |
      | 80          |             | 170          | 150          | errors.weightBirth  | This field is required |


