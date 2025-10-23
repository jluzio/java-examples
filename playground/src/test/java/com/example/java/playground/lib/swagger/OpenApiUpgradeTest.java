package com.example.java.playground.lib.swagger;

import static org.assertj.core.api.Assertions.fail;

import io.swagger.v3.oas.models.OpenAPI;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
class OpenApiUpgradeTest {

  @Value("${test.input:classpath:test-data/OpenApiUpgrade-input.yml}")
  Resource inputResource;
  @Value("${test.output:file:build/OpenApiUpgrade-output.yml}")
  Resource outputResource;

  @Test
  void upgrade() {
    var parser = new io.swagger.v3.parser.OpenAPIV3Parser();
    var objectMapper = io.swagger.v3.core.util.ObjectMapperFactory.createYaml();

    try {
      OpenAPI openAPI = parser.read(inputResource.getFile().getAbsolutePath());
      objectMapper.writeValue(outputResource.getFile(), openAPI);
    } catch (IOException e) {
      fail(e);
    }
  }
}
