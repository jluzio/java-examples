package com.example.java.playground.lib.io.xml;

import jakarta.xml.bind.JAXB;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ModelTest {

  @Test
  void test() {
    var dataItem = DataItem.builder()
        .id("id-1")
        .description("desc")
        .numberValue(123)
        .localDateTime(LocalDateTime.now().plusHours(3))
        .references(List.of("ref-1", "ref-2"))
        .build();

    var writer = new StringWriter();
    JAXB.marshal(dataItem, writer);
    var dataAsString = writer.toString();

    log.info("data{}{}", System.lineSeparator(), dataAsString);
  }
}
