@regression @article @article-user
Feature: Article: User


  Background:
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    # create category
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
      | CATEGORY_AUTO | AUTO kênh 1        | AUTO kênh 1                  | 0        | Testing   |
    And The request should be succeed
    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg       | postStatus |
      | 0        | AUTO kênh Nước Đen | 0          |
      | 0        | AUTO kênh 1        | 0          |
    And The request should be succeed

  @article-user-1
  Scenario Outline: user and guest view article

    #User load list article
    Given I login as "user_2"
    #User load articles
    When User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |

    Then The request should be succeed
    And List "data.list[*].title" of response should be contains "<subject>"
    And List "data.list[*].createdName" of response should be contains "<org>"

    #User load details article
    And User load details article
      | postTitle |
      | <subject> |
    Then The response should be
      | success           | true          |
      | data.categoryName | CATEGORY_AUTO |


    #load list article without login
    Given I log out
    When Guest load articles
      | postTitle |
      | <subject> |
    Then The request should be succeed
    And List "data.list[*].title" of response should be contains "<subject>"

    #load details article without login
    And Guest load details article
      | postTitle |
      | <subject> |
    Then The response should be
      | success           | true          |
      | data.categoryName | CATEGORY_AUTO |
    Examples:
      | org         | categoryName  | subject            |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO kênh Nước Đen |

  @article-user-3
  Scenario Outline: user view, like, unlike article
    #User like article
    Given I login as "user_2"
    And User like article
      | postTitle |
      | <subject> |
    Then The request should be succeed
    And User load details article
      | postTitle |
      | <subject> |
    Then The response should be
      | success        | true |
      | data.countLike | 1    |
      | data.countView | 2    |

    #User unlike article
    And User unlike article
      | postTitle |
      | <subject> |
    Then The request should be succeed
    And User load details article
      | postTitle |
      | <subject> |
    Then The response should be
      | success        | true |
      | data.countLike | 0    |
      | data.countView | 4    |
    Examples:
      | subject            |
      | AUTO kênh Nước Đen |

  @article-user-4 @upload-file
  Scenario Outline: user comment article
    Given I login as "user_2"
    # comment article
    And I comment into article
      | postTitle | msgContent |
      | <subject> | <cmt>      |
    Then The request should be succeed
    And I send image to comment into article
      | postTitle | files   |
      | <subject> | <image> |
    Then The request should be succeed
    # load comment article
    And User load comments above article
      | postTitle |
      | <subject> |
    Then The response should be
      | success                 | true  |
      | data.list[0].msgContent | <cmt> |
    And The image into "data.list[1].docs[0].url" should be match "<image>"
    Examples:
      | subject            | cmt          | image                     |
      | AUTO kênh Nước Đen | bài viết hay | data/image/3mb/laptop.jpg |

  @article-user-5
  Scenario Outline: user like comment of article
    Given I login as "user_2"
    # comment article
    And I comment into article
      | postTitle | msgContent |
      | <subject> | <cmt>      |
    Then The request should be succeed

    Given I login as "user_3"
    # like comment article
    And User like comment
      | postTitle | msgContent |
      | <subject> | <cmt>      |
    Then The request should be succeed
    And User load comments above article
      | postTitle |
      | <subject> |
    And The response should be
      | success             | true |
      | data.list[0].isLike | true |
    Examples:
      | subject            | cmt          |
      | AUTO kênh Nước Đen | bài viết hay |
