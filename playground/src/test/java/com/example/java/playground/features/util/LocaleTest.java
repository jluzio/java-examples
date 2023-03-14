package com.example.java.playground.features.util;

import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Locale;
import java.util.Locale.IsoCountryCode;
import java.util.MissingResourceException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class LocaleTest {

  @Test
  void localeInfo_getAvailableLocales() {
    Arrays.stream(Locale.getAvailableLocales()).forEach(this::localeInfo);
  }

  @Test
  void localeInfo_getISOCountries() {
    var isoCountries = Locale.getISOCountries(IsoCountryCode.PART1_ALPHA2);
    Arrays.stream(Locale.getAvailableLocales()).forEach(locale -> {
      if (isoCountries.contains(locale.getCountry())) {
        localeInfo(locale);
      }
    });
  }

  @Test
  void localeInfo_customLocale() {
    var customLocale = Locale.of("PT", "PT");
    localeInfo(customLocale);
  }

  @Test
  void getISOCountries() {
    log.info("{}", Locale.getISOCountries(IsoCountryCode.PART1_ALPHA2));
    log.info("{}", Locale.getISOCountries(IsoCountryCode.PART1_ALPHA3));
    log.info("{}", Locale.getISOCountries(IsoCountryCode.PART3));
  }

  void localeInfo(Locale locale) {
    log.info(Strings.repeat("-", 80));
    log.info("locale: {}", locale);
    log.info("country: {}", locale.getCountry());
    try {
      log.info("iso3Country: {}", locale.getISO3Country());
    } catch (MissingResourceException e) {
      log.info("iso3Country: missing :: {}", e.getMessage());
    }
    log.info("language: {}", locale.getLanguage());
  }

}
