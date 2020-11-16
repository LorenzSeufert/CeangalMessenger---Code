package StepDefs

import io.cucumber.java8.En

class CreateTextChannelDefinition : En {

    init {
        Then("I have to fill out the {string} field") { string: String? -> /*throw PendingException()*/ }

        Then("I can fill out the {string} field") { string: String? -> /*throw PendingException()*/ }

        Then("a confirmation Dialog is shown") { /*throw PendingException()*/ }
    }
}