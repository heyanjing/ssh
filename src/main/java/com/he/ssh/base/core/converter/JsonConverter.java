package com.he.ssh.base.core.converter;

/**
 * Created by heyanjing on 2017/12/19 17:07.
 *
 * springmvc @responsebody 对java8日期的处理
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JsonConverter {
    private static final Logger log = LoggerFactory.getLogger(JsonConverter.class);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("转换json字符失败!");
        }
    }

    public <T> T toObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("将json字符转换为对象时失败!");
        }
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            log.info("将LocalDate {} 转换为毫秒值 {}", value.format(dateFormatter), value.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli() - 3600000l);
            jgen.writeNumber(value.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli() - 3600000l);//返回毫秒值  会多一小时，原因不明
//            jgen.writeString(dateFormatter.format(value));//返回字符串
        }
    }

    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            log.info("将LocalDateTime {} 转换为毫秒值 {}", value.format(dateTimeFormatter), value.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
            jgen.writeNumber(value.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());//返回毫秒值
//            jgen.writeString(dateTimeFormatter.format(value));//返回字符串
        }

    }

    static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            log.info("将LocalTime {} 转换为毫秒值 {}", value.format(timeFormatter), value.atDate(LocalDate.now()).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
            jgen.writeNumber(value.atDate(LocalDate.now()).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());//返回毫秒值
//            jgen.writeString(timeFormatter.format(value));//返回字符串
        }
    }

}

