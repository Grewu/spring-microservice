package org.example.handler;

import org.example.annotation.Logging;
import org.example.exception.AbstractExceptionMessageException;
import org.example.exception.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Logging
@RestControllerAdvice
public class GlobalHandlerAdvice {


    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handle(AbstractExceptionMessageException e) {
        return Optional.of(e)
                .map(AbstractExceptionMessageException::getExceptionMessage)
                .map(exceptionMessage ->
                        ResponseEntity
                                .status(e.getStatusCode())
                                .body(exceptionMessage))
                .orElseThrow();
    }
}
