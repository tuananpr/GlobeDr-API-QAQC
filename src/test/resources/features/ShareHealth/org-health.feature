@regression @org-health @share-health`
Feature: Manager org sending medical document to user.

  As manager
  I want send health record to member
  In order to update  record to member (or patient ) then medical examination

  Background:
    #    Create organization to test
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "user_1" account and update profile
    When I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_DA_CHIEN | Hospital | manager_1 |

    When I login as "user_1"
    And I FOLLOW business
      | name        |
      | BV_DA_CHIEN |
    And The request should be succeed

    And I share my sub-account to org
      | name   | sharedConnectionName | sharedType   |
      | user_1 | BV_DA_CHIEN          | ShareOrgEdit |

    When I login as "manager_1"
    And I accept join organization
    And I select org "BV_DA_CHIEN" that I manage

  @org-health-1 @upload-file
  Scenario Outline: Org add health document to user successfully

    When As manager, I send health record to member
      | memberName | file                                | attributes | description                                | note                                       | medicalType   | docType   |
      | user_1     | data/image/immunization/immu-01.jpg | 1          | Ket qua tiem chung vaccine phong chong HIV | Ket qua tiem chung vaccine phong chong HIV | <medicalType> | <docType> |

    When I login as "user_1"
    And I load health document
      | medicalType |
      | <type>      |
    Then The response should be
      | success         | true |
      | data.totalCount | 1    |
    And I load notifications
      | groupType  |
      | HealthNoti |
    Then The response should be
      | success               | true      |
      | data.list[0].userName | <org>     |
      | data.list[0].message  | <message> |
      | data.list[0].seen     | false     |
    Examples:
      | org         | type         | docType | medicalType | message                            |
      | BV_DA_CHIEN | Prescription | 1       | 1           | Update health document information |
      | BV_DA_CHIEN | LabResult    | 2       | 2           | Update health document information |
      | BV_DA_CHIEN | Others       | 12      | 6           | Update health document information |
