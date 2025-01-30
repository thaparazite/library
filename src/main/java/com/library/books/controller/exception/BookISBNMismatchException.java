package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookISBNMismatchException extends RuntimeException{
    public BookISBNMismatchException(String s) {
        super(s);
    }// end of BookISBNMismatchException constructor
}// end of BookISBNMismatchException class
