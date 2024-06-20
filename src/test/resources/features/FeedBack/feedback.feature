@regression @feedback
Feature: User feedback for app

  As user
  I want to feedback to Globedr

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"

  @feedback-1
  Scenario Outline: Users send feedback to the GlobeDr with true value.
    And I want to send feedback to the app with below infor
      | score   | review   |
      | <score> | <review> |
    Then The request should be succeed
    And I will check gdrLogin "84000000011" in the database have below infor
      | score  | <score>  |
      | review | <review> |

    Examples:
      | score | review                            |
      | 4     | App nay rat huu ich voi moi nguoi |
      | 5     | App nay qua tot                   |
      | 3     | NICE                              |

  @feedback-2
  Scenario Outline: Users send feedback to the GlobeDr with wrong value
    And I want to send feedback to the app with below infor
      | score   | review   |
      | <score> | <review> |
    Then The request should be fail
    Then The response should be
      | message | <message> |

    Examples:
      | score | review          | message                                         |
      | 4     |                 | Please enter the score and rate the application |
      | 6     | App nay qua tot | Please enter a score from 1 to 5                |
      | 0     | App nay qua tot | Please enter a score from 1 to 5                |
      |       | App nay qua tot | Please enter the score and rate the application |

  @feedback-3
  Scenario Outline: System admin load feedback on web.
    And I want to send feedback to the app with below infor
      | score   | review   |
      | <score> | <review> |
    Then The request should be succeed

    And I login as "system_admin_1"
    And As sysAdmin, I load feedbacks
      | fromScore | toScore | userName |
      | <score>   | <score> | user_1   |
    Then The request should be succeed
    And I want to export data
      | fromScore | toScore | userName |
      | <score>   | <score> | user_1   |
    Then The request should be succeed
    And CSV file should contains
      | user_1 |
    Examples:
      | score | review          |
      | 5     | App nay qua tot |

  @feedback-4
  Scenario Outline:
    And I want to send feedback to the app with below infor
      | score | review          |
      | 4     | App nay qua tot |
    And I login as "system_admin_1"
    And As sysAdmin, I load feedbacks
      | fromScore   | toScore   | userName   |
      | <fromScore> | <toScore> | <userName> |
    Then The request should be succeed
    Then The response should be
      | data.list[0].<key> | <result> |
    Examples:
      | fromScore | toScore | userName | result | key        |
      | 2         | 4       | user_1   | user_1 | userName   |
      |           |         |          | user_1 | userName   |
      | 2         | 4       |          | 4/5    | scoreValue |

