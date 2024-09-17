package com.hackease.restapiprojectone.Exceptions.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.ResponseDto;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RootExceptionHandler {
    
    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto<ObjectNode>> handleDataNotFoundException(
            DataNotFoundException exception
    ) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage(),
                        null
                ), HttpStatus.NOT_FOUND
        );
    }
    
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto<ObjectNode>> handleValidationException(
            DataNotFoundException exception
    ) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.BAD_REQUEST.value(),
                        Constants.INVALID_DATA_ENTERED,
                        null
                ), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<ObjectNode>> handleException(
            Exception exception
    ) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Constants.SOMETHING_WENT_WRONG,
                        null
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    
}
