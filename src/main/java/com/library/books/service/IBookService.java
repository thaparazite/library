package com.library.books.service;

import com.library.books.repository.entity.Book;
import com.library.books.service.dto.BookDTO;

import java.util.List;

public interface IBookService {

    // POST -> add a book to the database
    BookDTO addBook(Book book);
    List<BookDTO> addAllBooks(List<Book> books);

    // GET -> get all books, get a book by isbn, get all books by author
    List<BookDTO> getAllBooks();
    BookDTO getBookByISBN(String isbn);
    List<BookDTO> getAllBooksByAuthors(String author);
    List<BookDTO> getBookByBookTitle(String bookTitle);
    List<BookDTO> getBookByPublisher(String publisher);
    List<BookDTO> getBookByYearPublished(int yearPublished);
    List<BookDTO> getBookByPrice(double price);


    // PUT -> update a book by isbn
    void updateBook(String isbn, Book book);

    // DELETE -> delete a book by isbn, delete all books in the database
    void deleteBookByISBN(String isbn);
    void deleteAllBooks();
    void deleteBookByBookTitle(String bookTitle);

}// end of IBookServiceImpl interface
