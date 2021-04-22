package com.dhbw.ceangal.example

import org.springframework.stereotype.Repository

@Repository
interface EmployeeServiceInterface {
    fun createEmployee(id: String, firstName: String, lastName: String, email: String): Employee
}