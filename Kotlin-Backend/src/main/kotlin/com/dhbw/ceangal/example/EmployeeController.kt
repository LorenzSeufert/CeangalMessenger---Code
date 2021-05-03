package com.dhbw.ceangal.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
open class EmployeeController {
    @Autowired
    private lateinit var employeeService: EmployeeService

    @PostMapping(value = ["/createEmployee"])
    fun createEmployee(): ResponseEntity<Employee> {
        val employee = employeeService.createEmployee("23232", "Bobs", "Burger", "bla@bla.com")
        return ResponseEntity(employee, HttpStatus.OK)
    }

    @GetMapping(value = ["/board"])
    fun getEmployee(): ResponseEntity<String> {
        val employeeList = ArrayList<Employee>()
        employeeList.add(Employee("1", "lok", "gupta", "bla@bla"))
        val hello = "hello"
        return ResponseEntity(hello, HttpStatus.OK)
    }
    @GetMapping(value = ["/something"])
    fun getSomething(): ResponseEntity<String>
    {
        //val employeeList = ArrayList<Employee>()
        //employeeList.add(Employee(1, "lok", "gupta", "bla@bla"))
        val something = "something"
        return ResponseEntity(something, HttpStatus.OK)
    }
}