@regression @org
Feature: Profile of org.

  Background:
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name     | type     | address                            | country                                               | city                                  | district                        | ward                                |
      | BV_LY_AN | Hospital | 520 CMT8, Quan 3, Tp.HCM, Việt Nam | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |


  @profile-org-1 @upload-file
  Scenario: Add cover for org
    And I update cover "data/image/avatar/org-1.jpg" for above org
    Then The request should be succeed
    And The image into "data.orgCoverUrl" should be match "data/image/avatar/org-1.jpg"

    And I update cover "data/image/3mb/laptop.png" for above org
    Then The request should be succeed
    And The image into "data.orgCoverUrl" should be match "data/image/3mb/laptop.png"

  @profile-org-2
  Scenario: Add default image for org
    And As sysAdmin, I search org
      | name     |
      | BV_LY_AN |
    And I add default image number '2' for org
    And The request should be succeed
    Then The response return should be image

  @profile-org-3
  Scenario Outline: Change org type
    When I login as "system_admin_1"
    And I change org type
      | name     | type      |
      | BV_LY_AN | <orgType> |
    And The request should be succeed
    Examples:
      | orgType       |
      | Hospital      |
      | ClinicOrgType |
      | Pharmacy      |
      | Dental        |
      | LabAndTest    |


  @profile-org-4
  Scenario: Update introduction for org
    When I login as "system_admin_1"
    And As sysAdmin, I search org
      | name     |
      | BV_LY_AN |
    And I update introduction for organization with description "Bệnh viện giành cho người không thể gầy như ông Duy, cho ông Duy vào và ngủ đông"
    And The request should be succeed
    And I get information org
      | orgName  |
      | BV_LY_AN |
    Then The response should be
      | data.orgInfo.intro | Bệnh viện giành cho người không thể gầy như ông Duy, cho ông Duy vào và ngủ đông |

  @profile-org-5
  Scenario Outline: Update information for org
    And On SqlServer, I delete organization by name "<name>"
    When I login as "system_admin_1"
    And As sysAdmin, I search org
      | name     |
      | BV_LY_AN |
    And I update information for organization below info
      | name   | website   | workHours   | address   | zipCode   | country                                               | city                                  | district                                          | ward                                      | email   | fax   | longitude   | latitude   |
      | <name> | <website> | <workHours> | <address> | <zipCode> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"<districtCode>","name":"<districtName>"} | {"code":"<wardCode>","name":"<wardName>"} | <email> | <fax> | <longitude> | <latitude> |
    And The request should be succeed
    And I get information org
      | orgName     |
      | BV_LY_AN_02 |
    Then The response should be
      | data.orgInfo.orgName         | <name>         |
      | data.orgInfo.city.code       | <city>         |
      | data.orgInfo.website         | <website>      |
      | data.orgInfo.workHours       | <workHours>    |
      | data.orgInfo.baseAddress     | <address>      |
      | data.orgInfo.zipCode         | <zipCode>      |
      | data.orgInfo.country.country | <country>      |
      | data.orgInfo.email           | <email>        |
      | data.orgInfo.fax             | <fax>          |
      | data.orgInfo.longitude       | <longitude>    |
      | data.orgInfo.latitude        | <latitude>     |
      | data.orgInfo.district.name   | <districtName> |
      | data.orgInfo.ward.name       | <wardName>     |
    Examples:
      | name        | website         | workHours    | address                                                     | zipCode | country | city | districtCode | districtName | wardCode | wardName | email               | fax         | longitude  | latitude  |
      | BV_LY_AN_02 | www.globedr.com | 6h.AM-24H.PM | 74 Ông Ích Khiêm, Phường 14, Quận 11, Hồ Chí Minh, Việt Nam | 68437   | VN      | HCM  | 1447         | Quận 5       | 20501    | Phường 1 | info@globedr.com.vn | 13235551234 | 106.645239 | 10.765982 |


  @profile-org-6
  Scenario: I get information org
    And I get information org
      | orgName  |
      | BV_LY_AN |
    Then The response should be
      | data.orgInfo.orgName       | BV_LY_AN                                                  |
      | data.orgInfo.type          | 1                                                         |
      | data.orgInfo.status        | 2                                                         |
      | data.orgInfo.country.name  | Việt Nam                                                  |
      | data.orgInfo.city.name     | Hồ Chí Minh                                               |
      | data.orgInfo.address       | 520 CMT8, Quan 3, Tp.HCM, Việt Nam, Hồ Chí Minh, Việt Nam |
      | data.orgInfo.district.name | Quận 3                                                    |
      | data.orgInfo.ward.name     | Phường 11                                                 |

  @profile-org-7
  Scenario: I view org information at search page
    When I login as "user_1"
    And As User, I search org
      | name     |
      | BV_LY_AN |
    Then The response should be
      | data.list[0].name          | BV_LY_AN                                                  |
      | data.list[0].orgType       | 1                                                         |
      | data.list[0].country.name  | Việt Nam                                                  |
      | data.list[0].city.name     | Hồ Chí Minh                                               |
      | data.list[0].address       | 520 CMT8, Quan 3, Tp.HCM, Việt Nam, Hồ Chí Minh, Việt Nam |
      | data.list[0].district.name | Quận 3                                                    |
      | data.list[0].ward.name     | Phường 11                                                 |

  @profile-org-8
  Scenario: I view information of followed org
    When I login as "user_1"
    And I FOLLOW business
      | name     |
      | BV_LY_AN |
    Then The request should be succeed

    And I load the followed org
    Then The response should be
      | data.list[0].orgName       | BV_LY_AN                                                  |
      | data.list[0].orgType       | 1                                                         |
      | data.list[0].country.name  | Việt Nam                                                  |
      | data.list[0].city.name     | Hồ Chí Minh                                               |
      | data.list[0].orgAddress    | 520 CMT8, Quan 3, Tp.HCM, Việt Nam, Hồ Chí Minh, Việt Nam |
      | data.list[0].district.name | Quận 3                                                    |
      | data.list[0].ward.name     | Phường 11                                                 |
