package com.example.java.playground.lib.validator.jsr380;

import com.example.java.playground.lib.validator.jsr380.ValidationGroups.OnUpdate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Person {

  private String id;
  @NotEmpty
  private String name;
  @Min(1)
  @Max(110)
  private Integer age;
  @NotEmpty(groups = OnUpdate.class)
  private String email;

  // Property validation
  @Size(max = 50)
  public String getNameAndAge() {
    return "%s:%s".formatted(name, age);
  }

}
