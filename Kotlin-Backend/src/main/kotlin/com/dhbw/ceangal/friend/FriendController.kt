package com.dhbw.ceangal.friend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
class FriendController {
    @Autowired
    private lateinit var friendService: FriendService

    @PostMapping(path = ["/addFriend"])
    fun addFriend(@RequestBody friend: Friend): ResponseEntity<String>{
        friendService.addFriend(friend)
        return ResponseEntity("Friend was created successfully.", HttpStatus.OK)
    }
}