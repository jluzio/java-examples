package com.example.wiremock.playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Slf4j
class JsonStubMappingTest {

  private final ResourceLoader loader = new DefaultResourceLoader();

  @Test
  void testDefault() throws Exception {
    String location = "classpath:/mappings/request-matching/matching-json-path-absent.json";
    Resource resource = loader.getResource(location);
    String json = IOUtils.toString(resource.getURL(), StandardCharsets.UTF_8);
    StubMapping stubMapping = Json.read(json, StubMapping.class);
    assertThat(stubMapping)
        .isNotNull();
    log.info("stubMapping: {}", stubMapping);
  }

  @Test
  void testAlternative() throws Exception {
    String location = "classpath:/mappings/request-matching/matching-json-path-absent-alt.json";
    Resource resource = loader.getResource(location);
    String json = IOUtils.toString(resource.getURL(), StandardCharsets.UTF_8);
    StubMapping stubMapping = Json.read(json, StubMapping.class);
    assertThat(stubMapping)
        .isNotNull();
    log.info("stubMapping: {}", stubMapping);
  }

}
