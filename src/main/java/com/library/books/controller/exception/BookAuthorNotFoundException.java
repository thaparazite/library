package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookAuthorNotFoundException extends RuntimeException{
    public BookAuthorNotFoundException(String message) {
        super(message);
    }// end of BookAuthorNotFoundException constructor
}// end of BookAuthorNotFoundException class
