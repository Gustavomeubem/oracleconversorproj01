package com.currencyconverter.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRateResponse {
    private String base;
    private String date;
    private Map<String, Double> rates;

    public ExchangeRateResponse() {
        this.rates = new HashMap<>();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public static ExchangeRateResponse fromJson(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        
        ExchangeRateResponse response = gson.fromJson(jsonObject, ExchangeRateResponse.class);
        
        // Extract rates from JSON
        if (jsonObject.has("rates")) {
            JsonObject ratesObject = jsonObject.getAsJsonObject("rates");
            Map<String, Double> ratesMap = new HashMap<>();
            
            for (String key : ratesObject.keySet()) {
                ratesMap.put(key, ratesObject.get(key).getAsDouble());
            }
            
            response.setRates(ratesMap);
        }
        
        return response;
    }
}