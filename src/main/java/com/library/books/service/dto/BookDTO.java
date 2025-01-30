package com.library.books.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookDTO extends RepresentationModel<BookDTO>{

    private String bookTitle, authors, publisher, isbn;
    private int yearPublished;
    private double price;

}// end of BookDTO class
