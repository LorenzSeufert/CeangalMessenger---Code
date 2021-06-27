Feature: ShowFriends

  Scenario: User has no friends
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the friends page in the navbar
    Then the user sees the friends page with his friend list
    When the user has no friends added
    Then no users get displayed in the friendlist


  Scenario: User has friends added
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the friends page in the navbar
    Then the user sees the friends page with his friend list
    When the user has already friends added
    Then all friends get displayed with name and description