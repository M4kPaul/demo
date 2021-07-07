package dev.m4k.demo.dto;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {

  private String id, username;
  private List<String> roles;
}
