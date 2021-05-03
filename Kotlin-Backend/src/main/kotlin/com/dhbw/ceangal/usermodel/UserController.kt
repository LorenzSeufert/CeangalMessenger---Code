package com.dhbw.ceangal.usermodel

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping(value = ["/user/createUser"])
    fun createUser(@RequestBody userProfile:UserProfile): ResponseEntity<String> {
        userService.createUser(userProfile)
        return ResponseEntity("User creating was succesful.", HttpStatus.OK)
    }

    @PutMapping(value = ["/user/editUser/{id}"])
    fun editUser(@RequestBody userProfile:UserProfile, @PathVariable id: String): ResponseEntity<UserProfile> {
        val user = userService.editUser(userProfile, id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @DeleteMapping(value = ["/user/deleteUser/{id}"])
    fun deleteUser(@PathVariable id: String): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity("User was succesful deleted", HttpStatus.OK)
    }
}