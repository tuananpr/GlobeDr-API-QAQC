@regression @invitation
Feature: Invitation

  As user
  I want to introduce GlobedrApp to others

  Background:
    And I re-signup "user_1" account and update profile
    Given I login as "user_1"

  @invitation-01
  Scenario: Invite with valid phone number
    And I invitation user
      | phone      | country |
      | 0988603107 | VN      |
    Then The request should be succeed

  @invitation-02
  Scenario: Invited to the phone number has been invited
    And I invitation user
      | phone      | country |
      | 0988603117 | VN      |
    Then The request should be succeed

    And I invitation user
      | phone      | country |
      | 0988603117 | VN      |
    Then The request should be succeed

    And I invitation user
      | phone      | country |
      | 0988603117 | VN      |
    Then The request should be fail


  @invitation-03
  Scenario: invite with phone number is itself
    And I invitation user
      | phone       | country |
      | 84000000011 | VN      |
    Then The request should be fail

  @invitation-04
  Scenario: Invite to user from US
    And I invitation user
      | phone      | country |
      | 0347249656 | VN      |

    Then The request should be succeed
    And I invitation user
      | phone      | country |
      | 0347249656 | US      |
    Then The request should be succeed

  @invitation-05
  Scenario: Invite more than 1 user
    And I invitation user
      | phone      | country |
      | 0988600147 | VN      |
    Then The request should be succeed

    And I invitation user
      | phone      | country |
      | 0983504256 | VN      |
    Then The request should be succeed

    And I invitation user
      | phone      | country |
      | 0347220941 | VN      |
    Then The request should be succeed








