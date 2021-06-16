package com.dhbw.ceangal.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

class UserNotFoundException(val error: String = "", val status: HttpStatus = NOT_FOUND) : Exception()