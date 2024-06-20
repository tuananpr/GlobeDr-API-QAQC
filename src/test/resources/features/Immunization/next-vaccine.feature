@regression @health @immnunization @next-vaccine
Feature: Next vaccine for main acc in US

  Background:
    Given I re-signup "user_1" account

  @next-vaccine-1
  Scenario Outline: '<group>' vaccine should be loaded correctly
    When I switch main of family members with below info
      | name   |
      | user_1 |
    And I update profile of logged user with dob is '<time>' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | US      | 1      | 0903123455 |
    And The request should be succeed
    When I get immunization by age
    And The request should be succeed
    And Next vaccine group id should be "<group>"
    Then "<group>" vaccine group id should have '<shot>' shot
    Examples:
      | time     | group | shot |
      | At Birth | AB    | 1    |
      | 1 month  | M01   | 1    |
      | 2 month  | M02   | 4    |
      | 4 month  | M04   | 4    |
      | 6 month  | M06   | 5    |
      | 7 month  | M0709 | 1    |
      | 12 month | M1218 | 6    |
      | 4 yr     | Y0406 | 3    |
      | 11 yr    | Y1112 | 4    |
      | 16 yr    | Y1618 | 1    |


  @next-vaccine-2
  Scenario Outline: '<group>' vaccine should be loaded correctly
    And I update profile of logged user with dob is '<time>' and below info
      | displayName | email                  | address                                     | city                              | country | gender | phone      |
      | Son_Vo      | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | 1      | 0903123455 |
    And The request should be succeed
    When I get immunization by age

    And The request should be succeed
    And Next vaccine group id should be "<group>"
    Then "<group>" vaccine group id should have '<shot>' shot
    Examples:
      | time     | group | shot |
      | At Birth | AB    | 2    |
      | 2 month  | M02   | 8    |
      | 3 month  | M03   | 8    |
      | 4 month  | M04   | 7    |
      | 5 month  | M06   | 2    |
      | 8 month  | M0709 | 3    |
      | 14 month | M1218 | 14   |
      | 2 year   | Y02   | 5    |
      | 4 years  | Y0406 | 5    |
      | 7 years  | Y0710 | 8    |
      | 11 years | Y1112 | 5    |
      | 13 years | Y1315 | 6    |
      | 16 years | Y1618 | 5    |



