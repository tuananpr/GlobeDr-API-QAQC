@regression @article @article
Feature: Article: category
# Admin
  # Search bài : ForumManager/Posts
  # xem chi tiet : ForumManager/LoadPostDetail
  # update  :ForumManager/UpdatePost
  # xem nhat ky :  ForumManager/LoadRejectLog
  # xoa bai viet : ForumManager/DeletePost
  # tao bai viet  :
    # ForumManager/AddPostInit,
    # ForumManager/AddPost (tao moi PostStatus: null, tao nhap PostStatus: 512, tao bai hot)
    # ForumManager/UploadForumIcon
 # load danh muc  : ForumManager/LoadCategories

# Approver
  # xem chi tiet : ForumManager/LoadPostDetail
  # xem chi tiet : ForumManager/LoadPostDetail
  # xem nhat ky :  /ForumManager/LoadNotes
  # load danh muc  : ForumManager/LoadCategories
  # xoa bai viet : ForumManager/DeletePost
  # chua duyet : /ForumManager/RejectPost
  # duyet : /ForumManager/ApprovePost
  # Quan ly system post


  Background:
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a article category with below info
      | categoryName  | description   | language |
      | CATEGORY_AUTO | CATEGORY_AUTO | 0        |
    And The request should be succeed

    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |
    And I login as "manager_1"
    And I accept join organization

  @article-1
  Scenario Outline: create article <type> and it is displayed at interfaces articles but not in globedr articles
    When I select org "<org>" that I manage
    # create article
    And Manager create article into category
      | categoryName   | subject   | msg   | language   | inputTags   | appId   |
      | <categoryName> | <subject> | <msg> | <language> | <inputTags> | <appId> |

    And I add this "<avatar>" image into the above article
    And The request should be succeed
    # load list article
    When Manager load article with below info
      | language   | subject   |
      | <language> | <subject> |

    Then The response should be
      | success                   | true            |
      | data.list[0].categoryName | <categoryName>  |
      | data.list[0].title        | <subject>       |
      | data.list[0].createdName  | <org>           |
      | data.list[0].language     | <language>      |
      | data.list[0].isRequestHot | <isRequestHot>  |
      | data.list[0].appId        | <expectedAppId> |
    And The "data.list[0].categorySig" should "not empty"
    And The "data.list[0].categoryId" should "not empty"
    And The "data.list[0].postId" should "not empty"
    And The "data.list[0].postSignature" should "not empty"
    And The "data.list[0].onDate" should "not empty"
    And The image into "data.list[0].avatar" should be match "<avatar>"
    # load details article
    When Manager load details article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success                 | true            |
      | data.subject            | <subject>       |
      | data.appId              | <expectedAppId> |
      | data.postStatusName     | New             |
      | data.myMsg.createByName | <org>           |
      | data.myMsg.msgContent   | <msg>           |
    And The image into "data.iconUrl" should be match "<avatar>"

    # User can view article at interfaces of org
    When User load articles
      | orgName     | postTitle |
      | BV_NHAN_VAN | <subject> |
    Then The response should be
      | success                  | true       |
      | data.list[0].title       | <subject>  |
      | data.list[0].createdName | <org>      |
      | data.list[0].language    | <language> |

  # User can't view article at Globedr Articles
    When User load articles
      | postTitle |
      | <subject> |
    Then The response should be
      | success   | true |
      | data.list | []   |
    Examples:
      | org         | categoryName  | subject                | msg                          | language | inputTags   | appId | expectedAppId | isRequestHot | avatar                     |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO kênh Nước Đen     | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing,abc | 5     | 5             | true         | data/image/Forum/music.png |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO HOT kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing     |       | null          | false        | data/image/Forum/music.png |

  @article-2
  Scenario Outline: delete article
    When I select org "<org>" that I manage
    # create article
    And Manager create article into category
      | categoryName   | subject   | msg   | language   | inputTags   |
      | <categoryName> | <subject> | <msg> | <language> | <inputTags> |

    And The request should be succeed
    # delete article
    When Manager delete article with below info
      | language   | postTitleMsg |
      | <language> | <subject>    |
    And The request should be succeed
    # load list article
    When Manager load article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success   | true |
      | data.list | []   |
    Examples:
      | org         | categoryName  | subject            | msg                          | language | inputTags |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing   |


  @article-3
  Scenario Outline: update article
    When I select org "<org>" that I manage
    # create article
    And Manager create article into category
      | categoryName   | subject   | msg   | language   | inputTags   |
      | <categoryName> | <subject> | <msg> | <language> | <inputTags> |
    And The request should be succeed
    And I add this "<avatar>" image into the above article
    And The request should be succeed
    # update article
    When Manager update below information for article title "<subject>", language is <language>
      | subject          | msg          | appId         |
      | update <subject> | update <msg> | <updateAppId> |
    And The request should be succeed
    # check list article
    When Manager load details article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success               | true             |
      | data.subject          | update <subject> |
      | data.myMsg.msgContent | update <msg>     |
      | data.appId            | <updateAppId>    |
    And The image into "data.iconUrl" should be match "<avatar>"
    Examples:
      | org         | categoryName  | subject            | msg                          | language | inputTags | updateAppId | isRequestHot | avatar                     |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing   | 5           | true         | data/image/Forum/music.png |

  @article-4
  Scenario Outline:  load notes of new article
    When I select org "<org>" that I manage
    # create article
    And Manager create article into category
      | categoryName   | subject   | msg   | language   | inputTags   |
      | <categoryName> | <subject> | <msg> | <language> | <inputTags> |
    And The request should be succeed
    # load notes
    When Manager load notes article with below info
      | language   | subject   |
      | <language> | <subject> |
    Then The response should be
      | success   | true |
      | data.list | []   |
    Examples:
      | org         | categoryName  | subject            | msg                          | language | inputTags |
      | BV_NHAN_VAN | CATEGORY_AUTO | AUTO kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | testing   |


  @article-5
  Scenario Outline: create valid article with <type>
    When I select org "<org>" that I manage
    # create article
    And Manager create article into category
      | categoryName | subject | msg   | language   |
      |              |         | <msg> | <language> |
    Then The response should be
      | success                  | false      |
      | errors.subject           | <errorMsg> |
      | errors.msg               | <errorMsg> |
      | errors.categorySignature | <errorMsg> |
    Examples:
      | org         | categoryName  | subject | msg | language | errorMsg               |
      | BV_NHAN_VAN | CATEGORY_AUTO |         |     | 0        | This field is required |