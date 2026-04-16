package com.matias.challenge;

import com.matias.challenge.model.Book;
import com.matias.challenge.service.LibraryService;
import com.matias.challenge.util.CsvReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        LibraryService libraryService = new LibraryService();

        //
        String fileName = "books.csv";

        List<String> rawLines = csvReader.readCsv(fileName);

        if (rawLines.isEmpty()) {
            System.out.println("Warning: No data found in file.");
            return;
        }

        List<Book> cleanBooks = libraryService.processRawData(rawLines);
        libraryService.displayStats();
        libraryService.displayOrganizedData(cleanBooks);

        System.out.println("\nProcess completed successfully.");
    }
}
