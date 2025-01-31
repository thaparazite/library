package com.library.books.service.impl;

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.repository.BookRepository;
import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;

import static com.library.books.service.dto.BookMapper.mapToBookDTO;
import static com.library.books.utility.ISBNFormatter.formatISBN;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    private static final String ISBN_NOT_FOUND = "Book with the following ISBN is not found in database! : ";
    private static final String TITLE_NOT_FOUND = "Book with the following title is not found in database : ";
    private static final String ISBN_ALREADY_EXISTS = "Book with the following ISBN already exists in database! : ";
    private static final String PRICE_NOT_FOUND = "Book with the following price is not found in database! : ";
    private static final String YEAR_PUBLISHED_NOT_FOUND = "Book with the following year published is not found in database! : ";
    private static final String AUTHOR_NOT_FOUND = "Book with the following author is not found in database! : ";

    // add a book to the database
    @Override
    public BookDTO addBook(Book book) {
        // set the ISBN of the book to the formatted ISBN
        book.setIsbn(formatISBN(book.getIsbn()));

        // if book ISBN already exists in the database, throw a BookISBNAlreadyExistsException
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new BookISBNAlreadyExistsException(ISBN_ALREADY_EXISTS + book.getIsbn());
        }// end of if block
        Book savedBook = bookRepository.save(book); // save the book in the database
        return mapToBookDTO(savedBook, new BookDTO()); // map and return the saved book as BookDTO
    }// end of addBook method

    // add a list of books to the database
    @Override
    public List<BookDTO> addAllBooks(List<Book> books) {
        return books.stream() // convert the list of books to a stream
                .map(this::addBook) // use the existing addBook method for each book
                .collect(Collectors.toList()); // collect the results as a list of BookDTO
    }// end of addAllBooks method

    // retrieve all books from the database
    @Override
    public List<BookDTO> getAllBooks() {
        return mapToBookDTOList(bookRepository.findAll()); // map all books to BookDTO and return
    }// end of getAllBooks method

    // retrieve a book by its ISBN
    @Override
    public BookDTO getBookByISBN(String isbn) {
        return bookRepository.findByIsbn(formatISBN(isbn))
                .map(book -> mapToBookDTO(book, new BookDTO()))
                .orElseThrow(() -> new BookISBNNotFoundException(ISBN_NOT_FOUND + isbn));
    }// end of getBookByISBN method

    /*
     * A generic method to find books by a specific field (author, publisher, yearPublished, price)
     * and throw a custom exception if no books are found.
     */
    private <T extends RuntimeException> List<BookDTO> findBooks(Supplier<List<Book>> query, Supplier<T> exceptionSupplier) {

        // retrieve all books by the given field
        List<Book> books = query.get();

        // if no books are found, throw a custom exception
        if (books.isEmpty()) {
            throw exceptionSupplier.get();  // Throws the appropriate custom exception
        }// end of if block

        // map all books to BookDTO and return
        return mapToBookDTOList(books);
    }// end of findBooks method

    // retrieve all books by the given author
    @Override
    public List<BookDTO> getAllBooksByAuthors(String author) {
        // retrieve all books by the given author or throw a BookISBNNotFoundException
        return findBooks(
                () -> bookRepository.findByAuthors(author),
                () -> new BookISBNNotFoundException(AUTHOR_NOT_FOUND + author)
        );// end of findBooks method
    }// end of getAllBooksByAuthors method

    // retrieve all books by their title
    @Override
    public BookDTO getBookByBookTitle(String title) {
        return bookRepository.findByBookTitle(title)
                .map(book -> mapToBookDTO(book, new BookDTO()))
                .orElseThrow(() -> new BookISBNNotFoundException(TITLE_NOT_FOUND + "\"" + title + "\""));
    }// end of getBookByBookTitle method

    // retrieve all books by the given publisher
    @Override
    public List<BookDTO> getBookByPublisher(String publisher) {
        // retrieve all books by the given publisher or throw a BookISBNNotFoundException
        return findBooks(
                () -> bookRepository.findByPublisher(publisher),
                () -> new BookISBNNotFoundException("Book with the following publisher is not found in database! : " + publisher)
        );// end of findBooks method
    }// end of getBookByPublisher method

    // retrieve all books published in a specific year
    @Override
    public List<BookDTO> getBookByYearPublished(int yearPublished) {
        // retrieve all books published in a specific year or throw a BookISBNNotFoundException
        return findBooks(
                () -> bookRepository.findByYearPublished(yearPublished),
                () -> new BookISBNNotFoundException(YEAR_PUBLISHED_NOT_FOUND + yearPublished)
        );// end of findBooks method
    }// end of getBookByYearPublished method

    // retrieve all books by their price
    @Override
    public List<BookDTO> getBookByPrice(double price) {
        // retrieve all books by their price or throw a BookISBNNotFoundException
        return findBooks(
                () -> bookRepository.findByPrice(price),
                () -> new BookISBNNotFoundException(PRICE_NOT_FOUND + price)
        );// end of findBooks method
    }// end of getBookByPrice method

    // delete a book by its ISBN
    @Override
    public void deleteBookByISBN(String isbn) {
        if (validateBookByISBN(formatISBN(isbn))) {
            bookRepository.deleteByIsbn(formatISBN(isbn)); // delete the book by its ISBN
        }// end of if block
    }// end of deleteBookByISBN method

    // delete all books from the database
    @Override
    public void deleteAllBooks() {
        bookRepository.deleteAll(); // delete all books
    }// end of deleteAllBooks method

    // delete a book by its title
    @Override
    public void deleteBookByBookTitle(String bookTitle) {
        bookRepository.findByBookTitle(bookTitle)
                .map(_ -> {
                    bookRepository.deleteByBookTitle(bookTitle);
                    return true;
                })
                .orElseThrow(() -> new BookISBNNotFoundException(TITLE_NOT_FOUND + bookTitle));
    }// end of deleteBookByBookTitle method

    // update a book by its ISBN
    @Override
    public void updateBook(String isbn, Book book) {
        if (validateBookByISBN(formatISBN(isbn))) { // validate if the book exists by its ISBN
            bookRepository.updateBook(
                    book.getBookTitle(),
                    book.getAuthors(),
                    book.getPublisher(),
                    book.getYearPublished(),
                    book.getPrice(),
                    book.getIsbn()); // update the book by its ISBN
        }// end of if block
    }// end of updateBook method

    // helper method to map a list of Book entities to a list of BookDTO
    private List<BookDTO> mapToBookDTOList(List<Book> books) {
        return books.stream()
                .map(book -> mapToBookDTO(book, new BookDTO()))
                .collect(Collectors.toList());
    }// end of mapToBookDTOList method

    // helper method to validate if a book exists by its ISBN
    private boolean validateBookByISBN(String isbn) {
        bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookISBNNotFoundException(ISBN_NOT_FOUND + isbn));
        return true;
    }// end of validateBookByISBN method
}// end of BookServiceImpl class
