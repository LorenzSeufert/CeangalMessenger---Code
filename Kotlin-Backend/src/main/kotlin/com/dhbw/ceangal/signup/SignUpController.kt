package com.dhbw.ceangal.signup

import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
open class SignUpController
{
    @PostMapping(path = ["/signup"], consumes = ["application/json"], produces = ["application/json"])
    fun signUp(@RequestBody signUpModel: SignUpModel): ResponseEntity<String>
    {
        var gson = Gson()
        var jsonString = gson.toJson(signUpModel)
        return ResponseEntity(jsonString, HttpStatus.OK)
    }
}