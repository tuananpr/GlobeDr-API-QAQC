@regression @bmi @health
Feature: My health - BMI


  Background:
    Given I re-signup "user_1" account and update profile
    Given I login as "user_1"


  @bmi-1
  Scenario Outline: User update measurement unit
    And I update measurement unit
      | unit   |
      | <unit> |
    And I get personal information
    Then The response should be
      | success              | true       |
      | data.measurementUnit | <expected> |
    Examples:
      | unit     | expected |
      | American | 1        |
      | Metric   | 2        |

# Công thức tính BMI : Cân nặng / bình phương chiều cao
# Với cân nặng theo đơn vị kg
# Với chiều cao theo đơn vị m

  @bmi-2
  Scenario Outline: User add and load BMI for <description>
    And I update profile of logged user with dob is '<age>' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | 1      | 0903123455 |
    And The request should be succeed
    When I add BMI
      | heightMajor | heightMinor | weight   | head   | fromUnit | onDate |
      | <major>     | <minor>     | <weight> | <head> | <unit>   | today  |
    And The request should be succeed
    And I load BMI
      | toUnit |
      | <unit> |
    Then The response should be
      | success                  | true           |
      | data.totalCount          | 1              |
      | data.list[0].heightMajor | <major>        |
      | data.list[0].heightMinor | <minor>.0      |
      | data.list[0].weight      | <weight>.0     |
      | data.list[0].head        | <expextedHead> |
      | data.list[0].bmi         | <bmi>          |
    And I load chart BMI
      | toUnit |
      |        |
    Then The response should be
      | success                 | true  |
      | data.list[0].bmi        | <bmi> |
      | data.list[0].status.key | <key> |
    Examples:
      | description             | major | minor | weight | head | expextedHead | unit   | typeChartAge | bmi   | key         | age      | descriptionBmi       |
      | older than 20 years old | 1     | 50    | 40     |      | null         | Metric | To20Y        | 17.78 | Underweight | 25 yr    | Underweight < 18,5   |
      | older than 20 years old | 1     | 60    | 60     |      | null         | Metric | To20Y        | 23.44 | Normal      | 25 yr    | Normal = 18,5 - 25,0 |
      | older than 20 years old | 1     | 50    | 60     |      | null         | Metric | To20Y        | 26.67 | Overweight  | 25 yr    | Overweight > 25      |
      | older than 20 years old | 1     | 50    | 70     |      | null         | Metric | To20Y        | 31.11 | Obesity     | 25 yr    | Obesity > 30         |
      | older than 20 years old | 1     | 50    | 50     |      | null         | Metric | To20Y        | 22.22 | Normal      | 25 yr    | Normal = 18,5 - 25,0 |
      | less 2 years old        | 0     | 75    | 10     | 45   | 45.00000     | Metric | To24M        | 17.78 | N/A         | 12 month | Underweight < 18,5   |


  @bmi-3
  Scenario Outline: Add invalid BMI
    When I add BMI
      | heightMajor | heightMinor | weight   | fromUnit | onDate |
      | <major>     | <minor>     | <weight> | <unit>   | today  |
    Then The response should be
      | success            | false     |
      | message            | <message> |
      | errors.heightMinor | <error>   |
      | errors.weight      | <error>   |
    And I load BMI
      | toUnit |
      | <unit> |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |

    And I load chart BMI
      | toUnit |
      |        |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    Examples:
      | major | minor | weight | message                | error                  | unit   | typeChartAge |
      | 1     |       |        | Please try again later | This field is required | Metric | To20Y        |
      | 1     | abc   | cc     | Please try again later | Field invalid          | Metric | To20Y        |


  @bmi-4
  Scenario Outline: Add BMI with out of range values (Boundary values)
    When I add BMI
      | heightMajor   | heightMinor   | weight   | fromUnit | onDate |
      | <heightMajor> | <heightMinor> | <weight> | <unit>   | today  |
    And The request should be fail
    Examples:
      | heightMajor | heightMinor | weight | unit   |
      | 3           | 1           | 70     | Metric |
      | -1          | 1           | 70     | Metric |
      | 1           | 1           | -70    | Metric |
      | 1           | 65          | 501    | Metric |


  @bmi-5
  Scenario Outline: Delete BMI
    When I add BMI
      | heightMajor | heightMinor | weight   | fromUnit | onDate |
      | <major>     | <minor>     | <weight> | <unit>   | today  |
    And The request should be succeed
    And I delete BMI
      | toUnit |
      | <unit> |
    And The request should be succeed

    And I load BMI
      | toUnit |
      | <unit> |
    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    Examples:
      | major | minor | weight | unit   |
      | 1     | 50    | 40     | Metric |

  @bmi-7
  Scenario Outline: Load last BMI
    When I add BMI
      | heightMajor | heightMinor | weight   | fromUnit | onDate    |
      | <major>     | 10          | <weight> | <unit>   | yesterday |
    And The request should be succeed

    And I add BMI
      | heightMajor | heightMinor | weight   | fromUnit | onDate |
      | <major>     | 20          | <weight> | <unit>   | today  |
    And The request should be succeed

    And As user, I load last BMI
      | toUnit |
      | <unit> |
    Then The response should be
      | data.info.heightMajor | <major>    |
      | data.info.heightMinor | 20.0       |
      | data.info.weight      | <weight>.0 |

    And I delete BMI
      | toUnit |
      | <unit> |
    And The request should be succeed

    And As user, I load last BMI
      | toUnit |
      | <unit> |
    Then The response should be
      | data.info.heightMajor | <major>    |
      | data.info.heightMinor | 10.0       |
      | data.info.weight      | <weight>.0 |
    Examples:
      | major | minor | weight | unit   |
      | 1     | 50    | 40     | Metric |

