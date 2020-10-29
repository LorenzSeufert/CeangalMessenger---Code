Feature: EditUserProfile
  As a signed in USER
  want to edit my user profile

  Background:
    And I am on the main page

  Scenario: open user page
    Given I am signed in with username "USER" and password "PASSWORD"
    And I am on the "main" page
    When I press the "user" button
    Then I am on the "user" page

  Scenario: edit user page
    Given I am on the "user" page
    When I click the "edit" button
    Then I get redirected to the "edit" page
    Then I can upload a new profile picture
    And I can change my nickname
    And I can change my description
    When I press the "save" button
    Then a confirmation Dialog is shown
    Then I am on the "user" page again

  Scenario: enter invalid picture
    Given the uploaded picture is not png, jpeg
    Then a error message is shown
    And the process is being canceled