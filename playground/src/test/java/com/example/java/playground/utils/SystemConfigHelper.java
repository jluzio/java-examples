package com.example.java.playground.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemConfigHelper {

  public static final char ENVIRONMENT_VAR_SEPARATOR = '_';
  private static final Function<String, String> envVarFormatConverter = CaseFormat.LOWER_CAMEL
      .converterTo(CaseFormat.UPPER_UNDERSCORE)
      .andThen(v -> CharMatcher.forPredicate(Character::isLetterOrDigit).negate()
          .replaceFrom(v, ENVIRONMENT_VAR_SEPARATOR));

  public String convertToEnvironmentConfigKey(String key) {
    return envVarFormatConverter.apply(key);
  }

}
