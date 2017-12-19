package com.he.ssh.base.core.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Created by heyanjing on 2017/12/19 16:10.
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", locale));
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", locale));
    }
}
