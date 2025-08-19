package com.currencyconverter.util;

import com.currencyconverter.model.ConversionResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    public void saveConversion(ConversionResult result) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(result.toFileString());
        } catch (IOException e) {
            System.err.println("Error saving conversion: " + e.getMessage());
        }
    }

    public List<String> readConversionHistory() {
        List<String> history = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ConversionResult result = ConversionResult.fromFileString(line);
                if (result != null) {
                    history.add(result.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading history: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing history data: " + e.getMessage());
        }
        
        return history;
    }

    public void clearHistory() {
        try {
            Files.deleteIfExists(Paths.get(filePath));
            ensureFileExists();
        } catch (IOException e) {
            System.err.println("Error clearing history: " + e.getMessage());
        }
    }
}