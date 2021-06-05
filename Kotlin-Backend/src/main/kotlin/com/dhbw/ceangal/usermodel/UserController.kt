package com.dhbw.ceangal.usermodel

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * This class is controls the user. It contains REST Services for creating, editing and deleting a user.
 */
@Controller
@RequestMapping("/api")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    /**
     * This function represents the REST-entry for creating a user
     *
     * @param userProfile the user to be created
     * @return responseMessage the message and status which is send back as a response
     */
    @PostMapping(value = ["/user/createUser"])
    fun createUser(@RequestBody userProfile:UserProfile): ResponseEntity<String> {
        userService.createUser(userProfile)
        return ResponseEntity("User creating was succesful.", HttpStatus.OK)
    }
    /**
     * This function represents the REST-entry for editing a user
     *
     * @param userProfile data which contains the requested fields to be changed
     * @param id the id of the user whose data should be changed
     * @return responseMessage the full changed user and status which is send back as a response
     */
    @PutMapping(value = ["/user/editUser/{id}"])
    fun editUser(@RequestBody userProfile:UserProfile, @PathVariable id: Long): ResponseEntity<UserProfile> {
        val user = userService.editUser(userProfile, id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for deleting a user
     *
     * @param id the id of the user who should be deleted
     * @return responseMessage the message and status which is send back as a response
     */
    @DeleteMapping(value = ["/user/deleteUser/{id}"])
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity("User was succesful deleted", HttpStatus.OK)
    }
}