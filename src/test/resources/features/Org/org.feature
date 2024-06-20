@regression @org
Feature: Organization

#  public enum OrgAttributes : int
#  {
#    None = 0, DisableMakeConnection = 1, NonRegistered = 2,
#    EnableCompleteOrder = 32, EnableSendSMS = 64, Confirmed = 128,
#    EnableCall = 512, EnableAutoAddUser = 1024, EnableManagePost = 4096,
#    EnableManageVoucher = 16384, JoinedGdr = 32768, AutoReminderVaccine = 65536, ManualReminderVaccine = 131072,
#    EnablePayment = 524288, All = 1048575
#  }; //Bitwise
#  public enum OrgFeatureAttributes : int
#  {
#    None = 0, OrgInfo = 1, Deparment = 2, Member = 4, Specialty = 8, QRCode = 16, SettingInterface = 32, Rating = 64,
#    Post = 128, Voucher = 256, ChatInbox = 512, ChatCampaign = 1024, ProductService = 2048, Order = 4096, OrderMedicineTest = 8192,
#    Appointment = 16384, ALL = 32767
#  }


  Background:
    When On SqlServer, I delete organization by name "BV_BINH_HUNG"
    And I login as "system_admin_1"
    And I create a org
      | name         | type     | status | address                  | latitude           | longitude          | country                                               | city                                  | district                        | ward                                |
      | BV_BINH_HUNG | Business | Active | 520 CMT8, Quan 3, Tp.HCM | 10.785729039069945 | 106.66766245536387 | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    Then The response should be
      | data.newOrg.orgName       | BV_BINH_HUNG             |
      | data.newOrg.type          | 32                       |
      | data.newOrg.status        | 2                        |
      | data.newOrg.address       | 520 CMT8, Quan 3, Tp.HCM |
      | data.newOrg.district.name | Quận 3                   |
      | data.newOrg.ward.name     | Phường 11                |


  @org-1
  Scenario: set all attribute for org
    And As sysAdmin, I set attributes for org name "BV_BINH_HUNG"
      | None                     |
      | CallRoundRobin           |
      | NonRegistered            |
      | EnableShipTo             |
      | EnableSendSMSAppointment |
      | Confirmed                |
      | EnableAutoAddUser        |
      | EnableManagePost         |
      | EnableManageVoucher      |
      | JoinedGdr                |
      | AutoReminderVaccine      |
      | ManualReminderVaccine    |
      | EnablePayment            |

    Then The request should be succeed
    And I check attribute of organization name "BV_BINH_HUNG"
      | None                     |
      | CallRoundRobin           |
      | NonRegistered            |
      | EnableShipTo             |
      | EnableSendSMSAppointment |
      | Confirmed                |
      | EnableAutoAddUser        |
      | EnableManagePost         |
      | EnableManageVoucher      |
      | JoinedGdr                |
      | AutoReminderVaccine      |
      | ManualReminderVaccine    |
      | EnablePayment            |


  @org-3
  Scenario Outline: change appointment type for org
    When I change appointment type of org
      | appointmentType   |
      | <appointmentType> |
    And The request should be succeed
    And I get appointment type of org
    And The response should be
      | data.appointmentType | <appointmentType> |
    Examples:
      | appointmentType |
      | 1               |
      | 2               |
      | 4               |
      | 8               |
      | 16              |
      | 32              |
      | 64              |
      | 128             |
      | 256             |
      | 512             |
      | 1024            |


  @org-4
  Scenario: set Delivery Types for org
    When As sysAdmin, I search org
      | name         |
      | BV_BINH_HUNG |
    And I get delivery types of org
    Then The response should be
      | success            | true            |
      | data.total         | 1               |
      | data.list[0].value | 1               |
      | data.list[0].text  | Pickup in store |
      | data.list[0].tag   | 1               |

    And As sysAdmin, I set attributes for org name "BV_BINH_HUNG"
      | EnableShipTo |
    Then The request should be succeed
    And I check attribute of organization name "BV_BINH_HUNG"
      | EnableShipTo |

    When As sysAdmin, I search org
      | name         |
      | BV_BINH_HUNG |
    And I get delivery types of org
    Then The response should be
      | success            | true            |
      | data.total         | 2               |
      | data.list[0].value | 1               |
      | data.list[0].text  | Pickup in store |
      | data.list[0].tag   | 1               |
      | data.list[1].value | 2               |
      | data.list[1].text  | Ship to         |
      | data.list[1].tag   | 2               |
