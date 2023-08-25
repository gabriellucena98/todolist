package com.GB.todolist.mapper;

import com.GB.todolist.exception.SerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class JSONMapper {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    public static String objectToJSON(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

            objectMapper.registerModule(javaTimeModule);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Erro no processo de serialização do objeto!");
            throw new SerializationException("Erro ao serializar o objeto");
        }
    }
}
