package com.example.java.playground.xml.model;

import com.example.java.playground.AbstractTest;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.xml.bind.JAXB;
import org.junit.jupiter.api.Test;

public class ModelTest extends AbstractTest {

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
