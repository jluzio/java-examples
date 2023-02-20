package com.example.java.playground.features.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Strings;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Base64.Decoder;
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
    log.info("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void mime_coding() {
    String encodedData = Base64.getMimeEncoder().encodeToString(INITIAL_DATA.getBytes());
    log.info("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getMimeDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void url_coding() {
    String encodedData = Base64.getUrlEncoder().encodeToString(INITIAL_DATA.getBytes());
    log.info("encodedData: {}", encodedData);
    assertThat(encodedData).isNotNull();
    String decodedData = new String(Base64.getUrlDecoder().decode(encodedData));
    assertThat(decodedData).isEqualTo(INITIAL_DATA);
  }

  @Test
  void encodings() throws Exception {
    byte[] fileBytes = Files.readAllBytes(imageResource.getFile().toPath());
    log.info("fileBytesAsString: {}", new String(fileBytes));
    var encodedData = Base64.getEncoder().encodeToString(fileBytes);
    log.info("encodedData: {}", encodedData);
    var encodedDataMime = Base64.getMimeEncoder().encodeToString(fileBytes);
    log.info("encodedDataMime: {}", encodedDataMime);
    var encodedDataUrl = Base64.getUrlEncoder().encodeToString(fileBytes);
    log.info("encodedDataUrl: {}", encodedDataUrl);
    log.info("comparisons: {}", Map.of(
        "eq encodedDataMime", Objects.equals(encodedData, encodedDataMime),
        "eq encodedDataUrl", Objects.equals(encodedData, encodedDataUrl)
    ));
  }

  @Test
  void test_data_with_header() {
    String data = "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAYAAADED76LAAAAr0lEQVQYV22PIQ6FMBBEpwrZpBeo4QYkCK5AgsJgEJVcAYfmEhg4AZYT9AgEiWuTCgSpWNImfMNftcnM7L5h8zzTuq6o6xp5nuOd+75hrQXruo6UUliWBeM4Rt05h33fIYQAq6qKsiyDMQbDMERDENM0xXVdYH3fU1EU2LYNTdNEQxA55zjPE6xtW5qmKabLsvwapJQUAL33/18cx0GB9j37gdRaU6BNkuRXMSxvzQf0FFu5VGl5DQAAAABJRU5ErkJggg==";
    String dataWithHeader = "data:image/png;base64," + data;
    testDecoder(Base64.getDecoder(), "Base64.getDecoder()",
        data.getBytes(), dataWithHeader.getBytes());
    testDecoder(Base64.getUrlDecoder(), "Base64.getUrlDecoder()",
        data.getBytes(), dataWithHeader.getBytes());
    testDecoder(Base64.getMimeDecoder(), "Base64.getMimeDecoder()",
        data.getBytes(), dataWithHeader.getBytes());
  }

  private void testDecoder(Decoder decoder, String decoderId, byte[] data, byte[] dataWithHeader) {
    try {
      byte[] decodedData = decoder.decode(data);
      log.info("decodedData.length = {}", decodedData.length);
    } catch (Exception e) {
      log.info("Does not decode basic data with {}", decoderId);
    }
    try {
      byte[] decodedDataWithHeader = decoder.decode(dataWithHeader);
      log.info("decodedDataWithHeader.length = {}", decodedDataWithHeader.length);
    } catch (Exception e) {
      log.info("Does not decode data with header with {}", decoderId);
    }
  }

}
