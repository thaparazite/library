package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookYearPublishedNotFoundException extends RuntimeException{
    public BookYearPublishedNotFoundException(String message) {
        super(message);
    }// end of BookYearPublishedNotFoundException constructor
}// end of BookYearPublishedNotFoundException class
