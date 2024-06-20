@regression @article @article-post-system
Feature: Manage post in System

  Background:
    Given On SqlServer, I delete organization by name "BV_NHAN_VAN"
    Given On SqlServer, I delete all posts in system
    When I login as "approver_1"

  @article-post-system-01
  Scenario: create system post
    When I create a new system post
      | key | type | subject                                | msg                                             | language |
      | 9   | 9    | POST About us                          | This is content information of About us         | 0        |
      | 10  | 10   | POST Terms of service                  | This is content information of Terms of service | 0        |
      | 11  | 11   | POST About setting                     | This is content information of About setting    | 0        |
      | 13  | 13   | POST Immunization                      | This is content information of Immunization     | 0        |
      | 14  | 14   | POST About the immunization            | About the immunization                          | 0        |
      | 15  | 15   | POST About Immunization by age         | About Immunization by age                       | 0        |
      | 16  | 16   | POST About Immunization by vaccination | About Immunization by vaccination               | 0        |
      | 17  | 17   | POST About Immunization record         | About Immunization record                       | 0        |
      | 18  | 18   | POST About Pregnancy vaccination       | About Pregnancy vaccination                     | 0        |
      | 20  | 20   | POST About consult                     | About consult                                   | 0        |
      | 30  | 30   | POST About health                      | About health                                    | 0        |
      | 31  | 31   | POST About health document             | About health document                           | 0        |
      | 32  | 32   | POST About health history              | About health history                            | 0        |
      | 33  | 33   | POST Developmental milestones          | Developmental milestones                        | 0        |
      | 34  | 34   | POST About BMI                         | About BMI                                       | 0        |
      | 35  | 35   | POST About growth chart                | About growth chart                              | 0        |
      | 36  | 36   | POST About blood pressure              | About blood pressure                            | 0        |
      | 37  | 37   | POST About blood glucose               | About blood glucose                             | 0        |
      | 38  | 38   | POST About Children's BMI              | About Children's BMI                            | 0        |
      | 40  | 40   | POST supportVoucher                    | About supportVoucher                            | 0        |
      | 45  | 45   | POST aboutBestFunction                 | aboutBestFunction                               | 0        |
      | 46  | 46   | POST About Comprehensive healthcare    | About Comprehensive healthcare                  | 0        |
      | 47  | 47   | POST About Changelog                   | About the Changelog                             | 0        |
      | 48  | 48   | POST FAQ AUTOMATION                    | This is content information of FAQ              | 0        |
    Then I must see a below system post
      | key | type | subject                                | msg                                             | language |
      | 9   | 9    | POST About us                          | This is content information of About us         | 0        |
      | 10  | 10   | POST Terms of service                  | This is content information of Terms of service | 0        |
      | 11  | 11   | POST About setting                     | This is content information of About setting    | 0        |
      | 13  | 13   | POST Immunization                      | This is content information of Immunization     | 0        |
      | 14  | 14   | POST About the immunization            | About the immunization                          | 0        |
      | 15  | 15   | POST About Immunization by age         | About Immunization by age                       | 0        |
      | 16  | 16   | POST About Immunization by vaccination | About Immunization by vaccination               | 0        |
      | 17  | 17   | POST About Immunization record         | About Immunization record                       | 0        |
      | 18  | 18   | POST About Pregnancy vaccination       | About Pregnancy vaccination                     | 0        |
      | 20  | 20   | POST About consult                     | About consult                                   | 0        |
      | 30  | 30   | POST About health                      | About health                                    | 0        |
      | 31  | 31   | POST About health document             | About health document                           | 0        |
      | 32  | 32   | POST About health history              | About health history                            | 0        |
      | 33  | 33   | POST Developmental milestones          | Developmental milestones                        | 0        |
      | 34  | 34   | POST About BMI                         | About BMI                                       | 0        |
      | 35  | 35   | POST About growth chart                | About growth chart                              | 0        |
      | 36  | 36   | POST About blood pressure              | About blood pressure                            | 0        |
      | 37  | 37   | POST About blood glucose               | About blood glucose                             | 0        |
      | 38  | 38   | POST About Children's BMI              | About Children's BMI                            | 0        |
      | 40  | 40   | POST supportVoucher                    | About supportVoucher                            | 0        |
      | 45  | 45   | POST aboutBestFunction                 | aboutBestFunction                               | 0        |
      | 46  | 46   | POST About Comprehensive healthcare    | About Comprehensive healthcare                  | 0        |
      | 47  | 47   | POST About Changelog                   | About the Changelog                             | 0        |
      | 48  | 48   | POST FAQ AUTOMATION                    | This is content information of FAQ              | 0        |


  @article-post-system-02
  Scenario: System post is created and updated successfully
    When I create a new system post
      | key  | type | subject                | msg                                         | language |
      | Rota | 13   | POST SYSTEM AUTOMATION | This is content information of Rota vaccine | 0        |
    And The request should be succeed
    Then I update system post that has "13" type and "POST SYSTEM AUTOMATION" subject with below info
      | key     | type | subject | msg             | language |
      | updated | 9    | WELCOME | welcome content | 0        |
    And The request should be succeed
    Then I must see a below system post
      | key     | type | subject | msg             | language |
      | updated | 9    | WELCOME | welcome content | 0        |


