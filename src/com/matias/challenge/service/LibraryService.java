package com.matias.challenge.service;

import com.matias.challenge.model.Book;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LibraryService {
    private int totalLinesProcessed = 0;
    private int validBooksCount = 0;
    private int corruptedRecordsCount = 0;

    public List<Book> processRawData (List<String> rawLines) {
        int currentYear = Year.now().getValue();
        this.totalLinesProcessed = rawLines.size();

        List<Book> cleanBooks = rawLines.stream()
                .filter(line -> {
                    String[] columns = line.split(",", -1);
                    boolean hasTitle = columns.length > 0 && !columns[0].isBlank();
                    if (!hasTitle) this.corruptedRecordsCount++;
                    return hasTitle;
                        })
                .map(line -> {
                    String[] columns = line.split(",", -1);
                    String title = columns[0].trim();
                    int confidenceScore = 1;
                    String author;
                    if (columns.length > 1 && !columns[1].isBlank()) {
                        author = columns[1].trim();
                        confidenceScore++;
                    } else {
                        author = "Author Unknown";
                        this.corruptedRecordsCount++;
                    }

                    int year = 0;
                    if (columns.length > 2) {
                        year = parseYear(columns[2], currentYear);
                        if (year > 0) confidenceScore++;
                    }
                    else this.corruptedRecordsCount++;

                    return new Book(title, author, year, confidenceScore);
                })
                .distinct()
                .collect(Collectors.toList());

        this.validBooksCount = cleanBooks.size();
        return cleanBooks;
    }

    private int parseYear(String yearStr, int currentYear) {
        try {
            int year = Integer.parseInt(yearStr.trim());
            if (year < 0 || year > currentYear) {
                corruptedRecordsCount++;
                return 0;
            } else {
                return year;
            }
        } catch (Exception e) {
            this.corruptedRecordsCount++;
            return 0;
        }
    }

    public void displayStats() {
        System.out.println("\n========== PROCESSING STATISTICS ==========");
        System.out.println("Total lines read:        " + this.totalLinesProcessed);
        System.out.println("Clean books identified:  " + this.validBooksCount);
        System.out.println("Corrupted/Fixed records: " + this.corruptedRecordsCount);
        System.out.println("=============================================");
    }

    public void displayOrganizedData(List<Book> cleanBooks) {
        System.out.println("\n========== ORGANIZED LIBRARY CATALOG ==========");

        // Books without author
        List<Book> unknownAuthorBooks = cleanBooks.stream()
                .filter(book -> book.getAuthor().equals("Author Unknown"))
                .sorted(Comparator.comparingInt(Book::getPublicationYear))
                .collect(Collectors.toList());

        if (!unknownAuthorBooks.isEmpty()) {
            Map<Integer, List<Book>> groupedByYear = unknownAuthorBooks.stream()
                    .collect(Collectors.groupingBy(Book::getPublicationYear, TreeMap::new, Collectors.toList()));

            groupedByYear.forEach((year, books) -> {
                System.out.println("\nPublication Year: " + year);
                books.forEach(book -> System.out.println("  - " + book));
            });
        }

        // Books with author
        Map<String, List<Book>> groupedByAuthor = cleanBooks.stream()
                .filter(book -> !book.getAuthor().equals("Author Unknown"))
                .collect(Collectors.groupingBy(Book::getAuthor, TreeMap::new, Collectors.toList()));

        groupedByAuthor.forEach((author, books) -> {
            System.out.println("\nAuthor: " + author);
            books.stream()
                    .sorted(Comparator.comparingInt(Book::getPublicationYear))
                    .forEach(book -> System.out.println("  - " + book));
        });

        System.out.println("\n===============================================");
    }
}
