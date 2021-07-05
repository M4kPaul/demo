package dev.m4k.demo.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {

  private String accessToken;
  private UserInfo user;
}
