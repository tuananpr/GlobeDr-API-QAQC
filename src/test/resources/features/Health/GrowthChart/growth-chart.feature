@regression @health @growth-chart
Feature: Growth chart

  Background:
    Given I re-signup "user_4" account
    And I login as "user_4"

  @growth-chart-1
  Scenario Outline: load growth chart for <description>
    And I update profile of logged user with dob is '<age>' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | 1      | 0903123455 |
    And The request should be succeed

    When I add BMI
      | heightMajor | heightMinor | weight   | head   | fromUnit | onDate |
      | <major>     | <minor>     | <weight> | <head> | <unit>   | today  |
    And The request should be succeed

    When I load growth chart
      | typeChartAge   |
      | <typeChartAge> |
    Then The request should be succeed
    Examples:
      | description                                  | major | minor | weight | head | unit   | typeChartAge | age     |
      | older less 2 years old                       |       | 50    | 40     | 30   | Metric | To24M        | 2 month |
      | older than 2 years old but less 20 years old | 1     | 50    | 40     |      | Metric | To20Y        | 18 yr   |
