@regression @require-update-info
Feature: User must update information to use some features

  Background:
    Given On SqlServer, I delete user by username "0903304444" and country "VN"
    Given On SqlServer, I delete user by displayName "BE_TI"
    When I signup new account with below info and verify it
      | country | gdrLogin   | password | displayName | language |
      | VN      | 0903304444 | 123456   | Tran_Tu     | 0        |

    And On SqlServer, I update below info to username "0903304444" and country "VN"
      | phone   | null |
      | gender  | 4    |
      | country | null |
      | dob     | null |
    And I login account
      | gdrLogin   | password | country |
      | 0903304444 | 123456   | VN      |
    And The request should be succeed
    When I add new sub-account
      | displayName | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | BE_TI       | 2018-09-30T00:00:00.000 | 1         | 1      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed
    And On SqlServer, I update below info to username with displayName "BE_TI"
      | phone   | null |
      | gender  | 4    |
      | country | null |
      | dob     | null |


  @require-update-info-1
  Scenario Outline: require information of main and sub account
    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true             |
      | data.phone   | <requirePhone>   |
      | data.gender  | <requireGender>  |
      | data.country | <requireCountry> |
      | data.dob     | <requireDob>     |
    When I switch sub-account of family members with below info
      | name  |
      | BE_TI |
    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true             |
      | data.phone   | false            |
      | data.gender  | <requireGender>  |
      | data.country | <requireCountry> |
      | data.dob     | <requireDob>     |
    Examples:
      | feature             | requirePhone | requireGender | requireCountry | requireDob |
      | Consult             | true         | true          | true           | true       |
      | Forum               | false        | false         | false          | false      |
      | Chat                | false        | false         | false          | false      |
      | Voucher             | false        | false         | false          | false      |
      | Connection          | false        | false         | false          | false      |
      | Health              | true         | true          | true           | true       |
      | Telemedicine        | false        | false         | false          | false      |
      | Vaccination         | false        | false         | false          | false      |
      | Subaccount          | false        | false         | false          | false      |
      | Appointment         | true         | true          | true           | true       |
      | OrderMedicine       | false        | false         | false          | false      |
      | OrderProductService | false        | false         | false          | false      |


  @require-update-info-2
  Scenario Outline: user update information of main and sub account successfully
    # main-account update information
    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true |
      | data.phone   | true |
      | data.gender  | true |
      | data.country | true |
      | data.dob     | true |

    And I update the below info for requirement
      | dob   | gender   | phone   | countryCode |
      | <dob> | <gender> | <phone> | <country>   |


    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true  |
      | data.phone   | false |
      | data.gender  | false |
      | data.country | false |
      | data.dob     | false |
    # sub-account update information
    When I switch sub-account of family members with below info
      | name  |
      | BE_TI |
    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true  |
      | data.phone   | false |
      | data.gender  | true  |
      | data.country | true  |
      | data.dob     | true  |
    And I update the below info for requirement
      | dob   | gender   | phone   | countryCode |
      | <dob> | <gender> | <phone> | <country>   |
    And I check user information before use feature
      | screen    |
      | <feature> |
    Then The response should be
      | success      | true  |
      | data.phone   | false |
      | data.gender  | false |
      | data.country | false |
      | data.dob     | false |

    Examples:
      | phone      | country | dob                      | gender | feature |
      | 0903304444 | VN      | 1996-10-17T00:00:00.000Z | 0      | Health  |

  @require-update-info-3
  Scenario Outline: user update invalid <description>
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName | gender   |
      | <country> | <phone>  | <pass>   | Tran_Tu     | <gender> |
    And On SqlServer, I update below info to username "<phone>" and country "<country>"
      | phone   | null |
      | gender  | 4    |
      | country | null |
      | dob     | null |
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | <pass>   | <country> |

    And The request should be succeed
    And I update the below info for requirement
      | dob      | gender      | phone      | countryCode      |
      | <newDob> | <newGender> | <newPhone> | <newCountryCode> |
    Then The response should be
      | success  | false      |
      | message  | <message>  |
      | <errors> | <errorMsg> |
    Examples:
      | phone      | country | pass   | gender | newDob      | newGender | newPhone   | newCountryCode | errors     | errorMsg          | message           |
      | 0903304444 | VN      | 123456 | 0      | next 2 days | 0         | 0903304444 | VN             | errors.dob | Ngày không hợp lệ | Ngày không hợp lệ |

