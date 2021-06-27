package com.dhbw.ceangal.StepDefs

import io.cucumber.java8.En

class AddFriendDefinition : En {
    init {
        Given("the user is on the profile page") { /*throw PendingException()*/ }

        Given("the user is logged in") { /*throw PendingException()*/ }

        When("the user clicks on the friends page in the navbar") { /*throw PendingException()*/ }

        Then("the user should see his friendlist") { /*throw PendingException()*/ }

        When("the user enters a not existing user to add") { /*throw PendingException()*/ }

        Then(
            "the user sees an error message {string}"
        ) { string: String? -> /*throw PendingException()*/ }

        Then("the user sees the friendlist again") { /*throw PendingException()*/ }

        When("the user enters an existing user to add") { /*throw PendingException()*/ }

        Then(
            "the user sees a confirmation message {string}"
        ) { string: String? -> /*throw PendingException()*/ }

        Then("the user sees the friendlist again with the new friend") { /*throw PendingException()*/ }
    }
}