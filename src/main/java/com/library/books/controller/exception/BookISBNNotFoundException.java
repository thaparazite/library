package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookISBNNotFoundException extends RuntimeException {
    public BookISBNNotFoundException(String message) {
        super(message);
    }// end of constructor
}// end of BookISBNNotFoundException class
