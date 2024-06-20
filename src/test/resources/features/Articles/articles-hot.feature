@regression @article @article-hot
Feature: Article: User

  As manager
  I want to create hot article
  In order to show hot article on homepage

  Background:
    Given On SqlServer, I delete articles category by name "CATEGORY_AUTO"
    Given I re-signup "manager_1" account and update profile
    Given I login as "system_admin_1"
    # create category
    And I re-create a article category with below info
      | categoryName  | description   | language |
      | CATEGORY_AUTO | CATEGORY_AUTO | 0        |
    #create hot category
    When I re-create a article category with below info
      | categoryName      | description              | weight | status | type     | language |
      | HOT_CATEGORY_AUTO | EN - ACTIVE - HOT - AUTO | 10     | 1      | HotTitle | 0        |
    And The request should be succeed

    # create org
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |

    #create hot article
    Given I login as "manager_1"
    And I accept join organization
    When I select org "BV_NHAN_VAN" that I manage

    And Manager create article into category
      | categoryName  | subject | msg               | language | inputTags | appId |
      | CATEGORY_AUTO | Bài hot | nội dung bài viết | 0        | hot       | 3     |

    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg | note | comment | isSendNoti | postStatus |
      | 0        | Bài hot      | ok   | good    | false      | 0          |
    And The request should be succeed


  @article-hot-1
  Scenario: user view hot article
    Given I login as "user_2"
    # user view related article
    When User load article at homepage
      | categoryType | language |
      | HotTitle     | 0        |
    And The request should be succeed
    And List "data.list[*].list[*].title" of response should contains
      | Bài hot |

    Given I log out
    # user view related article
    When Guest load article at homepage
      | categoryType | language |
      | HotTitle     | 0        |
    And The request should be succeed
    And List "data.list[*].list[*].title" of response should contains
      | Bài hot |