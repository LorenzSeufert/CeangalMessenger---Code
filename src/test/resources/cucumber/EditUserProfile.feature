Feature: EditUserProfile
  AS a signed in user
  I want to edit my user profile

  Scenario Outline: Edit profile
    Given The user is logged in
    Given User is on the profile page
    When I click the button "Edit profile"
    Then Redirect to the "Edit" page
    Then I can change my description
    And I can change my nickname
    When I upload a picture of <format>
    And I click the button "Update profile"
    Then <status>-Popup with <message>

    Examples:
      | format | status | message         |
      | png    | OK     | Profile updated |
      | jpeg   | OK     | Profile updated |
      | gif    | ERROR  | Invalid format  |

  Scenario: Open user page
    Given I am signed in with username 'USER' and password 'PASSWORD'
    And I am on the "main" page
    When I press the "user" button
    Then I get on the "user" page
