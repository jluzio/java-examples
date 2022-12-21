package com.example.java.playground.features.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Strings;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Slf4j
@SpringBootTest
class Base64Test {

  public static final String INITIAL_DATA = Strings.repeat("0123456789", 10);

  @Configuration
  static class Config {

  }

  @Value("classpath:features/image.svg")
  Resource imageResource;

  @Test
  void basic_coding() {
    String encodedData = Base64.getEncoder().encodeToString(INITIAL_DATA.getBytes());
    log.debug("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void mime_coding() {
    String encodedData = Base64.getMimeEncoder().encodeToString(INITIAL_DATA.getBytes());
    log.debug("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getMimeDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void url_coding() {
    String encodedData = Base64.getUrlEncoder().encodeToString(INITIAL_DATA.getBytes());
    log.debug("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getUrlDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void encodings() throws Exception {
    byte[] fileBytes = Files.readAllBytes(imageResource.getFile().toPath());
    log.debug("fileBytesAsString: {}", new String(fileBytes));
    var encodedData = Base64.getEncoder().encodeToString(fileBytes);
    log.debug("encodedData: {}", encodedData);
    var encodedDataMime = Base64.getMimeEncoder().encodeToString(fileBytes);
    log.debug("encodedDataMime: {}", encodedDataMime);
    var encodedDataUrl = Base64.getUrlEncoder().encodeToString(fileBytes);
    log.debug("encodedDataUrl: {}", encodedDataUrl);
    log.debug("comparisons: {}", Map.of(
        "eq encodedDataMime", Objects.equals(encodedData, encodedDataMime),
        "eq encodedDataUrl", Objects.equals(encodedData, encodedDataUrl)
    ));
  }

}
