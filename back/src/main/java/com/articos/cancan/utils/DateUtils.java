package com.articos.cancan.utils;

import java.time.*;
import java.time.format.*;

public class DateUtils {

    public static DateTimeFormatter displayPatternFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String displayPattern(LocalDate date) {
        return date.format(displayPatternFormatter);
    }
}
