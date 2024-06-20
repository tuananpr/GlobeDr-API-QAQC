@regression @health-history @print-health-history @health
Feature: Health History for main acc

  Background:
    When I re-signup "user_1" account
    Given I login as "user_1"
    Then I update my profile with below info
      | displayName | dob                      | email                  | address                                     | city                              | country | gender | phone     |
      | user_1      | 1996-10-17T00:00:00.000Z | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | {"code": "AG","name": "An Giang"} | VN      | Female | 376559999 |
    Then The request should be succeed
    When I switch main of family members with below info
      | name |
      |      |
    And The request should be succeed

  @print-health-history-1
  Scenario: print health history to pdf file with default information
    When I print health history
    And The request should be succeed
    And I print health history and it should match with "data/expected_result/health/defaultHealthHistory.pdf"

  @print-health-history-2
  Scenario: print health history to pdf file with full information
    Then I change blood type with below info
      | bloodType |
      | O         |
    And The request should be succeed
    And I update health history information
      | groupType           | questionName                     | itemName                      | itemType   | isYes | value                                                         |
      | StatusAtBirth       | Delivery status                  | C-section                     | Checkbox   | true  |                                                               |
      | StatusAtBirth       | Birth data                       | Height at birth               | Height     |       | 165                                                           |
      | StatusAtBirth       | Birth data                       | Weight at birth               | Weight     |       | 70                                                            |
      | StatusAtBirth       | Birth data                       | Head circumference at birth   | Head       |       | 30                                                            |
      | StatusAtBirth       | Birth Problem                    | Birth defect                  | OptionText | true  | bình thường                                                   |
      | StatusAtBirth       | Birth Problem                    | Other issue                   | OptionText | true  | không có                                                      |
      | LifestyleHealth     | Smoking                          | Quit                          | Checkbox   | true  |                                                               |
      | LifestyleHealth     | Drink alcohol                    | Quit                          | Checkbox   | true  |                                                               |
      | LifestyleHealth     | Use narcotic                     | Regularly                     | Checkbox   | true  |                                                               |
      | LifestyleHealth     | Exercises                        | No                            | Checkbox   | true  |                                                               |
      | LifestyleHealth     | Occupational risk factors        | Occupational risk factors     | OptionText | true  | tiếp xúc chất độc hại                                         |
      | LifestyleHealth     | Household toilet type            | Two-Piece Toilet              | Checkbox   | true  |                                                               |
      | LifestyleHealth     | Other                            | Other potential health hazard | OptionText | true  | ô nhiễm không khí                                             |
      | HistoryOfAllergy    | History of allergy               | Medicine                      | OptionText | true  | hội chứng quá mẫn với aspirin                                 |
      | HistoryOfAllergy    | History of allergy               | Chemicals/ cosmetics          | OptionText | true  | tiền sử phản vệ không rõ nguyên nhân                          |
      | HistoryOfAllergy    | History of allergy               | Foods                         | OptionText | true  | hội chứng quá mẫn với thuốc chống viêm giảm đau không steroid |
      | HistoryOfAllergy    | History of allergy               | Other                         | OptionText | true  | dị ứng vaccine                                                |
      | HealthHistory       | Select all that apply            | Heart diseases                | Checkbox   | true  |                                                               |
      | HealthHistory       | History description              | Cancer                        | OptionText | true  | ưng thư phổi                                                  |
      | HealthHistory       | History description              | Tb                            | OptionText | true  | có chịu chứng nhẹ                                             |
      | HealthHistory       | History description              | Other                         | OptionText | true  | viêm mũi dị ứng                                               |
      | HistoryOfDisability | History of disability            | Hearing                       | OptionText | true  | có,bà con                                                     |
      | HistoryOfDisability | History of disability            | Eyesight                      | OptionText | true  | có,bà con                                                     |
      | HistoryOfDisability | History of disability            | Hand                          | OptionText | true  | có,bà con                                                     |
      | HistoryOfDisability | History of disability            | Foot                          | OptionText | true  | có,bà con                                                     |
      | HistoryOfDisability | History of disability            | Spinal curvature              | OptionText | true  | có                                                            |
      | HistoryOfDisability | History of disability            | Opened lips, palate           | OptionText | true  | có                                                            |
      | HistoryOfDisability | History of disability            | Other                         | OptionText | true  | có                                                            |
      | HistoryFamily       | History of surgery               | History of surgery            | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Heart                         | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Mental                        | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Athsma                        | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Diabetes                      | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Epileptic                     | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Cancer                        | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Tuberculosis                  | OptionText | true  | có                                                            |
      | HistoryFamily       | History of family illness        | Other                         | OptionText | true  | có                                                            |
      | FamilyPlan          | Birth control and Last pregnancy | Birth control                 | OptionText | true  | có                                                            |
      | FamilyPlan          | Birth control and Last pregnancy | Last pregnancy                | DateTime   |       | 2021-10-12T00:00:00.000Z                                     |
      | FamilyPlan          | Number of pregnancies            | Pregnant                      | Number     |       | 100                                                           |
      | FamilyPlan          | Number of pregnancies            | Miscarriage                   | Number     |       | 200                                                           |
      | FamilyPlan          | Number of pregnancies            | Abortion                      | Number     |       | 300                                                           |
      | FamilyPlan          | Number of births                 | Number of births              | Number     |       | 51                                                            |
      | FamilyPlan          | Number of births                 | Ordinary                      | Number     |       | 52                                                            |
      | FamilyPlan          | Number of births                 | C-section                     | Number     |       | 53                                                            |
      | FamilyPlan          | Number of births                 | Difficult                     | Number     |       | 54                                                            |
      | FamilyPlan          | Number of full-time births       | Number of full-time births    | Number     |       | 55                                                            |
      | FamilyPlan          | Number of full-time births       | Premature                     | Number     |       | 56                                                            |
      | FamilyPlan          | Number of full-time births       | Number of children living     | Number     |       | 57                                                            |
      | FamilyPlan          | Gynecological disease            | Gynecological disease         | OptionText | true  | có                                                            |
      | Other               | Other                            | Other                         | OptionText | true  | bình thường                                                   |
    And The request should be succeed
    When I print health history
    And The request should be succeed
    And I print health history and it should match with "data/expected_result/health/fullHealthHistory.pdf"
