@regression @org @review
Feature: Review org.

  Background:
    Given I login as "0767776999"


  @prepare-review
  Scenario: get default review of org successfully
    And I re-signup "0767776999" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name          | type     |
      | BV_LIEN_THANH | Hospital |

  @review-1
  Scenario: get default review of org successfully
    When I get review of org name "BV_LIEN_THANH"
    Then The response should be
      | success                     | true |
      | data.allowReview            | true |
      | data.scoreInfo.averageScore | 0.0  |
      | data.scoreInfo.oneScore     | 0    |
      | data.scoreInfo.twoScore     | 0    |
      | data.scoreInfo.threeScore   | 0    |
      | data.scoreInfo.fourScore    | 0    |
      | data.scoreInfo.fiveScore    | 0    |

  @review-2
  Scenario: review org successfully
    And I review to org name "BV_LIEN_THANH"
      | score | review |
      | 5     | good   |
    Then The request should be succeed

    When I get review of org name "BV_LIEN_THANH"
    Then The response should be
      | success                     | true       |
      | data.allowReview            | true       |
      | data.scoreInfo.averageScore | 5.0        |
      | data.scoreInfo.oneScore     | 0          |
      | data.scoreInfo.twoScore     | 0          |
      | data.scoreInfo.threeScore   | 0          |
      | data.scoreInfo.fourScore    | 0          |
      | data.scoreInfo.fiveScore    | 1          |
      | data.list[0].userName       | 0767776999 |
      | data.list[0].score          | 5          |
      | data.list[0].reviewMsg      | good       |

  @review-3
  Scenario: Only last review is displayed
    And I review to org name "BV_LIEN_THANH"
      | score |
      | 5     |
    Then The request should be succeed
    And I review to org name "BV_LIEN_THANH"
      | score | review |
      | 1     | bad    |
    When I get review of org name "BV_LIEN_THANH"
    Then The response should be
      | success                     | true       |
      | data.allowReview            | true       |
      | data.list[0].userName       | 0767776999 |
      | data.list[0].reviewMsg      | bad        |
      | data.list[0].score          | 1          |
      | data.scoreInfo.averageScore | 1.0        |
      | data.scoreInfo.oneScore     | 1          |
      | data.scoreInfo.twoScore     | 0          |
      | data.scoreInfo.threeScore   | 0          |
      | data.scoreInfo.fourScore    | 0          |
      | data.scoreInfo.fiveScore    | 0          |


  @review-4
  Scenario Outline: invalid review
    And I review to org name "BV_LIEN_THANH"
      | score   | review   |
      | <score> | <review> |
    Then The response should be
      | success | false   |
      | message | <error> |
    Examples:
      | score | review | error                                           |
      |       | good   | Please review and rate star for BV_LIEN_THANH ! |

  @review-5
  Scenario: load rating successfully
    And I review to org name "BV_LIEN_THANH"
      | score | review |
      | 5     | good   |
    Then The request should be succeed

    Given I login as "system_admin_1"
    Then The request should be succeed
    When On org, I load rating
      | userName   | orgName       |
      | 0767776999 | BV_LIEN_THANH |
    Then The response should be
      | success               | true       |
      | data.list[0].userName | 0767776999 |
      | data.list[0].gender   | 4          |
      | data.list[0].comment  | good       |
      | data.list[0].rating   | 5          |

    When On org, I load rating
      | gender | orgName       |
      | 4      | BV_LIEN_THANH |
    Then The response should be
      | success               | true       |
      | data.total            | 1          |
      | data.list[0].userName | 0767776999 |
    When On org, I load rating
      | score | orgName       |
      | 5     | BV_LIEN_THANH |
    Then The response should be
      | success               | true       |
      | data.total            | 1          |
      | data.list[0].userName | 0767776999 |
