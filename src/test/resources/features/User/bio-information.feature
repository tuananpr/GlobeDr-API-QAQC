@regression @bio-info
Feature: User: Biology information

  Background:
    Given I re-signup "DOCTOR_TEO" account provider and update profile
    And I login as "DOCTOR_TEO"

  @bio-info-1
  Scenario Outline: Doctor load bio information
    # load full user info
    And I load full information of above user
      | bioLanguage | appLanguage |
      | 0           | 0           |
    Then The response should be
      | success                   | true   |
      | data.userInfo.displayName | <name> |
      | data.userInfo.fullName    | <name> |
    And The "data.specialties" should "null"
    And The "<userBioPath>.bioList" should "empty array"
    And The "<userBioPath>.educationList" should "empty array"
    And The "<userBioPath>.experienceList" should "empty array"
    And The "<userBioPath>.awardList" should "empty array"
    And The "<userBioPath>.associationList" should "empty array"
    And The "<userBioPath>.affiliationList" should "empty array"
    And The "<userBioPath>.publicationList" should "empty array"
    And The "<userBioPath>.certificateList" should "empty array"

    # load bio
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The request should be succeed
    And The "<bioPath>.bioList" should "empty array"
    And The "<bioPath>.educationList" should "empty array"
    And The "<bioPath>.experienceList" should "empty array"
    And The "<bioPath>.awardList" should "empty array"
    And The "<bioPath>.associationList" should "empty array"
    And The "<bioPath>.affiliationList" should "empty array"
    And The "<bioPath>.publicationList" should "empty array"
    And The "<bioPath>.certificateList" should "empty array"
    Examples:
      | bioPath | userBioPath  | name       |
      | data    | data.userBio | DOCTOR_TEO |

  @bio-info-2
  Scenario Outline: Doctor add, update, remove <bioType> information

    # add bio
    And I add Bio information
      | type      | line1   | line2   | note   | referenceURL   | isVisible   | forLanguage   |
      | <bioType> | <line1> | <line2> | <note> | <referenceURL> | <isVisible> | <forLanguage> |
    Then The request should be succeed

    # load full user info
    And I load full information of above user
      | bioLanguage | appLanguage |
      | 0           | 0           |
    Then The response should be
      | success                    | true           |
      | <userBioPath>.line1        | <line1>        |
      | <userBioPath>.line2        | <line2>        |
      | <userBioPath>.note         | <note>         |
      | <userBioPath>.referenceUrl | <referenceURL> |
      | <userBioPath>.isVisible    | <isVisible>    |
    And The "<userBioPath>.bioSig" should "not null"

    # load bio
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The response should be
      | success                | true           |
      | <bioPath>.line1        | <line1>        |
      | <bioPath>.line2        | <line2>        |
      | <bioPath>.note         | <note>         |
      | <bioPath>.referenceUrl | <referenceURL> |
      | <bioPath>.isVisible    | <isVisible>    |
    And The "<bioPath>.bioSig" should "not null"

    # update bio
    And I update Bio information
      | type      | line1         | line2         | note         | referenceURL         | isVisible   | forLanguage   |
      | <bioType> | <updateLine1> | <updateLine2> | <updateNote> | <updateReferenceURL> | <isVisible> | <forLanguage> |
    Then The request should be succeed
    # load bio
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The response should be
      | success                | true                 |
      | <bioPath>.line1        | <updateLine1>        |
      | <bioPath>.line2        | <updateLine2>        |
      | <bioPath>.note         | <updateNote>         |
      | <bioPath>.referenceUrl | <updateReferenceURL> |
      | <bioPath>.isVisible    | <isVisible>          |
    And The "<bioPath>.bioSig" should "not null"
    # remove bio
    And I remove Bio
      | type      | forLanguage |
      | <bioType> | 0           |

    And The request should be succeed
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The request should be succeed
    Examples:
      | bioType      | line1             | line2           | note                                             | referenceURL               | isVisible | forLanguage | bioPath                 | userBioPath                     | updateLine1              | updateLine2          | updateNote                                | updateReferenceURL                                      |
      | Bio          | [blank]           | [blank]         | this is bio information                          | [blank]                    | true      | 0           | data.bioList[0]         | data.userBio.bioList[0]         | [blank]                  | [blank]              | update bio information                    | [blank]                                                 |
      | Education    | RMIT              | MMC             | truong hoc test                                  | https://www.rmit.edu.vn/vi | true      | 0           | data.educationList[0]   | data.userBio.educationList[0]   | update RMIT              | update MMC           | update truong hoc test                    | https://www.rmit.edu.vn                                 |
      | Experience   | GCompany          | Nguyễn Văn Trỗi | GCompany is G company                            | GCompany.com               | true      | 0           | data.experienceList[0]  | data.userBio.experienceList[0]  | update GCompany          | 3 tháng 2            | update GCompany is G company              | GCompany.com.vn                                         |
      | Awards       | phần thưởng       | [blank]         | phần thưởng là 1 chuyến đi du lịch               | phanthuong.com             | true      | 0           | data.awardList[0]       | data.userBio.awardList[0]       | update phần thưởng       | [blank]              | update phần thưởng là 1 chuyến đi du lịch | phanthuong.vn                                           |
      | Associations | Tổ chức tự nguyện | [blank]         | một nhóm các cá nhân tham gia vào một thỏa thuận | [blank]                    | true      | 0           | data.associationList[0] | data.userBio.associationList[0] | update Tổ chức tự nguyện | [blank]              | [blank]                                   | update một nhóm các cá nhân tham gia vào một thỏa thuận |
      | Affiliation  | Affiliation A     | Affiliation B   | [blank]                                          | [blank]                    | true      | 0           | data.affiliationList[0] | data.userBio.affiliationList[0] | update Affiliation A     | update Affiliation B | [blank]                                   | [blank]                                                 |
      | Publications | PUBLICATION ABC   | Author ABC      | NOTE ABC                                         | ABC.com                    | true      | 0           | data.publicationList[0] | data.userBio.publicationList[0] | update PUBLICATION ABC   | update Author ABC    | update NOTE ABC                           | ABC.vn                                                  |


  @bio-info-3
  Scenario Outline: Doctor upload <docType> bio information
    # upload doc bio
    And I upload Bio document
      | userDocType | file   |
      | <docType>   | <file> |
    Then The response should be
      | success                      | true          |
      | <cerificatePath>.description | phan-van-tien |
      | <cerificatePath>.typeText    | <docType>     |
    And The "<cerificatePath>.docSig" should "not null"
    And The image into "<cerificatePath>.docUrl" should be match "<file>"
    # load full user info
    And I load full information of above user
      | bioLanguage | appLanguage |
      | 0           | 0           |
    Then The response should be
      | success                   | true          |
      | <userBioPath>.description | phan-van-tien |
      | <userBioPath>.typeText    | <docType>     |
    And The "<userBioPath>.docSig" should "not null"
    And The image into "<userBioPath>.docUrl" should be match "<file>"
     # load bio
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The response should be
      | success               | true          |
      | <bioPath>.description | phan-van-tien |
      | <bioPath>.typeText    | <docType>     |
    And The "<bioPath>.docSig" should "not null"
    And The image into "<bioPath>.docUrl" should be match "<file>"

    # remove doc bio
    And I remove Bio document
      | forLanguage |
      | 0           |
    Then The request should be succeed
      # load bio
    And I load bio information
      | forLanguage | language |
      | 0           | 0        |
    Then The request should be succeed
    And The "data.certificateList" should "empty array"
    Examples:
      | docType     | file                                | bioPath                 | cerificatePath       | userBioPath                     |
      | Diploma     | data/image/avatar/phan-van-tien.jpg | data.certificateList[0] | data.certificateFile | data.userBio.certificateList[0] |
      | License     | data/image/avatar/phan-van-tien.jpg | data.certificateList[0] | data.certificateFile | data.userBio.certificateList[0] |
      | Certificate | data/image/avatar/phan-van-tien.jpg | data.certificateList[0] | data.certificateFile | data.userBio.certificateList[0] |
      | Others      | data/image/avatar/phan-van-tien.jpg | data.certificateList[0] | data.certificateFile | data.userBio.certificateList[0] |