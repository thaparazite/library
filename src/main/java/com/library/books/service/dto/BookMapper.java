package com.library.books.service.dto;

import com.library.books.repository.entity.Book;

public class BookMapper {

    // mapToBookDTO method
    public static BookDTO mapToBookDTO(Book book, BookDTO bookDto) {
        bookDto.setBookTitle(book.getBookTitle());
        bookDto.setAuthors(book.getAuthors());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setYearPublished(book.getYearPublished());
        bookDto.setPrice(book.getPrice());

        return bookDto;
    }// end of mapToBookDTO method

}// end of BookMapper class
