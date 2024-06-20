@regression @org @department-staff @staff @department
Feature: Manager add user to become staff

  As Manager
  I want to add any account to become my staff
  In Order to staff work for my org

  Background:
    Given I re-signup "manager_1" account
    Given I re-signup "manager_2" account
    Given I re-signup "user_4" account

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     | admin     |
      | BV_HAI_QUAN | manager_1 | manager_2 |

    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage
    And I set features for staff
      | featureAttributes | displayName |
      | Deparment         | manager_2   |
    When I login as "manager_2"
    And I accept join organization


  @department-staff-1-a
  Scenario Outline: create <description>
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | user_4      | VN      | <staffAttributes> |
    And The request should be succeed

    And I login as "user_4"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage
    And As org, I get features of page
      | platform | pageDashboard |
      | Web      | PageManageOrg |
    Then List "<list>" of response should be contains all member into "<existfeatures>"

    Examples:
      | description             | user      | staffAttributes | platform | list              | existfeatures                                             |
      | staff is Administrators | manager_1 | Admin           | web      | data.list         | []                                                        |
      | staff is Content        | manager_1 | Content         | web      | data.list[*].text | Organization information,QR Code,Landing page themes,Post |


  @department-staff-1-b
  Scenario Outline: Must settings maximum customer care into org before creating <description>
    And I login as "system_admin_1"
    And As sysAdmin, I setting customer care
      | name        | maxCustomerCare |
      | BV_HAI_QUAN | 4               |
    Then The request should be succeed

    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country | staffAttributes   |
      | user_4      | VN      | <staffAttributes> |
    And The request should be succeed

    And I login as "user_4"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage
    And As org, I get features of page
      | platform | pageDashboard |
      | Web      | PageManageOrg |
    Then List "<list>" of response should be contains all member into "<existfeatures>"

    Examples:
      | description        | user      | staffAttributes | platform | list              | existfeatures                                                        |
      | staff is reception | manager_1 | Reception       | web      | data.list[*].text | Department,Customer Care,Campaign,Product/Service,Appointment list   |
      | staff is marketing | manager_1 | Marketing       | web      | data.list[*].text | Customer relationship management,QR Code,Post,Customer Care,Campaign |
      | staff is nurse     | manager_1 | Nurse           | web      | data.list[*].text | Customer Care,Campaign,Product/Service,Appointment list              |

  @department-staff-2
  Scenario Outline: Manager add admin for org successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country | isAdmin |
      | user_4      | VN      | true    |
    And The request should be succeed
    And I login as "user_4"
    And I accept join organization
    And I select org "BV_HAI_QUAN" that I manage
    Then The response should be
      | data.orgs[0].name | BV_HAI_QUAN |
    Examples:
      | user      |
      | manager_1 |

  @department-staff-3
  Scenario Outline: Manager add admin for org successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country | isAdmin |
      | user_4      | VN      | true    |
    And The request should be succeed
    And I login as "user_4"
    And I accept join organization
    And I select org "BV_HAI_QUAN" that I manage
    Then The response should be
      | data.orgs[0].name | BV_HAI_QUAN |
    Examples:
      | user      |
      | manager_1 |

  @department-staff-4
  Scenario Outline: Owner or Admin add staff for org successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country |
      | user_4      | VN      |
    And I load staffs of department
      | deptName |
      |          |
    And I have '3' staff in dept
    Then The list staff should be name
      | user_4    |
      | manager_1 |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |


  @department-staff-5
  Scenario Outline: Owner or Admin remove staff successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country |
      | user_4      | VN      |
    And The request should be succeed
    When I remove staffs with name
      | displayName |
      | user_4      |
    And The request should be succeed
    And I load staffs of department
      | deptName |
      |          |
    Then List "data.list" of response should be not contains "user_4"
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |

  @department-staff-7
  Scenario Outline: Owner add staff to 1 dept successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When I add department on org
      | name      |
      | nhan vien |
    And The request should be succeed
    And I load all department of org above
    Then The response should be
      | data.list[0].name | nhan vien |

    When On org, I add staff
      | displayName | country | isAdmin | deptName  |
      | user_4      | VN      | true    | nhan vien |
    And The request should be succeed
    And I load staffs of department
      | deptName  |
      | nhan vien |
    And I have '1' staff in dept
    Then The response should be
      | data.list[0].userName | user_4 |
    Examples:
      | user      |
      | manager_1 |

  @department-staff-8
  Scenario Outline: Owner or Admin move staff in other dept successfully
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country |
      | user_4      | VN      |
    When I add department on org
      | name      |
      | nhan vien |
    And The request should be succeed

    And I move staff to above department
      | displayName | toDeptName |
      | user_4      | nhan vien  |
    Then The request should be succeed
    And I load staffs of department
      | deptName  |
      | nhan vien |
    And I have '1' staff in dept
    Then The response should be
      | data.list[0].userName | user_4 |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |

  @department-staff-9
  Scenario Outline: Owner or Admin set hide staff success
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country |
      | user_4      | VN      |
    Then The request should be succeed
    When On org, I set hide staff
      | displayName | isHide |
      | user_4      | true   |
    And The request should be succeed
    And I load staffs of department
      | deptName |
      |          |
    And I have '3' staff in dept
    And The list staff should be name
      | user_4    |
      | manager_1 |
      | manager_2 |
    Then The response return user "user_4" is hide
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |

  @department-staff-10
  Scenario Outline: Can't user user that become to my staff
    When I login as "<user>"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country |
      | user_4      | VN      |
      | user_4      | VN      |
    And The request should be fail
    Then The response should be
      | message | This staff existed |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |

  @department-staff-11
  Scenario Outline: Staff can't remove Owner or Admin
    When I login as "manager_1"
    And I select org "BV_HAI_QUAN" that I manage
    Then The request should be succeed
    When On org, I add staff
      | displayName | country | isAdmin |
      | user_4      | VN      | true    |
    And The request should be succeed
    And I set features for staff
      | featureAttributes | displayName |
      | Deparment         | user_4      |
    And I login as "user_4"
    And I accept join organization
    And I select org "BV_HAI_QUAN" that I manage
    When I load all department of org above
    And I load staffs of department
      | deptName |
      |          |
    And I remove staffs with name
      | displayName |
      | <user>      |
    And The request should be fail
    Then The response should be
      | message | Please try again later |
    Examples:
      | user      |
      | manager_1 |
      | manager_2 |


