#@regression @guide
Feature: Guide

  Background:
    Given On SqlServer, I delete guide with appscreen is "200"
    Given On SqlServer, I delete guide with appscreen is "201"
    And I login account
      | gdrLogin   | password | country |
      | 0767892632 | 123456   | VN      |

  @guide-1
  Scenario Outline: create new guide
    When I new guide
      | appScreen                | <appScreen>           |
      | featureApp               | <featureApp>          |
      | action                   | <action>              |
      | featureAppForAction      | <featureAppForAction> |
      | isFirst                  | <isFirst>             |
      | titles[0].language       | 0                     |
      | titles[0].content        | tiêu đề Test          |
      | titles[1].language       | 1                     |
      | titles[1].content        | Title Test            |
      | descriptions[0].language | 0                     |
      | descriptions[0].content  | Nội dung Test         |
      | descriptions[1].language | 1                     |
      | descriptions[1].content  | Content Test          |
    And The response should be
      | success                        | true                  |
      | data.guide.appScreen           | <appScreen>           |
      | data.guide.featureApp          | <featureApp>          |
      | data.guide.action              | <action>              |
      | data.guide.isFirst             | <isFirst>             |
      | data.guide.featureAppForAction | <featureAppForAction> |
    When I load guides
      | appScreen | <appScreen> |
    And The response should be
      | success                          | true                  |
      | data.list[0].appScreen           | <appScreen>           |
      | data.list[0].featureApp          | <featureApp>          |
      | data.list[0].action              | <action>              |
      | data.list[0].featureAppForAction | <featureAppForAction> |
      | data.list[0].isFirst             | <isFirst>             |
      | data.list[0].title               | <title>               |
      | data.list[0].description         | <description>         |
    Examples:
      | appScreen | action | isFirst | featureAppForAction | featureApp | title                                                                           | description                                                                        |
      | 200       | 1      | true    | 1                   | 123        | [{"Language":0,"Content":"tiêu đề Test"},{"Language":1,"Content":"Title Test"}] | [{"Language":0,"Content":"Nội dung Test"},{"Language":1,"Content":"Content Test"}] |
      | 200       | 2      | false   | 2                   | 123        | [{"Language":0,"Content":"tiêu đề Test"},{"Language":1,"Content":"Title Test"}] | [{"Language":0,"Content":"Nội dung Test"},{"Language":1,"Content":"Content Test"}] |


  @guide-2
  Scenario Outline: delete guides
    When I new guide
      | appScreen                | <appScreen>           |
      | featureApp               | <featureApp>          |
      | action                   | <action>              |
      | featureAppForAction      | <featureAppForAction> |
      | isFirst                  | <isFirst>             |
      | titles[0].language       | 0                     |
      | titles[0].content        | tiêu đề Test          |
      | titles[1].language       | 1                     |
      | titles[1].content        | Title Test            |
      | descriptions[0].language | 0                     |
      | descriptions[0].content  | Nội dung Test         |
      | descriptions[1].language | 1                     |
      | descriptions[1].content  | Content Test          |
    And The request should be succeed
    And I delete guide
      | featureApp | <featureApp> |
    And The request should be succeed
    When I load guides
      | appScreen | <appScreen> |
    And The response should be
      | success         | true |
      | data.list       | []   |
      | data.totalCount | 0    |
    Examples:
      | appScreen | action | isFirst | featureAppForAction | featureApp |
      | 200       | 1      | true    | 1                   | 123        |


  @guide-3
  Scenario Outline: update guide

    When I new guide
      | appScreen                | <appScreen>           |
      | featureApp               | <featureApp>          |
      | action                   | <action>              |
      | featureAppForAction      | <featureAppForAction> |
      | isFirst                  | <isFirst>             |
      | titles[0].language       | 0                     |
      | titles[0].content        | tiêu đề Test          |
      | titles[1].language       | 1                     |
      | titles[1].content        | Title Test            |
      | descriptions[0].language | 0                     |
      | descriptions[0].content  | Nội dung Test         |
      | descriptions[1].language | 1                     |
      | descriptions[1].content  | Content Test          |
    And The request should be succeed
    When I update guide
      | appScreen                | 201                  |
      | featureApp               | <featureApp>         |
      | action                   | 2                    |
      | featureAppForAction      | 3                    |
      | isFirst                  | false                |
      | titles[0].language       | 0                    |
      | titles[0].content        | update tiêu đề Test  |
      | titles[1].language       | 1                    |
      | titles[1].content        | update Title Test    |
      | descriptions[0].language | 0                    |
      | descriptions[0].content  | update Nội dung Test |
      | descriptions[1].language | 1                    |
      | descriptions[1].content  | update Content Test  |
    And The response should be
      | success                        | true          |
      | data.guide.appScreen           | 201           |
      | data.guide.featureApp          | <featureApp>  |
      | data.guide.action              | 2             |
      | data.guide.isFirst             | false         |
      | data.guide.featureAppForAction | 3             |
      | data.guide.title               | <title>       |
      | data.guide.description         | <description> |
    When I load guides
      | appScreen | 201 |
    And The response should be
      | success                          | true          |
      | data.list[0].appScreen           | 201           |
      | data.list[0].featureApp          | <featureApp>  |
      | data.list[0].action              | 2             |
      | data.list[0].isFirst             | false         |
      | data.list[0].featureAppForAction | 3             |
      | data.list[0].title               | <title>       |
      | data.list[0].description         | <description> |
    Examples:
      | appScreen | action | isFirst | featureAppForAction | featureApp | title                                                                                         | description                                                                                      |
      | 200       | 1      | true    | 1                   | 123        | [{"Language":0,"Content":"update tiêu đề Test"},{"Language":1,"Content":"update Title Test"}] | [{"Language":0,"Content":"update Nội dung Test"},{"Language":1,"Content":"update Content Test"}] |


  @guide-4
  Scenario Outline: create new screen
    # create new screen
    When I new screen
      | appScreen          | <appScreen>          |
      | action             | <action>             |
      | isFirst            | <isFirst>            |
      | available          | <available>          |
      | appScreenForAction | <appScreenForAction> |

    And The response should be
      | success                           | true                 |
      | data.appScreen.appScreen          | <appScreen>          |
      | data.appScreen.action             | <action>             |
      | data.appScreen.isFirst            | <isFirst>            |
      | data.appScreen.available          | <available>          |
      | data.appScreen.appScreenForAction | <appScreenForAction> |
    # load screens (api not support filter)
    When I load screens
      | null | null |

    And List "data.list" of response has object with below info
      | appScreen          | <appScreen>          |
      | action             | <action>             |
      | isFirst            | <isFirst>            |
      | available          | <available>          |
      | appScreenForAction | <appScreenForAction> |
    Examples:
      | appScreen | action | isFirst | available | appScreenForAction |
      | 200       | 1      | true    | true      | 1                  |
      | 200       | 2      | false   | false     | 2                  |


  @guide-5
  Scenario Outline: update screen
    # create new screen
    When I new screen
      | appScreen          | <appScreen> |
      | action             | 1           |
      | isFirst            | true        |
      | available          | true        |
      | appScreenForAction | 1           |
    And The request should be succeed
    # update screen
    When I update screen
      | appScreen          | <appScreen>          |
      | action             | <action>             |
      | isFirst            | <isFirst>            |
      | available          | <available>          |
      | appScreenForAction | <appScreenForAction> |
    And The response should be
      | success                           | true                 |
      | data.appScreen.appScreen          | <appScreen>          |
      | data.appScreen.action             | <action>             |
      | data.appScreen.isFirst            | <isFirst>            |
      | data.appScreen.available          | <available>          |
      | data.appScreen.appScreenForAction | <appScreenForAction> |
    # load screens
    When I load screens
      | null | null |
    And List "data.list" of response has object with below info
      | appScreen          | <appScreen>          |
      | action             | <action>             |
      | isFirst            | <isFirst>            |
      | available          | <available>          |
      | appScreenForAction | <appScreenForAction> |
    Examples:
      | appScreen | action | isFirst | available | appScreenForAction |
      | 200       | 3      | false   | false     | 3                  |

  @guide-6
  Scenario Outline: delete screen
    # create new screen
    When I new screen
      | appScreen          | <appScreen>          |
      | action             | <action>             |
      | isFirst            | <isFirst>            |
      | available          | <available>          |
      | appScreenForAction | <appScreenForAction> |
    And The request should be succeed
    # delete screen
    When I delete screen
      | appScreen | <appScreen> |
    And The request should be succeed
    # load screens
    When I load screens
      | null | null |
    And List "data.list[*].appScreen" of response should be not contains "<appScreen>"

    Examples:
      | appScreen | action | isFirst | available | appScreenForAction |
      | 200       | 1      | true    | true      | 1                  |



