@regression @org @specialty-org
Feature: Specialty org

  Background:
    When I re-signup "manager_1" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name   | owner     | specialties |
      | BV_NHI | manager_1 | Diabetes    |


  @spec-1
  Scenario: add specialty for organization successfully
    And I get list specialty of org
    Then The response should be
      | data.specialties[0].name | Diabetes |


  @spec-2
  Scenario: remove specialty for organization
    When I want to remove the list specialty for organization "BV_NHI"
      | Diabetes |
    Then The request should be succeed
    And I get list specialty of org
    Then The response should be
      | data.specialties | [] |

