@regression @health @immunization @info-vaccine
Feature: Information vaccine

  Background:

    Given On SqlServer, I delete all posts in system
    Given I re-signup "manager_1" account and update profile
    When I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | type     | owner     |
      | BV_NHAN_VAN | Hospital | manager_1 |

  @info-vaccine-1
  Scenario Outline: The vaccine info that is created from system admin, is displayed successfully
    When I login as "system_admin_1"
    When I create a new system post
      | key        | type | subject | msg   | language |
      | <infoName> | 13   | <msg>   | <msg> | 0        |
    And The request should be succeed
    When I get "<infoName>" vaccine information

    And The request should be succeed
    Then The "data.info" should "<data>"
    Examples:
      | infoName            | msg                                         | data                                        |
      | Lao                 | This is vaccine info of Lao                 | This is vaccine info of Lao                 |
      | BachHau_UonVan_HoGa | This is vaccine info of BachHau_UonVan_HoGa | This is vaccine info of BachHau_UonVan_HoGa |
      | ViemGanA            | This is vaccine info of ViemGanA            | This is vaccine info of ViemGanA            |
      | ViemGanB            | This is vaccine info of ViemGanB            | This is vaccine info of ViemGanB            |
      | Hib                 | This is vaccine info of Hib                 | This is vaccine info of Hib                 |
      | HPV                 | This is vaccine info of HPV                 | This is vaccine info of HPV                 |
      | ViemNaoNhatBan      | This is vaccine info of ViemNaoNhatBan      | This is vaccine info of ViemNaoNhatBan      |
      | ViemNaoAC           | This is vaccine info of ViemNaoAC           | This is vaccine info of ViemNaoAC           |
      | Rubella             | This is vaccine info of Rubella             | This is vaccine info of Rubella             |
      | BaiLiet             | This is vaccine info of BaiLiet             | This is vaccine info of BaiLiet             |
      | Rota                | This is vaccine info of Rota                | This is vaccine info of Rota                |
      | ThuongHan           | This is vaccine info of ThuongHan           | This is vaccine info of ThuongHan           |
      | ThuyDau             | This is vaccine info of ThuyDau             | This is vaccine info of ThuyDau             |
      | Phoi                | This is vaccine info of Phoi                | This is vaccine info of Phoi                |
      | Soi                 | This is vaccine info of Soi                 | This is vaccine info of Soi                 |
      | Ta                  | This is vaccine info of Ta                  | This is vaccine info of Ta                  |

  @info-vaccine-2
  Scenario Outline: The other information that is created from system admin, is displayed successfully
    When I create a new system post
      | key | type   | subject                | msg   | language |
      | abc | <type> | A new post system AUTO | <msg> | 0        |
    And The request should be succeed
    When I get "<type>" system post information

    Then The "data.info" should "<data>"
    Examples:
      | msg                                | data                               | type |
      | This is info of Welcome            | This is info of Welcome            | 9    |
      | This is info of Sign up Intro      | This is info of Sign up Intro      | 10   |
      | This is info of User Guide Android | This is info of User Guide Android | 11   |
      | This is info of Document           | This is info of Document           | 14   |
      | This is info of Medical            | This is info of Medical            | 15   |
      | This is info of BMI                | This is info of BMI                | 16   |
      | This is info of Growth             | This is info of Growth             | 17   |
      | This is info of Glucoze            | This is info of Glucoze            | 18   |