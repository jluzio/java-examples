package com.example.java.playground.lib.mapper.mapstruct;

import org.apache.commons.lang3.StringUtils;
import org.graalvm.collections.Pair;
import org.springframework.stereotype.Component;

@Component
public class PersonNamesMapper {

  public Pair<String, String> names(String fullName) {
    return Pair.create(
        StringUtils.substringBefore(fullName, " "),
        StringUtils.substringAfter(fullName, " "));
  }
}
