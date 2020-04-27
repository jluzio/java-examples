package com.example.java.playground.json.model;

import com.example.java.playground.AbstractTest;
import com.example.java.playground.xml.model.DataItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class ModelTest extends AbstractTest {
    @Test
    void test() throws JsonProcessingException {
        var dataItem = DataItem.builder()
                .id("id-1")
                .description("desc")
                .numberValue(123)
                .localDateTime(LocalDateTime.now().plusHours(3))
                .references(List.of("ref-1", "ref-2"))
                .build();

        var mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var dataAsString = mapper.writeValueAsString(dataItem);

        log.info("data{}{}", System.lineSeparator(), dataAsString);
    }
}
