package com.library.books.service.impl;
/*
package com.library.books.service.impl;

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.controller.exception.BookTitleNotFound;
import com.library.books.repository.BookRepository;
import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import com.library.books.service.dto.BookMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    private static final String ISBN_NOT_FOUND = "Book ISBN not found in db! : ";
    private static final String TITLE_NOT_FOUND = "Book title not found in db! : ";
    private static final String ISBN_ALREADY_EXISTS = "Book ISBN already exists in db! : ";


    // add a book to the database
    @Override
    public BookDTO addBook(Book book) {
        // if book isbn already exists in db, throw a BookISBNAlreadyExistsException
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new BookISBNAlreadyExistsException("Book ISBN already exists in db! : " + book.getIsbn());
        }// end of if block
        Book savedBook = bookRepository.save(book);// no need to code save(Book)!
        return BookMapper.mapToBookDTO(savedBook, new BookDTO());// return the BookDTO
    }// end of addBook method

    @Override
    public List<BookDTO> addAllBooks(List<Book> books) {
        return books.stream()// convert the list to a stream
                .map(this::addBook)// use the existing addBook method to add each book
                .collect(Collectors.toList()); // collect the BookDTO's into a list
    }// end of addAllBooks method

    // get all books to the database
    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getAllBooks method

    // get a book by its isbn
    @Override
    public BookDTO getBookByISBN(String isbn) {
        Book book = bookRepository
                .findByIsbn(isbn) // returns Optional<Book>
                .orElseThrow(() -> new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn));
        return BookMapper.mapToBookDTO(book, new BookDTO());
    }// end of getBookByISBN method

    // find all books by their authors
    @Override
    public List<BookDTO> getAllBooksByAuthors(String author) {
        return bookRepository.findBooksByAuthorName(author).stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getAllBooksByAuthor method

    // find all books by their title
    @Override
    public List<BookDTO> getBookByBookTitle(String title) {
        return bookRepository.findBooksByTitle(title).stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getBookByBookTitle method

    @Override
    public List<BookDTO> getBookByPublisher(String publisher) {
        return bookRepository.findBooksByPublisher(publisher).stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getBookByPublisher method

    @Override
    public List<BookDTO> getBookByYearPublished(int yearPublished) {
        return bookRepository.findBooksByYearPublished(yearPublished).stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getBookByYearPublished method

    @Override
    public List<BookDTO> getBookByPrice(double price) {
        return bookRepository.findBooksByPrice(price).stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO())) // so the primary key is not returned
                .collect(Collectors.toList());
    }// end of getBookByPrice method

    // delete a book by its isbn
    @Override
    public boolean deleteBookByISBN(String isbn) {
        // if book isbn does not exist in db, throw a BookISBNNotFoundException
        if (bookRepository.findByIsbn(isbn).isEmpty()) {
            throw new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn);
        }// end of if block
        bookRepository.deleteByIsbn(isbn);// delete the book by its isbn
        return true;// return true if the book was deleted
    }// end of deleteBookByISBN method

    @Override
    public boolean deleteAllBooks() {
        bookRepository.deleteAll();// delete all books
        return true;// return true if all books were deleted
    }// end of deleteAllBooks method

    // delete a book by its title
    @Override
    public boolean deleteBookByBookTitle(String title) {
        // if book title does not exist in db, throw a BookISBNNotFoundException
        if (bookRepository.findBooksByTitle(title).isEmpty()) {
            throw new BookTitleNotFound("Book title not found in db! : " + title);
        }// end of if block
        bookRepository.deleteBookByBookTitle(title);// delete the book by its title
        return true;// return true if the book was deleted
    }// end of deleteBookByBookTitle method

    // update a book by its isbn
    @Override
    public boolean updateBook(String isbn, Book book) {
        // if book isbn does not exist in db, throw a BookISBNNotFoundException
        if (bookRepository.findByIsbn(isbn).isEmpty()) {
            throw new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn);
        }// end of if block
        // this is an update as the isbn is already in the database
        bookRepository.save(book);// save the book
        return true;// return true if the book was updated
    }// end of updateBook method

}// end of BookServiceImpl class
*/

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.controller.exception.BookTitleNotFound;
import com.library.books.repository.BookRepository;
import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import com.library.books.service.dto.BookMapper;
import static com.library.books.utility.ISBNFormatter.formatISBN;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    private static final String ISBN_NOT_FOUND = "Book with the following ISBN is not found in database! : ";
    private static final String TITLE_NOT_FOUND = "Book title not found in database : ";
    private static final String ISBN_ALREADY_EXISTS = "Book with the following ISBN already exists in database! : ";

    // add a book to the database
    @Override
    public BookDTO addBook(Book book) {
        // set the ISBN of the book to the formatted ISBN
        book.setIsbn(formatISBN(book.getIsbn()));

        System.out.println("Formatted ISBN: " + book.getIsbn());
        // if book ISBN already exists in the database, throw a BookISBNAlreadyExistsException
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new BookISBNAlreadyExistsException(ISBN_ALREADY_EXISTS + book.getIsbn());
        }// end of if block
        Book savedBook = bookRepository.save(book); // save the book in the database
        return BookMapper.mapToBookDTO(savedBook, new BookDTO()); // map and return the saved book as BookDTO
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
        Book book = validateBookByISBN(isbn); // validate if the book exists by its ISBN
        return BookMapper.mapToBookDTO(book, new BookDTO()); // map and return the book as BookDTO
    }// end of getBookByISBN method

    // retrieve all books by the given author
    @Override
    public List<BookDTO> getAllBooksByAuthors(String author) {
        return findBooksByAttribute(bookRepository.findBooksByAuthorName(author)); // map books by author to BookDTO
    }// end of getAllBooksByAuthors method

    // retrieve all books by their title
    @Override
    public List<BookDTO> getBookByBookTitle(String title) {
        return findBooksByAttribute(bookRepository.findBooksByTitle(title)); // map books by title to BookDTO
    }// end of getBookByBookTitle method

    // retrieve all books by the given publisher
    @Override
    public List<BookDTO> getBookByPublisher(String publisher) {
        return findBooksByAttribute(bookRepository.findBooksByPublisher(publisher)); // map books by publisher to BookDTO
    }// end of getBookByPublisher method

    // retrieve all books published in a specific year
    @Override
    public List<BookDTO> getBookByYearPublished(int yearPublished) {
        return findBooksByAttribute(bookRepository.findBooksByYearPublished(yearPublished)); // map books by year to BookDTO
    }// end of getBookByYearPublished method

    // retrieve all books by their price
    @Override
    public List<BookDTO> getBookByPrice(double price) {
        return findBooksByAttribute(bookRepository.findBooksByPrice(price)); // map books by price to BookDTO
    }// end of getBookByPrice method

    // delete a book by its ISBN
    @Override
    public boolean deleteBookByISBN(String isbn) {
        validateBookByISBN(isbn); // validate if the book exists by its ISBN
        bookRepository.deleteByIsbn(isbn); // delete the book by its ISBN
        return true; // return true if deletion is successful
    }// end of deleteBookByISBN method

    // delete all books from the database
    @Override
    public boolean deleteAllBooks() {
        bookRepository.deleteAll(); // delete all books
        return true; // return true if all books are deleted
    }// end of deleteAllBooks method

    // delete a book by its title
    @Override
    public boolean deleteBookByBookTitle(String title) {
        // if no book with the given title exists, throw a BookTitleNotFound exception
        if (bookRepository.findBooksByTitle(title).isEmpty()) {
            throw new BookTitleNotFound(TITLE_NOT_FOUND + title);
        }// end of if block
        bookRepository.deleteBookByBookTitle(title); // delete the book by its title
        return true; // return true if deletion is successful
    }// end of deleteBookByBookTitle method

    // update a book by its ISBN
    @Override
    public boolean updateBook(String isbn, Book book) {
        validateBookByISBN(formatISBN(isbn)); // validate if the book exists by its ISBN
        bookRepository.updateBook(
                book.getBookTitle(),
                book.getAuthors(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getPrice(),
                book.getIsbn()); // update the book by its ISBN
        return true; // return true if the update is successful
    }// end of updateBook method

    // helper method to map a list of Book entities to a list of BookDTO
    private List<BookDTO> mapToBookDTOList(List<Book> books) {
        return books.stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO()))
                .collect(Collectors.toList());
    }// end of mapToBookDTOList method

    // helper method to map books by a specific attribute to BookDTO
    private List<BookDTO> findBooksByAttribute(List<Book> books) {
        return mapToBookDTOList(books);
    }// end of findBooksByAttribute method

    // helper method to validate if a book exists by its ISBN
    private Book validateBookByISBN(String isbn) {
        return bookRepository.findByIsbn(formatISBN(isbn))
                .orElseThrow(() -> new BookISBNNotFoundException(ISBN_NOT_FOUND + isbn));
    }// end of validateBookByISBN method
}// end of BookServiceImpl class
