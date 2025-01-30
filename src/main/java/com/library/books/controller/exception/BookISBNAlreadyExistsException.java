package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookISBNAlreadyExistsException extends RuntimeException{
    public BookISBNAlreadyExistsException(String s) {
        super(s);
    }// end of BookISBNAlreadyExistsException constructor
}// end of BookISBNAlreadyExistsException class
