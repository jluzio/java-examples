package com.example.java.playground.xml.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class DataItem {

  private String id;
  private String description;
  private int numberValue;
  private LocalDateTime localDateTime;
  @XmlElement(name = "reference")
  private List<String> references;
}
