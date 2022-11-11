package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class PatternTest {

  @Test
  void named_groups() {
    final String FILENAME_TYPE_CLASSIFIER_PATTERN = "(?<type>[\\w|_]+)(-(?<classifier>[\\w|_]+))?\\.(?<extension>.+)";

    var matcher1 = Pattern.compile(FILENAME_TYPE_CLASSIFIER_PATTERN).matcher("type1-classifier1.pdf");
    assertThat(matcher1.find()).isTrue();
    assertThat(matcher1.group("type")).isEqualTo("type1");
    assertThat(matcher1.group("classifier")).isEqualTo("classifier1");
    assertThat(matcher1.group("extension")).isEqualTo("pdf");
    assertThat(matcher1.group(1)).isEqualTo("type1");
    assertThat(matcher1.group(3)).isEqualTo("classifier1");
    assertThat(matcher1.group(4)).isEqualTo("pdf");
  }

}
