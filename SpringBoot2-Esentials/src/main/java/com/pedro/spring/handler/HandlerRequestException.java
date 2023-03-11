package com.pedro.spring.handler;

import com.pedro.spring.exception.BadRequestException;
import com.pedro.spring.exception.BadRequestExceptionDetails;
import com.pedro.spring.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerRequestException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException exception) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder().timeStamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).error("Bad Request, check documentation!").message(exception.getMessage()).messageDeveloper(exception.getClass().getName()).build(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(ValidationExceptionDetails.builder().timeStamp(LocalDateTime.now()).status(status.value()).error("field(s) is null! check").message(ex.getMessage()).messageDeveloper(ex.getClass().getName()).fields(fields).fieldMessage(fieldsMessage).build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder().timeStamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).error(ex.getCause().toString()).message(ex.getMessage()).messageDeveloper(ex.getClass().getName()).build(), headers, status);
    }
}
