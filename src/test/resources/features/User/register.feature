@regression @register-device
Feature: Register device

  As user
  I want to receive notification from user
  In order to receive information as fast as possible


  @register-device-1
  Scenario Outline: register device successfully.
    Given On SqlServer, I delete "user_3" account
    When I signup "user_3" account
    And I login as "user_3"
    Then The request should be succeed
    Given I register device
      | osVersion   | installationId   | deviceId   | handle   | handlePushKit   | isManage   | platform   | deviceModel   | osName   |
      | <osVersion> | <installationId> | <deviceId> | <handle> | <handlePushKit> | <isManage> | <platform> | <deviceModel> | <osName> |
    And The request should be succeed
    Examples:
      | osVersion | installationId | deviceId                                                         | handle                                                           | handlePushKit                                                    | isManage | platform | appType | deviceModel | osName |
      | 12.3.1    | 0              | 3m4ihc9y8Tqe6nWRi6WVvBDYIWfgatYc6HxBAmtOesb6lYgwtHRPNSzhg1vd+MYb | 06607AC0A3E7BB8B8732B5D0B1602DE15611365FE8D7A2C21A33AB0A6C62F5DA | ab75fd5f13e81e52aca9e5b665b8dde5db4cdc864a9aadaec5c8b38af5ecda9e | false    | 1        | 1       | iPhone      | iPhone |

  @register-device-2
  Scenario Outline: register device by GenInstallation successfully.
    Given On SqlServer, I delete "user_3" account
    When I signup "user_3" account
    And I login as "user_3"
    Then The request should be succeed
    Given I register device by GenInstallation
      | osVersion   | installationId   | deviceId   | handle   | handlePushKit   | isManage   | platform   | deviceModel   | osName   |
      | <osVersion> | <installationId> | <deviceId> | <handle> | <handlePushKit> | <isManage> | <platform> | <deviceModel> | <osName> |
    And The request should be succeed
    Examples:
      | osVersion | installationId | deviceId                                                         | handle                                                           | handlePushKit                                                    | isManage | platform | appType | deviceModel | osName |
      | 12.3.1    | 0              | 3m4ihc9y8TrHMMITEmTYRk15Na0lue2mmvzt2/0SjK6v0z6/33ggWfLWAhH/494k | 776004B4E6E4F0EC1634BB24470158C64E795B3736F4A0342C98A784575A9418 | 094b40b887ae30a7e362007319ad67feb4a6e51d616686caba7bd9af9c316222 | false    | 1        | 1       | iPhone      | iPhone |
