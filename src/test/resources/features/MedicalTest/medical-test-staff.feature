@regression @medical-test @medical-test-staff
Feature: Medicine Test - Staff

  As manager
  I assign medical test to staff to take sample.

  Background:
    Given I re-signup "manager_2" account and update profile
    And I re-signup "user_1" account and update profile
    And I re-signup "nv_ly_thong" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type       | owner     | staff       |
      | BV_MY_THANH | LabAndTest | manager_2 | nv_ly_thong |

    And I set features for staff
      | featureAttributes      | displayName |
      | MedicalTestTakenSample | nv_ly_thong |

    When I login as "nv_ly_thong"
    And I accept join organization

    When I login as "manager_2"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage

    And I create product category with below info
      | status | nameVI   | nameEN |
      | Active | Suc Khoe | Health |
    Then The request should be succeed

    # Create product services
    When I create new product
      | name              | description             | orgProductType | link                  | isInternal | isPublic | isVisible |
      | Vật lý trị liệu   | mô tả vật lý trị liệu   | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
      | Dịch vụ khám răng | mô tả Dịch vụ khám răng | MedicalTest    | https://jsonpath.com/ | true       | true     | true      |
    Then The request should be succeed

    And I create new item for product name "Vật lý trị liệu"
      | currency | currencyName | description             | name              | price   |
      | 2        | VND          | mô tả điều trị cột sống | Điều trị cột sống | 1500000 |
      | 2        | VND          | mô tả điều trị giãn cơ  | Điều trị giãn cơ  | 1500000 |
    Then The request should be succeed

    And I create new item for product name "Dịch vụ khám răng"
      | currency | currencyName | description             | name      | price   |
      | 2        | VND          | mô tả dịch vụ trám răng | Trám răng | 1500000 |
    Then The request should be succeed
    When I login as "user_1"
    And As user, I register medical test
      | orgName     | deliveryType | address                   | phone     | productAndService                                                              |
      | BV_MY_THANH | 4            | 230 cong hoa tan binh hcm | 089999999 | Vật lý trị liệu:Điều trị cột sống+Điều trị giãn cơ,Dịch vụ khám răng:Trám răng |
    Then The request should be succeed


  @medical-test-staff-1
  Scenario: staff of medical test load dashboard and features
    And I login as "nv_ly_thong"
    And I get page dashboard
      | platform |
      | Web      |
    Then List "data.list[*].text" of response should be "Page manage Org,Page profile"
    And I get page dashboard
      | platform |
      | Android  |
    Then List "data.list[*].text" of response should be "Page manage Org"
    And I get page dashboard
      | platform |
      | IOS      |
    Then List "data.list[*].text" of response should be "Page manage Org"

    When I login as "nv_ly_thong"
    And I select org "BV_MY_THANH" that I manage
    And As org, I get features of page
      | platform | pageDashboard |
      | Web      | PageManageOrg |
    Then List "data.list[*].text" of response should be "Taken Sample Test"

    And As org, I get features of page
      | platform | pageDashboard |
      | Android  | PageManageOrg |
    Then List "data.list[*].text" of response should be "Taken Sample Test"

    And As org, I get features of page
      | platform | pageDashboard |
      | IOS      | PageManageOrg |
    Then List "data.list[*].text" of response should be "Taken Sample Test"

  @medical-test-staff-2
  Scenario: Org assign staff to take sample
    # user create medical test
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    And I assign staff name "nv_ly_thong" to take sample of patient name "user_1"
    And The request should be succeed

    When I login as "nv_ly_thong"
    And I select org "BV_MY_THANH" that I manage
    # staff load medical test that is assigned
    And As staff, I load orders
      | isTakenSampleStaff |
      | true               |
    And The response should contains
      | success                         | true                               |
      | data.list[0].receiverName       | user_1                             |
      | data.list[0].productServiceName | Vật lý trị liệu, Dịch vụ khám răng |
    # org view order process
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    And As manager, I load order detail
      | displayName |
      | user_1      |
    And The response should contains
      | success                                | true        |
      | data.info.status.name                  | New         |
      | data.info.userInfo.displayName         | user_1      |
      | data.info.takenSampleStaff.displayName | nv_ly_thong |
      | data.info.personOrgInfo.name           | BV_MY_THANH |

  @medical-test-staff-3
  Scenario: staff cancel medical test
    # user create medical test
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    And I assign staff name "nv_ly_thong" to take sample of patient name "user_1"
    And The request should be succeed

    When I login as "nv_ly_thong"
    And I select org "BV_MY_THANH" that I manage
    # staff update medical test that is assigned
    When As org, I update medical test for name "user_1"
      | orderProcessType | notes |
      | CancelOrder      | abcd  |
    And The request should be succeed

    # org view order process
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    And As org, I view orders process
      | displayName | orderType   |
      | user_1      | MedicalTest |
    And The response should contains
      | success                           | true                    |
      | data.list[0].orderProcessTypeName | Ordered test            |
      | data.list[0].personOrgInfo.name   | user_1                  |
      | data.list[1].orderProcessTypeName | ?assignTakenSampleStaff |
      | data.list[1].personOrgInfo.name   | BV_MY_THANH             |
      | data.list[2].orderProcessTypeName | Cancelled test          |
      | data.list[2].personOrgInfo.name   | BV_MY_THANH             |

    And As manager, I load order detail
      | displayName |
      | user_1      |
    And The response should contains
      | success                                | true        |
      | data.info.status.name                  | Cancel      |
      | data.info.userInfo.displayName         | user_1      |
      | data.info.takenSampleStaff.displayName | nv_ly_thong |
      | data.info.personOrgInfo.name           | BV_MY_THANH |


  @medical-test-staff-4
  Scenario: Org assign staff to take sample then staff load orders process
    # user create medical test
    When I login as "manager_2"
    And I select org "BV_MY_THANH" that I manage
    And I assign staff name "nv_ly_thong" to take sample of patient name "user_1"
    And The request should be succeed

    When I login as "nv_ly_thong"
    And I select org "BV_MY_THANH" that I manage
    # staff load process of medical test that is assigned
    And As org, I view orders process
      | displayName | orderType   |
      | user_1      | MedicalTest |
    And The response should contains
      | success                           | true                    |
      | data.list[0].orderProcessTypeName | Ordered test            |
      | data.list[0].personOrgInfo.name   | user_1                  |
      | data.list[1].orderProcessTypeName | ?assignTakenSampleStaff |
      | data.list[1].personOrgInfo.name   | BV_MY_THANH             |