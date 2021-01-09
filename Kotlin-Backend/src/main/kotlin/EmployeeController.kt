package de.dhbw.ceangalmessenger.KotlinBackend

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList


@RestController
class EmployeeController {
    @RequestMapping("/board")
    fun getEmployee(): ResponseEntity<String> {
        val employeeList = ArrayList<Employee>()
        employeeList.add(Employee(1, "lok", "gupta", "bla@bla"))
        val hello = "hello"
        return ResponseEntity(hello, HttpStatus.ACCEPTED)
    }
}