package com.kelvin.currencyexchangerapi.api;

import com.kelvin.currencyexchangerapi.model.EuroRateToCurrency;
import com.kelvin.currencyexchangerapi.repository.EuroRateToCurrencyRepository;
import com.kelvin.currencyexchangerapi.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.kelvin.currencyexchangerapi.managment.AppConstants.EXCHANGE_RATES_TO_EURO_URL;

@RestController
@RequestMapping(EXCHANGE_RATES_TO_EURO_URL)
@Slf4j
public class EuroRateToCurrencyApi {

    @Autowired
    EuroRateToCurrencyRepository euroRateToCurrencyRepository;

    @PostMapping("/upload")
    public ResponseEntity uploadEuroToOtherCurrenciesRates(@RequestParam(name = "file") MultipartFile multipartFile) {

        Path path = null;
        try {
            path = Files.createTempFile("rates", ".csv");
        } catch (IOException e) {
            log.error("There was a problem with creating the csv temp file.");
        }

        try (OutputStream os = new FileOutputStream(path.toFile())) {
            os.write(multipartFile.getBytes());
        }
        catch (Exception e){
            log.error("There was a problem with populating the content of csv temp file.");
        }

        List<String[]> lines = CsvUtils.parse(path);

        String[] currenciesNames = CsvUtils.getHeaderNames(path);

        List<EuroRateToCurrency> euroToCurrenciesRates = new ArrayList<>();
        for (String[] line : lines){

            String date = line[0];

            for (int i = 1; i < line.length; i++ ){
                euroToCurrenciesRates.add(new EuroRateToCurrency(date, currenciesNames[i-1], line[i]));
            }
        }

        euroToCurrenciesRates.forEach(euroRateToCurrency -> euroRateToCurrencyRepository.add(createKey(euroRateToCurrency), euroRateToCurrency));

        return ResponseEntity.ok("File was uploaded successfully.");
    }

    private String createKey(EuroRateToCurrency euroRateToCurrency) {

        return euroRateToCurrency.getDate() + "-" + euroRateToCurrency.getCurrencyName();
    }

//    getReferenceRateDataForDate(String date){
//
//    }
//
//    getRateConversionFromFirstToSecondCurrencyForDate(String date, String sourceCurrency, String targetCurrency){
//
//    }
//
//    getTheHighestExchangeRateForPeriod(String currency, String startDate, String endDate){
//
//    }
//
//    getTheAverageExchangeRateForPeriod(String currency, String startDate, String endDate){
//
//    }
}
