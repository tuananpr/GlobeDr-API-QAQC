@regression @branch
Feature: Branch

  As Manage
  I want create branch for my organization

  Background:
    And I re-signup "manager_1" account and update profile
    Given On SqlServer, I delete organization by name "CHI_NHANH_MY_THANH_1"
    Given On SqlServer, I delete organization by name "CHI_NHANH_MY_THANH_1 Edited"

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_MY_THANH | Hospital | manager_1 |

    And I login as "manager_1"
    And I accept join organization
    And I select org "BV_MY_THANH" that I manage


  @branch-1
  Scenario Outline: create new branch
    And On above org, I create a branch
      | name      | address                  | country                                               | city                                  | district                        | ward                                | zipCode | email               | phones                                                                            | fax         | website         | type   | status | latitude           | longitude          |
      | <orgName> | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | <type> | 2      | 10.785729039069945 | 106.66766245536387 |

    And The response should be
      | success                        | true                                            |
      | data.newBranch.isBranch        | true                                            |
      | data.newBranch.orgName         | <orgName>                                       |
      | data.newBranch.type            | <type>                                          |
      | data.newBranch.status          | 2                                               |
      | data.newBranch.address         | 520 CMT8, Quan 3, Tp.HCM, Hồ Chí Minh, Việt Nam |
      | data.newBranch.country.name    | Việt Nam                                        |
      | data.newBranch.district.name   | Quận 3                                          |
      | data.newBranch.ward.name       | Phường 11                                       |
      | data.newBranch.zipCode         | 68437                                           |
      | data.newBranch.email           | info@globedr.com.vn                             |
      | data.newBranch.phones.[0].name | 0289234675                                      |
      | data.newBranch.fax             | 13235551234                                     |
      | data.newBranch.website         | www.globedr.com                                 |
    And On above org, I load branch
    And The response should be
      | success                      | true                                            |
      | data.list[0].isBranch        | true                                            |
      | data.list[0].orgName         | <orgName>                                       |
      | data.list[0].type            | <type>                                          |
      | data.list[0].status          | 2                                               |
      | data.list[0].address         | 520 CMT8, Quan 3, Tp.HCM, Hồ Chí Minh, Việt Nam |
      | data.list[0].country.name    | Việt Nam                                        |
      | data.list[0].district.name   | Quận 3                                          |
      | data.list[0].ward.name       | Phường 11                                       |
      | data.list[0].zipCode         | 68437                                           |
      | data.list[0].email           | info@globedr.com.vn                             |
      | data.list[0].phones.[0].name | 0289234675                                      |
      | data.list[0].fax             | 13235551234                                     |
      | data.list[0].website         | www.globedr.com                                 |
    Examples:
      | orgName              | typeName      | type |
      | CHI_NHANH_MY_THANH_1 | Hospital      | 1    |
      | CHI_NHANH_MY_THANH_1 | Pharmacy      | 4    |
      | CHI_NHANH_MY_THANH_1 | Business      | 32   |
      | CHI_NHANH_MY_THANH_1 | ClinicOrgType | 2    |
      | CHI_NHANH_MY_THANH_1 | Dental        | 8    |
      | CHI_NHANH_MY_THANH_1 | LabAndTest    | 16   |
      | CHI_NHANH_MY_THANH_1 | School        | 64   |
      | CHI_NHANH_MY_THANH_1 | Others        | 128  |
      | CHI_NHANH_MY_THANH_1 | Factory       | 256  |


  @branch-2
  Scenario Outline: remove branch
    And On above org, I create a branch
      | name      | address                  | country                                               | city                                  | zipCode | email               | phones                                                                            | fax         | website         | type | status | latitude           | longitude          |
      | <orgName> | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | 1    | 2      | 10.785729039069945 | 106.66766245536387 |

    And On above org, I delete branch name "<orgName>"
    And The request should be succeed
    And On above org, I load branch
    And The response should be
      | success         | true |
      | data.totalCount | 0    |
      | data.list       | []   |
    Examples:
      | orgName              |
      | CHI_NHANH_MY_THANH_1 |

  @branch-3
  Scenario Outline: update branch
    And On above org, I create a branch
      | name      | address                  | country                                               | city                                  | district                        | ward                                | zipCode | email               | phones                                                                            | fax         | website         | type | status | latitude           | longitude          |
      | <orgName> | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | 1    | 2      | 10.785729039069945 | 106.66766245536387 |
    And The request should be succeed

    And On above org, I update branch name "<orgName>" with below info
      | orgName          | country                                               | city                                  | district                                          | ward                                      | website         | workHours    | address                                                     | zipCode | email               | fax         | longitude  | latitude  | type | status |
      | <orgName> Edited | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"<districtCode>","name":"<districtName>"} | {"code":"<wardCode>","name":"<wardName>"} | www.globedr.com | 6h.AM-24H.PM | 74 Ông Ích Khiêm, Phường 14, Quận 11, Hồ Chí Minh, Việt Nam | 68437   | info@globedr.com.vn | 13235551234 | 106.645239 | 10.765982 | 1    | 2      |
    Then The request should be succeed
    When On above org, I load branch
    Then The response should be
      | success                    | true                                                                               |
      | data.list[0].orgName       | <orgName> Edited                                                                   |
      | data.list[0].website       | www.globedr.com                                                                    |
      | data.list[0].workHours     | 6h.AM-24H.PM                                                                       |
      | data.list[0].address       | 74 Ông Ích Khiêm, Phường 14, Quận 11, Hồ Chí Minh, Việt Nam, Hồ Chí Minh, Việt Nam |
      | data.list[0].region        | null                                                                               |
      | data.list[0].zipCode       | 68437                                                                              |
      | data.list[0].country.name  | Việt Nam                                                                           |
      | data.list[0].city.name     | Hồ Chí Minh                                                                        |
      | data.list[0].district.name | <districtName>                                                                     |
      | data.list[0].ward.name     | <wardName>                                                                         |
      | data.list[0].email         | info@globedr.com.vn                                                                |
      | data.list[0].fax           | 13235551234                                                                        |
      | data.list[0].longitude     | 106.645239                                                                         |
      | data.list[0].latitude      | 10.765982                                                                          |
    Examples:
      | orgName              | districtCode | districtName | wardCode | wardName | city |
      | CHI_NHANH_MY_THANH_1 | 1447         | Quận 5       | 20501    | Phường 1 | HCM  |


  @branch-4
  Scenario: create invalid branch
    And On above org, I create a branch
      | name |
      |      |
    And The response should be
      | success        | false                  |
      | errors.name    | This field is required |
      | errors.type    | This field is required |
      | errors.status  | This field is required |
      | errors.address | This field is required |


  @branch-5
  Scenario Outline: update invalid branch
    And On above org, I create a branch
      | name      | address                  | country                                               | city                                  | district                        | ward                                | zipCode | email               | phones                                                                            | fax         | website         | type | status | latitude           | longitude          |
      | <orgName> | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | 1    | 2      | 10.785729039069945 | 106.66766245536387 |
    And The request should be succeed

    And On above org, I update branch name "<orgName>" with below info
      | orgName |
      |         |
    Then The response should be
      | success        | false                  |
      | errors.orgName | This field is required |
      | errors.address | This field is required |
      | errors.country | This field is required |
    Examples:
      | orgName              |
      | CHI_NHANH_MY_THANH_1 |

  @branch-6
  Scenario Outline: get information of new branch
    And On above org, I create a branch
      | name         | address                  | country                                               | city                                  | district                        | ward                                | zipCode | email               | phones                                                                            | fax         | website         | type   | status | latitude           | longitude          |
      | <branchName> | 520 CMT8, Quan 3, Tp.HCM | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | 68437   | info@globedr.com.vn | [{"id":"0289234675","name":"0289234675"},{"id":"0289234000","name":"0289234000"}] | 13235551234 | www.globedr.com | <type> | 2      | 10.785729039069945 | 106.66766245536387 |
    And The response should be
      | success | true |

    And I get information branch
      | orgName   | branchName   |
      | <orgName> | <branchName> |
    Then The response should be
      | data.orgInfo.isBranch        | true                                            |
      | data.orgInfo.orgName         | <branchName>                                    |
      | data.orgInfo.type            | <type>                                          |
      | data.orgInfo.status          | 2                                               |
      | data.orgInfo.address         | 520 CMT8, Quan 3, Tp.HCM, Hồ Chí Minh, Việt Nam |
      | data.orgInfo.city.name       | Hồ Chí Minh                                     |
      | data.orgInfo.country.name    | Việt Nam                                        |
      | data.orgInfo.district.name   | Quận 3                                          |
      | data.orgInfo.ward.name       | Phường 11                                       |
      | data.orgInfo.zipCode         | 68437                                           |
      | data.orgInfo.email           | info@globedr.com.vn                             |
      | data.orgInfo.phones.[0].name | 0289234675                                      |
      | data.orgInfo.fax             | 13235551234                                     |
      | data.orgInfo.website         | www.globedr.com                                 |
    Examples:
      | orgName     | branchName           | typeName | type |
      | BV_MY_THANH | CHI_NHANH_MY_THANH_1 | Hospital | 1    |
