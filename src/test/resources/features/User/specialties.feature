@regression @specialties
Feature: User: Specialties

  Background:
    Given I re-signup "DOCTOR_TEO" account provider and update profile
    And I login as "DOCTOR_TEO"

  @specialties-1
  Scenario: Doctor get specialties
    And I get list specialty of above user
    Then The response should be
      | success          | true |
      | data.specialties | null |

  @specialties-2
  Scenario Outline: Doctor add and remove specialties
    And I want to add "<specialty1>,<specialty2>" as my specialty
    And The request should be succeed

    # load full user info
    And I load full information of above user
      | bioLanguage | appLanguage |
      | 0           | 0           |
    Then List "data.specialties[*].name" of response should contains
      | <specialty1> |
      | <specialty2> |
    # load list specialty
    And I get list specialty of above user
    Then List "data.specialties[*].name" of response should contains
      | <specialty1> |
      | <specialty2> |
    And I want to remove specialty
      | specialtiesName | language |
      | <specialty1>    | 0        |

      # load full user info
    And I load full information of above user
      | bioLanguage | appLanguage |
      | 0           | 0           |
    Then List "data.specialties[*].name" of response should contains
      | <specialty2> |
    And List "data.specialties[*].name" of response should not contains
      | <specialty1> |
     # load list specialty
    And I get list specialty of above user
    Then List "data.specialties[*].name" of response should contains
      | <specialty2> |
    And List "data.specialties[*].name" of response should not contains
      | <specialty1> |
    Examples:
      | specialty1         | specialty2     |
      | Allergy/Immunology | Anesthesiology |

  @specialties-3
  Scenario: Doctor add invalid specialty
    And I want to add "invalid specialty" as my specialty
    Then The response should be
      | success | false                  |
      | message | Please try again later |



