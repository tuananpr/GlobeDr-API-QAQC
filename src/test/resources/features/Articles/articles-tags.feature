@regression @article @article-tag
Feature: Article: User

  As manager
  I want to add tags article
  In order to show article that has same tag as related article

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
      | categoryName  | subject                 | msg               | language | inputTags                  |
      | CATEGORY_AUTO | Bài về gà               | nội dung bài viết | 0        | ga,channuoi                |
      | CATEGORY_AUTO | Bài về bò               | nội dung bài viết | 0        | bo,channuoi                |
      | CATEGORY_AUTO | Bài dinh dưỡng về gà    | nội dung bài viết | 0        | ga,baidinhduong            |
      | CATEGORY_AUTO | Bài dinh dưỡng về bò    | nội dung bài viết | 0        | bo,baidinhduong            |
      | CATEGORY_AUTO | Hướng dẫn chăn bò       | nội dung bài viết | 0        | bo,baihuongdan             |
      | CATEGORY_AUTO | Hướng dẫn chăn bò và gà | nội dung bài viết | 0        | bo,baihuongdan,ga,channuoi |

    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg            | note | comment | isSendNoti | postStatus |
      | 0        | Bài về gà               | ok   | good    | false      | 0          |
      | 0        | Bài về bò               | ok   | good    | false      | 0          |
      | 0        | Bài dinh dưỡng về gà    | ok   | good    | false      | 0          |
      | 0        | Bài dinh dưỡng về bò    | ok   | good    | false      | 0          |
      | 0        | Hướng dẫn chăn bò       | ok   | good    | false      | 0          |
      | 0        | Hướng dẫn chăn bò và gà | ok   | good    | false      | 0          |
    And The request should be succeed


  @article-tag-1
  Scenario: user view related article
    Given I login as "user_2"
    # user view related article
    When User load related article
      | postTitle | language |
      | Bài về gà | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về bò               |
      | Bài dinh dưỡng về gà    |
      | Hướng dẫn chăn bò và gà |

    And List "data.list[*].title" of response should not has
      | Bài dinh dưỡng về bò |
      | Hướng dẫn chăn bò    |

    # user view related article
    When User load related article
      | postTitle | language |
      | Bài về bò | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về gà               |
      | Bài dinh dưỡng về bò    |
      | Hướng dẫn chăn bò       |
      | Hướng dẫn chăn bò và gà |
    And List "data.list[*].title" of response should not has
      | Bài dinh dưỡng về gà |

    # user view related article
    When User load related article
      | postTitle            | language |
      | Bài dinh dưỡng về gà | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về gà               |
      | Bài dinh dưỡng về bò    |
      | Hướng dẫn chăn bò và gà |

    And List "data.list[*].title" of response should not has
      | Bài về bò         |
      | Hướng dẫn chăn bò |

    # user view related article
    When User load related article
      | postTitle            | language |
      | Bài dinh dưỡng về bò | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về bò               |
      | Bài dinh dưỡng về gà    |
      | Hướng dẫn chăn bò       |
      | Hướng dẫn chăn bò và gà |
    And List "data.list[*].title" of response should not has
      | Bài về gà |

    # user view related article
    When User load related article
      | postTitle         | language |
      | Hướng dẫn chăn bò | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về bò               |
      | Bài dinh dưỡng về bò    |
      | Hướng dẫn chăn bò và gà |
    And List "data.list[*].title" of response should not has
      | Bài về gà            |
      | Bài dinh dưỡng về gà |

    # user view related article
    When User load related article
      | postTitle               | language |
      | Hướng dẫn chăn bò và gà | 0        |
    Then The request should be succeed
    And List "data.list[*].title" of response should be
      | Bài về gà            |
      | Bài về bò            |
      | Bài dinh dưỡng về gà |
      | Bài dinh dưỡng về bò |
      | Hướng dẫn chăn bò    |
