package com.currencyconverter.exception;

public class CurrencyConversionException extends Exception {
    public CurrencyConversionException(String message) {
        super(message);
    }

    public CurrencyConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}