@regression @consult-settings
Feature: Consult settings

  As GlobeDr Organization
  I want to settings consult as max time video call, Warning time video call, limit time video call, consult fees

  Background:
    Given I re-signup "manager_1" account
    And I login as "system_admin_1"
    And As sysAdmin, I add staff for org name "GlobeDr"
      | displayName | country | isAdmin |
      | manager_1   | VN      | true    |
    Then The request should be succeed

    And I set features for staff
      | featureAttributes | displayName |
      | ConsultConfig     | manager_1   |
    Then The request should be succeed

    Given I login as "manager_1"
    And I accept join organization
    And I select org "GlobeDr" that I manage


  @consult-settings-1
  Scenario: Set consult time
    And As manager, I setting consult
      | maxTimeVideoCallInSeconds | warningTimeVideoCallInSeconds | limitedTimeVideoCallInSeconds |
      | 300                       | 10                            | 5                             |
    Then The request should be succeed


  @consult-settings-2
  Scenario: Set consult fees
    And As manager, I setting consult
      | normalConsultantPntActFees | videoConsultantPntActFees | maxTimeVideoCallInSeconds | warningTimeVideoCallInSeconds | limitedTimeVideoCallInSeconds |
      | 1                          | 2                         | 300                       | 10                            | 5                             |
    Then The request should be succeed

    When I login as "user_2"
    And I load function fees
      | type                   |
      | NormalConsultantPntAct |
    And The response should be
      | success            | true |
      | data.info.pointFee | 1    |

    And I load function fees
      | type                  |
      | VideoConsultantPntAct |
    And The response should be
      | success            | true |
      | data.info.pointFee | 2    |


