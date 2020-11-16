package StepDefs

import io.cucumber.java8.En

class EditProfileDefinition : En {

    init {
        Given("The user is logged in") { /*throw PendingException()*/ }

        Given("User is on the profile page") { /*throw PendingException()*/ }

        When("I click the button {string}") { string: String? -> /*throw PendingException()*/ }

        Then("Redirect to the {string} page") { string: String? -> /*throw PendingException()*/ }

        Then("I can change my description") { /*throw PendingException()*/ }

        Then("I can change my nickname") { /*throw PendingException()*/ }

        When("I upload a picture of png") { /*throw PendingException()*/ }

        Then("OK-Popup with Profile updated") { /*throw PendingException()*/ }

        When("I upload a picture of jpeg") { /*throw PendingException()*/ }

        When("I upload a picture of gif") { /*throw PendingException()*/ }

        Then("ERROR-Popup with Invalid format") { /*throw PendingException()*/ }

        Given("I am signed in with username {string} and password {string}") { string: String?, string2: String? -> /*throw PendingException()*/ }

        Given("I am on the {string} page") { string: String? -> /*throw PendingException()*/ }

        When("I press the {string} button") { string: String? -> /*throw PendingException()*/ }

        Then("I get on the {string} page") { string: String? -> /*throw PendingException()*/ }
    }
}