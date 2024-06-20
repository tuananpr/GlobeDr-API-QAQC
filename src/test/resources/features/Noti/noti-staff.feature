@regression @noti-staff @noti
Feature: All notification of staff and org admin

#  //CommonTypes.NotiGroup.System
#  public static List<int> SystemNotiGroup = new List<int>() { (int)CommonTypes.NotiType.Post, (int)CommonTypes.NotiType.PostDetail, (int)CommonTypes.NotiType.Comment,
#  (int)CommonTypes.NotiType.ArticleDetail, (int)CommonTypes.NotiType.ArticleComment, (int)CommonTypes.NotiType.NewsComment,
#  (int)CommonTypes.NotiType.NewsDetail, (int)CommonTypes.NotiType.Article, (int)CommonTypes.NotiType.News,
#  (int)CommonTypes.NotiType.Forum, (int)CommonTypes.NotiType.SubAcc, (int)CommonTypes.NotiType.MedHistAccess,
#  (int)CommonTypes.NotiType.OrgSendHealthDoc, (int)CommonTypes.NotiType.OrgUpdateVaccine, (int)CommonTypes.NotiType.NotiToProvider,
#  (int)CommonTypes.NotiType.Order, (int)CommonTypes.NotiType.GiftBox, (int)CommonTypes.NotiType.Giftcard,
#  (int)CommonTypes.NotiType.Reset, (int)CommonTypes.NotiType.Others};
#
#
#  //CommonTypes.NotiGroup.Consultant
#  public static List<int> ConsultantNotiGroup = new List<int>() { (int)CommonTypes.NotiType.AskToGlobedrDoctor, (int)CommonTypes.NotiType.AssignProvider,
#  (int)CommonTypes.NotiType.RemoveAssign, (int)CommonTypes.NotiType.Accept, (int)CommonTypes.NotiType.Decline,
#  (int)CommonTypes.NotiType.SubmitToAuditor, (int)CommonTypes.NotiType.AddCommentConsult, (int)CommonTypes.NotiType.Approve,
#  (int)CommonTypes.NotiType.Reject, (int)CommonTypes.NotiType.PostClose, (int)CommonTypes.NotiType.Completed,
#  (int)CommonTypes.NotiType.RejectDoctor, (int)CommonTypes.NotiType.ReminderDoctor, (int)CommonTypes.NotiType.ReminderAuditor };
#
#
#  //CommonTypes.NotiGroup.Chat
#  public static List<int> ChatNotiGroup = new List<int>() {(int)CommonTypes.NotiType.Message, (int)CommonTypes.NotiType.ChatCreate, (int)CommonTypes.NotiType.ChatJoin,
#  (int)CommonTypes.NotiType.ChatLeft, (int)CommonTypes.NotiType.ChatSetName, (int)CommonTypes.NotiType.ChatSetAvatar,
#  (int)CommonTypes.NotiType.ChatMsgSend, (int)CommonTypes.NotiType.ChatMsgDelete, (int)CommonTypes.NotiType.ChatMsgSetSeen,
#  (int)CommonTypes.NotiType.ChatMsgSetEmotion, (int)CommonTypes.NotiType.ChatMsgDocSend, (int)CommonTypes.NotiType.ChatMsgDocDelete,
#  (int)CommonTypes.NotiType.ChatMsgDocSetEmotion, (int)CommonTypes.NotiType.ChatSendCampaign,
#  (int)CommonTypes.NotiType.ChatRemoveRecipient, (int)CommonTypes.NotiType.ChatSendPostForum,
#  (int)CommonTypes.NotiType.Message, (int)CommonTypes.NotiType.NotiViewMsg};
#
#
#
#  //CommonTypes.NotiGroup.Connection
#  public static List<int> ConnectionNotiGroup = new List<int>() { (int)CommonTypes.NotiType.RequestConnection, (int)CommonTypes.NotiType.MyConnection,
#  (int)CommonTypes.NotiType.AcceptConnection, (int)CommonTypes.NotiType.DeclineConnection, (int)CommonTypes.NotiType.OrgJoinUser,
#  (int)CommonTypes.NotiType.UserJoinOrg, (int)CommonTypes.NotiType.AddToGroup, (int)CommonTypes.NotiType.RemoveFromGroup,
#  (int)CommonTypes.NotiType.RemoveGroup, (int)CommonTypes.NotiType.RemoveConnection, (int)CommonTypes.NotiType.RemoveFromOrg };
#
#  Những hàm noti record
#
#  1. User/RequestJoinOrgs
#  2. User/AcceptJoinOrg
#  3. User/DeclineJoinOrg
#  4. User/RemoveJoinedOrg
#
#  5. Connection/RequestConnection
#
#  6. Org/SetOrgManager
#  7. Org/SetOrgAdmin
#  8. Org/VerifyStaffs
#  9. Org/AddStaffs
#
#

  Background:
    Given I re-signup "manager_1" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name   | type     | owner     |
      | BV_ONI | Hospital | manager_1 |

    And I login as "manager_1"
    And I accept join organization

  @noti-staff-1
  Scenario: orgAdmin receive noti after staff join and leave org
    And I login as "DOCTOR_TEO"
    And I join org
      | name   | country |
      | BV_ONI | VN      |
    Then The request should be succeed
    And I load joined orgs
      | language |
      | 0        |
    And The response should be
      | success              | true   |
      | data.list[0].orgName | BV_ONI |
    # admin login and check noti
    And I login as "manager_1"
    And I load notifications
      | groupType      |
      | ConnectionNoti |
    And The request should be succeed
    Then List "data.list[*].message" of response should contains
      | Send connection request to BV_ONI |
    # leave org
    And I login as "DOCTOR_TEO"
    And I leave org
      | name   | country |
      | BV_ONI | VN      |
    And The request should be succeed
    # admin login and check noti
    And I login as "manager_1"
    And I load notifications
      | groupType      |
      | ConnectionNoti |
    And The request should be succeed
    Then List "data.list[*].message" of response should contains
      | ?userLeaveOrgNoti |


  @noti-staff-2
  Scenario: Admin invite doctor to org
    # admin add doctor to org
    And I login as "manager_1"
    When On org, I add staff
      | displayName | country |
      | DOCTOR_TEO  | VN      |
    And The request should be succeed

    # doctor became to staff of org
    And I load staffs of department
      | deptName |
      |          |
    And List "data.list[*].userName" of response should contains
      | DOCTOR_TEO |

    #user login, accept and check noti
    And I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConnectionNoti |
    And The response should be
      | success              | true                                    |
      | data.list[0].message | You were added by BV_ONI as an employee |
    And I accept join organization
    And The request should be succeed

