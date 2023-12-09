package com.rhett.thymebackend.extensions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import kotlin.String
import kotlin.plus


@RestControllerAdvice
@Component
class ThymeExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundErrors(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequestError(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message + "=-=" + e.stackTrace, HttpStatus.BAD_REQUEST)
    }

}


