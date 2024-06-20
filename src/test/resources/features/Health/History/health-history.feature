@regression @health-history @health-history-main-acc @health
Feature: Health History for main acc

  Background:
    When I re-signup "user_1" account
    Given I login as "user_1"
    Then I update my profile with below info
      | displayName | dob                      | email                  | address                                     | city                              | country | gender | phone     |
      | user_1      | 1996-10-17T00:00:00.000Z | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | Female | 376559999 |
    Then The request should be succeed
    When I add new sub-account
      | displayName  | dob                     | carerType | gender | country                                               | city                                  | district                        | ward                                |
      | Sub AutoTest | 2000-09-30T00:00:00.000 | 1         | 2      | {"country": "VN","name": "Việt Nam","postCode": "84"} | {"code": "HCM","name": "Hồ Chí Minh"} | {"code":"1444","name":"Quận 3"} | {"code":"20311","name":"Phường 11"} |
    And The request should be succeed

  @health-history-main-acc-1
  Scenario Outline: Verify blood type default is unspecified

    When I switch main of family members with below info
      | name      |
      | <account> |
    And The request should be succeed
    And I load health history
      | GroupType     |
      | HealthHistory |
    And The response should be
      | success        | true |
      | data.bloodType | 8    |
    Examples:
      | account      |
      | user_1       |
      | Sub AutoTest |

  @health-history-main-acc-2
  Scenario Outline: Verify blood type is changed successfully
    When I switch main of family members with below info
      | name      |
      | <account> |
    And The request should be succeed
    Then I change blood type with below info
      | bloodType   |
      | <bloodType> |
    And The request should be succeed
    And The response should be
      | data.bloodType | <expected> |
    And I load health history
      | GroupType     |
      | HealthHistory |
    And The response should be
      | success        | true       |
      | data.bloodType | <expected> |
    Examples:
      | bloodType   | expected | account      |
      | A           | 1        | user_1       |
      | B           | 2        | user_1       |
      | AB          | 3        | user_1       |
      | O           | 4        | Sub AutoTest |
      | Unspecified | 8        | Sub AutoTest |

  @health-history-main-acc-3
  Scenario Outline: Update group "<groupType>" of health history success
    When I switch main of family members with below info
      | name      |
      | <account> |
    And The request should be succeed

    And I update health history information
      | groupType   | questionName   | itemName   | itemType   | isYes   | value   |
      | <groupType> | <questionName> | <itemName> | <itemType> | <isYes> | <value> |
    And The request should be succeed


    Then Health history information should match below info
      | groupType   | questionName   | itemName   | itemType   | isYes   | value   |
      | <groupType> | <questionName> | <itemName> | <itemType> | <isYes> | <value> |

    #Load percent of <groupType>
    And I load health history
      | groupType   |
      | <groupType> |
    And The response should be
      | success      | true           |
      | data.percent | <percentGroup> |


    #Load percent of Health history
    And I load health history
      | groupType |
      |           |
    And The response should be
      | success      | true      |
      | data.percent | <percent> |
    Examples:
      | account      | groupType           | questionName                     | itemName                      | itemType   | isYes | value                                                         | percentGroup | percent |
      | user_1       | StatusAtBirth       | Delivery status                  | C-section                     | Checkbox   | true  |                                                               | 14           | 1       |
      | Sub AutoTest | StatusAtBirth       | Birth data                       | Height at birth               | Height     |       | 165                                                           | 14           | 1       |
      | user_1       | StatusAtBirth       | Birth data                       | Weight at birth               | Weight     |       | 70                                                            | 14           | 1       |
      | user_1       | StatusAtBirth       | Birth data                       | Head circumference at birth   | Head       |       | 30                                                            | 14           | 1       |
      | user_1       | StatusAtBirth       | Birth Problem                    | Birth defect                  | OptionText | true  | bình thường                                                   | 14           | 1       |
      | user_1       | StatusAtBirth       | Birth Problem                    | Other issue                   | OptionText | true  | không có                                                      | 14           | 1       |
      | user_1       | LifestyleHealth     | Smoking                          | Quit                          | Checkbox   | true  |                                                               | 12           | 1       |
      | user_1       | LifestyleHealth     | Drink alcohol                    | Quit                          | Checkbox   | true  |                                                               | 12           | 1       |
      | user_1       | LifestyleHealth     | Use narcotic                     | Regularly                     | Checkbox   | true  |                                                               | 12           | 1       |
      | user_1       | LifestyleHealth     | Exercises                        | No                            | Checkbox   | true  |                                                               | 12           | 1       |
      | user_1       | LifestyleHealth     | Occupational risk factors        | Occupational risk factors     | OptionText | true  | tiếp xúc chất độc hại                                         | 12           | 1       |
      | user_1       | LifestyleHealth     | Household toilet type            | Two-Piece Toilet              | Checkbox   | true  |                                                               | 12           | 1       |
      | user_1       | LifestyleHealth     | Other                            | Other potential health hazard | OptionText | true  | ô nhiễm không khí                                             | 12           | 1       |
      | user_1       | HistoryOfAllergy    | History of allergy               | Medicine                      | OptionText | true  | hội chứng quá mẫn với aspirin                                 | 20           | 1       |
      | user_1       | HistoryOfAllergy    | History of allergy               | Chemicals/ cosmetics          | OptionText | true  | tiền sử phản vệ không rõ nguyên nhân                          | 20           | 1       |
      | user_1       | HistoryOfAllergy    | History of allergy               | Foods                         | OptionText | true  | hội chứng quá mẫn với thuốc chống viêm giảm đau không steroid | 20           | 1       |
      | user_1       | HistoryOfAllergy    | History of allergy               | Other                         | OptionText | true  | dị ứng vaccine                                                | 20           | 1       |
      | user_1       | HealthHistory       | Select all that apply            | Heart diseases                | Checkbox   | true  |                                                               | 20           | 1       |
      | user_1       | HealthHistory       | History description              | Cancer                        | OptionText | true  | ưng thư phổi                                                  | 20           | 1       |
      | user_1       | HealthHistory       | History description              | Tb                            | OptionText | true  | có chịu chứng nhẹ                                             | 20           | 1       |
      | user_1       | HealthHistory       | History description              | Other                         | OptionText | true  | viêm mũi dị ứng                                               | 20           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Hearing                       | OptionText | true  | có,bà con                                                     | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Eyesight                      | OptionText | true  | có,bà con                                                     | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Hand                          | OptionText | true  | có,bà con                                                     | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Foot                          | OptionText | true  | có,bà con                                                     | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Spinal curvature              | OptionText | true  | có                                                            | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Opened lips, palate           | OptionText | true  | có                                                            | 12           | 1       |
      | user_1       | HistoryOfDisability | History of disability            | Other                         | OptionText | true  | có                                                            | 12           | 1       |
      | user_1       | HistoryFamily       | History of surgery               | History of surgery            | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Heart                         | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Mental                        | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Athsma                        | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Diabetes                      | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Epileptic                     | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Cancer                        | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Tuberculosis                  | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | HistoryFamily       | History of family illness        | Other                         | OptionText | true  | có                                                            | 10           | 1       |
      | user_1       | FamilyPlan          | Birth control and Last pregnancy | Birth control                 | OptionText | true  | có                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Birth control and Last pregnancy | Last pregnancy                | DateTime   |       | today                                                         | 7            | 1       |
      | user_1       | FamilyPlan          | Number of pregnancies            | Pregnant                      | Number     |       | 100                                                           | 7            | 1       |
      | user_1       | FamilyPlan          | Number of pregnancies            | Miscarriage                   | Number     |       | 200                                                           | 7            | 1       |
      | user_1       | FamilyPlan          | Number of pregnancies            | Abortion                      | Number     |       | 300                                                           | 7            | 1       |
      | user_1       | FamilyPlan          | Number of births                 | Number of births              | Number     |       | 51                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of births                 | Ordinary                      | Number     |       | 52                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of births                 | C-section                     | Number     |       | 53                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of births                 | Difficult                     | Number     |       | 54                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of full-time births       | Number of full-time births    | Number     |       | 55                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of full-time births       | Premature                     | Number     |       | 56                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Number of full-time births       | Number of children living     | Number     |       | 57                                                            | 7            | 1       |
      | user_1       | FamilyPlan          | Gynecological disease            | Gynecological disease         | OptionText | true  | có                                                            | 7            | 1       |
      | user_1       | Other               | Other                            | Other                         | OptionText | true  | bình thường                                                   | 50           | 1       |



