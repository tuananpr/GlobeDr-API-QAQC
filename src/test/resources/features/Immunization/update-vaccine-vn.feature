@regression @health @immnunization @update-vaccine
Feature: Update vaccine

  Background:
    Given I re-signup "user_4" account and update profile

  @update-vaccine-1
  Scenario Outline:The injection record is updated successfully <description>
    Given I get the information immunization
    And The request should be succeed
    Then I update "HBV" vaccine id with "ENGERIXB10" med and "D0" dose below info
      | notes              | receiveDate |
      | test update record | <DATE>      |
    And The request should be succeed
    Examples:
      | DATE       |
      | 2018-10-12 |
      | 3365-12-10 |
      | 2983-12-10 |


  @update-vaccine-2
  Scenario Outline: <The type of vaccine> vaccine in <description> group is updated successfully
    Given I update "<vaccine>" vaccine id with "" med and "<dose>" dose below info
      | notes              | receiveDate |
      | test update record | today       |
    And The request should be succeed
    Then I get immunization by age

    And The request should be succeed
    Then The status "<vaccine2>" vaccine should be '3' in "<time>" group name with dose is "<dose>"

    And The request should be succeed
    #When I remove "<vaccine>" vaccine id with "" med and "<dose>" dose
    When I remove immunization
      | dose   | medId | vacId     |
      | <dose> |       | <vaccine> |

    And The request should be succeed
    Then I get immunization by age

    And The request should be succeed
    Then The status "<vaccine2>" vaccine should be '1' in "<time>" group name with dose is "<dose>"

    And The request should be succeed
    Examples:
      | vaccine | dose | vaccine2            | time        |
      | BCG     | D0   | Lao                 | At Birth    |
      | DTAP    | D1   | BachHau_UonVan_HoGa | 2 month     |
      | HIB     | D2   | Hib                 | 3 month     |
      | PCV     | D3   | Phoi                | 4 month     |
      | FLUV    | D1   | Cum                 | 5 month     |
      | FLUV    | D2   | Cum                 | 7-9 month   |
      | MMR     | D1   | Rubella             | 12-18 month |
      | MCVAC   | D1   | ViemNaoAC           | 02 yr       |
      | JEB     | R1   | ViemNaoNhatBan      | 4-6 yr      |
      | JEB     | R2   | ViemNaoNhatBan      | 7-10 yr     |
      | MCVAC   | R3   | ViemNaoAC           | 11-12 yr    |
      | TYV     | R4   | ThuongHan           | 13-15 yr    |
      | TYV     | R5   | ThuongHan           | 16-18 yr    |

  @update-vaccine-3
  Scenario Outline: '<vaccine>' vaccine is updated successfully with medicine is "<med>"
    Given I get immunization by age
    When I update "<vaccine>" vaccine id with "<med>" med and "<dose>" dose below info
      | notes              | receiveDate |
      | test update record | today       |
    And The request should be succeed

    Then I get immunization by vaccine
    And The request should be succeed
    Then "<vaccine>" group Id should have '<shot>' shot

    Examples:
      | vaccine | med        | dose | shot |
      | RV      | ROTARIX1.5 | D1   | 2    |
      | RV      | ROTATEQ2.0 | D1   | 3    |

