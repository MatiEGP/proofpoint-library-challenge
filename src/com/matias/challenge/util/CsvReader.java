package com.matias.challenge.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    // Read CSV function
    public List<String> readCsv (String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return lines;
    }
}
