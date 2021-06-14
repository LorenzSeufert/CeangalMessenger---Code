package com.dhbw.ceangal.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService: EmployeeServiceInterface {
    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    override fun createEmployee(id: String, firstName: String, lastName: String, email: String): Employee {
        return employeeRepository.save(Employee(id, firstName, lastName, email))
    }
}
