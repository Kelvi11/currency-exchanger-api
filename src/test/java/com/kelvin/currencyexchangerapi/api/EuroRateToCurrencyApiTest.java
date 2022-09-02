package com.kelvin.currencyexchangerapi.api;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.kelvin.currencyexchangerapi.managment.AppConstants.EXCHANGE_RATES_TO_EURO_URL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EuroRateToCurrencyApiTest {

    private final MockMvc mockMvc;

    @Autowired
    public EuroRateToCurrencyApiTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @Order(2)
    void givenCsvFile_whenUploadEuroToOtherCurrenciesRates_thenLoadFileContentToMemory() throws Exception {


        //given
        Path path = Path.of("src\\test\\resources\\eurofxref-hist.csv");

        byte[] fileContent = new byte[1024];
        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {

        }


        MultipartFile multipartFile = new MockMultipartFile("file", fileContent);

        //when
        this.mockMvc.perform(
                        multipart(EXCHANGE_RATES_TO_EURO_URL + "/upload")
                                .file((MockMultipartFile) multipartFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA))


                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));

    }
}
