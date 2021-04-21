package com.example.java.playground.commons.text;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class WordWrapTest {

  @Test
  void test() {
    String repeatText = "0123456789 ";
    String fullText = Strings.repeat(repeatText, 10);

    assertThat(WordUtils.wrap(fullText, 25))
        .isEqualTo(
            """
                0123456789 0123456789
                0123456789 0123456789
                0123456789 0123456789
                0123456789 0123456789
                0123456789 0123456789\040"""
                .replaceAll("\n", System.lineSeparator())
        );
    log.info(WordUtils.wrap(fullText, 25));
  }

}
