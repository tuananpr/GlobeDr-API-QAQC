@regression @org @department @staff @department-create-account
Feature: Department : manager create new staff

  As manager
  I want to create new staff as doctor, receptionists, ...

  Background:
    Given I re-signup "manager_1" account and update profile
    Given I re-signup "manager_2" account and update profile
    Given I re-signup "user_4" account and update profile

    And I login as "system_admin_1"
    And I re-create a org with full of feature
      | name        | owner     |
      | BV_HAI_QUAN | manager_1 |

    And As sysAdmin, I setting customer care
      | name        | maxCustomerCare |
      | BV_HAI_QUAN | 4               |
    And The request should be succeed
    When I login as "manager_1"
    And I accept join organization
    When I select org "BV_HAI_QUAN" that I manage
    When I add department on org
      | name     |
      | Khoa Tim |
    And The request should be succeed

  @department-create-account-1
  Scenario Outline: Owner create new <description> into department
    Given On SqlServer, I delete user by gdrLogin "<email>"

    # manager create new doctor to deparment
    When On org, I create new account on department
      | email   | title   | displayName  | password   | isProvider   | telemedicine   | vip   | deptName |
      | <email> | <title> | <doctorName> | <password> | <isProvider> | <telemedicine> | <vip> | Khoa Tim |
    Then The request should be succeed
    And I load staffs of department
      | deptName |
      | Khoa Tim |
    Then The response should be
      | success                    | true           |
      | data.list[0].userName      | <doctorName>   |
      | data.list[0].userTitle     | <title>        |
      | data.list[0].isAdmin       | false          |
      | data.list[0].isManager     | false          |
      | data.list[0].allowResetPwd | true           |
      | data.list[0].isVerified    | true           |
      | data.list[0].isProvider    | <isProvider>   |
      | data.list[0].telemedicine  | <telemedicine> |
      | data.list[0].isVipDoctor   | <vip>          |
    # login new doctor successfully
    And I login account
      | gdrLogin | password   | country | language |
      | <email>  | <password> | VN      | 0        |
    Then The response should be
      | data.gdrLogin   | <email> |
      | data.isVerified | true    |
      | data.gender     | 4       |
      | data.tokenType  | Bearer  |
    And The "data.accessToken" should "not null"
    Examples:
      | user      | email                       | title                | doctorName    | password    | department | isProvider | telemedicine | vip   |
      | manager_1 | nguyenvanba1234324@test.com | giám đốc             | nguyen van ba | nguyenvanba | Khoa Tim   | false      | false        | false |
      | manager_1 | thuytran@gmail.com          | bác sĩ               | thuy tran     | thuytran    | Khoa Tim   | true       | false        | false |
      | manager_1 | thuytran@gmail.com          | bác sĩ gọi trực tiếp | thuy tran     | thuytran    | Khoa Tim   | true       | true         | false |
      | manager_1 | thuytran@gmail.com          | bác sĩ vip           | thuy tran     | thuytran    | Khoa Tim   | true       | false        | true  |
      | manager_1 | thuytran@gmail.com          | bác sĩ toàn năng     | thuy tran     | thuytran    | Khoa Tim   | true       | true         | true  |

  @department-create-account-2
  Scenario Outline: Owner create invalid <description> into department
    Given On SqlServer, I delete user by gdrLogin "<email>"
    # manager create new doctor to deparment
    When On org, I create new account on department
      | email   | title   | displayName  | password   | isProvider   | telemedicine   | vip   | deptName |
      | <email> | <title> | <doctorName> | <password> | <isProvider> | <telemedicine> | <vip> | Khoa Tim |
    Then The response should be
      | success | false                  |
      | message | Please try again later |
      | <field> | <err>                  |

    # login new doctor successfully
    And I login account
      | gdrLogin | password   | country | language |
      | <email>  | <password> | VN      | 0        |
    Then The response should be
      | success | false |

    Examples:
      | user      | email              | title                | doctorName    | password    | department | isProvider | telemedicine | vip   | field               | err                    |
      | manager_1 |                    | giám đốc             | nguyen van ba | nguyenvanba | Khoa Tim   | false      | false        | false | errors.email        | This field is required |
      | manager_1 | thuytran@gmail.com |                      | thuy tran     | thuytran    | Khoa Tim   | true       | false        | false | errors.title        | This field is required |
      | manager_1 | thuytran@gmail.com | thư ký               |               | thuytran    | Khoa Tim   | true       | false        | false | errors.displayName  | This field is required |
      | manager_1 | thuytran@gmail.com | bác sĩ gọi trực tiếp | thuy tran     | thuytran    | Khoa Tim   | false      | true         | false | errors.telemedicine | Invalid data           |
      | manager_1 | thuytran@gmail.com | bác sĩ vip           | thuy tran     | thuytran    | Khoa Tim   | false      | false        | true  | errors.vIP          | Invalid data           |


  @department-create-account-3
  Scenario Outline: manger create invalid doctor by existed email
    Given On SqlServer, I delete user by gdrLogin "<email>"
    # manager create new doctor to deparment
    When On org, I create new account on department
      | email           | title   | displayName | password   | deptName |
      | staff1@test.com | <title> | staff1      | <password> | Khoa Tim |
      | staff1@test.com | <title> | staff1      | <password> | Khoa Tim |
    Then The response should be
      | success      | false                                                                                   |
      | errors.email | This number exists in our system already. Please correct the number or go back & log in |
    Examples:
      | user      | email                | doctorName    | password    | department | title    |
      | manager_1 | nguyenvanba@test.com | nguyen van ba | nguyenvanba | Khoa Tim   | giám đốc |


  @department-create-account-4
  Scenario Outline: Owner create new <description>
    Given On SqlServer, I delete user by gdrLogin "<email>"
    # manager create new doctor to deparment
    When On org, I create new account on department
      | email   | title   | displayName   | password   | isProvider   | telemedicine   | vip   | staffAttributes   | deptName |
      | <email> | <title> | <displayName> | <password> | <isProvider> | <telemedicine> | <vip> | <staffAttributes> | Khoa Tim |
    Then The request should be succeed


    # login new doctor successfully
    And I login account
      | gdrLogin | password   | country | language |
      | <email>  | <password> | VN      | 0        |
    When I select org "BV_HAI_QUAN" that I manage
    And As org, I get features of page
      | platform | pageDashboard |
      | Web      | PageManageOrg |

    Then List "<list>" of response should be contains all member into "<existfeatures>"


    Examples:
      | user      | email           | title     | displayName | password | department | isProvider | telemedicine | vip   | staffAttributes | description             | platform | list              | existfeatures                                                             |
      | manager_1 | staff1@test.com | nhan vien | staff1      | 123456   | Khoa Tim   | false      | false        | false | 2               | staff is Administrators | web      | data.list         | []                                                                        |
      | manager_1 | staff2@test.com | nhan vien | staff2      | 123456   | Khoa Tim   | false      | false        | false | 4               | staff is Content        | web      | data.list[*].text | Organization information,QR Code,Landing page themes,Post,Product/Service |
      | manager_1 | staff3@test.com | nhan vien | staff3      | 123456   | Khoa Tim   | false      | false        | false | 8               | staff is reception      | web      | data.list[*].text | Department,Customer Care,Campaign,Product/Service,Appointment list        |
      | manager_1 | staff4@test.com | nhan vien | staff4      | 123456   | Khoa Tim   | false      | false        | false | 16              | staff is marketing      | web      | data.list[*].text | Customer relationship management,QR Code,Post,Customer Care,Campaign      |
      | manager_1 | staff6@test.com | nhan vien | staff5      | 123456   | Khoa Tim   | false      | false        | false | 32              | staff is nurse          | web      | data.list[*].text | Customer Care,Campaign,Product/Service,Appointment list                   |

  @department-create-account-5
  Scenario Outline: can't change or recovery password for account that created by org
    Given On SqlServer, I delete user by gdrLogin "<email>"

    # manager create new doctor to deparment
    When On org, I create new account on department
      | email   | title   | displayName  | password   | isProvider   | telemedicine   | vip   | deptName |
      | <email> | <title> | <doctorName> | <password> | <isProvider> | <telemedicine> | <vip> | Khoa Tim |
    Then The request should be succeed


    And I login account
      | gdrLogin | password   | country   |
      | <email>  | <password> | <country> |
    And The request should be succeed
    When I change my password
      | oldPassword | newPassword |
      | <password>  | 123456      |
    Then The response should be
      | success            | false                                                    |
      | errors.oldPassword | Bạn không thể đổi mật khẩu cho tài khoản do tổ chức tạo. |

    When I recovery password
      | gdrLogin | country   | language |
      | <email>  | <country> | 0        |
    Then The response should be
      | success | false |

    Examples:
      | user      | email                       | title    | doctorName    | password    | department | isProvider | telemedicine | vip   | country |
      | manager_1 | nguyenvanba1234324@test.com | giám đốc | nguyen van ba | nguyenvanba | Khoa Tim   | false      | false        | false | VN      |






