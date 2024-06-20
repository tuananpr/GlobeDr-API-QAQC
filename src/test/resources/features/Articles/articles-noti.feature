@regression @article @article-noti
Feature: Article: noti


  Background:
    Given I re-signup "manager_1" account
    Given I re-signup "user_1" account

    # create category
    When I login as "system_admin_1"
    And I re-create a article category with below info
      | categoryName  | description   | language |
      | CATEGORY_AUTO | CATEGORY_AUTO | 0        |

    # create org
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |

    # create article
    Given I login as "manager_1"
    And I accept join organization
    When I select org "BV_NHAN_VAN" that I manage
    And Manager create article into category
      | categoryName  | subject            | msg                          | language | inputTags |
      | CATEGORY_AUTO | AUTO kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing   |
    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg       | note | comment | isSendNoti | postStatus |
      | 0        | AUTO kênh Nước Đen | ok   | good    | true       | 0          |
    And The request should be succeed

  @article-noti-1
  Scenario Outline: user are receive noti from new article
    #User load list article
    Given I login as "user_1"
    And I load notifications
      | groupType  |
      | SystemNoti |
    Then The response should be
      | success               | true                   |
      | data.list[0].userName | <org>                  |
      | data.list[0].message  | New article: <subject> |
      | data.list[0].seen     | false                  |

    Examples:
      | org         | subject            |
      | BV_NHAN_VAN | AUTO kênh Nước Đen |

