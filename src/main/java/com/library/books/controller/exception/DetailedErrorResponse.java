package com.library.books.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DetailedErrorResponse {

    private String apiPath, errorMessage;
    private HttpStatus httpStatus;

}// end of DetailedErrorResponse class
