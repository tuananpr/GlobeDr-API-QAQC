@regression @article @article-approver
Feature: Article: approver

  Background:
    Given I re-signup "manager_1" account and update profile

    # create category
    And I login as "system_admin_1"
    And I re-create a article category with below info
      | categoryName  | description   | language |
      | CATEGORY_AUTO | CATEGORY_AUTO | 0        |

    # create org
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |

    # create article
    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_NHAN_VAN" that I manage
    And Manager create article into category
      | categoryName  | subject                | msg                          | language | inputTags |
      | CATEGORY_AUTO | AUTO HOT kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing   |
    # submit article
    And Manager submit article
      | language | postTitleMsg           |
      | 0        | AUTO HOT kênh Nước Đen |
    And The request should be succeed

  @article-approver-0
  Scenario Outline: load articles pending approval
    When I login as "approver_1"
    # approve article
    And Approver load article with below info
      | language   | postTitleMsg | postStatus   |
      | <language> | <subject>    | <postStatus> |
    Then The response should be
      | success                 | true         |
      | data.list[0].title      | <subject>    |
      | data.list[0].language   | <language>   |
      | data.list[0].postStatus | <postStatus> |
    Examples:
      | subject                | language | postStatus |
      | AUTO HOT kênh Nước Đen | 0        | 8          |


  @article-approver-1
  Scenario Outline: create article <type> > approve article and it is displayed at interfaces articles, globedr articles
    When I login as "approver_1"
    # approve article
    And Approver approve article with below info
      | language   | postTitleMsg | note | comment | isSendNoti |
      | <language> | <subject>    | ok   | good    | false      |
    And The request should be succeed


    Given I login as "user_2"
    # User can view article at interfaces of org
    And User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success                  | true       |
      | data.list[0].title       | <subject>  |
      | data.list[0].createdName | <org>      |
      | data.list[0].language    | <language> |

    # User can view article at Globedr Articles
    When User load articles
      | postTitle |
      | <subject> |
    Then The response should be
      | success                  | true       |
      | data.list[0].title       | <subject>  |
      | data.list[0].createdName | <org>      |
      | data.list[0].language    | <language> |

    Examples:
      | subject                | language | org         |
      | AUTO HOT kênh Nước Đen | 0        | BV_NHAN_VAN |


  @article-approver-2
  Scenario Outline: manager create article <type> > reject article > it is still displayed at interfaces articles but not found in globedr article
    Given I login as "approver_1"
    # reject article
    When Approver reject article with below info
      | language   | postTitleMsg | comment   | note   | isSendNoti |
      | <language> | <subject>    | <comment> | <note> | false      |
    And The request should be succeed

    Given I login as "user_2"
    # User can view article at interfaces of org
    When User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success                  | true       |
      | data.list[0].title       | <subject>  |
      | data.list[0].createdName | <org>      |
      | data.list[0].language    | <language> |

    #User load list article
    When User load articles
      | postTitle |
      | <subject> |
    Then The request should be succeed
    And List "data.list[*].list[*].title" of response should be not contains "<subject>"
    Examples:
      | org         | subject                | language | activityType | activityTypeName | note            | comment               |
      | BV_NHAN_VAN | AUTO HOT kênh Nước Đen | 0        | 64           | Rejected         | khong chấp nhận | noi dung khong hop le |


  @article-approver-3
  Scenario Outline:  load notes after approving article
    When I login as "approver_1"
    # approve article
    And Approver <action> article with below info
      | language   | postTitleMsg | note   | comment   | isSendNoti |
      | <language> | <subject>    | <note> | <comment> | false      |
    And The request should be succeed

    When Approver load notes article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success                       | true               |
      | data.list[0].activityTypeName | Submited           |
      | data.list[1].note             | <note>             |
      | data.list[1].comment          | <comment>          |
      | data.list[1].activityTypeName | <activityTypeName> |

    And I login as "manager_1"
    And I select org "BV_NHAN_VAN" that I manage
    # User can view article at interfaces of org

    When Manager load notes article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success                       | true               |
      | data.list[0].activityTypeName | Submited           |
      | data.list[1].note             | <note>             |
      | data.list[1].comment          | <comment>          |
      | data.list[1].activityTypeName | <activityTypeName> |

    Examples:
      | subject                | language | note               | comment  | activityTypeName | action  |
      | AUTO HOT kênh Nước Đen | 0        | ok                 | good     | Approved         | approve |
      | AUTO HOT kênh Nước Đen | 0        | nội dung ko hợp lệ | ko duyệt | Rejected         | reject  |
