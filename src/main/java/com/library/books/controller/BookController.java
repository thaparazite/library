package com.library.books.controller;

import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import static com.library.books.utility.ISBNFormatter.formatISBN;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BookController {

    private IBookService IBookService;

    private void addLinksToBooks(List<BookDTO> bookDTOS) {
        for (BookDTO bookDTO : bookDTOS) {
            bookDTO.add(
                    linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel(),
                    linkTo(methodOn(BookController.class).addBook(new Book(), UriComponentsBuilder.newInstance())).withRel("add-book:"),
                    linkTo(methodOn(BookController.class).addAllBooks(List.of(), UriComponentsBuilder.newInstance())).withRel("add-books:"),
                    linkTo(methodOn(BookController.class).deleteBookByISBN(bookDTO.getIsbn())).withRel("delete-book:isbn="),
                    linkTo(methodOn(BookController.class).deleteBookByBookTitle(bookDTO.getBookTitle())).withRel("delete-book:bookTitle="),
                    linkTo(methodOn(BookController.class).deleteAllBooks()).withRel("delete-books:"),
                    linkTo(methodOn(BookController.class).updateBook(bookDTO.getIsbn(), null)).withRel("update-book:isbn="),
                    linkTo(methodOn(BookController.class).getBookByISBN(bookDTO.getIsbn())).withRel("getBookByISBN:"),
                    linkTo(methodOn(BookController.class).getAllBooksByAuthors(bookDTO.getAuthors())).withRel("getAllBooksByAuthors:"),
                    linkTo(methodOn(BookController.class).getBookByBookTitle(bookDTO.getBookTitle())).withRel("getAllBookByBookTitle:"),
                    linkTo(methodOn(BookController.class).getBookByPublisher(bookDTO.getPublisher())).withRel("getAllBooksByPublisher:"),
                    linkTo(methodOn(BookController.class).getBookByYearPublished(bookDTO.getYearPublished())).withRel("getAllBooksByYearPublished:"),
                    linkTo(methodOn(BookController.class).getBookByPrice(bookDTO.getPrice())).withRel("getAllBooksByPrice:")
            );
        }
    }// end of addLinksToBooks method

    @GetMapping("/getAllBooks:")
    public List<BookDTO> getAllBooks() {
        return IBookService.getAllBooks();
    }// end of getAllBooks method

    @GetMapping(path = "/getBookByISBN:", params = "isbn")
    public BookDTO getBookByISBN(@RequestParam String isbn) {
        return IBookService.getBookByISBN(isbn);
    }// end of getBookByISBN method

    @GetMapping(path = "/getAllBooksByAuthors:", params = "authors")
    public List<BookDTO> getAllBooksByAuthors(@RequestParam String authors) {
        return IBookService.getAllBooksByAuthors(authors);
    }// end of getAllBooksByAuthors method

    @GetMapping(path = "/getBookByBookTitle:", params = "bookTitle")
    public BookDTO getBookByBookTitle(@RequestParam String bookTitle) {
        return IBookService.getBookByBookTitle(bookTitle);
    }// end of getBookByBookTitle method

    @GetMapping(path = "/getAllBooksByPublisher:", params = "publishers")
    public List<BookDTO> getBookByPublisher(@RequestParam String publishers) {
        return IBookService.getBookByPublisher(publishers);
    }// end of getBookByPublisher method

    @GetMapping(path = "/getAllBooksByYearPublished:", params = "yearPublished")
    public List<BookDTO> getBookByYearPublished(@RequestParam int yearPublished) {
        return IBookService.getBookByYearPublished(yearPublished);
    }// end of getBookByYearPublished method

    @GetMapping(path = "/getAllBooksByPrice:", params = "price")
    public List<BookDTO> getBookByPrice(@RequestParam double price) {
        return IBookService.getBookByPrice(price);
    }// end of getBookByPrice method

    @PostMapping("/add-book:")
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book, UriComponentsBuilder uriComponentsBuilder) {
        BookDTO bookDTO = IBookService.addBook(book);
        addLinksToBooks(List.of(bookDTO));
        // build the location URI of the newly created resource
        URI locationURI = uriComponentsBuilder
                .path("/books/getByISBN:isbn={isbn}")
                .buildAndExpand(bookDTO.getIsbn())
                .toUri();
        // return the response entity with the location URI and the bookDTO
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(bookDTO);
    }// end of addBook method

    @PostMapping("/add-books:")
    public ResponseEntity<List<BookDTO>> addAllBooks(@RequestBody List<Book> books, UriComponentsBuilder uriComponentsBuilder) {
        List<BookDTO> bookDTOS = IBookService.addAllBooks(books);
        addLinksToBooks(bookDTOS);
        // build the location URI of the newly created resource
        URI locationURI = uriComponentsBuilder
                .path("/books")
                .build()
                .toUri();
        // return the response entity with the location URI and the bookDTO
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(bookDTOS);
    }// end of addAllBooks method

    @PostMapping()
    public ResponseEntity<String> postNotSupported() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("POST method is not supported for this endpoint!");
    }// end of postNotSupported method

    @PutMapping()
    public ResponseEntity<String> putNotSupported() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("PUT method is not supported for this endpoint!");
    }// end of putNotSupported method

    @PutMapping("/update-book:isbn={isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        IBookService.updateBook(isbn, book);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Book with ISBN: " + formatISBN(isbn) + " has been updated!");
    }// end of updateBook method

    @DeleteMapping("/delete-book:isbn={isbn}")
    public ResponseEntity<String> deleteBookByISBN(@PathVariable String isbn) {
        IBookService.deleteBookByISBN(isbn);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Book with ISBN: " + formatISBN(isbn) + " has been deleted!");
    }// end of deleteBookByISBN method

    @DeleteMapping("/delete-books:")
    public ResponseEntity<String> deleteAllBooks() {
        IBookService.deleteAllBooks();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("All books have been deleted!");
    }// end of deleteAllBooks method

    @DeleteMapping("/delete-book:bookTitle={bookTitle}")
    public ResponseEntity<String> deleteBookByBookTitle(@PathVariable String bookTitle) {
        IBookService.deleteBookByBookTitle(bookTitle);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Book with title: \"" + bookTitle + "\" has been deleted!");
    }// end of deleteBookByBookTitle method

}// end of BookController class
