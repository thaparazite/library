package com.library.books.repository;

import com.library.books.repository.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // method to find a book by its isbn
    Optional<Book> findByIsbn(String isbn);

    // method to find all books by their authors
    @Query("select b from Book b where b.authors like %:authorName%")
    List<Book> findBooksByAuthorName(@Param("authorName") String authorName);

    // method to find all books by their authors
    @Query("select b from Book b where b.bookTitle like %:bookTitle%")
    List<Book> findBooksByTitle(@Param("bookTitle") String bookTitle);

    // method to find all books by their publisher
    @Query("select b from Book b where b.publisher like %:publisher%")
    List<Book> findBooksByPublisher(@Param("publisher") String publisher);

    // method to find all books by their year published
    @Query("select b from Book b where b.yearPublished = :yearPublished")
    List<Book> findBooksByYearPublished(@Param("yearPublished") int yearPublished);

    // method to find all books by their price
    @Query("select b from Book b where b.price = :price")
    List<Book> findBooksByPrice(@Param("price") double price);

    // delete a book by its isbn
    @Transactional// jakarta, REQUIRED, this method is done completely or not at all
    boolean deleteByIsbn(String isbn);// delete a book by its isbn

    // delete a book by its title
    @Transactional
    boolean deleteBookByBookTitle(String bookTitle);

    // update a book by its isbn
    @Transactional
    @Modifying
    @Query("update Book b set b.bookTitle = ?1, b.authors = ?2, b.publisher = ?3, b.yearPublished = ?4, b.price = ?5 where b.isbn = ?6")
    void updateBook(String bookTitle, String authors, String publisher, int yearPublished, double price, String isbn);

}// end of BookRepository interface
