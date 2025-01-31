package com.library.books.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Data
public class Book {
    // Generate ID for each book in the library
    @Id // Primary key for the Book entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto-generate the ID
    private Long id; // ID of the book

    @NotBlank(message = "Book title cannot be blank!")
    @Column(name = "book_title")
    private String bookTitle; // Title of the book

    @NotBlank(message = "Authors cannot be blank!")
    private String authors; // Authors of the book

    @NotBlank(message = "Publisher cannot be blank!")
    private String publisher; // Publisher of the book

    @NotBlank(message = "ISBN cannot be blank!")
    @Pattern(
            regexp = "^(97[89]-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d)|(\\d-\\d{3}-\\d{5}-[\\dX])$",
            message = "Invalid ISBN format! It must be either ISBN-10 or ISBN-13 with dashes."
    )// pattern to match ISBN-10 or ISBN-13
    private String isbn; // ISBN of the book

    @Positive(message = "Year published must be a positive number!")
    @Column(name = "year_published")
    private int yearPublished; // Year the book was published

    @Positive(message = "Price must be a positive number!")
    private double price; // Price of the book

}// end of Book class
