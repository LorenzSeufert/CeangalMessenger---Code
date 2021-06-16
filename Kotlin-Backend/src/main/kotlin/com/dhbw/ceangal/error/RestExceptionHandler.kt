package com.dhbw.ceangal.error

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return buildResponseEntity(ex.message, BAD_REQUEST)
    }

    @ExceptionHandler(TextChannelNotFoundException::class)
    fun handleEntityNotFound(ex: TextChannelNotFoundException): ResponseEntity<Any> {
        return buildResponseEntity(ex.message, ex.status)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleEntityNotFound(ex: UserNotFoundException): ResponseEntity<Any> {
        return buildResponseEntity(ex.message, ex.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<Any> {
        return buildResponseEntity(ex.message, BAD_REQUEST)
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointer(ex: NullPointerException): ResponseEntity<Any> {
        return buildResponseEntity(ex.message, BAD_REQUEST)
    }

    private fun buildResponseEntity(message: String?, status: HttpStatus): ResponseEntity<Any> {
        return ResponseEntity(message, status)
    }
}
