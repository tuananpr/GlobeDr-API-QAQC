@regression @article @article-search
Feature: Article: User


  Background:
    Given I re-signup "manager_1" account and update profile
    Given I login as "system_admin_1"
    # create category
    And I re-create a article category with below info
      | categoryName  | description   | language |
      | CATEGORY_AUTO | CATEGORY_AUTO | 0        |

    # create org
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |

     #create article
    Given I login as "manager_1"
    And I accept join organization
    When I select org "BV_NHAN_VAN" that I manage

    And Manager create article into category
      | categoryName  | subject              | msg                      | language | inputTags |
      | CATEGORY_AUTO | AUTO kênh Nước Trong | nơi đẹp bậc nhất Sài Gòn | 0        | Testing   |


    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg         | note | comment | isSendNoti | postStatus |
      | 0        | AUTO kênh Nước Trong | ok   | good    | false      | 0          |

    And The request should be succeed


  @article-search-1
  Scenario Outline: user ( or guest ) search and open article title

    #User load articles on org
    Given I login as "user_2"
    When User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success           | true      |
      | <key>.title       | <subject> |
      | <key>.createdName | <org>     |
    And User load details article
      | postTitle |
      | <subject> |
    Then The request should be succeed

    #load articles on org without login
    Given I log out
    When Guest load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success           | true      |
      | <key>.title       | <subject> |
      | <key>.createdName | <org>     |
    And Guest load details article
      | postTitle |
      | <subject> |
    Then The request should be succeed
    Examples:
      | org         | subject              | key          |
      | BV_NHAN_VAN | AUTO kênh Nước Trong | data.list[0] |

  @article-search-2
  Scenario Outline: user load articles on org title for language of user

    #User load articles on org
    Given I login as "user_2"
    And I change language is <language>
    When User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success | true      |
      | <key>   | <subject> |
    And The "data.total" <totalKey> "<totalValue>"
    And The "data.list" should "<expectedList>"
    Examples:
      | key                | subject              | totalKey     | totalValue | expectedList    | language |
      | data.list[0].title | AUTO kênh Nước Trong | greater than | 1          | not empty array | US       |
      |                    |                      | greater than | 1          | not empty array | US       |
      |                    | not found article    | equal        | 0          | empty array     | US       |
      |                    | AUTO kênh Nước Trong | equal        | 0          | empty array     | VN       |


  @article-search-3
  Scenario Outline: guest load articles on org by all language
    #load articles on org without login
    Given I log out
    When Guest load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success | true      |
      | <key>   | <subject> |
    And The "data.total" <totalKey> "<totalValue>"
    And The "data.list" should "<expectedList>"
    Examples:
      | key                | subject              | totalKey     | totalValue | expectedList    |
      | data.list[0].title | AUTO kênh Nước Trong | greater than | 1          | not empty array |
      |                    |                      | greater than | 1          | not empty array |
      |                    | not found article    | equal        | 0          | empty array     |
      | data.list[0].title | AUTO kênh Nước Trong | greater than | 1          | not empty array |