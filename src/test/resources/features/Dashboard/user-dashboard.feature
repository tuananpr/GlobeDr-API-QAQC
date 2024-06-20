@regression @user-dashboard
Feature: Check permission before user entering page

  @precondition-user-dashboard-1
  Scenario: create user
    Given I re-signup "user_7" account and update profile


  @precondition-user-dashboard-2
  Scenario Outline: Create and promote user to become <type>
    Given I re-signup "<role>" account <type> and update profile
    Examples:
      | role             | type        |
      | COORDINATOR_AUTO | coordinator |
      | DOCTOR_TEO       | provider    |
      | Audit_Trung      | auditor     |
      | approver_1       | approver    |

  @precondition-user-dashboard-3
  Scenario: create manager and admin
    Given On SqlServer, I delete user by displayName "nguyen_van_ba"
    Given On SqlServer, I delete user by displayName "doctor_ba"
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "manager_2" account and update profile
    Given I re-signup "manager_3" account and update profile
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | type     | owner     | admin               |
      | BV_HOA_VAN | Hospital | manager_1 | manager_2,manager_3 |

    When I login as "manager_2"
    And I accept join organization

    When I login as "manager_3"
    And I accept join organization

    When I login as "manager_1"
    And I accept join organization
    And I select org "BV_HOA_VAN" that I manage
    Then The request should be succeed
      # set all features for manager_3
    And I set features for staff
      | featureAttributes | displayName |
      | ALL               | manager_3   |

       # manager create new user to deparment
    When I add department on org
      | name     |
      | Khoa Tim |
    And The request should be succeed

    When On org, I create new account on department
      | email              | title    | displayName   | password | deptName |
      | giamdocba@test.com | giám đốc | nguyen_van_ba | 123456   | Khoa Tim |
    Then The request should be succeed

       # manager create new doctor to deparment
    When On org, I create new account on department
      | email             | title | displayName | password | isProvider | deptName |
      | doctorba@test.com | bs    | doctor_ba   | 123456   | true       | Khoa Tim |
    Then The request should be succeed

  @user-dashboard-1
  Scenario Outline:  Check permission before user entering page
    And I login as "<account>"
    And I get page dashboard
      | platform   |
      | <platform> |
    Then The response should be
      | success         | true         |
      | data.totalCount | <totalCount> |
    Then List "data.list[*].text" of response should be "<containsList>"

    Examples:
      | account          | platform | totalCount | containsList                                                        |
      | system_admin_1   | Web      | 4          | Page system admin,Page approver,Page medical bussiness,Page profile |
      | system_admin_1   | Android  | 1          | Page medical bussiness                                              |
      | system_admin_1   | IOS      | 1          | Page medical bussiness                                              |
      | user_7           | Web      | 1          | Page profile                                                        |
      | COORDINATOR_AUTO | Web      | 2          | Page profile,Page medical bussiness                                 |
      | COORDINATOR_AUTO | Android  | 1          | Page medical bussiness                                              |
      | COORDINATOR_AUTO | IOS      | 1          | Page medical bussiness                                              |
      | DOCTOR_TEO       | Web      | 2          | Page profile,Page medical bussiness                                 |
      | DOCTOR_TEO       | Android  | 1          | Page medical bussiness                                              |
      | DOCTOR_TEO       | IOS      | 1          | Page medical bussiness                                              |
      | Audit_Trung      | Web      | 2          | Page profile,Page medical bussiness                                 |
      | Audit_Trung      | Android  | 1          | Page medical bussiness                                              |
      | Audit_Trung      | IOS      | 1          | Page medical bussiness                                              |
      | manager_1        | Web      | 2          | Page profile,Page manage Org                                        |
      | manager_1        | Android  | 1          | Page manage Org                                                     |
      | manager_1        | IOS      | 1          | Page manage Org                                                     |
      | manager_2        | Web      | 2          | Page profile,Page manage Org                                        |
      | manager_2        | Android  | 1          | Page manage Org                                                     |
      | manager_2        | IOS      | 1          | Page manage Org                                                     |


  @user-dashboard-2
  Scenario Outline:  Check permission before user entering page
    And I login as "<account>"
    And I get page dashboard
      | platform   |
      | <platform> |
    Then The response should be
      | success         | true         |
      | data.list       | []           |
      | data.totalCount | <totalCount> |

    Examples:
      | account | platform | totalCount |
      | user_7  | Android  | 0          |
      | user_7  | IOS      | 0          |


  @user-dashboard-3
  Scenario Outline:  check feature permission after <account> entering page <pageDashboard> on <platform>
    And I login as "<account>"
    And As user, I get features of page
      | platform   | pageDashboard   |
      | <platform> | <pageDashboard> |

    Then List "<list>" of response should be "<features>"

    Examples:
      | account          | platform | pageDashboard        | list              | features                                                                                                                             |
      | system_admin_1   | Web      | PageSystemAdmin      | data.list[*].text | Member,Organization,Globedr Config,Sponsors,Specialty                                                                                |
      | system_admin_1   | Web      | PageApprover         | data.list[*].text | Voucher,Article,Changelog,Language Translator,User feedback,Instructions                                                             |
      | system_admin_1   | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | system_admin_1   | Web      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Coordinator,Auditor,Answer consulting questions,Export data,Doctor reviews,Working Schedule,Report |
      | system_admin_1   | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | system_admin_1   | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | system_admin_1   | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | system_admin_1   | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | system_admin_1   | Android  | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Coordinator,Auditor,Answer consulting questions,Working Schedule,Report                            |
      | system_admin_1   | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | system_admin_1   | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | system_admin_1   | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | system_admin_1   | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | system_admin_1   | IOS      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Coordinator,Auditor,Answer consulting questions,Working Schedule,Report                            |
      | system_admin_1   | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | user_7           | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_7           | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | user_7           | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_7           | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_7           | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | user_7           | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_7           | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | user_7           | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_7           | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_7           | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | user_7           | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_7           | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | user_7           | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_7           | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_7           | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | COORDINATOR_AUTO | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Web      | PageMedicalBussiness | data.list[*].text | Chat,Coordinator,Export data,Doctor reviews                                                                                          |
      | COORDINATOR_AUTO | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | COORDINATOR_AUTO | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | Android  | PageMedicalBussiness | data.list[*].text | Chat,Coordinator                                                                                                                     |
      | COORDINATOR_AUTO | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | COORDINATOR_AUTO | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | COORDINATOR_AUTO | IOS      | PageMedicalBussiness | data.list[*].text | Chat,Coordinator                                                                                                                     |
      | COORDINATOR_AUTO | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | DOCTOR_TEO       | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Web      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Gift Point,Report                                     |
      | DOCTOR_TEO       | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | DOCTOR_TEO       | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | Android  | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Gift Point,Report                                     |
      | DOCTOR_TEO       | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | DOCTOR_TEO       | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | DOCTOR_TEO       | IOS      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Gift Point,Report                                     |
      | DOCTOR_TEO       | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | Audit_Trung      | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Web      | PageMedicalBussiness | data.list[*].text | Chat,Auditor                                                                                                                         |
      | Audit_Trung      | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | Audit_Trung      | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | Audit_Trung      | Android  | PageMedicalBussiness | data.list[*].text | Chat,Auditor                                                                                                                         |
      | Audit_Trung      | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | Audit_Trung      | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | Audit_Trung      | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | Audit_Trung      | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | Audit_Trung      | IOS      | PageMedicalBussiness | data.list[*].text | Chat,Auditor                                                                                                                         |
      | Audit_Trung      | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | approver_1       | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | approver_1       | Web      | PageApprover         | data.list[*].text | Voucher,Article,Changelog,Language Translator,User feedback,Instructions                                                             |
      | approver_1       | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | approver_1       | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | approver_1       | Web      | PageProfile          | data.list[*].text | Health,Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account         |

      | approver_1       | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | approver_1       | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | approver_1       | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | approver_1       | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | approver_1       | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | approver_1       | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | approver_1       | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | approver_1       | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | approver_1       | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | approver_1       | IOS      | PageProfile          | data.list         | []                                                                                                                                   |
        # the below account were created into org
      | user_giamdocba   | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Web      | PageProfile          | data.list[*].text | Chat,Biographical profile,Software account                                                                                           |

      | user_giamdocba   | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_giamdocba   | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | user_giamdocba   | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | user_giamdocba   | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | user_giamdocba   | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | user_giamdocba   | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                   |
      | user_giamdocba   | IOS      | PageProfile          | data.list         | []                                                                                                                                   |

      | doctorba         | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | doctorba         | Web      | PageApprover         | data.list         | []                                                                                                                                   |
      | doctorba         | Web      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | doctorba         | Web      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Report                                                |
      | doctorba         | Web      | PageProfile          | data.list[*].text | Chat,Biographical profile,Software account                                                                                           |

      | doctorba         | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | doctorba         | Android  | PageApprover         | data.list         | []                                                                                                                                   |
      | doctorba         | Android  | PageManageOrg        | data.list         | []                                                                                                                                   |
      | doctorba         | Android  | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Report                                                |
      | doctorba         | Android  | PageProfile          | data.list         | []                                                                                                                                   |

      | doctorba         | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                   |
      | doctorba         | IOS      | PageApprover         | data.list         | []                                                                                                                                   |
      | doctorba         | IOS      | PageManageOrg        | data.list         | []                                                                                                                                   |
      | doctorba         | IOS      | PageMedicalBussiness | data.list[*].text | Chat,Appointment with the patient,Answer consulting questions,Working Schedule,Report                                                |
      | doctorba         | IOS      | PageProfile          | data.list         | []                                                                                                                                   |


  @user-dashboard-4
  Scenario Outline:  Check feature permission after <account> entering page <pageDashboard> on <platform>
    Given I login as "<account>"
    When I select org "BV_HOA_VAN" that I manage
    And As org, I get features of page
      | platform   | pageDashboard   |
      | <platform> | <pageDashboard> |
    Then The response should be
      | success | true |
    Then List "<list>" of response should be contains all member into "<features>"
    Examples:
      | account   | platform | pageDashboard        | list              | features                                                                                                                                                                                                                            |
      | manager_1 | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Web      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Web      | PageManageOrg        | data.list[*].text | Organization information,Department,Customer relationship management,Specialty,QR Code,Landing page themes,Rating,Post,Voucher,Customer Care,Campaign,Product/Service,Order,Medical Test,Appointment list,Appointment configuration |
      | manager_1 | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Web      | PageProfile          | data.list[*].text | Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account                                                                                                               |

      | manager_1 | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Android  | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Android  | PageManageOrg        | data.list[*].text | QR Code,Customer Care,Medical Test,Appointment list                                                                                                                                                                                 |
      | manager_1 | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | Android  | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |

      | manager_1 | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | IOS      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | IOS      | PageManageOrg        | data.list[*].text | QR Code,Customer Care,Medical Test,Appointment list                                                                                                                                                                                 |
      | manager_1 | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_1 | IOS      | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |


      | manager_2 | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Web      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Web      | PageManageOrg        | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Web      | PageProfile          | data.list[*].text | Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account                                                                                                               |

      | manager_2 | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Android  | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Android  | PageManageOrg        | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | Android  | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |

      | manager_2 | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | IOS      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | IOS      | PageManageOrg        | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_2 | IOS      | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |

      | manager_3 | Web      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Web      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Web      | PageManageOrg        | data.list[*].text | Organization information,Department,Customer relationship management,Specialty,QR Code,Landing page themes,Rating,Post,Voucher,Customer Care,Campaign,Product/Service,Order,Medical Test,Appointment list,Appointment configuration |
      | manager_3 | Web      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Web      | PageProfile          | data.list[*].text | Chat,Ask consultant doctor,Appointment,Contact the pharmacy,Joined Organization,Biographical profile,Software account                                                                                                               |

      | manager_3 | Android  | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Android  | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Android  | PageManageOrg        | data.list[*].text | QR Code,Customer Care,Medical Test,Appointment list                                                                                                                                                                                 |
      | manager_3 | Android  | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | Android  | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |

      | manager_3 | IOS      | PageSystemAdmin      | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | IOS      | PageApprover         | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | IOS      | PageManageOrg        | data.list[*].text | QR Code,Customer Care,Medical Test,Appointment list                                                                                                                                                                                 |
      | manager_3 | IOS      | PageMedicalBussiness | data.list         | []                                                                                                                                                                                                                                  |
      | manager_3 | IOS      | PageProfile          | data.list         | []                                                                                                                                                                                                                                  |
