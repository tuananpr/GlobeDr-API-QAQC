@regression @org @feature-attribute
Feature: Feature attribute on org and branch

  As system
  I want to features of org based on system configuration


  Background:
    When I re-signup "manager_1" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_MY_THANH | manager_1 |

    And I login as "manager_1"
    And I accept join organization

  @feature-attribute-1
  Scenario: feature attribute for org and branch
    # Load feature on Org
    When I select org "BV_MY_THANH" that I manage
    And On org, I get feature attribute of staff
    And The response should be
      | success            | true                             |
      | data.list[0].text  | Organization information         |
      | data.list[1].text  | Department                       |
      | data.list[2].text  | Customer relationship management |
      | data.list[3].text  | Specialty                        |
      | data.list[4].text  | QR Code                          |
      | data.list[5].text  | Landing page themes              |
      | data.list[6].text  | Rating                           |
      | data.list[7].text  | Post                             |
      | data.list[8].text  | Voucher                          |
      | data.list[9].text  | Customer Care                    |
      | data.list[10].text | Campaign                         |
      | data.list[11].text | Product/Service                  |
      | data.list[12].text | Order                            |
      | data.list[13].text | Medical test                     |
      | data.list[14].text | Appointment list                 |
      | data.list[15].text | Appointment configuration        |
      | data.list[16].text | Branch                           |

    And On above org, I create a branch
      | name                 | address                  | country                                               | city                                  | district                        | ward                                | zipCode | email               | phones                                                                            | fax         | website         | type | status | latitude           | longitude          |
      | CHI_NHANH_MY_THANH_1 | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | 1    | 2      | 10.785729039069945 | 106.66766245536387 |
    And The response should be
      | success | true |
    # Load feature on Branch
    When I select org "CHI_NHANH_MY_THANH_1" that I manage
    And On org, I get feature attribute of staff
    And The response should be
      | success            | true                             |
      | data.list[0].text  | Organization information         |
      | data.list[1].text  | Department                       |
      | data.list[2].text  | Customer relationship management |
      | data.list[3].text  | Specialty                        |
      | data.list[4].text  | QR Code                          |
      | data.list[5].text  | Landing page themes              |
      | data.list[6].text  | Rating                           |
      | data.list[7].text  | Post                             |
      | data.list[8].text  | Voucher                          |
      | data.list[9].text  | Customer Care                    |
      | data.list[10].text | Campaign                         |
      | data.list[11].text | Product/Service                  |
      | data.list[12].text | Order                            |
      | data.list[13].text | Medical test                     |
      | data.list[14].text | Appointment list                 |
      | data.list[15].text | Appointment configuration        |
