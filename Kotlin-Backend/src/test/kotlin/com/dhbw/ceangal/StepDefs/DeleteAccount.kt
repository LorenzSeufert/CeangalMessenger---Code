package com.dhbw.ceangal.StepDefs

import io.cucumber.java8.En

class DeleteAccount : En {
    init {
        When("the user clicks on the edit profile page in the navbar") { /*throw PendingException()*/ }

        Then("the user sees the edit profile page") { /*throw PendingException()*/ }

        When(
            "the user clicks on {string}"
        ) { string: String? -> /*throw PendingException()*/ }

        When("the client lost the connection to the server") { /*throw PendingException()*/ }

        Then("the user gets redirected to the login page") { /*throw PendingException()*/ }

        Then("the user get redirected to the login page") { /*throw PendingException()*/ }
    }
}