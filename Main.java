package com.currencyconverter;

import com.currencyconverter.api.CurrencyService;
import com.currencyconverter.model.ConversionResult;
import com.currencyconverter.util.FileManager;
import com.currencyconverter.util.Validator;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "b3a0506e4e14986c0e51e820";
    private static CurrencyService currencyService;
    private static FileManager fileManager;

    public static void main(String[] args) {
        try {
            currencyService = new CurrencyService(API_KEY);
            fileManager = new FileManager("data/conversion_history.txt");
            
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            System.out.println("=== Currency Converter API ===");
            
            while (running) {
                displayMenu();
                int choice = getIntInput(scanner, "Choose an option: ");
                
                switch (choice) {
                    case 1:
                        convertCurrency(scanner);
                        break;
                    case 2:
                        showAvailableCurrencies();
                        break;
                    case 3:
                        showConversionHistory();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Thank you for using Currency Converter!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            
            scanner.close();
            
        } catch (Exception e) {
            System.out.println("Error initializing application: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Convert Currency");
        System.out.println("2. Show Available Currencies");
        System.out.println("3. Show Conversion History");
        System.out.println("4. Exit");
    }

    private static void convertCurrency(Scanner scanner) {
        try {
            System.out.println("\n=== Currency Conversion ===");
            
            String fromCurrency = getStringInput(scanner, "Enter source currency (e.g., USD): ").toUpperCase();
            String toCurrency = getStringInput(scanner, "Enter target currency (e.g., EUR): ").toUpperCase();
            double amount = getDoubleInput(scanner, "Enter amount to convert: ");
            
            if (!Validator.isValidCurrency(fromCurrency) || !Validator.isValidCurrency(toCurrency)) {
                System.out.println("Error: Invalid currency code. Please use 3-letter codes like USD, EUR, BRL.");
                return;
            }
            
            if (amount <= 0) {
                System.out.println("Error: Amount must be greater than 0.");
                return;
            }
            
            ConversionResult result = currencyService.convertCurrency(fromCurrency, toCurrency, amount);
            
            if (result != null) {
                System.out.println("\nConversion Result:");
                System.out.printf("%.2f %s = %.2f %s%n", 
                    amount, fromCurrency, result.getConvertedAmount(), toCurrency);
                System.out.printf("Exchange Rate: 1 %s = %.6f %s%n",
                    fromCurrency, result.getExchangeRate(), toCurrency);
                
                // Save to history file
                fileManager.saveConversion(result);
                System.out.println("Conversion saved to history.");
            }
            
        } catch (Exception e) {
            System.out.println("Conversion error: " + e.getMessage());
        }
    }

    private static void showAvailableCurrencies() {
        try {
            System.out.println("\n=== Available Currencies ===");
            List<String> currencies = currencyService.getAvailableCurrencies();
            
            if (currencies != null && !currencies.isEmpty()) {
                for (int i = 0; i < currencies.size(); i++) {
                    System.out.printf("%-8s", currencies.get(i));
                    if ((i + 1) % 10 == 0) {
                        System.out.println();
                    }
                }
                System.out.println("\n\nTotal: " + currencies.size() + " currencies available");
            }
        } catch (Exception e) {
            System.out.println("Error fetching currencies: " + e.getMessage());
        }
    }

    private static void showConversionHistory() {
        try {
            System.out.println("\n=== Conversion History ===");
            List<String> history = fileManager.readConversionHistory();
            
            if (history.isEmpty()) {
                System.out.println("No conversion history found.");
            } else {
                for (String entry : history) {
                    System.out.println(entry);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}