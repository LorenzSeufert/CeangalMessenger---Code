package StepDefs

import io.cucumber.java8.En
import io.cucumber.java8.PendingException

class EditProfileDefinition : En {

    init {
        Given("I am signed in with username {string} and password {string}") { string: String?, string2: String? -> throw PendingException() }

        Given("I am on the {string} page") { string: String? -> throw PendingException() }

        When("I press the {string} button") { string: String? -> throw PendingException() }

        Then("I get on the {string} page") { string: String? -> throw PendingException() }

        When("I click the {string} button") { string: String? -> throw PendingException() }

        Then("I get redirected to the {string} page") { string: String? -> throw PendingException() }

        Then("I can upload a new profile picture") { throw PendingException() }

        Then("I can change my nickname") { throw PendingException() }

        Then("I can change my description") { throw PendingException() }

        Then("a confirmation Dialog is shown") { throw PendingException() }

        Then("I am on the {string} page again") { string: String? -> throw PendingException() }

        Given("the uploaded picture is not png, jpeg") { throw PendingException() }

        Then("{string} message is shown") { string: String? -> throw PendingException() }

        Then("the process is being canceled") { throw PendingException() }
    }
}