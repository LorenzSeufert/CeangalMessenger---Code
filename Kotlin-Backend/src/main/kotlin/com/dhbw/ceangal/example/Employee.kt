package com.dhbw.ceangal.example

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Employee(@Id val id: Int,
               var firstName: String,
               var lastName: String,
               var email: String) {

    //getters and setters
    override fun toString(): String {
        return ("Employee [id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + "]")
    }
}