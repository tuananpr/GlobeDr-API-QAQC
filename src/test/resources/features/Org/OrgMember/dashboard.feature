@regression @org-member @dashboard
Feature: Dashboard of org

  As System Admin
  I want to view user statistics chart
  In order to analyze growth rate of user

  Background:
    Given On SqlServer, I delete organization by name "BV_HOA_VAN"
    And I re-signup "user_4" account
    And I re-signup "user_5" account
    And I re-signup "user_6" account
    And I re-signup "user_Test" account
    And I re-signup "manager_3" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name       | owner     |
      | BV_HOA_VAN | manager_3 |

    # User Country: VN, city : HO CHI MINH, Gender : Male
    When I login as "user_Test"
    And I update my profile with below info
      | displayName | dob         | email                  | address                                     | country | gender | phone      | city                                  |
      | user_test   | 2000-10-17T | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | 1      | 0376559999 | {"code": "HCM","name": "HO CHI MINH"} |
    Then The request should be succeed
    And I FOLLOW business
      | name       |
      | BV_HOA_VAN |
    And I FOLLOW business
      | name       |
      | BV_HOA_VAN |
    Then The request should be succeed

    # User Country: VN, city : An Giang, Gender : Female
    When I login as "user_5"
    And I update my profile with below info
      | displayName | dob         | email                  | address                                     | country | gender | phone      | city                              |
      | user_5      | 2000-10-17T | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | VN      | 2      | 0376559999 | {"code": "AG","name": "An Giang"} |
    Then The request should be succeed

    And I FOLLOW business
      | name       |
      | BV_HOA_VAN |
    Then The request should be succeed

     # User Country: US, city : Other, Gender : Unspecified
    When I login as "user_4"
    And On SqlServer, I update below info to username "0000000014" and country "VN"
      | gender | 4 |
    Then The request should be succeed

    And I FOLLOW business
      | name       |
      | BV_HOA_VAN |
    Then The request should be succeed

    When I login as "user_6"
    And I update my profile with below info
      | displayName | dob         | email                  | address                                     | country | gender | phone      | city                        |
      | user_6      | 2000-10-17T | sonvo@bacsitoancau.com | 69 Hoàng Văn Thụ, Phường 15, Quận Phú Nhuận | US      | 2      | 0376559999 | {"code": null,"name": null} |

    And I FOLLOW business
      | name       |
      | BV_HOA_VAN |
    Then The request should be succeed

  @dashboard-1
  Scenario: System admin view user statistics chart on dashboard
    When I login as "manager_3"
    And I accept join organization
    And I select org "BV_HOA_VAN" that I manage

    And I load user dashboard by gender
    Then The response should be
      | data.maleNo        | 1 |
      | data.femaleNo      | 2 |
      | data.unspecifiedNo | 1 |

    And I load user dashboard by country
    Then The response should be
      | data.list[0].country | VN |
      | data.list[0].count   | 3  |
      | data.list[1].country | US |
      | data.list[1].count   | 1  |

    And I load user dashboard by city
      | country |
      | VN      |
    Then The response should be
      | data.total            | 3           |
      | data.list[1].cityName | An Giang    |
      | data.list[1].count    | 1           |
      | data.list[2].cityName | Hồ Chí Minh |
      | data.list[2].count    | 1           |

    And I load user dashboard by growth chart
      | fromDate    | toDate | country   |
      | prev 10 day | today  | {{empty}} |
    Then The result return should be "today" and count '4' user

