package com.matias.challenge.model;

import java.util.Objects;

public class Book {

    // Attributes
    private final String title;
    private final String author;
    private final int publicationYear;
    private final int confidenceScore;

    // Constructor
    public Book(String title, String author, int publicationYear, int confidenceScore) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.confidenceScore = confidenceScore;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getConfidenceScore() {
        return confidenceScore;
    }

    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publicationYear == book.publicationYear &&
                title.equalsIgnoreCase(book.title) &&
                author.equalsIgnoreCase(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase(), publicationYear);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear + '\'' +
                ", confidenceScore=" + confidenceScore +
                '}';
    }
}
