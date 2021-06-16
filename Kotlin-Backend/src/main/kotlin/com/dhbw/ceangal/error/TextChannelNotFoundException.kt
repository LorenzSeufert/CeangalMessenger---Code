package com.dhbw.ceangal.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

class TextChannelNotFoundException(val error: String = "Text channel not found", val status: HttpStatus = NOT_FOUND) : Exception()