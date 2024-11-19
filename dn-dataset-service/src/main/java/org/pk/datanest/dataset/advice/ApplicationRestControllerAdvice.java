package org.pk.datanest.dataset.advice;

import org.pk.datanest.dataset.exception.StorageFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationRestControllerAdvice {

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ProblemDetail handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getLocalizedMessage());
    }
}
