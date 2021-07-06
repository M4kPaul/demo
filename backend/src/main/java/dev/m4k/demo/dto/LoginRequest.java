package dev.m4k.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  @Size(max = 64)
  private String username;

  @NotBlank
  private String password;
}
