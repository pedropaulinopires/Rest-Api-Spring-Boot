package com.pedro.spring.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationException extends ExceptionDetails{

    private String fields;
    private String fieldsMessage;
}
