package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookPublisherNotFoundException extends RuntimeException{
    public BookPublisherNotFoundException(String message) {
        super(message);
    }// end of BookPublisherNotFoundException constructor
}// end of BookPublisherNotFoundException class
