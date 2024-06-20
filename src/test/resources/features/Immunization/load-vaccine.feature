@regression @health @immunization @load-vaccine
Feature: Load vaccine

  Background:
    Given I re-signup "user_1" account and update profile
    Given I login as "user_1"

  @load-vaccine-1
  Scenario Outline: Check vaccine list by country (VN or US) will be loaded correctly by vaccine
    And I update my profile with below info
      | displayName | dob         | email                  | address                                     | country   | gender | phone      |
      | Son_Vo      | 2000-10-17T | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | <country> | 2      | 0376559999 |
    And The request should be succeed
    When I get immunization by vaccine
    Then The response path "data" should be matched with "<vaccine>"
    When I get immunization by age
    And The request should be succeed
    Then The response path "data" should be matched with "<age>"
    Examples:
      | country | vaccine                                                            | age                                                     |
      | VN      | data/expected_result/vaccination/all_vaccine_in_vn_by_vaccine.json | data/expected_result/vaccination/all_vaccine_in_vn.json |
      | US      | data/expected_result/vaccination/all_vaccine_in_us_by_vaccine.json | data/expected_result/vaccination/all_vaccine_in_us.json |


  @load-vaccine-2
  Scenario Outline: The medicine list are displayed successfully by vaccine name
    When I load medicines
      | <vacId> |
    And The request should be succeed
    Then The response path "data" should be matched with "<filepath>"
    Examples:
      | vacId | filepath                                                  |
      | HBV   | data/expected_result/vaccination/medicine_list/HBV.json   |
      | HIB   | data/expected_result/vaccination/medicine_list/HIB.json   |
      | IPV   | data/expected_result/vaccination/medicine_list/IPV.json   |
      | PCV   | data/expected_result/vaccination/medicine_list/PCV.json   |
      | RV    | data/expected_result/vaccination/medicine_list/RV.json    |
      | IIV   | data/expected_result/vaccination/medicine_list/IIV.json   |
      | HAV   | data/expected_result/vaccination/medicine_list/HAV.json   |
      | MMR   | data/expected_result/vaccination/medicine_list/MMR.json   |
      | VAR   | data/expected_result/vaccination/medicine_list/VAR.json   |
      | MCVAC | data/expected_result/vaccination/medicine_list/MCVAC.json |
      | DTAP  | data/expected_result/vaccination/medicine_list/DTAP.json  |
      | COV   | data/expected_result/vaccination/medicine_list/COV.json   |
      | FLUV  | data/expected_result/vaccination/medicine_list/FLUV.json  |
      | JEB   | data/expected_result/vaccination/medicine_list/JEB.json   |
      | POLIO | data/expected_result/vaccination/medicine_list/POLIO.json |
      | TYV   | data/expected_result/vaccination/medicine_list/TYV.json   |
      | VAR   | data/expected_result/vaccination/medicine_list/VAR.json   |
      | BCG   | data/expected_result/vaccination/medicine_list/BCG.json   |

  @load-vaccine-3
  Scenario Outline: The vaccine is displayed correctly after choosing many medicines from list
    When I load vaccines list by "<medId1>" medIds and "<vacId>" vacId and "<dose>" dose
    And The request should be succeed
    Then The response should be
      | data.list[0].vacId   | <vacId>   |
      | data.list[0].name    | <name>    |
      | data.list[0].infoURL | <infoURL> |
      | data.list[0].dose    | <dose>    |
      | data.list[0].medId   | <medId>   |
      | data.list[0].medName | <medName> |
    Examples:
      | medId1                                  | vacId | dose | name                                             | infoURL  | medId      | medName    |
      | PENTAXIM,QUINVAXEM                      | DTAP  | D0   | Diphtheria, tetanus & acellular pertussis (DTaP) | At birth | QUINVAXEM  | QUINVAXEM  |
      | TWINRIX,IFARXHX0.5,ENGERIXB10,QUINVAXEM | HBV   | D1   | Hepatitis B (HepB)                               | Dose 1   | QUINVAXEM  | QUINVAXEM  |
      | PENTAXIM,IFARXHX0.5                     | HIB   | D1   | Haemophilus influenzae type b (Hib)              | Dose 1   | IFARXHX0.5 | IFARXHX0.5 |


