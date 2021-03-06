package com.example.java.playground.lib.mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  private String username;
  private String fullName;
  private String email;

}
