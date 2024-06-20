@regression @ui-org
Feature: Change UI of org

  Background:
    When I re-signup "manager_1" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_MY_THANH | manager_1 |

    Given I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage


  @ui-org
  Scenario Outline: change UI for org
    When I change UI Type of org is "<UIType>"
    Then The request should be succeed
    And I get information org
      | orgName     |
      | BV_MY_THANH |
    And The response should be
      | data.orgInfo.uiType | <UIType> |
    Examples:
      | UIType |
      | 1      |
      | 2      |
      | 3      |
      | 4      |

