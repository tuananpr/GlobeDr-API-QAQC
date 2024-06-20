Feature: Test


  @encrypt-request
  Scenario: Encrypt
    When I encrypt content
    """
{
  "orderSig": "79777241345456384E724779593447397A6674522B4372494B4B6734726566684E4D486567775755326B2B53664E355575774C526552677562734F4837314F46"
}


    """


  @decrypt-request
  Scenario: Decrypt
    When I decrypt content
    """


{"k":"CZJ7/yYXXIxeW1DroHufiVIqqS2lsupQOn4Rig+B6NZZA4HnpQlTv80lHH+rM4lJOhPo97QDWQLIx7qsMvHUiHWyZaS89mgeWn067V+aHTcfe3aL4CRdqMDoAMLTUc0VT1DjQ9Je9QfoHKCArudJFibdyly95OQddQ24bS6F0FVch5tKo5RYSZlX7xk3IuxXf7UR2m+nEhl2yLNRAHv93t2rfGL4kOhiivY2guiEJmaNMIvXm6/7G+BI1c+QhaBjuR5CGJLum4BQMZu/1lsD0DZDAMwXL4bShso87sXK04Qsx6EincV/8Obr6lVwJIfKmZmwwoLxSJQo/V94aTq0/w==","d":"q9pTRIWXOYjMypH2TaM/94D5LpGPRVJRpWnurS9Qe4NZm3QZzcfPSuYY6cgeEoIiLqVR4uvp0PwcWXzb9eGMEGFm7MogjBjJd9dr+AkhBBKIkjg/NQyhL7gx20YItYlca7KfNFItZ1bYnZdxesjUeA=="}



  """


  @bit
  Scenario Outline: bit caculator
    When I re-signup "<role>" account
    And I login as "anh_hai"
    Examples:
      | role    |
      | anh_hai |

  @t
  Scenario: t
    And test



  @d
  Scenario Outline: clear all account for recaptcha v3
    Given On SqlServer, I delete user
      | userId  |
      | 4016505 |
      | 4016825 |
      | 4016729 |
      | 4016502 |
      | 4016508 |
      | 4016514 |
      | 4016527 |
      | 4016759 |
      | 4016607 |
      | 4016823 |

    Examples:
      | phone      | country |
      | 0767890100 | VN      |
      | 0767890101 | VN      |


  @recaptcha-part-2
  Scenario: open web to get recaptcha v3
    Given I open chrome browser
    And I navigate to "http://globedr.com:9090/Testing/demoRecaptcha.jsp"

  @recaptcha-part-3
  Scenario Outline: signup with recaptcha v3
    And I refresh page
    And I get recaptcha
    When I signup new account with below info
      | country | gdrLogin | password | displayName | gender | language | tokenCaptchaV3     |
      | VN      | <phone>  | 123456   | duy test    | 1      | 0        | {{tokenCaptchaV3}} |
    Examples:
      | phone      |
      | 0767890100 |
      | 0767890101 |


  @aa
  Scenario Outline: asd dasda
    Given On SqlServer, I delete user by username "<phone>" and country "<country>"
    When I signup new account with below info and verify it
      | country   | gdrLogin | password | displayName   | gender | language |
      | <country> | <phone>  | 123456   | <displayName> | 1      | 0        |
    And The request should be succeed
    And I login account
      | gdrLogin | password | country   |
      | <phone>  | 123456   | <country> |

    When I request connection to below user
      | name |
      | duy  |
    And The request should be succeed
    And I login account
      | gdrLogin   | password | country   |
      | 0767892632 | 123456   | <country> |
    When I load request connection list
    And I have '1' connections request
    And I accept all request connection
    Then The request should be succeed
    Examples:
      | phone      | country | displayName |
      | 0303305401 | VN      | be_1        |
      | 0303305402 | VN      | be_2        |
      | 0303305403 | VN      | be_3        |
      | 0303305404 | VN      | be_4        |
      | 0303305405 | VN      | be_5        |
      | 0303305406 | VN      | be_6        |
      | 0303305407 | VN      | be_7        |
      | 0303305408 | VN      | be_8        |
      | 0303305409 | VN      | be_9        |
      | 0303305410 | VN      | be_10       |
      | 0303305411 | VN      | be_11       |
      | 0303305412 | VN      | be_12       |
      | 0303305413 | VN      | be_13       |
      | 0303305414 | VN      | be_14       |
      | 0303305415 | VN      | be_15       |
      | 0303305416 | VN      | be_16       |
      | 0303305417 | VN      | be_17       |
      | 0303305418 | VN      | be_18       |
      | 0303305419 | VN      | be_19       |
      | 0303305420 | VN      | be_20       |
      | 0303305421 | VN      | be_21       |
      | 0303305422 | VN      | be_22       |
      | 0303305423 | VN      | be_23       |


  @manual
  Scenario: Signup and get code.
    When get verify code of number phone "0767891596" with country "VN"


  @manual-change-phone
  Scenario: upload phone and get code.
    When get verify code while changing number phone "0311110000" with country "VN"


  @manual-recovery-password
  Scenario: recovery password and get code.
    When get verify code while recovery password for number phone "0767891240" with country "VN"

  @clear-all-cache
  Scenario: I clear all cache
    When I clear all cache

  @allow-delete-es
  Scenario: allow delete elastic search
    When allow delete elastic search


  @doctor-test
  Scenario Outline: Create doctor with specialty
    Given I re-signup "<phone>" account provider and update profile
    When I login as "<phone>"
    Then The request should be succeed

    Examples:
      | phone      |
      | 0714141414 |