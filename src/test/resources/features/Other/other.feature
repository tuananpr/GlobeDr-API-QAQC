@regression @other

Feature: Other

  @other-1
  Scenario: load regions of country
    Given I login as "system_admin_1"
    When I change language is VN
    And I get regions of country VN
    Then The response path "data" should be matched with "data/expected_result/other/expected_regions_vn.json"
    When I change language is US
    And I get regions of country VN
    Then The response path "data" should be matched with "data/expected_result/other/expected_regions_vn.json"
    When I change language is US
    And I get regions of country US
    Then The response path "data" should be matched with "data/expected_result/other/expected_regions_us.json"
    When I change language is VN
    And I get regions of country US
    Then The response path "data" should be matched with "data/expected_result/other/expected_regions_us.json"


  @other-2
  Scenario: load meta data
    When I load meta data
    And The request should be succeed

  @other-3
  Scenario: get download app
    When I get download app
    And The request should be succeed

  @other-4
  Scenario: get download app
    When I get landing page
    And The request should be succeed

  @other-5
  Scenario: check phone
    When I check phone "0988889999"
    And The request should be succeed


  @other-6
  Scenario: load guides
    When I load guides
      | language | 1 |
    And The request should be succeed

  @other-7
  Scenario: detect my location
    When I detect my location
    Then The request should be succeed
    And Response has country is match with my location

  @other-8
  Scenario Outline: get countries
    When I get countries
    Then The response path "data" should be matched with "<expected>"
    Examples:
      | expected                                  |
      | data/expected_result/other/countries.json |

  @other-9
  Scenario Outline: get cities
    When I get cities
      | country   |
      | <country> |
    Then The response path "data" should be matched with "<expected>"
    Examples:
      | country | expected                                 |
      | VN      | data/expected_result/other/citiesVN.json |

  @other-10
  Scenario Outline: get districts
    When I get districts
      | country   | city   |
      | <country> | <city> |
    Then The response path "data" should be matched with "<expected>"
    Examples:
      | country | city | expected                                     |
      | VN      | HCM  | data/expected_result/other/districtsHCM.json |

  @other-11
  Scenario Outline: get wards
    When I get wards
      | country   | city   | district   |
      | <country> | <city> | <district> |
    Then The response path "data" should be matched with "<expected>"
    Examples:
      | country | city | district    | expected                                |
      | VN      | HCM  | Quận Gò Vấp | data/expected_result/other/wardsGV.json |
