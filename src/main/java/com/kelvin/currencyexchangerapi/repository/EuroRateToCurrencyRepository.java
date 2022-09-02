package com.kelvin.currencyexchangerapi.repository;

import com.kelvin.currencyexchangerapi.model.EuroRateToCurrency;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EuroRateToCurrencyRepository {
    
    private Map<String, EuroRateToCurrency> euroToCurrenciesRates = new HashMap<>();

    public Map<String, EuroRateToCurrency> getAll(){
        return euroToCurrenciesRates;
    }

    public void add(String key, EuroRateToCurrency euroRateToCurrency){
        euroToCurrenciesRates.put(key, euroRateToCurrency);
    }
}
