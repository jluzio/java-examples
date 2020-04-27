package com.example.java.playground.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
