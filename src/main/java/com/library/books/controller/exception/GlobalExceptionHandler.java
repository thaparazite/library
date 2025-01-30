package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<DetailedErrorResponse> handleException(RuntimeException e, WebRequest webRequest, HttpStatus status) {
        DetailedErrorResponse response = new DetailedErrorResponse(
                webRequest.getDescription(false),
                e.getMessage(),
                status
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler({
            BookISBNAlreadyExistsException.class,
            BookISBNMismatchException.class,
            BookISBNNotFoundException.class,
            BookTitleNotFound.class
    })
    public ResponseEntity<DetailedErrorResponse> handleBookExceptions(RuntimeException e, WebRequest webRequest) {
        return handleException(e, webRequest, HttpStatus.BAD_REQUEST);
    }

}// end of GlobalExceptionHandler class