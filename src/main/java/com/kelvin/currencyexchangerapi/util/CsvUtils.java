package com.kelvin.currencyexchangerapi.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CsvUtils {

    public static List<String[]> parse(Path path){

        List<String[]> lines;
        try {
            lines = Files.lines(path)
                    .skip(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error during the opening of the file.");
            lines = new ArrayList<>();
        }

        return lines;
    }

    public static String[] getHeaderNames(Path path){

        List<String[]> lines;
        try {
            lines = Files.lines(path)
                    .limit(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error during the opening of the file.");
            lines = new ArrayList<>();
        }

        return lines.get(0);
    }

}
