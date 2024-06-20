@regression @gift-points @gift-points-manage
Feature: Gift points

  As System admin
  I want to create reward points daily for user

  Background:
    And I login as "system_admin_1"

  @gift-points-manage-1
  Scenario Outline: sysAdmin create reward points daily
    Given On SqlServer, I delete point rules
      | descriptionHtml   |
      | <descriptionHtml> |
    And As sysAdmin, I create point rules
      | descriptionHtml   | amount   | nameVi   | nameEn   | fromDate   | toDate   | pointActivity   |
      | <descriptionHtml> | <amount> | <nameVi> | <nameEn> | <fromDate> | <toDate> | <pointActivity> |
    Then The request should be succeed
    And As sysAdmin, I load point rules
      | page |
      |      |
    Then The response should be
      | success                      | true                |
      | data.list[0].descriptionHtml | <descriptionHtml>   |
      | data.list[0].amount          | <amount>            |
      | data.list[0].pointActivity   | <pointActivityCode> |
      | data.list[0].ruleName.nameVi | <nameVi>            |
      | data.list[0].ruleName.nameEn | <nameEn>            |
    Given On SqlServer, I delete point rules
      | descriptionHtml   |
      | <descriptionHtml> |
    Examples:
      | descriptionHtml       | amount | nameVi                | nameEn          | fromDate | toDate | pointActivity          | pointActivityCode |
      | DON_DAT_HANG_DAU_TIEN | 10     | DON_DAT_HANG_DAU_TIEN | THE_FIRST_ORDER | today    | today  | NormalConsultantPntAct | 32                |
