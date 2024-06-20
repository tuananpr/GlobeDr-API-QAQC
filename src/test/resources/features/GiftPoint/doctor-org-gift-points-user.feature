@regression @gift-points @gift-points-user @doctor-gift-points-user @doctor-org-gift-points-user
Feature: Doctor of org gift points to user

  As system
  I want to Doctor that is created by org, can't gift point to user

  Background: create doctor and user then check default points
    Given On SqlServer, I delete user by gdrLogin "thuytran@gmail.com"
    Given I re-signup "user_2" account
    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name         | owner     |
      | BV_THUY_TRAN | manager_1 |

    And As sysAdmin, I setting customer care
      | name         | maxCustomerCare |
      | BV_THUY_TRAN | 4               |
    And The request should be succeed

    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_THUY_TRAN" that I manage
    When I add department on org
      | name     |
      | Khoa Tim |
    And The request should be succeed
    
    # manager create new doctor to deparment
    When On org, I create new account on department
      | email              | title  | displayName | password | isProvider | telemedicine | vip   | deptName |
      | thuytran@gmail.com | bác sĩ | thuy tran   | thuytran | true       | false        | false | Khoa Tim |
    Then The request should be succeed

    And I login account
      | gdrLogin           | password | country | language |
      | thuytran@gmail.com | thuytran | VN      | 0        |
    And The request should be succeed

  @doctor-org-gift-points-user-1
  Scenario: doctor org can't gift point to user
    And As doctor, I search user to gift point
      | name   | phone       |
      | user_2 | 84000000012 |
    Then The response should be
      | success   | false                  |
      | message   | Please try again later |
      | data.list | []                     |