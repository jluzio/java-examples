package com.example.java.playground.features.util;

import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class LocaleTest {

  @Test
  void locales() {
    Arrays.stream(Locale.getAvailableLocales()).forEach(locale -> {
      log.info(Strings.repeat("-", 80));
      log.info("locale: {}", locale);
      log.info("country: {}", locale.getCountry());
      try {
        log.info("iso3Country: {}", locale.getISO3Country());
      } catch (MissingResourceException e) {
        log.info("iso3Country: missing :: {}", e.getMessage());
      }
      log.info("language: {}", locale.getLanguage());
    });
  }

}
