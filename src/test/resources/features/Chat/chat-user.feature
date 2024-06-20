@regression @chat @chat-user-user
Feature: Chat user

  As user
  I want to create room chat to friends
  In order to chat chit

  Background:
    Given I re-signup "user_1" account and update profile
    Given I re-signup "user_2" account and update profile
    Given I re-signup "user_3" account and update profile

    #Make connection to each others
    When I login as "user_1"
    And I request connection to below user
      | name   |
      | user_2 |
      | user_3 |
    When I login as "user_2"
    And I accept all request connection
    Then The request should be succeed

    When I login as "user_3"
    And I accept all request connection
    Then The request should be succeed
    #user chat to group : user 2, user 3
    When I login as "user_1"
    And As user, I create conversation with user
      | recipientName | msg                 |
      | user_2,user_3 | Chào 2 bạn trỏe :). |
    Then The request should be succeed
    And As user, I load conversation details
      | conversationName |
      |                  |
    Then The request should be succeed
    Then The recipient list should contain
      | user_2 |
      | user_3 |

  @chat-user-1
  Scenario: Change name conversation
    # user 1 change group name
    When I login as "user_1"
    And I change name my conversation to "TEST"
    And As user, I load conversation details
      | conversationName |
      | TEST             |
    And I check name return should be "TEST"
    And I load msg on conversation
      | conversationName |
      | TEST             |
    Then The response should be
      | success          | true                               |
      | data.list[0].msg | You changed the group name to TEST |
    # user 2 see message after changing group name
    When I login as "user_2"
    And As user, I load conversation details
      | conversationName |
      | TEST             |
    And I check name return should be "TEST"
    And I load msg on conversation
      | conversationName |
      | TEST             |
    Then The response should be
      | success          | true                                  |
      | data.list[0].msg | user_1 changed the group name to TEST |

  @chat-user-2 @upload-file
  Scenario: Add conversation from others
    When I login as "user_2"
    When As user, I add message into conversation with content
      | conversationName       | msg                                                                                                                                  |
      | user_1, user_2, user_3 | Oanh love Dai forever and ever, Dai will make Oanh get high like Oanh is on Everest mount anytime he can.                            |
      | user_1, user_2, user_3 | Tien is here, Tien fell in love with Hien, but Tien already have Thao as his girl friend. So Tien and Hien will never happy together |
    Then The request should be succeed
    When I send image "data/image/3mb/laptop.jpg" into conversation name "user_1, user_2, user_3"
    And The image into "data.msg.docs[0].docUrl" should be match "data/image/3mb/laptop.jpg"

    When I login as "user_3"
    And I load msg on conversation
      | conversationName |
      |                  |
    Then The response should be
      | success          | true                                                                                                                                 |
      | data.total       | 4                                                                                                                                    |
      | data.list[1].msg | Tien is here, Tien fell in love with Hien, but Tien already have Thao as his girl friend. So Tien and Hien will never happy together |
      | data.list[2].msg | Oanh love Dai forever and ever, Dai will make Oanh get high like Oanh is on Everest mount anytime he can.                            |
      | data.list[3].msg | Chào 2 bạn trỏe :).                                                                                                                  |
    And The image into "data.list[0].docs[0].docUrl" should be match "data/image/3mb/laptop.jpg"

  @chat-user-3-a
  Scenario: User can find conversation base on recipient name.
    When I login as "user_2"
    And I load my connection list
    And I find conversation
      | name   |
      | user_1 |
    Then The response should be
      | success                | true   |
      | data.conversation.name | user_1 |

  @chat-user-3-b
  Scenario: load user and user conversation
    When I login as "user_2"

    And I load user and user conversation
      | name   |
      | user_1 |
    Then The request should be succeed
    Then List "data.list[*].name" of response should contains
      | user_1, user_2, user_3 |
    Then List "data.list[*].name" of response should contains
      | user_1 |

  @chat-user-4
  Scenario: User leave group and can't add message after leave group
    When I login as "user_2"
    And I leave conversation
      | conversationName       |
      | user_1, user_2, user_3 |
    Then The request should be succeed
    And As user, I load conversations
      | conversationName |
      |                  |
    Then The response should be
      | success | true |
    When I login as "user_1"
    Then The recipient list should contain
      | user_3 |
    Then The recipient list should not contain
      | user_2 |


  @chat-user-5
  Scenario: Owner remove member into conversation
    When I login as "user_2"
    When As user, I add message into conversation with content
      | conversationName       | msg                         |
      | user_1, user_2, user_3 | Cho tao rời nhóm đi mài ơi. |
    Then The request should be succeed

    When I login as "user_1"
    And I kick a member out of conversation
      | recipientName |
      | user_2,user_3 |
    Then The request should be succeed
    Then The recipient list should not contain
      | user_2 |
      | user_3 |
    And I leave conversation
      | conversationName |
      |                  |
    And As user, I load conversations
      | conversationName |
      |                  |
    Then The response should be
      | success    | true |
      | data.total | 0    |
      | data.list  | []   |

  @chat-user-6
  Scenario: The owner leaving the conversation will have someone else replace
    When I login as "user_1"
    And As user, I create conversation with user
      | recipientName | msg   |
      | user_2,user_3 | hello |
    Then The request should be succeed
    And I leave conversation
      | conversationName |
      |                  |
    Then The request should be succeed

    When I login as "user_2"
    And As user, I load conversation details
      | conversationName |
      |                  |
    Then The response should be
      | success                   | true |
      | data.conversation.isOwner | true |

    Then The recipient list should contain
      | user_3 |


  @chat-user-7
  Scenario: Update avarar for conversation
    When I login as "user_1"
    And I change avatar conversation
      | file                      |
      | data/image/3mb/laptop.jpg |
    Then The request should be succeed
    And The image into "data.url" should be match "data/image/3mb/laptop.jpg"
    And I change avatar conversation
      | file                      |
      | data/image/3mb/laptop.jpg |
    Then The request should be succeed
    And The image into "data.url" should be match "data/image/3mb/laptop.png"


  @chat-user-8
  Scenario:  Display name of user into profile is changed. Display name of user into conversation is also changed
    When I login as "user_1"
    When I update my profile with below info
      | displayName | dob         | email                  | address                                     | country | gender | phone      |
      | Minh_Yên    | 2000-10-17T | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | US      | 1      | 0376559999 |
    Then The request should be succeed

    When I login as "user_2"
    And As user, I load conversation details
      | conversationName |
      |                  |
    Then The recipient list should not contain
      | user_1 |
    Then The recipient list should contain
      | Minh_Yên |
      | user_3   |


  @chat-user-9
  Scenario: User get seen
    Given I login as "user_3"
    When I get seen
    And The response should be
      | data.isSeen | false |
    And I load msg on conversation
      | conversationName |
      |                  |
    And I get seen
    Then The response should be
      | data.isSeen | true |


  @chat-user-10
  Scenario: User count unread
    Given I login as "user_3"
    When I count unread
    And The response should be
      | success | true |
    And The "data.totalCount" greater than "1"

