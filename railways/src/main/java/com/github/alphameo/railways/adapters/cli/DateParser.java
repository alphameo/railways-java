package com.github.alphameo.railways.adapters.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm");

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMAT);
    }
}
