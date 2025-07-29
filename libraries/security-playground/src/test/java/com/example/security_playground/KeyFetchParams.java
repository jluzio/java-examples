package com.example.security_playground;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.vavr.control.Try;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import lombok.Builder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Builder
@JsonDeserialize(builder = KeyFetchParams.KeyFetchParamsBuilder.class)
public record KeyFetchParams(
    String storeType, char[] storePass, Try<InputStream> storeInputStream,
    String alias, char[] keyPass
) {

  @JsonPOJOBuilder(withPrefix = "")
  public static class KeyFetchParamsBuilder {

    public static byte[] decodeBase64(String input) {
      return Base64.getDecoder().decode(input);
    }

    public static char[] decodeBase64ToCharArray(String input) {
      byte[] decodedValue = decodeBase64(input);
      return new String(decodedValue).toCharArray();
    }

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();


    public KeyFetchParamsBuilder storeFile(String file) {
      return storeResource(file);
    }

    public KeyFetchParamsBuilder storeResource(String location) {
      return storeInputStream(Try.of(() -> resourceLoader.getResource(location).getInputStream()));
    }

    public KeyFetchParamsBuilder storeBase64(String base64) {
      return storeInputStream(Try.of(() -> {
        byte[] decodedValue = decodeBase64(base64);
        return new ByteArrayInputStream(decodedValue);
      }));
    }

    public KeyFetchParamsBuilder storePassBase64(String base64) {
      return storePass(decodeBase64ToCharArray(base64));
    }

    public KeyFetchParamsBuilder keyPassBase64(String base64) {
      return keyPass(decodeBase64ToCharArray(base64));
    }
  }
}
