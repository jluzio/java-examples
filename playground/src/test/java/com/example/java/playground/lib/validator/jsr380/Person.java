package com.example.java.playground.lib.validator.jsr380;

import com.example.java.playground.lib.validator.jsr380.ValidationGroups.OnUpdate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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

}
