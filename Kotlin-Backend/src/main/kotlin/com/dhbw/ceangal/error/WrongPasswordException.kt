package com.dhbw.ceangal.error

import org.springframework.http.HttpStatus

class WrongPasswordException(val error: String = "", val status: HttpStatus = HttpStatus.FORBIDDEN) : Exception()