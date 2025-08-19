package com.currencyconverter.api;

import com.currencyconverter.exception.CurrencyConversionException;
import com.currencyconverter.exception.InvalidCurrencyException;
import com.currencyconverter.model.ConversionResult;
import com.currencyconverter.util.Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class CurrencyService {
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final String apiKey;

    public CurrencyService(String apiKey) {
        this.apiKey = apiKey;
    }

    public ConversionResult convertCurrency(String fromCurrency, String toCurrency, double amount) 
            throws CurrencyConversionException, InvalidCurrencyException {
        
        if (!Validator.isValidCurrency(fromCurrency)) {
            throw new InvalidCurrencyException("Invalid source currency: " + fromCurrency);
        }
        
        if (!Validator.isValidCurrency(toCurrency)) {
            throw new InvalidCurrencyException("Invalid target currency: " + toCurrency);
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        try {
            String apiUrl = BASE_URL + fromCurrency + "?apikey=" + apiKey;
            ExchangeRateResponse response = fetchExchangeRates(apiUrl);
            
            if (response == null || response.getRates() == null) {
                throw new CurrencyConversionException("Failed to fetch exchange rates");
            }
            
            Map<String, Double> rates = response.getRates();
            Double exchangeRate = rates.get(toCurrency);
            
            if (exchangeRate == null) {
                throw new InvalidCurrencyException("Target currency not found: " + toCurrency);
            }
            
            double convertedAmount = amount * exchangeRate;
            
            return new ConversionResult(fromCurrency, toCurrency, amount, convertedAmount, exchangeRate);
            
        } catch (Exception e) {
            throw new CurrencyConversionException("Currency conversion failed: " + e.getMessage(), e);
        }
    }

    public List<String> getAvailableCurrencies() throws CurrencyConversionException {
        try {
            // Use USD as base to get all available currencies
            String apiUrl = BASE_URL + "USD" + "?apikey=" + apiKey;
            ExchangeRateResponse response = fetchExchangeRates(apiUrl);
            
            if (response != null && response.getRates() != null) {
                return new ArrayList<>(response.getRates().keySet());
            }
            
            return new ArrayList<>();
            
        } catch (Exception e) {
            throw new CurrencyConversionException("Failed to fetch available currencies: " + e.getMessage(), e);
        }
    }

    private ExchangeRateResponse fetchExchangeRates(String apiUrl) throws CurrencyConversionException {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return ExchangeRateResponse.fromJson(response.toString());
                
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new CurrencyConversionException("Currency not found or API endpoint unavailable");
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new CurrencyConversionException("Invalid API key or unauthorized access");
            } else {
                throw new CurrencyConversionException("HTTP error: " + responseCode);
            }
            
        } catch (Exception e) {
            throw new CurrencyConversionException("API request failed: " + e.getMessage(), e);
        }
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) throws CurrencyConversionException {
        try {
            String apiUrl = BASE_URL + baseCurrency + "?apikey=" + apiKey;
            ExchangeRateResponse response = fetchExchangeRates(apiUrl);
            
            if (response != null) {
                return response.getRates();
            }
            
            return new HashMap<>();
            
        } catch (Exception e) {
            throw new CurrencyConversionException("Failed to fetch exchange rates: " + e.getMessage(), e);
        }
    }
}