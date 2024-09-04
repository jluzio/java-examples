package com.example.java.playground.lib.mapper.mapstruct;

import jakarta.validation.constraints.Email;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class DefaultValidations {

  @Named("validateEmail")
  public String validateEmail(@Email String email) {
    return email;
  }
}
