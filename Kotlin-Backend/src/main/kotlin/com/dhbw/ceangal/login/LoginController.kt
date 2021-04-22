package com.dhbw.ceangal.login

import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
open class LoginController
{
    @PostMapping(path = ["/login"], consumes = ["application/json"], produces = ["application/json"])
    fun logIn(@RequestBody loginModel: LoginModel): ResponseEntity<String>
    {
        var gson = Gson()
        var jsonString = gson.toJson(loginModel)
        return ResponseEntity(jsonString, HttpStatus.OK)
    }
}