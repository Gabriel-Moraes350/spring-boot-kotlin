package com.example.eventskt.exceptions.handlers

import com.example.eventskt.dtos.ErrorDTO
import com.example.eventskt.exceptions.EventNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*
import java.util.function.Consumer


@RestControllerAdvice
class EventControllerAdvice {

    @Autowired
    private lateinit var messageSource: MessageSource

    @ExceptionHandler(EventNotFoundException::class)
    fun handleException(ex: EventNotFoundException, request: WebRequest): ResponseEntity<ErrorDTO>{
        return ResponseEntity(ErrorDTO(messageSource.getMessage(ex.localizedMessage, null, getLocale(request))), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
            ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorDTO>{

        val errorDto = ErrorDTO(messageSource.getMessage("error.body.validation", null, getLocale(request)))
        errorDto.errors = ex.bindingResult.allErrors.mapNotNull { it.defaultMessage }
        return ResponseEntity(errorDto, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    private fun getLocale(request: WebRequest) : Locale{

        if(request.getParameter("lang") != null){
            return Locale.forLanguageTag(request.getParameter("lang"))
        }

        return Locale.getDefault()
    }
}