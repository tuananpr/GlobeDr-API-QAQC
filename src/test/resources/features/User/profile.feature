@regression @profile
Feature: User: Edit Profile
#  Use default data for this feature - this feature use hook to control test data
#  Patient: username 0000000001, password 123456, country VN
#  Doctor: 'will be created later'

  Background:


  @update-profile-1 @upload-file
  Scenario Outline: User - Profile can be loaded and edited successfully.
    Given On SqlServer, I delete user by username "<gdrLogin>" and country "<country>"
    When I signup new account with below info and verify it
      | country | gdrLogin   | password | displayName | language   |
      | VN      | <gdrLogin> | 123456   | Nguyen_Hai  | <language> |

    And I login account
      | gdrLogin   | password | country   | language   |
      | <gdrLogin> | 123456   | <country> | <language> |
    And I load my profile
    Then The response should be
      | success                  | true        |
      | data.gdrLogin            | 84303305466 |
      | data.displayName         | Nguyen_Hai  |
      | data.inviteCodeActivated | false       |
      | data.language            | <language>  |
      | data.country             | <country>   |
      | data.visitingCountry     | <country>   |
      | data.phone               | 84303305466 |
    And I upload avatar
      | file    |
      | <image> |
    And The request should be succeed
    And The image into "data.url" should be match "<image>"
    When I update my profile with below info
      | displayName   | dob   | email   | address   | country   | city   | district   | ward   | gender   | phone   |
      | <displayName> | <dob> | <email> | <address> | <country> | <city> | <district> | <ward> | <gender> | <phone> |
    And The request should be succeed
    And I load my profile
    Then The response should be
      | success                  | true           |
      | data.gdrLogin            | 84303305466    |
      | data.displayName         | <displayName>  |
      | data.inviteCodeActivated | false          |
      | data.gender              | <gender>       |
      | data.country             | <country>      |
      | data.visitingCountry     | <country>      |
      | data.city.name           | <cityName>     |
      | data.district.name       | <districtName> |
      | data.ward.name           | <wardName>     |
      | data.phone               | <phone>        |
      | data.telemedicine        | false          |
      | data.dob                 | <dob>          |
      | data.address             | <address>      |
      | data.email               | <email>        |
    And The image into "data.avatar" should be match "<image>"
    And I get information user
    Then The response should be
      | success                     | true           |
      | data.userInfo.displayName   | <displayName>  |
      | data.userInfo.gender        | <gender>       |
      | data.userInfo.country       | <country>      |
      | data.userInfo.cityName      | <cityName>     |
      | data.userInfo.district.name | <districtName> |
      | data.userInfo.ward.name     | <wardName>     |
      | data.userInfo.phone         | <phone>        |
      | data.userInfo.telemedicine  | false          |
      | data.userInfo.dob           | <dob>          |
      | data.userInfo.address       | <address>      |
      | data.userInfo.email         | <email>        |
    Examples:
      | gdrLogin   | language | displayName | dob                     | email                             | address                                     | country | city                                | cityName    | district                        | districtName | ward                                | wardName  | gender | phone       | image                         |
      | 0303305466 | 0        | Tran_Lieu   | 1969-09-10T00:00:00.000 | changtraicute111@bacsitoancau.com | 10 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | {"code":"HCM","name":"Hồ Chí Minh"} | Hồ Chí Minh | {"code":"1444","name":"Quận 3"} | Quận 3       | {"code":"20311","name":"Phường 11"} | Phường 11 | 1      | 84303305400 | data/image/7mb/Waterfalls.jpg |

  @update-profile-2
  Scenario Outline: Doctor active telemedicine
    When I login as "DOCTOR_TEO"
    When I update my profile with below info
      | displayName   | dob   | email   | address   | country   | gender   | phone   | telemedicine   |
      | <displayName> | <dob> | <email> | <address> | <country> | <gender> | <phone> | <telemedicine> |

    And The request should be succeed
    And I load my profile
    And The request should be succeed
    Then The response should be
      | data.displayName  | <displayName>  |
      | data.gender       | <gender>       |
      | data.country      | <country>      |
      | data.phone        | <phone>        |
      | data.telemedicine | <telemedicine> |
      | data.dob          | <dob>          |
      | data.address      | <address>      |
      | data.email        | <email>        |
    Examples:
      | displayName | dob                     | email                             | address                                     | country | gender | phone       | telemedicine |
      | DOCTOR_TEO  | 1969-09-10T00:00:00.000 | changtraicute111@bacsitoancau.com | 10 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | 1      | 84303305400 | true         |

  @update-profile-3
  Scenario Outline: user can't active telemedicine
    When I login as "user_5"
    When I update my profile with below info
      | displayName   | dob   | email   | address   | country   | gender   | phone   | telemedicine   |
      | <displayName> | <dob> | <email> | <address> | <country> | <gender> | <phone> | <telemedicine> |

    And The request should be succeed
    And I load my profile
    And The request should be succeed
    Then The response should be
      | data.displayName  | <displayName> |
      | data.gender       | <gender>      |
      | data.country      | <country>     |
      | data.phone        | <phone>       |
      | data.telemedicine | false         |
      | data.dob          | <dob>         |
      | data.address      | <address>     |
      | data.email        | <email>       |
    Examples:
      | displayName | dob                     | email                             | address                                     | country | gender | phone       | telemedicine |
      | user_5      | 1969-09-10T00:00:00.000 | changtraicute111@bacsitoancau.com | 10 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | 1      | 84303305400 | true         |

  @update-profile-4
  Scenario Outline: user load personal information
    Given On SqlServer, I delete user by username "<gdrLogin>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin   | password   | displayName   | language |
      | <country> | <gdrLogin> | <password> | <displayName> | 0        |

    And I login account
      | gdrLogin   | password   | country   |
      | <gdrLogin> | <password> | <country> |

    And I get personal information
    Then The response should be
      | data.displayName  | <displayName>  |
      | data.gender       | <gender>       |
      | data.country      | <country>      |
      | data.phoneSupport | +8428 73006880 |
      | data.telemedicine | false          |
    Examples:
      | gdrLogin   | password | country | displayName | gender |
      | 0303305466 | 123456   | VN      | Nguyen_Hai  | 4      |


  @update-profile-5
  Scenario: user view phone support
    When I login as "user_1"
    And I get personal information
    Then The response should be
      | data.phoneSupport | +8428 73006880 |
