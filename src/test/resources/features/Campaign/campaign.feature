@regression @campaign @chat
Feature: Campaign

  Background:


  @prepare-campaign
  Scenario: prepare campaign org
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "user_1" account and update profile
    Given I re-signup "user_2" account and update profile

    # create org
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name    | type     | owner     |
      | BV_PHAP | Hospital | manager_1 |

    # user1 follow org
    Given I login as "user_1"
    And I FOLLOW business
      | name    |
      | BV_PHAP |
    Then The request should be succeed
    # user2 follow org
    Given I login as "user_2"
    And I FOLLOW business
      | name    |
      | BV_PHAP |

    Then The request should be succeed
    When I login as "manager_1"
    And I accept join organization

  @prepare-campaign
  Scenario: prepare campaign voucher
       #Create category for voucher
    And I login as "approver_1"
    And As approver, I re-create voucher category
      | nameVN         | nameEN      | weight |
      | Do an Han Quoc | Korean food | 10     |
    Then The request should be succeed

    #Create voucher
    When I login as "manager_1"
    And I accept join organization
    And I select org "BV_PHAP" that I manage
    And As manager, I create voucher info category name "Do an Han Quoc"
      | name         | description | note     | fromDate  | toDate   | country |
      | VOUCHER_CAMP | mô tả       | không có | yesterday | tomorrow | VN      |
    Then The request should be succeed

    And As manager, I add cards for voucher name "VOUCHER_CAMP"
      | receiptCode | description   | total | expiredDate |
      | AUTOMATION  | mã khuyến mãi | 2     | 2029-12-31  |
    And The request should be succeed

    And I login as "approver_1"
    And As approver, I approve vouchers
      | name         |
      | VOUCHER_CAMP |
    Then The request should be succeed


  @prepare-article
  Scenario: prepare campaign article
    And I login as "system_admin_1"
    And I re-create a article category with below info
      | categoryName  | description     | language |
      | CATEGORY_AUTO | Auto - Articles | 0        |
    And The request should be succeed

     # create article
    Given I login as "manager_1"
    When I select org "BV_PHAP" that I manage
    And Manager create article into category
      | categoryName  | subject            | msg                          | language | inputTags |
      | CATEGORY_AUTO | AUTO kênh Nước Đen | nơi ô nhiễm bậc nhất Sài Gòn | 0        | Testing   |
    And The request should be succeed

    Given I login as "approver_1"
    # approve article
    When Approver approve article with below info
      | language | postTitleMsg       | note | comment | isSendNoti | postStatus |
      | 0        | AUTO kênh Nước Đen | ok   | good    | false      | 0          |
    And The request should be succeed

  @prepare-topdeal
  Scenario Outline: prepare campaign TopDeal
    # Login manager
    Given I login as "manager_1"
    When I select org "BV_PHAP" that I manage
    # create topdeal into org
    Given I create TopDeal
      | name          |
      | <topDealName> |
    And I add promotion for TopDeal "<topDealName>"
      | name            | notes            | price    |
      | <promotionName> | Giam gia quan ao | giam 50% |
    Then The request should be succeed
    And I upload image for promotion "<promotionName>" of TopDeal "<topDealName>"
      | file                                |
      | data/image/avatar/phan-van-tien.jpg |
    And I upload attachment file for promotion "<promotionName>" of TopDeal "<topDealName>"
      | file              |
      | data/pdf-test.pdf |
    Then The request should be succeed
    Examples:
      | promotionName            | topDealName             |
      | Auto campaign Hot deal 2 | Auto_campaign_topdeal_2 |

  @campaign-01
  Scenario Outline:  OrgAdmin send campaign with text
      # Login manager
    When I login as "manager_1"
    And I select org "BV_PHAP" that I manage
    #Org-Admin create Campaign
    And I create campaign
      | msg | conversationName   | group      |
      | abc | <conversationName> | All member |

    Then The request should be succeed
    When I load campaign list
      | name |
      |      |
    Then I should have '1' campaigns in my conversation list

    #User login and load campaign
    When I login as "user_1"
    And I open msg on conversation
      | conversationName | type     |
      | BV_PHAP          | Campaign |
    Then The request should be succeed

    When I login as "user_2"
    And I open msg on conversation
      | conversationName | type     |
      | BV_PHAP          | Campaign |
    Then The request should be succeed
    Examples:
      | conversationName                   |
      | Thong bao cho nguoi nghe thong bao |


  @campaign-02
  Scenario Outline: OrgAdmin send campaign with <campaignType> to all member and view statistics after user view it
    # Login manager
    When I login as "manager_1"
    And I select org "BV_PHAP" that I manage

#   Org-Admin create Campaign voucher
    And I create <campaignType> campaign
      | msg | conversationName   | group      | attachmentFileName   |
      | abc | <conversationName> | All member | <attachmentFileName> |
    Then The request should be succeed

   #user_1 read campaign
    Given I login as "user_1"
    And I open msg on conversation
      | conversationName | type     |
      | BV_PHAP          | Campaign |
    Then The request should be succeed

    When I login as "manager_1"
    And I select org "BV_PHAP" that I manage
    And I load all user that unreal on campaign
      | name               |
      | <conversationName> |
    Then The response should be
      | success           | true   |
      | data.list[0].name | user_2 |
      | data.total        | 1      |

    #user_1 click campaign
    Given I login as "user_1"
    And I click <campaignType> on conversation
      | name               | objectClick        | type     |
      | <conversationName> | <conversationName> | Campaign |
    Then The request should be succeed

    When I login as "manager_1"
    And I select org "BV_PHAP" that I manage
    And I load all user that unreal on campaign
      | conversationName   |
      | <conversationName> |
    Then The response should be
      | success           | true   |
      | data.list[0].name | user_2 |
      | data.total        | 1      |

    When I load all user that unclick on campaign
      | conversationName   |
      | <conversationName> |
    Then The response should be
      | success    | true |
      | data.list  | []   |
      | data.total | 0    |

    When I load all user that click on campaign
      | conversationName   |
      | <conversationName> |
    Then The response should be
      | success           | true   |
      | data.list[0].name | user_1 |
      | data.total        | 1      |
    Examples:
      | conversationName                      | attachmentFileName      | campaignType |
      | VOUCHER_CAMP                          | VOUCHER_CAMP            | voucher      |
      | AUTO kênh Nước Đen                    | AUTO kênh Nước Đen      | article      |
      | Auto Topdeal, hang nong hang nong day | Auto_campaign_topdeal_2 | topdeal      |

