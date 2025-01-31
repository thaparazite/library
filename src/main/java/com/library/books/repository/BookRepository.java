package com.library.books.repository;

import com.library.books.repository.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // method to find a book by its isbn
    Optional<Book> findByIsbn(String isbn);

    // method to find all books by their authors
    List<Book> findByAuthors(String authors);

    // method to find all books by their authors
    Optional<Book> findByBookTitle(String bookTitle);

    // method to find all books by their publisher
    List<Book> findByPublisher(String publisher);

    // method to find all books by their year published
    List<Book> findByYearPublished(int yearPublished);

    // method to find all books by their price
    List<Book> findByPrice(double price);

    // delete a book by its isbn
    @Transactional// jakarta, REQUIRED, this method is done completely or not at all
    void deleteByIsbn(String isbn);// delete a book by its isbn

    // delete a book by its title
    @Transactional
    void deleteByBookTitle(String bookTitle);

    // update a book by its isbn
    @Transactional
    @Modifying
    @Query("update Book b set b.bookTitle = ?1, b.authors = ?2, b.publisher = ?3, b.yearPublished = ?4, b.price = ?5 where b.isbn = ?6")
    void updateBook(String bookTitle, String authors, String publisher, int yearPublished, double price, String isbn);

}// end of BookRepository interface
