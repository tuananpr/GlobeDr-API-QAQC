@regression @topdeal
Feature: topdeal

  Background:
    Given I re-signup "manager_3" account and update profile
    # create org
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name      | owner     |
      | BV_Y_KHOA | manager_3 |

    # Login manager
    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_Y_KHOA" that I manage

  @topdeal-1
  Scenario: create TopDeal successfully
    Given I create TopDeal
      | name           |
      | Auto_topdeal_1 |
    And The response should be
      | success        | true           |
      | data.info.name | Auto_topdeal_1 |
    And The "data.info.topDealSig" should "not empty"
    And The "data.info.orgSig" should "not empty"
    And The "data.info.iconUrl" should "not empty"
    When As manager, I load TopDeal
      | name           |
      | Auto_topdeal_1 |
    And The response should be
      | success           | true           |
      | data.list[0].name | Auto_topdeal_1 |
    And The "data.list[0].topDealSig" should "not empty"
    And The "data.list[0].orgSig" should "not empty"
    And The "data.list[0].iconUrl" should "not empty"


  @topdeal-2
  Scenario: remove TopDeal
    Given I create TopDeal
      | name           |
      | Auto_topdeal_1 |
    Then The request should be succeed
    When I remove TopDeal
      | name           |
      | Auto_topdeal_1 |
    Then The request should be succeed
    When As manager, I load TopDeal
      | name           |
      | Auto_topdeal_1 |
    And The response should be
      | success         | true |
      | data.list       | []   |
      | data.totalCount | 0    |


  @topdeal-3
  Scenario: create promotion successfully
    Given I create TopDeal
      | name           |
      | Auto_topdeal_1 |
    Then The request should be succeed
    And I add promotion for TopDeal "Auto_topdeal_1"
      | name           | notes            | price    |
      | Auto promotion | Giam gia quan ao | giam 50% |
    Then The request should be succeed

    And As manager, I load below promotions of TopDeal "Auto_topdeal_1"
      | name           |
      | Auto promotion |
    And The response should be
      | success            | true             |
      | data.list[0].name  | Auto promotion   |
      | data.list[0].price | giam 50%         |
      | data.list[0].notes | Giam gia quan ao |
      | data.totalCount    | 1                |

      # Login manager
    And As user, I load TopDeal on org "BV_Y_KHOA"
    And The response should be
      | success                         | true             |
      | data.list[0].promotions[0].name | Auto promotion   |
      | data.list[0].promotions[0]price | giam 50%         |
      | data.list[0].promotions[0]notes | Giam gia quan ao |
      | data.totalCount                 | 1                |
    And The "data.list[0].promotions[0].topDealSig" should "not empty"
    And The "data.list[0].promotions[0].promotionSig" should "not empty"
    And The "data.list[0].promotions[0].orgSig" should "not empty"


  @topdeal-4 @upload-file
  Scenario: upload file for promotion successfully
    Given I create TopDeal
      | name           |
      | Auto_topdeal_1 |
    Then The request should be succeed
    And I add promotion for TopDeal "Auto_topdeal_1"
      | name           | notes            | price    |
      | Auto promotion | Giam gia quan ao | giam 50% |
    Then The request should be succeed
    And I upload image for promotion "Auto promotion" of TopDeal "Auto_topdeal_1"
      | file                                |
      | data/image/avatar/phan-van-tien.jpg |
    Then The request should be succeed

    And I upload attachment file for promotion "Auto promotion" of TopDeal "Auto_topdeal_1"
      | file              |
      | data/pdf-test.pdf |
    Then The request should be succeed

    And As manager, I load below promotions of TopDeal "Auto_topdeal_1"
      | name           |
      | Auto promotion |
    Then The request should be succeed
    And The image into "data.list[0].iconUrl" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.list[0].fileUrl" should be match "data/pdf-test.pdf"
      # Login manager
    And As user, I load TopDeal on org "BV_Y_KHOA"
    Then The request should be succeed
    And The image into "data.list[0].promotions[0].iconUrl" should be match "data/image/avatar/phan-van-tien.jpg"
    And The pdf into "data.list[0].promotions[0].fileUrl" should be match "data/pdf-test.pdf"


  @topdeal-5
  Scenario: remove promotion successfully
    Given I create TopDeal
      | name           |
      | Auto_topdeal_1 |
    Then The request should be succeed
    And I add promotion for TopDeal "Auto_topdeal_1"
      | name           | notes            | price    |
      | Auto promotion | Giam gia quan ao | giam 50% |
    Then The request should be succeed
    And I remove promotion of TopDeal "Auto_topdeal_1"
      | name           |
      | Auto promotion |
    Then The request should be succeed
    And As manager, I load below promotions of TopDeal "Auto_topdeal_1"
      | name           |
      | Auto promotion |
    And The response should be
      | success         | true |
      | data.list       | []   |
      | data.totalCount | 0    |