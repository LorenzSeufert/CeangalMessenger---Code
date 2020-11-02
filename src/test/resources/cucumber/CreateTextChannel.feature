Feature: CreateTextChannel
  AS a signed in user
  I want to create a text channel


  Scenario: Click 'Create text channel' button
    Given I am signed in with username 'USER' and password 'PASSWORD'
    And I am on the 'main' page
    When I press the 'Create text channel' button
    Then I am on the 'Create text channel' page

  Scenario: Fill out data
    Given I am on the 'Create text channel' page
    Then I have to fill out the 'name' field
    And I can fill out the 'description' field
    When I press the 'Save' button
    Then a confirmation Dialog is shown
    Then I am on the 'Text channel' page