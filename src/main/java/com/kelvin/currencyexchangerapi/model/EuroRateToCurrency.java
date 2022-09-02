package com.kelvin.currencyexchangerapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EuroRateToCurrency {

    private LocalDate date;

    private String currencyName;

    private double rateToEuro;

    public EuroRateToCurrency(String date, String currencyName, String rateToEuro) {
        this.date = LocalDate.parse(date);
        this.currencyName = currencyName;
        if (!rateToEuro.equals("N/A")) {
            this.rateToEuro = Double.parseDouble(rateToEuro);
        }
    }
}
