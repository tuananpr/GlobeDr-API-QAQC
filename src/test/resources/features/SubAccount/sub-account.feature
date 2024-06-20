@regression @sub-account
Feature: Manage sub account

  As user
  I want create sub-account for my family
  In order to health monitoring of  my family

  Background:
    Given I re-signup "user_1" account and update profile
    And I login as "user_1"

  @sub-account-1
  Scenario Outline: Sub-account can be created and then loaded successfully.
    When I add new sub-account
      | displayName   | dob   | carerType   | gender   | height   | weight   | country                                               | city                                  | district                        | ward                                |
      | <displayName> | <dob> | <carerType> | <gender> | <height> | <weight> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |

    And The request should be succeed
    And The response should be
      | data.subAccount.displayName     | <displayName> |
      | data.subAccount.dob             | <dob>         |
      | data.subAccount.carerType       | <carerType>   |
      | data.subAccount.country.country | <country>     |
      | data.subAccount.city.code       | HCM           |
      | data.subAccount.district.name   | Quận 3        |
      | data.subAccount.ward.name       | Phường 11     |
      | data.subAccount.gender          | <gender>      |
      | data.subAccount.height          | <height>      |
      | data.subAccount.weight          | <weight>      |
    When I load all account of logged user with below info
      | name          | carerType   |
      | <displayName> | <carerType> |
    And The response should be
      | success                  | true          |
      | data.list[0].displayName | <displayName> |
      | data.list[0].height      | <height>      |
      | data.list[0].weight      | <weight>      |
    Examples:
      | displayName | dob                     | carerType | country | gender | height    | weight   |
      | Minh_Béo    | 2018-09-30T00:00:00.000 | 1         | VN      | 1      | 165.00000 | 65.00000 |
      | Johnny_Sins | 1960-11-25T00:00:00.000 | 2         | VN      | 2      | 150.00000 | 50.00000 |


  @sub-account-2
  Scenario: check total sub-account in list
    When I add 25 sub-account
    And The request should be succeed
    When I load all account of logged user with below info
      | name | page | pageSize |
      |      | 1    | 20       |
    And The request should be succeed
    And The response should be
      | data.totalCount | 20 |
    When I load all account of logged user with below info
      | name | page | pageSize |
      |      | 2    | 20       |
    And The request should be succeed
    And The response should be
      | data.totalCount | 6 |

  @sub-account-3
  Scenario Outline: remove sub-account
    When I add new sub-account
      | displayName   | dob   | carerType   | gender   | country                                               | city                                  | district                        | ward                                |
      | <displayName> | <dob> | <carerType> | <gender> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    When I remove a above sub-account with name "<displayName>"
    And The request should be succeed
    Examples:
      | displayName | dob                     | carerType | country | gender |
      | Minh_Béo    | 2018-09-30T00:00:00.000 | 1         | VN      | 1      |

  @sub-account-4
  Scenario Outline: Update sub-account with value will be success.
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | subABC      | 2019-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    Then I update profile for sub-account "subABC"
      | displayName   | dob   | carerType   | gender   | height   | weight   | country                                               | city                                  | district                        | ward                                |
      | <displayName> | <dob> | <carerType> | <gender> | <height> | <weight> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |

    And The request should be succeed
    And The response should be
      | data.subAccount.displayName     | <displayName> |
      | data.subAccount.dob             | <dob>         |
      | data.subAccount.carerType       | <carerType>   |
      | data.subAccount.country.country | <country>     |
      | data.subAccount.city.code       | HCM           |
      | data.subAccount.district.name   | Quận 3        |
      | data.subAccount.ward.name       | Phường 11     |
      | data.subAccount.gender          | <gender>      |
      | data.subAccount.height          | <height>      |
      | data.subAccount.weight          | <weight>      |
    When I load all account of logged user with below info
      | name          |
      | <displayName> |

    And The request should be succeed
    And The response should be
      | data.list[1].displayName     | <displayName> |
      | data.list[1].dob             | <dob>         |
      | data.list[1].carerType       | <carerType>   |
      | data.list[1].gender          | <gender>      |
      | data.list[1].country.country | <country>     |
      | data.list[1].city.code       | HCM           |
      | data.list[1].district.name   | Quận 3        |
      | data.list[1].ward.name       | Phường 11     |

    Examples:
      | displayName | dob                     | carerType | country | gender | height    | weight   |
      | Minh_Béo    | 2018-09-30T00:00:00.000 | 1         | VN      | 1      | 165.00000 | 65.00000 |
      | ahihi_01    | 2017-09-30T00:00:00.000 | 4         | VN      | 2      | 135.00000 | 45.00000 |

  @sub-account-5
  Scenario Outline: update sub-account with special value will be fail
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | subABC      | 2019-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    Then I update profile for sub-account "subABC"
      | displayName   | dob   | carerType   | gender   | height   | weight   | country                                               | city                                  | district                        | ward                                |
      | <displayName> | <dob> | <carerType> | <gender> | <height> | <weight> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The response should be
      | success      | false                  |
      | message      | Please try again later |
      | <errorField> | <messageError>         |
    Examples:
      | displayName | dob                     | carerType | country | gender | height    | weight    | errorField    | messageError        |
      | ahihi_01    | 2018-09-30T00:00:00.000 | 1         | VN      | 1      | 365.00000 | 65.00000  | errors.height | No more than 250 cm |
      | ahihi_02    | 2016-09-30T00:00:00.000 | 2         | US      | 1      | 65.00000  | 605.00000 | errors.weight | No more than 500 kg |
      | ahihi_02    | 2026-09-30T00:00:00.000 | 2         | US      | 1      | 165.00000 | 105.00000 | errors.dob    | Field invalid       |
      | ahihi_02    | 2006-09-30T00:00:00.000 | 2         | US      | 6      | 165.00000 | 105.00000 | errors.gender | Field invalid       |

  @sub-account-6
  Scenario: create sub-account fail
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | tenten      | 2099-09-30T00:00:00.000 | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The response should be
      | success    | false                  |
      | message    | Please try again later |
      | errors.dob | Field invalid          |

    When I add new sub-account
      | displayName | dob       | carerType | gender | country                                               | city                                  | district                        | ward                                |
      |             | yesterday | 2         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The response should be
      | success            | false                  |
      | message            | Please try again later |
      | errors.displayName | This field is required |

  @sub-account-7
  Scenario Outline: check all information after load sub account
    # update information of main account
    When I update my profile with below info
      | displayName   | dob   | email   | address   | gender   | phone   | country   | city                                  | district                        | ward                                |
      | <displayName> | <dob> | <email> | <address> | <gender> | <phone> | <country> | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    # add new sub-account
    When I add new sub-account
      | displayName | dob                     | carerType | gender | height | weight | address   | country                                               | city                                  | district                        | ward                                |
      | Tran_Beo    | 2010-09-10T00:00:00.000 | 1         | 1      | 100    | 45     | <address> | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |

    # check information of main and sub-account
    When I load all account of logged user with below info
      | name |
      |      |
    And The response should be
      | success                      | true                    |
      | data.list[0].displayName     | <displayName>           |
      | data.list[0].dob             | <dob>                   |
      | data.list[0].email           | <email>                 |
      | data.list[0].country.country | <country>               |
      | data.list[0].city.code       | HCM                     |
      | data.list[0].district.name   | Quận 3                  |
      | data.list[0].ward.name       | Phường 11               |

      | data.list[0].gender          | <gender>                |
      | data.list[0].phone           | <phone>                 |
      | data.list[0].height          | null                    |
      | data.list[0].weight          | null                    |

      | data.list[1].displayName     | Tran_Beo                |
      | data.list[1].dob             | 2010-09-10T00:00:00.000 |
      | data.list[1].country.country | <country>               |
      | data.list[1].city.code       | HCM                     |
      | data.list[1].district.name   | Quận 3                  |
      | data.list[1].ward.name       | Phường 11               |
      | data.list[1].gender          | 1                       |
      | data.list[1].phone           | <phone>                 |
      | data.list[1].height          | 100                     |
      | data.list[1].weight          | 45                      |
    Examples:
      | displayName | dob                     | email                             | address                                     | country | gender | phone       |
      | DươngCute   | 1969-09-10T00:00:00.000 | changtraicute111@bacsitoancau.com | 10 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | 1      | 84303305400 |

