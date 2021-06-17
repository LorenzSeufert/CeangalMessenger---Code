package com.dhbw.ceangal.usermodel

import com.google.gson.GsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
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
        return ResponseEntity("User creating was successful.", HttpStatus.OK)
    }
    /**
     * This function represents the REST-entry for editing a user
     *
     * @param userProfile data which contains the requested fields to be changed
     * @param id the current session id
     * @return responseMessage the full changed user and status which is send back as a response
     */
    @PutMapping(value = ["/user/editUser"])
    fun editUser(@RequestBody userProfile:UserProfile, @RequestHeader id: String): ResponseEntity<UserProfile> {
        val user = userService.editUser(userProfile, id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for deleting a user
     *
     * @param id the current session id
     * @return responseMessage the message and status which is send back as a response
     */
    @DeleteMapping(value = ["/user/deleteUser"])
    fun deleteUser(@RequestHeader id: String): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity("User was successful deleted", HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for logging in
     *
     * @param userProfile the user to be logged in with email and password
     * @return responseMessage the message and status which is send back as a response
     */
    @PostMapping(value = ["/user/login"])
    fun login(@RequestBody userProfile:UserProfile): ResponseEntity<UserProfile> {
        val sessionId = userService.login(userProfile)

        var header = HttpHeaders()
        header.add("sessionId",sessionId)

        val user = userService.getUser(sessionId)
        return ResponseEntity(user,header, HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for logging out
     *
     * @param id the current session id
     * @return responseMessage the message and status which is send back as a response
     */
    @DeleteMapping(value = ["/user/logout"])
    fun logout(@RequestHeader id: String): ResponseEntity<String> {
        userService.logout(id)
        return ResponseEntity("You have logged out successfully", HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for adding a friend
     *
     * @param id the current session id
     * @param friendName the Name of the friend to add
     * @return responseMessage the message and status which is send back as a response
     */
    @GetMapping(value = ["/user/addFriend"])
    fun addFriend(@RequestHeader id: String, @RequestHeader friendName: String): ResponseEntity<String> {
        userService.addFriend(id, friendName)
        return ResponseEntity("Friend has been added successfully.", HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for removing a friend
     *
     * @param id the current session id
     * @param friendName the Name of the friend to remove
     * @return responseMessage the message and status which is send back as a response
     */
    @DeleteMapping(value = ["/user/removeFriend"])
    fun removeFriend(@RequestHeader id: String, @RequestHeader friendName: String): ResponseEntity<String> {
        userService.removeFriend(id, friendName)
        return ResponseEntity("Friend was successfully removed", HttpStatus.OK)
    }

    /**
     * This function represents the REST-entry for removing a friend
     *
     * @param id the current session id
     * @return responseMessage the message and status which is send back as a response
     */
    @GetMapping(value = ["/user/getFriends"])
    fun getFriends(@RequestHeader id: String): ResponseEntity<String> {
        val friends = userService.getFriends(id)
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val jsonString = gson.toJson(friends)

        return ResponseEntity(jsonString, HttpStatus.OK)
    }
}