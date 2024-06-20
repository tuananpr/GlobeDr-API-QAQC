@regression @appt-ui @appt-ui @appt
Feature: Appointment - user make appointment to org

  As Client,
  I want load dynamic appointment UI from config

  # expected match with value into metaData.controlTypeAppt
  @appt-ui-1-a
  Scenario Outline: get ui when user book appointment
    Given I load ui of appointment
      | type              |
      | <appointmentType> |
    Then The response should be
      | success   | true              |
      | data.list | <controlTypeAppt> |
    Examples:
      | appointmentType         | controlTypeAppt            |
      | VisitInPerson           | [5,16,17,14,24]            |
      | VisitVideoCall          | [24]                       |
      | FirstTimeExamination    | [15,14,24]                 |
      | ReExamination           | [4,3,14,24]                |
      | PhysicalExamination     | [3,14,24]                  |
      | TelemedicineExamination | [3,8,9,10,14,24]           |
      | OnlineConsultant        | [3,8,9,10,14,24]           |
      | HomeMedicalService      | [7,3,12,20,21,22,23,14,24] |
      | ImmunizationExamination | [11,7,3,14,24]             |
      | VisitAtOneDoctorClinic  | [5,16,17,14,24]            |
      | VisitAtSmallPolyClinic  | [16,5,14,24]               |
