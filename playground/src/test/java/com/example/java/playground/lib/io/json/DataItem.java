package com.example.java.playground.lib.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataItem {

  private String id;
  private String description;
  private int numberValue;
  private LocalDateTime localDateTime;
  @JsonProperty("ref")
  private List<String> references;
}
