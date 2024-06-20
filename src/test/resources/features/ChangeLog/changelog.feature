@regression @changelog
Feature: Changelog

  As system
  I want to introduce new information to user as new features, new articles, new vouchers,...


  Background:
    Given On SqlServer, I delete change log "Thông báo hằng tháng"
    When I login as "approver_1"


  @prepare-changelog-1
  Scenario: create system post of changelog
    Given On SqlServer, I delete change log of app version english
    Given On SqlServer, I delete all posts in system
    When I login as "system_admin_1"
    When I create a new system post
      | key       | type | subject              | msg                                                                                                                                                                                | language |
      | changelog | 47   | Changelog App        | GlobeDr is the first healthcare social network that connects patient with care provider and provides patient with a tool to maintain & manage their Personal Health Record easily. | 0        |
      | appvesion | 47   | App version          | App version 001                                                                                                                                                                    | 0        |
      | post      | 47   | POST About Changelog | About the Changelog                                                                                                                                                                | 0        |


  @prepare-changelog-2
  Scenario Outline: create org
    When I login as "system_admin_1"
    # create org
    And I re-create a org with full of feature
      | name  | type     | owner     |
      | <org> | Hospital | manager_1 |
    Then The request should be succeed
    When I login as "manager_1"
    And I accept join organization
    Then The request should be succeed
    Examples:
      | org              |
      | DOANH_NGHIEP_DAU |


  @prepare-changelog-3
  Scenario Outline: create voucher for org
       #Create category for voucher
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN              | nameEN           | weight |
      | Khuyến mãi xăng dầu | petrol promotion | 10     |
    Then The request should be succeed

    #Create voucher
    When I login as "manager_1"
    And I select org "<org>" that I manage
    And As manager, I create voucher info category name "Khuyến mãi xăng dầu"
      | name       | description | note     | fromDate  | toDate   | country | language |
      | <voucher1> | mô tả       | không có | yesterday | tomorrow | US      | 0        |
      | <voucher2> | mô tả       | không có | yesterday | tomorrow | US      | 0        |
    Then The request should be succeed

    And As manager, I add cards for voucher name "<voucher1>"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 2     | 2029-12-31  |
    And The request should be succeed

    And As manager, I add cards for voucher name "<voucher2>"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 2     | 2029-12-31  |
    And The request should be succeed

    # approve voucher
    And I login as "approver_1"
    And As approver, I approve vouchers
      | name       |
      | <voucher1> |
      | <voucher2> |
    Then The request should be succeed

    Examples:
      | org              | voucher1          | voucher2          |
      | DOANH_NGHIEP_DAU | Giảm giá xăng 10% | Giảm giá xăng 20% |


  @prepare-changelog-4
  Scenario Outline: create article for org
    And I login as "approver_1"
    # create category
    And I re-create a article category with below info
      | categoryName   | description   | language |
      | <categoryName> | CATEGORY_AUTO | 0        |
    And The request should be succeed
    # create articles into org

    When I login as "manager_1"
    And I select org "<org>" that I manage
    # create article 1,2
    And Manager create article into category
      | categoryName   | subject    | msg                                                 | language | inputTags |
      | <categoryName> | <article1> | Post can be added into normal category successfully | 0        | Testing   |
      | <categoryName> | <article2> | Post can be added into normal category successfully | 0        | Testing   |
    And The request should be succeed
    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg | note | comment | isSendNoti | postStatus |
      | 0        | <article1>   | ok   | good    | false      | 0          |
      | 0        | <article2>   | ok   | good    | false      | 0          |
    And The request should be succeed
    Examples:
      | org              | categoryName    | article1                | article2                |
      | DOANH_NGHIEP_DAU | Auto - Articles | Bài viết không tên số 1 | Bài viết không tên số 2 |


  @changelog-1
  Scenario Outline: create new changelog successfully
    When I create new changelog <type>
      | name   | country   | language   | startDate   | endDate   | image   | linkName   |
      | <name> | <country> | <language> | <startDate> | <endDate> | <image> | <linkName> |

    Then The response should be
      | success             | true        |
      | data.info.name      | <name>      |
      | data.info.linkType  | <linkType>  |
      | data.info.status    | 2           |
      | data.info.language  | <language>  |
      | data.info.country   | <country>   |
      | data.info.startDate | <startDate> |
      | data.info.endDate   | <endDate>   |
    And The "data.info.linkId" <condition> "0"
    And The "data.info.linkSig" should "<linkSig>"
    And The image into "data.info.imgUrl" should be match "<image>"
    Given I load list changelog
      | name   | language   |
      | <name> | <language> |

    Then The response should be
      | success                | true        |
      | data.list[0].name      | <name>      |
      | data.list[0].linkType  | <linkType>  |
      | data.list[0].status    | 2           |
      | data.list[0].language  | <language>  |
      | data.list[0].country   | <country>   |
      | data.list[0].startDate | <startDate> |
      | data.list[0].endDate   | <endDate>   |
    And The "data.list[0].linkId" <condition> "0"
    And The "data.list[0].linkSig" should "<linkSig>"
    And The image into "data.list[0].imgUrl" should be match "<image>"
    And On SqlServer, I delete change log "<name>"
    Examples:
      | type       | linkName                | linkType | name                 | country | language | linkSig   | condition | startDate           | endDate             | image                               |
      | appVersion | App version             | 4        | Thông báo hằng tháng | US      | 0        | not empty | greater   | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | data/image/prescription/pres-01.jpg |
      | voucher    | Giảm giá xăng 10%       | 2        | Thông báo hằng tháng | US      | 0        | not empty | greater   | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | data/image/prescription/pres-01.jpg |
      | article    | Bài viết không tên số 1 | 1        | Thông báo hằng tháng | US      | 0        | not empty | greater   | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | data/image/prescription/pres-01.jpg |
      | normal     |                         | 0        | Thông báo hằng tháng | US      | 0        | null      | equal     | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | data/image/prescription/pres-01.jpg |

  @changelog-2
  Scenario Outline: update changelog successfully

    When I create new changelog <type>
      | name   | country   | language   | startDate | endDate  | image   | linkName   |
      | <name> | <country> | <language> | yesterday | tomorrow | <image> | <linkName> |
    And The request should be succeed

    When I update above changelog <type>
      | name      | country   | language   | startDate      | endDate      | image   | linkName      |
      | <newName> | <country> | <language> | <newStartDate> | <newEndDate> | <image> | <newLinkName> |
    And The request should be succeed

    Given I load list changelog
      | name      | language   |
      | <newName> | <language> |

    Then The response should be
      | success                | true           |
      | data.list[0].name      | <newName>      |
      | data.list[0].linkType  | <linkType>     |
      | data.list[0].status    | 2              |
      | data.list[0].language  | <language>     |
      | data.list[0].country   | <country>      |
      | data.list[0].startDate | <newStartDate> |
      | data.list[0].endDate   | <newEndDate>   |
    And The "data.list[0].linkId" <condition> "0"
    And The "data.list[0].linkSig" should "<linkSig>"
    And The image into "data.list[0].imgUrl" should be match "<image>"
    And On SqlServer, I delete change log "<newName>"
    Examples:
      | name                 | type    | linkName                | newLinkName             | newName            | country | language | newStartDate        | newEndDate          | linkSig   | condition | linkType | image                               |
      | Thông báo hằng tháng | article | Bài viết không tên số 1 | Bài viết không tên số 2 | Thông báo bài viết | US      | 1        | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | not empty | greater   | 1        | data/image/prescription/pres-03.jpg |
      | Thông báo hằng tháng | voucher | Giảm giá xăng 10%       | Giảm giá xăng 20%       | Thông báo xăng dầu | VN      | 1        | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | not empty | greater   | 2        | data/image/prescription/pres-03.jpg |

  @changelog-3
  Scenario Outline: remove changelog successfully
    When I create new changelog <type>
      | name   | country   | language   | startDate | endDate  | image   | linkName   |
      | <name> | <country> | <language> | yesterday | tomorrow | <image> | <linkName> |

    And The request should be succeed
    When I remove changelog
      | name   | language   |
      | <name> | <language> |
    And The request should be succeed
    Given I load list changelog
      | name   | language   |
      | <name> | <language> |

    Then The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    And On SqlServer, I delete change log "<name>"
    Examples:
      | type   | linkName | linkType | name                 | country | language | linkSig | condition | startDate           | endDate             | image                               |
      | normal |          | 0        | Thông báo hằng tháng | US      | 0        | null    | equal     | 2030-02-19T10:29:00 | 2040-02-19T10:29:00 | data/image/prescription/pres-01.jpg |


  @changelog-4
  Scenario Outline: create invalid changelog
    When I create new changelog <type>
      | name | country | language | startDate | endDate | image | linkName |
      |      |         |          |           |         |       |          |

    And The response should be
      | success          | false                  |
      | message          | Please try again later |
      | errors.name      | <content>              |
      | errors.endDate   | <content>              |
      | errors.language  | <content>              |
      | errors.imgBase64 | <content>              |
      | errors.startDate | <content>              |

    Examples:
      | content                | name | country | language | startDate | endDate  | type   |
      | This field is required |      | US      | 0        | yesterday | tomorrow | normal |