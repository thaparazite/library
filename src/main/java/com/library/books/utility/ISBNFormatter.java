package com.library.books.utility;

// Utility class to format ISBN
public class ISBNFormatter {
    public static String formatISBN(String isbn) {
        if (isbn == null) {
            return null;
        }
        // Remove any existing dashes
        isbn = isbn.replaceAll("-", "");
        // Format ISBN-10
        if (isbn.length() == 10) {
            return isbn.replaceAll("(.)(.{3})(.{5})(.)", "$1-$2-$3-$4");
        }
        // Format ISBN-13
        if (isbn.length() == 13) {
            return isbn.replaceAll("(.{3})(.)(.{4})(.{4})(.)", "$1-$2-$3-$4-$5");
        }
        // Return the original ISBN if it doesn't match ISBN-10 or ISBN-13 length
        return isbn;
    }// end of formatISBN method
}// end of ISBNFormatter class
