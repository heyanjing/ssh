package com.he.ssh;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by heyanjing on 2017/12/20 8:31.
 */
public class Java8Test {
    private static final Logger log = LoggerFactory.getLogger(Java8Test.class);

    @Test
    public void time() {
//        long startTime = System.currentTimeMillis(); //获取开始时间
//        long endTime = System.currentTimeMillis(); //获取结束时间
//        log.info("程序运行时间： " + (endTime - startTime) + "ms");

        Date date = new Date();
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();

        log.info("date============{}", date.getTime());
        log.info("instant========={}", instant.toEpochMilli());
        log.info("localDateTime==={}", localDateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
        log.info("localTime======={}", localTime.atDate(LocalDate.now()).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
        log.info("localDate======={}", localDate.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());


        log.info(ZoneId.systemDefault().getId());//系统默认的ZoneId
        log.info(TimeZone.getDefault().toZoneId().getId());//系统默认的ZoneId
        log.info("{}",OffsetDateTime.now().getOffset());//系统默认的ZoneOffset

        log.info("将LocalDate {} 转换为毫秒值 {}",localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),localDate.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
    }

    @Test
    public void test() {
        long i = 621400332000l - 621360000000l;
        long j = i % 3600000;
        long k = j % 60000;

        log.info("{}",i/1000/60/60);
        log.info("{}",j/1000/60);
        log.info("{}",k/1000);


        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(621356400000l), ZoneId.systemDefault());
        LocalDate localDate = Instant.ofEpochMilli(621360000000l).atZone(ZoneId.systemDefault()).toLocalDate();
        log.info(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

//        将LocalDate 1989-09-10 转换为毫秒值 621360000000
        LocalDate localDate2 = LocalDate.of(1989, 9, 10);
        log.info("{}",OffsetDateTime.now(ZoneId.systemDefault()).getOffset().getId());
        log.info("{}",OffsetDateTime.now().getOffset().getId());
        log.info("localDate======={}", localDate2.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
        log.info("localDate======={}", localDate2.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());

        Date date = new Date(621360000000l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(format.format(date));
    }
}
