@regression @search
Feature: User search org

  As user
  I want to search org as Hospital, pharmacy..

  Background: Login by any user
    Given I login as "system_admin_1"


  @prepare-location-organization-1
  Scenario Outline: Create organization in <country> for search around
    Given On SqlServer, I delete organization by name "<orgName>"
    And I create a org
      | name      | type      | status | address   | latitude   | longitude   | country   | city   | district   | ward   |
      | <orgName> | <orgType> | Active | <address> | <latitude> | <longitude> | <country> | <city> | <district> | <ward> |
    Then The request should be succeed
    Examples:
      | orgName            | address                  | latitude           | longitude          | country                                               | city                                  | district                        | ward                                | orgType       |
      | NHA_KHOA_THIEN_PHU | 520 CMT8, Quan 3, Tp.HCM | 10.785729039069945 | 106.66766245536387 | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | ClinicOrgType |
      | NHA_THUOC_123      | 520 CMT8, Quan 3, Tp.HCM | 10.785729039069945 | 106.66766245536387 | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | Pharmacy      |
      | NHA_KHOA_SO_2      | 520 CMT8, Quan 3, Tp.HCM | 10.785729039069945 | 106.66766245536387 | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} | Dental        |


  @prepare-location-organization-2
  Scenario Outline: Create organization in <country> for search around
    Given On SqlServer, I delete organization by name "<orgName>"
    And I create a org
      | name      | type      | status | address                  | country   | city   |
      | <orgName> | <orgType> | Active | 520 CMT8, Quan 3, Tp.HCM | <country> | <city> |
    Then The request should be succeed
    Examples:
      | orgName       | address                                 | country                                                   | city                                  | districtName | wardName | orgType  |
      | PK_LUAN       | 4033 Hwy 6, Sugar Land, TX 77478, USA   | {"country": "US","name": "United States","postCode": "1"} | {"code": "1029","name": "Sugar Land"} |              |          | Hospital |
      | Pharmacy_Land | 1 Stadium Dr, Sugar Land, TX 77498, USA | {"country": "US","name": "United States","postCode": "1"} | {"code": "1029","name": "Sugar Land"} |              |          | Pharmacy |
      | BV_AN         | 1 Stadium Dr, Sugar Land, TX 77498, USA | {"country": "US","name": "United States","postCode": "1"} | {"code": "1029","name": "Sugar Land"} |              |          | Hospital |


  @search-1
  Scenario Outline: Search for org by name, address, type
    And As User, I search org
      | type      | name      | address   |
      | <orgType> | <orgName> | <address> |
    Then The response should be
      | success | true |
    And List "data.list[*].name" of response should contains
      | <orgName> |
    Examples:
      | country | orgName            | address                  | country | city | districtName | wardName  | orgType       |
      | VN      | NHA_KHOA_THIEN_PHU | 520 CMT8, Quan 3, Tp.HCM | VN      | HCM  | Quận 3       | Phường 11 | ClinicOrgType |
      | VN      | NHA_THUOC_123      | 520 CMT8, Quan 3, Tp.HCM | VN      | HCM  | Quận 3       | Phường 11 | Pharmacy      |
      | VN      | NHA_KHOA_SO_2      | 520 CMT8, Quan 3, Tp.HCM | VN      | HCM  | Quận 3       | Phường 11 | Dental        |