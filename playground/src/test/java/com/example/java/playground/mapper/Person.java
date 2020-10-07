package com.example.java.playground.mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
  private String firstName;
  private String surname;
  private String email;

}
