package com.example.java.playground.lib.commons.tika;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

class DetectorTest {

  @Test
  void text_stream() throws IOException {
    ByteArrayResource resource = new ByteArrayResource("some text".getBytes());
    try (InputStream inputStream = resource.getInputStream()) {
      Detector detector = new DefaultDetector();
      MediaType mediaType = detector.detect(inputStream, new Metadata());
      assertThat(mediaType).isEqualTo(MediaType.TEXT_PLAIN);
    }
  }

}