## New update 19-08-2020 :  admin or owner not receive noti after user accept to join org
  @noti-staff-3
  Scenario: Admin receive noti after staff accept join org
    And I login as "manager_1"
    When On org, I add staff
      | displayName | country |
      | DOCTOR_TEO  | VN      |
    And The request should be succeed

  #user login, accept and check noti
    And I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConnectionNoti |
    And The request should be succeed
    Then List "data.list[*].message" of response should contains
      | You were added by BV_ONI as an employee |

    And I load joined orgs
      | language |
      | 0        |
    And The response should be
      | success              | true   |
      | data.list[0].orgName | BV_ONI |
    And I accept join organization
    And The request should be succeed


# New update 19-08-2020 : admin or owner not receive noti after user decline to join org
  @noti-staff-4
  Scenario: Admin invite and receive noti after staff decline join org
    And I login as "manager_1"
    When On org, I add staff
      | displayName | country |
      | DOCTOR_TEO  | VN      |
    And The request should be succeed

  #user login, accept and check noti
    And I login as "DOCTOR_TEO"
    And I load notifications
      | groupType      |
      | ConnectionNoti |
    And The request should be succeed
    Then List "data.list[*].message" of response should contains
      | You were added by BV_ONI as an employee |

    And I decline join organization
    And The request should be succeed
