package com.dhbw.ceangal.StepDefs

import io.cucumber.java8.En

class ShowFriends : En {
    init {
        Then("the user sees the friends page with his friend list") { /*throw PendingException()*/ }

        When("the user has no friends added") { /*throw PendingException()*/ }

        Then("no users get displayed in the friendlist") { /*throw PendingException()*/ }

        When("the user has already friends added") { /*throw PendingException()*/ }

        Then("all friends get displayed with name and description") { /*throw PendingException()*/ }
    }
}