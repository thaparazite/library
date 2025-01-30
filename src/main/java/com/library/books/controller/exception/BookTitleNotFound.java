package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookTitleNotFound extends RuntimeException{
    public BookTitleNotFound(String message) {
        super(message);
    }// end of constructor
}// end of BookTitleNotFound class
