Feature: DeleteAccount

  Scenario: User cant delete account
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the edit profile page in the navbar
    Then the user sees the edit profile page
    When the user clicks on "Delete Account"
    And the client lost the connection to the server
    Then the user sees an error message "Connection lost"
    And the user gets redirected to the login page


  Scenario: User deletes account successfully
    Given the user is on the profile page
    And the user is logged in
    When the user clicks on the edit profile page in the navbar
    Then the user sees the edit profile page
    When the user clicks on "Delete Account"
    Then the user sees a confirmation message "Account deleted successfully"
    And the user get redirected to the login page