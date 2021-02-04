package com.dhbw.ceangal.example

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
open class EmployeeController {
    @GetMapping(value = ["/board"])
    fun getEmployee(): ResponseEntity<String> {
        val employeeList = ArrayList<Employee>()
        employeeList.add(Employee(1, "lok", "gupta", "bla@bla"))
        val hello = "hello"
        return ResponseEntity(hello, HttpStatus.OK)
    }
}