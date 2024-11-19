package org.pk.datanest.puller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ApplicationRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleStorageFileNotFound(RuntimeException exc) {
        return
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getLocalizedMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(NullPointerException exc) {
        return
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getLocalizedMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail handleHttpClientErrorException(HttpClientErrorException exc) {
        return
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getLocalizedMessage());
    }
}
