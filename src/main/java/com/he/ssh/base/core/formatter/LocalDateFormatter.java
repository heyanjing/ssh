package com.he.ssh.base.core.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by heyanjing on 2017/12/19 16:10.
 */
public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd", locale));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd", locale));
    }
}
