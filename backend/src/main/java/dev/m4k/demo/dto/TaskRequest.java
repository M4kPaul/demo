package dev.m4k.demo.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

  private Long id;

  @NotBlank
  private String description;

  @NotBlank
  private boolean isDone;
}
