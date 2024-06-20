@prepare-test-data @prepare-location-user
Feature: Prepare data before testing.


  @prepare-system-admin-1
  Scenario Outline: Signup and verify should be success.
    Given On SqlServer, I remove "<role>" account from role updated config list
    When On SqlServer, I delete "<role>" account
    And I signup "<role>" account
    And The request should be succeed
    When On SqlServer, I set "<role>" account to become SYSTEM ADMIN
    When On SqlServer, I update "<role>" account to Globedr Config
    When I login as "<role>"
    And The request should be succeed
    When As sysAdmin, I search org
      | name    |
      | GlobeDr |
    Then The request should be succeed
    Examples:
      | role           |
      | system_admin_1 |


  @create-account-for-consult @delete-all
  Scenario Outline: Create and promote user to become <type>
    Given I re-signup "<role>" account <type> and update profile
    And The request should be succeed
    Examples:
      | role             | type        |
      | COORDINATOR_AUTO | coordinator |
      | coor1            | coordinator |
      | DOCTOR_TEO       | provider    |
      | Audit_HOA        | auditor     |
      | Audit_Trung      | auditor     |
      | DOCTOR_Duc_Dien  | provider    |
      | DOCTOR_TIEN      | provider    |
      | approver_1       | approver    |
      | approver_2       | approver    |
      | DOCTOR_MINH      | provider    |
      | DOCTOR_DUY       | provider    |


  @prepare-location-user-1
  Scenario Outline: Create doctor with specialty
    Given I re-signup "<name>" account provider and update profile
    When I login as "<name>"
    And I want to add "<specialty>" as my specialty
    Then The request should be succeed

    Examples:
      | gdrlogin   | name        | address                                              | specialty          |
      | 0000001000 | DoctorAuto  | 100 Điện Biên Phủ, Ho Chi Minh City                  | ALLERGY/IMMUNOLOGY |
      | 0000001001 | DoctorAuto1 | 101 Điện Biên Phủ, Ho Chi Minh City                  | ANESTHESIOLOGY     |
      | 0000001002 | DoctorAuto2 | 102 Điện Biên Phủ, Ho Chi Minh City                  | CARDIOLOGY         |
      | 0000001007 | DoctorAuto7 | 100 Quang Trung, phường 10, Gò Vấp, Ho Chi Minh City | Diabetes           |
