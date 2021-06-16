package com.dhbw.ceangal.error

import org.springframework.http.HttpStatus

class UserAlreadyExistsException(val error: String = "", val status: HttpStatus = HttpStatus.FORBIDDEN) : Exception()