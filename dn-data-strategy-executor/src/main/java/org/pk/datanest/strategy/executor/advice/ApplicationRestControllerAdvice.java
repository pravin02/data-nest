package org.pk.datanest.strategy.executor.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleStorageFileNotFound(RuntimeException exc) {
        return
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getLocalizedMessage());
    }
}
