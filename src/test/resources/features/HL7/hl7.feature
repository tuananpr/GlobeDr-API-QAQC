@regression @hl7
Feature: HL7

  @hl7
  Scenario Outline: read information from insurance card
    Given I get information from below insurance card "DN4797937337969|5068616e20486fc3a06e67205175c3bd|31/05/1994|1|3839204e677579e1bb856e2056c4836e2043c3b46e672c205068c6b0e1bb9d6e672030332c205175e1baad6e2047c3b22056e1baa5702c205468c3a06e68207068e1bb912048e1bb93204368c3ad204d696e68|79 - 426|01/10/2018|-|29/10/2018|79077937337969|-|4|01/11/2022|6f34b2d123350b6-7102|$"
    Then The response should be
      | success            | true                                                              |
      | <key>.id           | DN4797937337969                                                   |
      | <key>.name         | Phan Hoàng Quý                                                    |
      | <key>.dob          | 1994-05-31T00:00:00.000                                           |
      | <key>.address      | 89 Nguyễn Văn Công, Phường 03, Quận Gò Vấp, Thành phố Hồ Chí Minh |
      | <key>.gender       | 1                                                                 |
      | <key>.registerDate | 2022-11-01T00:00:00.000                                           |
      | <key>.fromDate     | 2018-10-01T00:00:00.000                                           |
      | <key>.toDate       | 2018-10-29T00:00:00.000                                           |
    Examples:
      | key       |
      | data.info |

