package com.teachhub.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRunTime(RuntimeException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidation(MethodArgumentNotValidException exception){
        ErrorMessage errorMessage=new ErrorMessage();
        exception.getBindingResult().getFieldErrors()
                .forEach(
                        error->errorMessage.setMessage(
                                error.getField()+":"+error.getDefaultMessage()
                        )
                );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(ResourceNotFoundException exception){
        ErrorMessage errorMessage=ErrorMessage
                .builder()
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDBError(DataIntegrityViolationException exception){
        return ResponseEntity.badRequest().body(
                new ErrorMessage("Duplicate or invalid data")
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleAllException(Exception exception){
        return ResponseEntity.status(500).body(new ErrorMessage("Something went wrong...."));
    }
}
