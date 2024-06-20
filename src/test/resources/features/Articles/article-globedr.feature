@regression @article @article-globedr
Feature: Article: use globedr articles

  As Manager
  I want to use globedr articles for my organization
  In order to share globedr articles to audience

  Background:
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"

    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |
    And I login as "manager_1"
    And I accept join organization
    When I select org "BV_NHAN_VAN" that I manage
    # turn on config to show globedr articles
    And I turn on config to show globedr articles on my organization
    Then The request should be succeed

  @article-globedr-1
  Scenario: turn on and turn off config to show globedr articles on my organization
    When I load config to show globedr articles on my organization
    Then The response should be
      | success                 | true |
      | data.showGlobedrArticle | true |

    And I turn off config to show globedr articles on my organization
    Then The request should be succeed

    When I load config to show globedr articles on my organization
    Then The response should be
      | success                 | true  |
      | data.showGlobedrArticle | false |

  @article-globedr-2
  Scenario: User and Guest view globedr articles on my organization

    Given I login as "user_2"
    # load articles on org
    When User load articles
      | orgName     |
      | BV_NHAN_VAN |
    And The "data.total" greater than "0"

    Given I log out
    # load articles on org
    When Guest load articles
      | orgName     |
      | BV_NHAN_VAN |
    And The "data.total" greater than "0"
    # turn off config to show globedr articles
    And I login as "manager_1"
    When I select org "BV_NHAN_VAN" that I manage
    And I turn off config to show globedr articles on my organization
    Then The request should be succeed

    Given I login as "user_2"
    # load articles on org
    When User load articles
      | orgName     |
      | BV_NHAN_VAN |
    And The "data.total" equal "0"

    Given I log out
    # load articles on org
    When Guest load articles
      | orgName     |
      | BV_NHAN_VAN |
    And The "data.total" equal "0"

