package com.currencyconverter.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionResult {
    private String fromCurrency;
    private String toCurrency;
    private double originalAmount;
    private double convertedAmount;
    private double exchangeRate;
    private LocalDateTime timestamp;

    public ConversionResult(String fromCurrency, String toCurrency, 
                          double originalAmount, double convertedAmount, double exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s|%s|%.2f|%.2f|%.6f|%s",
                fromCurrency, toCurrency, originalAmount, convertedAmount, 
                exchangeRate, timestamp.format(formatter));
    }

    public static ConversionResult fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            ConversionResult result = new ConversionResult(
                parts[0], parts[1], Double.parseDouble(parts[2]), 
                Double.parseDouble(parts[3]), Double.parseDouble(parts[4])
            );
            return result;
        }
        return null;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %.2f %s → %.2f %s (Rate: %.6f)",
                timestamp.format(formatter), originalAmount, fromCurrency, 
                convertedAmount, toCurrency, exchangeRate);
    }
}