@regression @sponsor

Feature: Sponsor of organization

  Background:
    When I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     |
      | BV_DA_NANG | Hospital |

    And I update cover "data/image/avatar/org-1.jpg" for above org
    Then The request should be succeed
    When As sysAdmin, I search org
      | name       |
      | BV_DA_NANG |


  @sponsor-00
  Scenario: approve sponsor successfully
    # Approve sponsor
    And I approve new sponsor for above org
    And The response should be
      | success            | true       |
      | data.info.status   | 1          |
      | data.info.org.name | BV_DA_NANG |
    # set duration for sponsor
    And I set duration for sponsor
      | startDate | endDate  |
      | yesterday | tomorrow |
    # admin load above sponsor
    Then The request should be succeed
    And admin load list of sponsor
      | orgName | BV_DA_NANG |
    And The response should be
      | success               | true       |
      | data.list[0].status   | 1          |
      | data.list[0].org.name | BV_DA_NANG |
    And The image into "data.list[0].org.cover" should be match "data/image/avatar/org-1.jpg"
    And The "data.list[0].org.sig" should "not empty"
    # user load above sponsor
    And I login as "user_1"
    And User get list sponsor
    And The request should be succeed
    And The "data.list" should "not null"

  @sponsor-01
  Scenario: Approve this sponsor again
    And I approve new sponsor for above org
    Then The request should be succeed
    And I approve new sponsor for above org
    And The request should be fail
    And The response should be
      | message   | Already existed |
      | data.info | null            |

  @sponsor-02
  Scenario: Update status active or inactive for sponsor
    And I approve new sponsor for above org
    Then The request should be succeed
    And admin load list of sponsor and I see sponsor of org "BV_DA_NANG"
    Then The request should be succeed
    And I update sponsor for org with status active
    Then The request should be succeed
    And I select org with name "BV_DA_NANG" that have status active

    And admin load list of sponsor and I see sponsor of org "BV_DA_NANG"
    And I update sponsor for org with status inactive
    And I select org with name "BV_DA_NANG" that have status inactive

  @sponsor-03
  Scenario: Set time duration for sponsor
    And I approve new sponsor for above org
    Then The request should be succeed
    And admin load list of sponsor and I see sponsor of org "BV_DA_NANG"
    And I set duration for sponsor
      | startDate | endDate  |
      | yesterday | tomorrow |
    Then The request should be succeed
    And I load list sponsor and check duration of org with name "BV_DA_NANG"
      | startDate | endDate  |
      | yesterday | tomorrow |

  @sponsor-04
  Scenario: Set time duration with invalid
    And I approve new sponsor for above org
    Then The request should be succeed
    And admin load list of sponsor and I see sponsor of org "BV_DA_NANG"
    And I set duration for org with below info
      | startDate | 2020-02-18T06:14:00 |
      | endDate   | 2020-02-23T06:14:00 |
    Then The request should be fail
    And The response should be
      | message        | Please try again later |
      | errors.endDate | Field invalid          |
    And admin load list of sponsor
      | orgName | BV_DA_NANG |
    Then The request should be succeed
    And The response should be
      | data.list[0].startDate | null |
      | data.list[0].endDate   | null |

  @sponsor-05
  Scenario: Remove sponsor
    And I approve new sponsor for above org
    Then The request should be succeed
    And admin load list of sponsor and I see sponsor of org "BV_DA_NANG"
    And I remove sponsor of org with name "BV_DA_NANG"
    Then The request should be succeed
    And admin load list of sponsor
      | orgName | BV_DA_NANG |
    And I check sponsor return should be '0'


