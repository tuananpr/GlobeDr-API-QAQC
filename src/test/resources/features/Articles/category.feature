@regression @article @article-category
Feature: Article: Manage category

  Background:
    Given On SqlServer, I delete all hot english articles category
    Given On SqlServer, I delete articles category by name "update CATEGORY_AUTO"
    Given On SqlServer, I delete articles category by name "VN - NEW - HOT - AUTO"

  @article-category-1
  Scenario Outline: SystemAdmin create category (status always active = 1)
    When I login as "approver_1"
    And The request should be succeed
    When I re-create a article category with below info
      | categoryName   | description   | weight   | status   | type   | language   |
      | <categoryName> | <description> | <weight> | <status> | <type> | <language> |
    And The response should be
      | success                    | true           |
      | data.category.categoryName | <categoryName> |
      | data.category.status       | 1              |
      | data.category.language     | <language>     |
      | data.category.description  | <description>  |
      | data.category.weight       | <weight>       |
    And The "data.category.categoryName" should "not empty"
    Examples:
      | categoryName  | description                 | weight | language | status | type     |
      | CATEGORY_AUTO | EN - ACTIVE - HOT - AUTO    | 10     | 0        | 1      | HotTitle |
      | CATEGORY_AUTO | VN - NEW - NORMAL - AUTO    | 10     | 1        | 2      | Normal   |
      | CATEGORY_AUTO | EN - ACTIVE - NORMAL - AUTO | 10     | 0        | 1      | Normal   |


  @article-category-2
  Scenario Outline: Cant create two hot category
    When I login as "approver_1"
    When I re-create a article category with below info
      | categoryName    | description   | weight   | status   | type   | language   |
      | <categoryName1> | <description> | <weight> | <status> | <type> | <language> |
    And The request should be succeed
    When I re-create a article category with below info
      | categoryName    | description   | weight   | status   | type   | language   |
      | <categoryName2> | <description> | <weight> | <status> | <type> | <language> |
    And The request should be fail
    Examples:
      | categoryName1 | categoryName2         | description                           | weight | language | status | type     |
      | CATEGORY_AUTO | VN - NEW - HOT - AUTO | HOT - CREATED BY AUTOMATION - UPDATED | 10     | 0        | 1      | HotTitle |


  @article-category-3
  Scenario Outline: update category
    When I login as "approver_1"
    When I re-create a article category with below info
      | categoryName   | description   | weight | status | type   | language |
      | <categoryName> | <description> | 10     | 1      | Normal | 1        |
    And The request should be succeed
    When I update below information for article category "<categoryName>", language is 1
      | categoryName          | description          | weight   | status   | type   | language   |
      | update <categoryName> | update <description> | <weight> | <status> | <type> | <language> |
    And The response should be
      | success                    | true                  |
      | data.category.categoryName | update <categoryName> |
      | data.category.status       | 1                     |
      | data.category.language     | <language>            |
      | data.category.description  | update <description>  |
      | data.category.weight       | <weight>              |
    Examples:
      | categoryName  | description                           | weight | language | status | type     |
      | CATEGORY_AUTO | HOT - CREATED BY AUTOMATION - UPDATED | 10     | 0        | 1      | HotTitle |


  @article-category-4
  Scenario Outline: close category
    When I login as "approver_1"
    When I re-create a article category with below info
      | categoryName   | description   | weight   | status   | type   | language   |
      | <categoryName> | <description> | <weight> | <status> | <type> | <language> |
    And The request should be succeed

    When I update below information for article category "<categoryName>", language is <language>
      | status |
      | 4      |
    And The request should be succeed

    When Approver load article categories with below info
      | language   | categoryName   |
      | <language> | <categoryName> |
    Then The response should be
      | success                   | true |
      | data.categories[0].status | 4    |


    Examples:
      | categoryName  | description                 | weight | language | status | type   |
      | CATEGORY_AUTO | EN - ACTIVE - NORMAL - AUTO | 10     | 0        | 1      | Normal |