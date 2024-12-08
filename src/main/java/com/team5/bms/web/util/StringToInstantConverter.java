package com.team5.bms.web.util;

import org.springframework.core.convert.converter.Converter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class StringToInstantConverter implements Converter<String, Instant> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                        .withZone(ZoneId.systemDefault());

    @Override
    public Instant convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Instant.from(formatter.parse(source));
    }

}