package com.softech.ehr.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AzDateFormatter {
    private static final DateTimeFormatter localDateFormatter =
        DateTimeFormatter.ofPattern("MMM-dd-yyyy");

    private static final DateTimeFormatter localDateTimeFormatter =
        DateTimeFormatter.ofPattern("d-MMM-yyyy");

    public static String formatLocalDate(LocalDate date) {
        return date == null ? null : date.format(localDateFormatter);
    }

    public static String formatLocalDateTime(LocalDateTime date) {
        return date == null ? null : date.format(localDateTimeFormatter);
    }

}
