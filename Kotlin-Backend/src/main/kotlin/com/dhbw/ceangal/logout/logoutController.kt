package com.dhbw.ceangal.logout

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
open class logoutController {
    @GetMapping(value = ["/logout"])
    fun logout(): ResponseEntity<String>
    {
        val message = "you are logged out of the system"
        return ResponseEntity(message, HttpStatus.OK)
    }
}