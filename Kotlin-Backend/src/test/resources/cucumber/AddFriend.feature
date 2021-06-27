Feature: AddFriend

  Scenario: User adds not existing user
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the friends page in the navbar
    Then the user should see his friendlist
    When the user enters a not existing user to add
    Then the user sees an error message "User not found"
    And the user sees the friendlist again


  Scenario: User adds an existing user
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the friends page in the navbar
    Then the user should see his friendlist
    When the user enters an existing user to add
    Then the user sees a confirmation message "Friend added successfully"
    And the user sees the friendlist again with the new friend
