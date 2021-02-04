package com.dhbw.ceangal.example

class Employee {
    constructor() {}
    constructor(id: Int?, firstName: String?, lastName: String?, email: String?) : super() {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    private var id: Int? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null

    //getters and setters
    override fun toString(): String {
        return ("Employee [id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + "]")
    }
}