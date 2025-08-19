package com.currencyconverter.util;

import java.util.HashSet;
import java.util.Set;

public class Validator {
    private static final Set<String> VALID_CURRENCIES = new HashSet<>();
    
    static {
        // Common currency codes
        VALID_CURRENCIES.add("USD");
        VALID_CURRENCIES.add("EUR");
        VALID_CURRENCIES.add("GBP");
        VALID_CURRENCIES.add("JPY");
        VALID_CURRENCIES.add("BRL");
        VALID_CURRENCIES.add("CAD");
        VALID_CURRENCIES.add("AUD");
        VALID_CURRENCIES.add("CHF");
        VALID_CURRENCIES.add("CNY");
        VALID_CURRENCIES.add("INR");
        VALID_CURRENCIES.add("MXN");
        VALID_CURRENCIES.add("RUB");
        VALID_CURRENCIES.add("KRW");
        VALID_CURRENCIES.add("TRY");
        VALID_CURRENCIES.add("ZAR");
        // Add more currencies as needed
    }

    public static boolean isValidCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.length() != 3) {
            return false;
        }
        
        // Basic validation - in real application, you'd validate against API response
        return VALID_CURRENCIES.contains(currencyCode.toUpperCase());
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }
}