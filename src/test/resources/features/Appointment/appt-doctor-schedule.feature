@regression @appt-doctor-schedule @appt
Feature: Appointment - Doctor create work schdule

  Background:
    Given I re-signup "DoctorAuto" account provider and update profile
    And I login as "DoctorAuto"

  @appt-doctor-schedule-1
  Scenario: Doctor load work schedule
    Given I load my work schedule
    Then The response should be
      | success   | true |
      | data.list | []   |


  @appt-doctor-schedule-2
  Scenario: Doctor add new work schedule
    Given I update my work schedule
      | weekday   | timeType    |
      | Sunday    | OnlyMorning |
      | Monday    | OnlyMorning |
      | Tuesday   | OnlyMorning |
      | Wednesday | OnlyMorning |
      | Thursday  | OnlyMorning |
      | Friday    | OnlyMorning |
      | Saturday  | OnlyMorning |
    Then The request should be succeed
    Given I load my work schedule
    Then The response should be
      | success                               | true       |
      | data.list[0].doctor.name              | DoctorAuto |
      | data.list[0].examination.timeType     | 1          |
      | data.list[0].examination.timeTypeName | Morning    |
      | data.list[0].weekdayName              | Sunday     |

      | data.list[1].examination.timeType     | 1          |
      | data.list[1].examination.timeTypeName | Morning    |
      | data.list[1].weekdayName              | Monday     |

      | data.list[2].examination.timeType     | 1          |
      | data.list[2].examination.timeTypeName | Morning    |
      | data.list[2].weekdayName              | Tuesday    |

      | data.list[3].examination.timeType     | 1          |
      | data.list[3].examination.timeTypeName | Morning    |
      | data.list[3].weekdayName              | Wednesday  |

      | data.list[4].examination.timeType     | 1          |
      | data.list[4].examination.timeTypeName | Morning    |
      | data.list[4].weekdayName              | Thursday   |

      | data.list[5].examination.timeType     | 1          |
      | data.list[5].examination.timeTypeName | Morning    |
      | data.list[5].weekdayName              | Friday     |

      | data.list[6].examination.timeType     | 1          |
      | data.list[6].examination.timeTypeName | Morning    |
      | data.list[6].weekdayName              | Saturday   |

  @appt-doctor-schedule-3
  Scenario: Doctor clear all work schedule
    Given I update my work schedule
      | weekday   | timeType    |
      | Sunday    | OnlyMorning |
      | Monday    | OnlyMorning |
      | Tuesday   | OnlyMorning |
      | Wednesday | OnlyMorning |
      | Thursday  | OnlyMorning |
      | Friday    | OnlyMorning |
      | Saturday  | OnlyMorning |
    Then The request should be succeed

    Given I clear my work schedule
    Then The request should be succeed
    Given I load my work schedule
    Then The response should be
      | success   | true |
      | data.list | []   |


